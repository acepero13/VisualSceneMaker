package de.dfki.vsm.editor.action;

import de.dfki.vsm.editor.Edge.TYPE;
import static de.dfki.vsm.editor.Edge.TYPE.CEDGE;
import static de.dfki.vsm.editor.Edge.TYPE.EEDGE;
import static de.dfki.vsm.editor.Edge.TYPE.IEDGE;
import static de.dfki.vsm.editor.Edge.TYPE.PEDGE;
import static de.dfki.vsm.editor.Edge.TYPE.TEDGE;
import de.dfki.vsm.editor.Editor;
import de.dfki.vsm.editor.Node.Flavour;
import de.dfki.vsm.editor.SceneFlowEditor;
import de.dfki.vsm.editor.WorkSpace;
import de.dfki.vsm.editor.util.grid.AStarEdgeFinder;
import de.dfki.vsm.editor.util.grid.BezierFit;
import de.dfki.vsm.editor.util.grid.BezierPoint;
import de.dfki.vsm.editor.util.grid.GridConstants;
import de.dfki.vsm.editor.util.grid.GridRectangle;
import de.dfki.vsm.editor.util.grid.pathfinding.Path;
import de.dfki.vsm.model.sceneflow.CEdge;
import de.dfki.vsm.model.sceneflow.EEdge;
import de.dfki.vsm.model.sceneflow.Edge;
import de.dfki.vsm.model.sceneflow.FEdge;
import de.dfki.vsm.model.sceneflow.IEdge;
import de.dfki.vsm.model.sceneflow.PEdge;
import de.dfki.vsm.model.sceneflow.TEdge;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.undo.UndoManager;

/**
 * @author Gregor Mehlmann
 */
public abstract class EdgeAction extends EditorAction {

    protected UndoManager mUndoManager = null;
    protected SceneFlowEditor mSceneFlowPane = null;
    protected WorkSpace mWorkSpace = null;
    protected de.dfki.vsm.editor.Node mSourceGUINode = null;
    protected de.dfki.vsm.editor.Node mTargetGUINode = null;
    protected de.dfki.vsm.editor.Node mLastTargetGUINode = null;
    protected de.dfki.vsm.editor.Edge mGUIEdge = null;
    protected Edge mDataEdge = null;
    protected TYPE mGUIEdgeType = null;
    protected Point mSourceGUINodeDockPoint = null;
    protected Point mTargetGUINodeDockPoint = null;
    protected Point mLastTargetGUINodeDockPoint = null;
    protected GridRectangle gridSource = null;
    protected GridRectangle gridDestination = null;

    public void create() {
        mDataEdge.setTargetNode(mTargetGUINode.getDataNode());
        mDataEdge.setSourceNode(mSourceGUINode.getDataNode());
        mDataEdge.setTarget(mDataEdge.getTargetNode().getId());

        switch (mGUIEdgeType) {
            case EEDGE:
                mSourceGUINode.getDataNode().setDedge(mDataEdge);
                break;
            case FEDGE:
                mSourceGUINode.getDataNode().addFEdge((FEdge) mDataEdge);
                break;
            case TEDGE:
                mSourceGUINode.getDataNode().setDedge(mDataEdge);
                break;
            case CEDGE:
                mSourceGUINode.getDataNode().addCEdge((CEdge) mDataEdge);
                break;
            case PEDGE:
                mSourceGUINode.getDataNode().addPEdge((PEdge) mDataEdge);
                break;
            case IEDGE:
                mSourceGUINode.getDataNode().addIEdge((IEdge) mDataEdge);
                break;
        }

        // Revalidate data node and graphical node types
        switch (mSourceGUINode.getDataNode().getFlavour()) {
            case NONE:
                Edge dedge = mSourceGUINode.getDataNode().getDedge();
                if (dedge instanceof EEdge) {
                    mSourceGUINode.setFlavour(Flavour.ENode);
                } else if (dedge instanceof TEdge) {
                    mSourceGUINode.setFlavour(Flavour.TNode);
                } else {
                    mSourceGUINode.setFlavour(Flavour.None);
                }
                break;
            case PNODE:
                mSourceGUINode.setFlavour(Flavour.PNode);
                break;
            case FNODE:
                mSourceGUINode.setFlavour(Flavour.FNode);
                break;
            case CNODE:
                mSourceGUINode.setFlavour(Flavour.CNode);
                break;
            case INODE:
                mSourceGUINode.setFlavour(Flavour.INode);
                break;
        }

    // Connect GUI Edge to Source GUI node
        // Connect GUI Edge to Target GUI node
        // TODO: Recompute the appearance of the source GUI node
        if (mGUIEdge == null) {
            mGUIEdge = new de.dfki.vsm.editor.Edge(mWorkSpace, mDataEdge, mGUIEdgeType, mSourceGUINode, mTargetGUINode);
        } else {
            if (mSourceGUINode.equals(mTargetGUINode)) {
                // same nodes
                mSourceGUINodeDockPoint = mSourceGUINode.connectEdgeAtSourceNode(mGUIEdge, mSourceGUINodeDockPoint);
                mTargetGUINodeDockPoint = mTargetGUINode.connectSelfPointingEdge(mGUIEdge, mTargetGUINodeDockPoint);
            } else {
                // different nodes
                mSourceGUINodeDockPoint = mSourceGUINode.connectEdgeAtSourceNode(mGUIEdge, mSourceGUINodeDockPoint);
                mTargetGUINodeDockPoint = mTargetGUINode.connectEdgetAtTargetNode(mGUIEdge, new Point(50, 50));
            }
        }
        //mSourceGUINode.update();
        Editor.getInstance().update();
//        mWorkSpace.add(mGUIEdge);
//        mWorkSpace.revalidate();
//        mWorkSpace.repaint();
        setEdgePath();
    }
    
    public void setEdgePath() {
        //if weight of grid intersection is larger than max weight threshold, rerouting needed.
        if(isReroutingNeeded()) {
            AStarEdgeFinder aStarPath = new AStarEdgeFinder(mWorkSpace.mGridManager.getmTransitionArea());
            Path alternatePath = aStarPath.getPath(gridSource.getColumnIndex(), gridSource.getRowIndex(), 
                    gridDestination.getColumnIndex(), gridDestination.getRowIndex());
            
            aStarPath.printPath(gridSource.getColumnIndex(), gridSource.getRowIndex(), 
                    gridDestination.getColumnIndex(), gridDestination.getRowIndex());
            
            // Calculate the control point of the bezier curve that should be made
            ArrayList<BezierPoint> pathPoints = new ArrayList<BezierPoint>();
            int deviationX = 0;
            int deviationY = 0;
            for(int i = 0; i < alternatePath.getLength(); i++) {
                BezierPoint point = new BezierPoint(
                    mWorkSpace.mGridManager.getmTransitionArea()[alternatePath.getY(i)][alternatePath.getX(i)].getX(),
                    mWorkSpace.mGridManager.getmTransitionArea()[alternatePath.getY(i)][alternatePath.getX(i)].getY());
                pathPoints.add(point);
                if(i < (i-1)) {
                    deviationX += (alternatePath.getX(i+1) - alternatePath.getX(i));
                    deviationY += (alternatePath.getY(i+1) - alternatePath.getY(i));
                }
            }
            
            BezierFit bezierFit = new BezierFit();
            BezierPoint[] controlPoint = bezierFit.bestFit(pathPoints);
            
            //Check direction from target node, this means a vertical movement
            if(Math.abs(deviationY) > Math.abs(deviationX)) {
                int deviationVert = 0;
                for(int i = 0; i < alternatePath.getLength()/3; i++) {
                    deviationVert += (alternatePath.getX(i+1) - alternatePath.getX(i));
                }
                
                //This means the direction is downward
                if(deviationVert > 0) {
                    mSourceGUINodeDockPoint = mSourceGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mSourceGUINode.getX() + 50, mSourceGUINode.getY() + 100));
                    mTargetGUINodeDockPoint = mTargetGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mTargetGUINode.getX() + 50, mTargetGUINode.getY() + 100));
                }   
                //This means the direction is upward
                else {
                    mSourceGUINodeDockPoint = mSourceGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mSourceGUINode.getX() + 50, mSourceGUINode.getY())); 
                    mTargetGUINodeDockPoint = mTargetGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mTargetGUINode.getX() + 50, mTargetGUINode.getY()));  
                }
            }
            //Check direction from target node, this means a horizontal movement
            else {
                int deviationHorz = 0;
                for(int i = 0; i < alternatePath.getLength()/3; i++) {
                    deviationHorz += (alternatePath.getY(i+1) - alternatePath.getY(i));
                }
                //This means the direction is to the right
                if(deviationHorz > 0) {
                    mSourceGUINodeDockPoint = mSourceGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mSourceGUINode.getX() + 100, mSourceGUINode.getY() + 50));
                    mTargetGUINodeDockPoint = mTargetGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mTargetGUINode.getX() + 100, mTargetGUINode.getY() + 50));
                }
                //This means the direction is to the left
                else {
                    mSourceGUINodeDockPoint = mSourceGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mSourceGUINode.getX(), mSourceGUINode.getY() + 50));
                    mTargetGUINodeDockPoint = mTargetGUINode.connectEdgeAtSourceNode(mGUIEdge, new Point(
                                                mTargetGUINode.getX(), mTargetGUINode.getY() + 50));
                }
            }
            
            //Manipulate the control point based on the BezierFit calculation
            mGUIEdge.mEg.mCCrtl1.x = (int) Math.round(controlPoint[1].getX());
            mGUIEdge.mEg.mCCrtl1.y = (int) Math.round(controlPoint[1].getY());
            
            mGUIEdge.mEg.mCCrtl2.x = (int) Math.round(controlPoint[2].getX());
            mGUIEdge.mEg.mCCrtl2.y = (int) Math.round(controlPoint[2].getY());
            
            getEdgeTotalWeight();
            setGridWeight();
            mWorkSpace.add(mGUIEdge);
            mWorkSpace.revalidate();
            mWorkSpace.repaint();
        }
        
        else {
            setGridWeight();
            mWorkSpace.add(mGUIEdge);
            mWorkSpace.revalidate();
            mWorkSpace.repaint();
        }
    }
    
    public void setGridWeight() {
        mWorkSpace.getGridManager().setEdgeWeight(mGUIEdge);
        mWorkSpace.getGridManager().setNodeWeight(mSourceGUINode);
        mWorkSpace.getGridManager().setNodeWeight(mTargetGUINode);
    }
    
    public boolean isReroutingNeeded() {
        return getEdgeTotalWeight() >= GridConstants.MAX_WEIGHT_THRESHOLD;
    }
    
    public int getEdgeTotalWeight() {
        int sumWeight = 0;
        
        //Determining the positioning of edge's anchor. False means source has
        //smaller coordinate than destination
        boolean anchorMode = false;
        if(mSourceGUINode.getX() >= mTargetGUINode.getX() ||
                mSourceGUINode.getY() >= mTargetGUINode.getY()) {
            anchorMode = true;
        }
        gridSource = null;
        gridDestination = null;
        
        for(GridRectangle[] gridParent : mWorkSpace.mGridManager.getmTransitionArea()) {
            for(GridRectangle gridRectangle : gridParent) {
                if(gridRectangle.isIntersectedbyNode(mSourceGUINode)) {
                    gridRectangle.setIntersectionType(GridRectangle.NODE_INTERSECTION);
                    sumWeight += gridRectangle.getWeight();
                    if(anchorMode) {
                        if(gridSource == null) {
                            gridSource = gridRectangle;
                        }
                    }
                    
                    else {
                        gridSource = gridRectangle;
                    }
                }

                if(gridRectangle.isIntersectedbyNode(mTargetGUINode)) {
                    gridRectangle.setIntersectionType(GridRectangle.NODE_INTERSECTION);
                    sumWeight += gridRectangle.getWeight();
                    if(anchorMode) {
                        gridDestination = gridRectangle;
                    }
                    
                    else {
                        if(gridDestination == null) {
                            gridDestination = gridRectangle;
                        }
                    }
                }
                
                if(gridRectangle.isIntersectByRectangle(mGUIEdge.mEg)) {
                    gridRectangle.setIntersectionType(GridRectangle.EDGE_INTERSECTION);
                    sumWeight += gridRectangle.getWeight();
                }
            }
        }
        System.out.println("Sum Weight is :" + sumWeight);
        return sumWeight;
    }

    public void deleteDeflected() {
        if (mSourceGUINode.equals(mTargetGUINode)) {
            mSourceGUINodeDockPoint = mSourceGUINode.disconnectEdge(mGUIEdge);
            //
            mLastTargetGUINodeDockPoint = mGUIEdge.mLastTargetNodeDockPoint;
        } else {
            mSourceGUINodeDockPoint = mSourceGUINode.disconnectEdge(mGUIEdge);
            mLastTargetGUINodeDockPoint = mGUIEdge.mLastTargetNodeDockPoint;
        }
        cleanUpData();
    }

    public void delete() {
        // Disconnect the GUI edge from the GUI nodes
        if (mSourceGUINode.equals(mTargetGUINode)) {
            mSourceGUINodeDockPoint = mSourceGUINode.disconnectEdge(mGUIEdge);
            mTargetGUINodeDockPoint = mTargetGUINode.disconnectSelfPointingEdge(mGUIEdge);
        } else {
            mSourceGUINodeDockPoint = mSourceGUINode.disconnectEdge(mGUIEdge);
            mTargetGUINodeDockPoint = mTargetGUINode.disconnectEdge(mGUIEdge);
        }
        cleanUpData();
    }

    private void cleanUpData() {
        // Disconnect the data edge from the source data node
        switch (mGUIEdgeType) {
            case EEDGE:
                mSourceGUINode.getDataNode().removeDEdge();
                break;
            case TEDGE:
                mSourceGUINode.getDataNode().removeDEdge();
                break;
            case CEDGE:
                mSourceGUINode.getDataNode().removeCEdge((CEdge) mDataEdge);
                break;
            case PEDGE:
                mSourceGUINode.getDataNode().removePEdge((PEdge) mDataEdge);
                break;
            case FEDGE:
                mSourceGUINode.getDataNode().removeFEdge((FEdge) mDataEdge);
                break;
            case IEDGE:
                mSourceGUINode.getDataNode().removeIEdge((IEdge) mDataEdge);
                break;
        }

        // Revalidate data node and graphical node types
        switch (mSourceGUINode.getDataNode().getFlavour()) {
            case NONE:
                Edge dedge = mSourceGUINode.getDataNode().getDedge();
                if (dedge instanceof EEdge) {
                    mSourceGUINode.setFlavour(Flavour.ENode);
                } else if (dedge instanceof TEdge) {
                    mSourceGUINode.setFlavour(Flavour.TNode);
                } else {
                    mSourceGUINode.setFlavour(Flavour.None);
                }
                break;
            case PNODE:
                mSourceGUINode.setFlavour(Flavour.PNode);
                break;
            case FNODE:
                mSourceGUINode.setFlavour(Flavour.FNode);
                break;
            case CNODE:
                mSourceGUINode.setFlavour(Flavour.CNode);
                break;
            case INODE:
                mSourceGUINode.setFlavour(Flavour.INode);
                break;
        }

    // Remove the GUI-Edge from the workspace and
        // update the source node appearance
        Editor.getInstance().update();
        mWorkSpace.remove(mGUIEdge);
        mWorkSpace.revalidate();
        mWorkSpace.repaint();
    }
}

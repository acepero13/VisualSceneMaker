package de.dfki.vsm.editor.dialog;

//~--- non-JDK imports --------------------------------------------------------

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import de.dfki.vsm.editor.CancelButton;
import de.dfki.vsm.editor.EditorInstance;
import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.editor.OKButton;
import de.dfki.vsm.editor.util.HintTextField;
import de.dfki.vsm.model.sceneflow.SceneFlow;
import de.dfki.vsm.model.sceneflow.command.expression.Expression;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.Bool;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.Float;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.Int;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.List;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.String;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.Struct;
import de.dfki.vsm.model.sceneflow.definition.VarDef;
import de.dfki.vsm.runtime.RunTimeInstance;
import de.dfki.vsm.sfsl.parser._SFSLParser_;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Dimension;

import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 * @author Not me
 */
public class MonitorDialog extends JDialog {
    private static MonitorDialog sSingeltonInstance = null;
    private JPanel               mMainPanel;
    private JPanel               mButtonsPanel;
    private CancelButton         mCancelButton;
    private OKButton             mOkButton;
    private JPanel               mWorkPanel;
    //private JList                mVariableList;
    private JTable               mVariableTable;
    private HintTextField           mInputTextField;
    private JScrollPane          mVariableScrollPane;
    private Vector<VarDef>       mVarDefListData;
    private static EditorProject       mEditorProject;

    private MonitorDialog() {
        super(EditorInstance.getInstance(), "Run Monitor", true);
        EditorInstance.getInstance().addEscapeListener(this);
        mEditorProject = EditorInstance.getInstance().getSelectedProjectEditor().getEditorProject();
        initComponents();
    }

    public static MonitorDialog getInstance() {
        mEditorProject = EditorInstance.getInstance().getSelectedProjectEditor().getEditorProject();
        if (sSingeltonInstance == null) {
            sSingeltonInstance = new MonitorDialog();
        }
        return sSingeltonInstance;
    }

    public void init(SceneFlow sceneFlow) {}
    
    public void resetView()
    {
        remove(mMainPanel);
        mMainPanel = new JPanel(null);
        initWorkPanel();
        mMainPanel.add(mButtonsPanel);
        mMainPanel.add(mWorkPanel);
        add(mMainPanel);
    }
    private void initWorkPanel() {
        mWorkPanel = new JPanel(null);
        mWorkPanel.setBounds(0, 0, 400, 400);
        mWorkPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        initVariableList();
        mVariableScrollPane = new JScrollPane(mVariableTable);
        mVariableScrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
        mVariableScrollPane.setBounds(20, 10, 360, 300);
        mInputTextField = new HintTextField("Enter new value");
        mInputTextField.setBounds(20, 320, 360, 30);
        mWorkPanel.add(mVariableScrollPane);
        if(!RunTimeInstance.getInstance().isRunning(mEditorProject))
        {
            mInputTextField.setEnabled(false);
        }
        mWorkPanel.add(mInputTextField);
    }

    private boolean process() {
        int selectedIndex = mVariableTable.getSelectedRow();
        
        if (selectedIndex != -1) {
            VarDef           varDef      = mVarDefListData.get(selectedIndex);
            java.lang.String inputString = mInputTextField.getText().trim();

            try {
                _SFSLParser_.parseResultType = _SFSLParser_.EXP;
                _SFSLParser_.run(inputString);

                Expression exp = _SFSLParser_.expResult;

                if ((exp != null) &&!_SFSLParser_.errorFlag) {
                    if (exp instanceof Bool) {
                        return RunTimeInstance.getInstance().setVariable(mEditorProject, varDef.getName(), ((Bool) exp).getValue());
                    } else if (exp instanceof Int) {
                        return RunTimeInstance.getInstance().setVariable(mEditorProject, varDef.getName(), ((Int) exp).getValue());
                    } else if (exp instanceof Float) {
                        return RunTimeInstance.getInstance().setVariable(mEditorProject, varDef.getName(),
                                ((Float) exp).getValue());
                    } else if (exp instanceof String) {
                        return RunTimeInstance.getInstance().setVariable(mEditorProject, varDef.getName(),
                                ((String) exp).getValue());
                    } else if (exp instanceof List) {
                        //return RunTimeInstance.getInstance().setVariable(mEditorProject,  varDef.getName(), exp);

                        // Evaluator eval = interpreter.getEvaluator();
                        // Environment env = interpreter.getEnvironment();
                        // return RunTime.getInstance().setVariable(mSceneFlow, varDef.getName(), eval.evaluate(exp, env));
                    } else if (exp instanceof Struct) {
                        //return RunTimeInstance.getInstance().setVariable(mEditorProject,  varDef.getName(), exp);
                    } else {
                        //return RunTimeInstance.getInstance().setVariable(mEditorProject,  varDef.getName(), exp);
                    }
                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

        return false;
    }

    private void initComponents() {
        initWorkPanel();
        mButtonsPanel = new JPanel(null);
        mButtonsPanel.setBounds(0, 400, 400, 40);
        mOkButton = new OKButton();
        mOkButton.setBounds(205, 0, 125, 30);
        mOkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boolean varAssigned = process();
                dispose();
            }
        });
        mCancelButton = new CancelButton();
        mCancelButton.setBounds(50, 0, 125, 30);
        mCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
            }
        });
        mButtonsPanel.add(mOkButton);
        mButtonsPanel.add(mCancelButton);
        mMainPanel = new JPanel(null);
        mMainPanel.add(mButtonsPanel);
        mMainPanel.add(mWorkPanel);
        add(mMainPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setSize(new Dimension(400 + getInsets().left + getInsets().right, 440 + getInsets().top + getInsets().bottom));
        setLocation(getParent().getLocation().x + (getParent().getWidth() - getWidth()) / 2,
                    getParent().getLocation().y + (getParent().getHeight() - getHeight()) / 2);
        mOkButton.requestFocus();
    }

    private void initVariableList() {
        mVarDefListData = mEditorProject.getSceneFlow().getCopyOfVarDefList();
        java.lang.String listofVars[][] = new java.lang.String[mVarDefListData.size()][2];
        java.lang.String[] listOfColumns = {"Variable", "Value"};
        int counter = 0;
        for (VarDef varDef : mVarDefListData) {
            java.lang.String[] tempString = { varDef.getName(), varDef.getExp().toString()};
            listofVars[counter] =  tempString;
            counter++;
        }
        mVariableTable = new JTable(new DefaultTableModel(listofVars, listOfColumns)
        {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        });
    }
  }

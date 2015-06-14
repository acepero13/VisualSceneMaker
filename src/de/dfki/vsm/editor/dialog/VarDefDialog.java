package de.dfki.vsm.editor.dialog;

//~--- non-JDK imports --------------------------------------------------------

import de.dfki.vsm.editor.CancelButton;
import de.dfki.vsm.editor.Editor;
import de.dfki.vsm.editor.OKButton;
import de.dfki.vsm.model.sceneflow.Node;
import de.dfki.vsm.model.sceneflow.SuperNode;
import de.dfki.vsm.model.sceneflow.command.expression.Expression;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.Bool;
import de.dfki.vsm.model.sceneflow.definition.VarDef;
import de.dfki.vsm.model.sceneflow.definition.type.TypeDef;
import de.dfki.vsm.util.ios.ResourceLoader;
import java.awt.Dimension;

//~--- JDK imports ------------------------------------------------------------

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Gregor Mehlmann
 */
public class VarDefDialog extends Dialog {
    private final Node   mNode;
    private final VarDef mVarDef;

    // GUI Components
    private JLabel               mNameLabel;
    private JLabel               mTypeDefLabel;
    private JLabel               mExpLabel;
    private JTextField           mNameTextField;
    private JTextField           mExpTextField;
    private JButton              mAddExpButton;
    private JComboBox            mTypeDefComboBox;
    private DefaultComboBoxModel mTypeDefComboBoxModel;
    private OKButton             mOkButton;
    private CancelButton         mCancelButton;
    private Dimension            labelSize = new Dimension(75, 30);
    private Dimension            textFielSize = new Dimension(250, 30);
    public VarDefDialog(Node node, VarDef varDef) {
        super(Editor.getInstance(), "Create/Modify Variable Definition", true);
        mNode = node;

        if (varDef != null) {
            mVarDef = varDef.getCopy();
        } else {
            mVarDef = new VarDef("newVar", "Bool", new Bool(true));
        }

        initComponents();
        fillComponents();
    }

    private void initComponents() {

        //
        mNameLabel     = new JLabel("Name:");
        mNameTextField = new JTextField(mVarDef.getName());
        sanitizeComponent(mNameLabel, labelSize);
        sanitizeComponent(mNameTextField, textFielSize);
        //Name box
        Box nameBox = Box.createHorizontalBox();
        nameBox.add(mNameLabel);
        nameBox.add(Box.createHorizontalStrut(10));
        nameBox.add(mNameTextField);
        
        //
        mTypeDefLabel         = new JLabel("Type:");
        mTypeDefComboBoxModel = new DefaultComboBoxModel();
        mTypeDefComboBox      = new JComboBox(mTypeDefComboBoxModel);
        sanitizeComponent(mTypeDefLabel, labelSize);
        sanitizeComponent(mTypeDefComboBox, textFielSize);
        //Exp box
        Box typeDefBox = Box.createHorizontalBox();
        typeDefBox.add(mTypeDefLabel);
        typeDefBox.add(Box.createHorizontalStrut(10));
        typeDefBox.add(mTypeDefComboBox);
        
        //
        mExpLabel     = new JLabel("Value:");
        mExpTextField = new JTextField();
        mExpTextField.setEditable(false);
        mAddExpButton = new JButton(ResourceLoader.loadImageIcon("/res/img/search_icon.png"));
        mAddExpButton.setRolloverIcon(ResourceLoader.loadImageIcon("/res/img/search_icon_blue.png"));
        mAddExpButton.setOpaque(false);
        mAddExpButton.setContentAreaFilled(false);
        mAddExpButton.setFocusable(false);
        mAddExpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectExp();
            }
        });
        sanitizeComponent(mExpLabel, labelSize);
        sanitizeComponent(mExpTextField, new Dimension(200, 30));
        //Exp box
        Box expBox = Box.createHorizontalBox();
        expBox.add(mExpLabel);
        expBox.add(Box.createHorizontalStrut(10));
        expBox.add(mExpTextField);
//        expBox.add(Box.createHorizontalStrut(10));
        expBox.add(mAddExpButton);
        
        //
        mOkButton = new OKButton();
        mOkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okActionPerformed();
            }
        });

        //
        mCancelButton = new CancelButton();
        mCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelActionPerformed();
            }
        });
        //Button box
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(mCancelButton);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(mOkButton);
        
        
        Box finalBox = Box.createVerticalBox();
        finalBox.add(nameBox);
        finalBox.add(Box.createVerticalStrut(15));
        finalBox.add(typeDefBox);
        finalBox.add(Box.createVerticalStrut(15));
        finalBox.add(expBox);
        finalBox.add(Box.createVerticalStrut(25));
        finalBox.add(buttonBox);
        addComponent(finalBox, 10, 20, 400, 280);
        packComponents(420, 320);
    }

    private void sanitizeComponent(JComponent jb, Dimension dim) {
        jb.setPreferredSize(dim);
        jb.setMinimumSize(dim);
        jb.setMaximumSize(dim);
    }
    private void fillComponents() {

        // Show the basic built-in types
        mTypeDefComboBoxModel.addElement("Int");
        mTypeDefComboBoxModel.addElement("Bool");
        mTypeDefComboBoxModel.addElement("Float");
        mTypeDefComboBoxModel.addElement("String");
        mTypeDefComboBoxModel.addElement("Object");

        // Show the type definitions of the current node modification status.
        for (TypeDef def : mNode.getTypeDefList()) {
            mTypeDefComboBoxModel.addElement(def.getName());
        }

        // Show the type definitions of all parent nodes of the current node
        SuperNode parentNode = mNode.getParentNode();

        while (parentNode != null) {
            for (TypeDef def : parentNode.getTypeDefList()) {
                mTypeDefComboBoxModel.addElement(def.getName());
            }

            parentNode = parentNode.getParentNode();
        }

        // Select the type of the variable definition
        mTypeDefComboBox.setSelectedItem(mVarDef.getType());

        // Select the expression of the variable definition
        if (mVarDef.getExp() != null) {
            mExpTextField.setText(mVarDef.getExp().getAbstractSyntax());
        }
    }

    private void selectExp() {
        Expression exp = new CreateExpDialog(null).run();

        if (exp != null) {
            mVarDef.setExp(exp);
            mExpTextField.setText(exp.getAbstractSyntax());
        }
    }

    public VarDef run() {
        setVisible(true);

        if (mPressedButton == Button.OK) {
            return mVarDef;
        } else {
            return null;
        }
    }

    @Override
    protected void okActionPerformed() {
        mVarDef.setName(mNameTextField.getText().trim());
        mVarDef.setType((String) mTypeDefComboBoxModel.getSelectedItem());
        dispose(Button.OK);
    }

    @Override
    protected void cancelActionPerformed() {
        dispose(Button.CANCEL);
    }
}

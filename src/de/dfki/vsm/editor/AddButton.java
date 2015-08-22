package de.dfki.vsm.editor;

//~--- non-JDK imports --------------------------------------------------------

import de.dfki.vsm.util.ios.ResourceLoader;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author mfallas
 */
public class AddButton extends JLabel {
    private final Dimension buttonSize = new Dimension(20, 20);
    private int TabPos; //ONLY NECESSARY TO WORK WITH THE PLUS BUTTONS ON TABBEDPANES
    //Icons
    private final ImageIcon ICON_ADD_STANDARD = ResourceLoader.loadImageIcon("/res/img/toolbar_icons/add.png");
    private final ImageIcon ICON_ADD_ROLLOVER = ResourceLoader.loadImageIcon("/res/img/toolbar_icons/add_blue.png");
    public int getTabPos() {
        return TabPos;
    }

    public void setTabPos(int TabPos) {
        this.TabPos = TabPos;
    }
    public AddButton() {
        setHorizontalAlignment(SwingConstants.RIGHT);
        setIcon(ICON_ADD_STANDARD);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setToolTipText("Add");
        setIconTextGap(10);
        setFont(new Font("Helvetica", Font.PLAIN, 24));
        setFocusable(false);
        setPreferredSize(buttonSize);
        setMinimumSize(buttonSize);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                setIcon(ICON_ADD_ROLLOVER);
            }
            public void mouseExited(MouseEvent me) {
                setIcon(ICON_ADD_STANDARD);
            }
        });
    }
}

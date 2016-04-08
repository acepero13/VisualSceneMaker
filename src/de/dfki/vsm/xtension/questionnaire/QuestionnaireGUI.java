/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.vsm.xtension.questionnaire;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JFrame;

/**
 *
 * @author Patrick Gebhard
 */
public class QuestionnaireGUI {

    private JFrame mFrame;
    private QuestionnaireExecutor mExecutor;

    public void init(QuestionnaireExecutor executor) {
        mExecutor = executor;

        mFrame = new JFrame("EmpaT User Info");
        mFrame.setLayout(new BorderLayout());
        final JFXPanel jfxPanel = new JFXPanel();
        mFrame.add(jfxPanel, BorderLayout.CENTER);

        // Set Not Rezizable
        mFrame.setResizable(false);
        // Set Always On Top
        mFrame.setAlwaysOnTop(true);
        // Set Undecorated
        mFrame.setUndecorated(true);
        // Set Transparent
        mFrame.setBackground(new Color(0, 0, 0, 0));

        mFrame.setSize(800, 600);
        mFrame.setLocationRelativeTo(null);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mFrame.setVisible(true);

        Platform.runLater(() -> initFX(jfxPanel));
    }

    public void setVisible(boolean visible) {
        mFrame.setVisible(visible);
    }

    private void initFX(JFXPanel jfxPanel) {
        //try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/de/dfki/vsm/xtension/questionnaire/FXMLDocument.fxml"));
        //Parent root = fxmlLoader.load(getClass().getResource("/res/de/dfki/vsm/xtension/questionnaire/FXMLDocument.fxml"));
//            Parent root = new Parent() {
//};

        FXMLDocumentController controller = new FXMLDocumentController();
        fxmlLoader.setController(controller);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        Parent root = fxmlLoader.getRoot();

        //System.out.println("controller" + controller);
        controller.addListener(mExecutor);

        Scene scene = new Scene(root);

        //scene.setFill(new javafx.scene.paint.Color(0, 0, 0, 0));
        // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        jfxPanel.setScene(scene);
//        } catch (IOException exc) {
//            exc.printStackTrace();
//            System.exit(1);
//        }
    }
}

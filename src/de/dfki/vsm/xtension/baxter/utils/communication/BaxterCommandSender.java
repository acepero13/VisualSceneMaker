package de.dfki.vsm.xtension.baxter.utils.communication;

import de.dfki.vsm.xtension.baxter.command.BaxterCommand;

import java.util.ArrayList;

/**
 * Created by alvaro on 8/27/16.
 */
public class BaxterCommandSender {

    private static BaxterCommandServerWrapper baxterServer;
    public static void setCommandServer(BaxterCommandServerWrapper wrapper){
        baxterServer = wrapper;
    }

    public static void BaxterLookLeft(){
        checkIfBaxterIsRunning();
        BaxterCommand command = baxterServer.BaxterBuildCommand("look_left", new ArrayList<String>());
        baxterServer.sendToServer(command);
    }

    public static void BaxterLookRight(){
        checkIfBaxterIsRunning();
        BaxterCommand command = baxterServer.BaxterBuildCommand("look_right", new ArrayList<String>());
        baxterServer.sendToServer(command);
    }

    public static void BaxterLookFace(String movement){
        checkIfBaxterIsRunning();
        ArrayList<String> params = new ArrayList<>();
        params.add(movement);
        BaxterCommand command = baxterServer.BaxterBuildCommand("look_at", params);
        baxterServer.sendToServer(command);
    }

    public static void BaxterSayYes(){
        checkIfBaxterIsRunning();
        BaxterCommand command = baxterServer.BaxterBuildCommand("assent", new ArrayList<String>());
        baxterServer.sendToServer(command);
    }

    public static void BaxterSayNo(){
        checkIfBaxterIsRunning();
        BaxterCommand command = baxterServer.BaxterBuildCommand("negate", new ArrayList<String>());
        baxterServer.sendToServer(command);
    }

    public static void BaxterLookCenter(){
        checkIfBaxterIsRunning();
        BaxterCommand command = baxterServer.BaxterBuildCommand("set_neutral", new ArrayList<String>());
        baxterServer.sendToServer(command);
    }

    private static void checkIfBaxterIsRunning() {
        if(baxterServer == null){
            throw new ExceptionInInitializerError("No handler specified");
        }
    }

}

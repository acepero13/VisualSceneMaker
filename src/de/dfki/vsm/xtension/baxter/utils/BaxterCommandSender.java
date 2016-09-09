package de.dfki.vsm.xtension.baxter.utils;

import de.dfki.vsm.util.ios.IOSIndentWriter;
import de.dfki.vsm.util.xml.XMLUtilities;
import de.dfki.vsm.xtension.baxter.BaxterHandler;
import de.dfki.vsm.xtension.baxter.command.BaxterCommand;

import java.io.ByteArrayOutputStream;
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
        if(baxterServer == null){
            throw new ExceptionInInitializerError("No handler specified");
        }
        BaxterCommand command = baxterServer.BaxterBuildCommand("look_left", new ArrayList<String>());
        baxterServer.sendToServer(command);
    }

    public static void BaxterLookFace(String movement){
        if(baxterServer == null){
            throw new ExceptionInInitializerError("No handler specified");
        }
        ArrayList<String> params = new ArrayList<>();
        params.add(movement);
        BaxterCommand command = baxterServer.BaxterBuildCommand("look_at", params);
        baxterServer.sendToServer(command);
    }

}

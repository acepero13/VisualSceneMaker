package de.dfki.vsm.xtension.baxter.utils;

import de.dfki.vsm.xtension.baxter.BaxterHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

/**
 * Created by alvaro on 6/1/16.
 */
public class BaxterServerProcess {
    private final String processName = "imageviwer";
    private final String serverBasePath;

    public BaxterServerProcess(String basePath){
        serverBasePath = basePath;
    }
    public int  unloadBaxterServer() throws IOException, InterruptedException {
        Process killer = null;
        final String killCmd = "ps aux | grep '" + processName + "' | awk '{print $2}' | xargs kill";
        String[] cmd = {"/bin/sh", "-c", killCmd};
        killer = Runtime.getRuntime().exec(cmd);
        int value = killer.waitFor();
        return value;
    }

    private String[] getServerCmdPath() throws FileNotFoundException {
        if(serverBasePath == null || serverBasePath.equals("")){
            throw new FileNotFoundException("Baxter Server not found");
        }
        File f = new File(serverBasePath);
        if(!f.exists()) {
            throw new FileNotFoundException("Baxter Server not found");
        }
        String cmdPath[] = {"python", serverBasePath};
        return cmdPath;
    }

    //TODO: Hacer como en el MaryTTSServer que espera hasta que se ejecute
    public void launchBaxterServer() throws IOException {
        String processName = "imageviwer";
        String []serverPath = getServerCmdPath();
        Runtime.getRuntime().exec(serverPath);
    }

    public Socket connectToSocket() throws IOException {
        InetAddress inteAddress = null;
        inteAddress = InetAddress.getByName("localhost");
        SocketAddress socketAddress = new InetSocketAddress(inteAddress, 1313);
        Socket mSocket = new Socket();
        mSocket.connect(socketAddress, 2000); // wait max. 2000ms

        return mSocket;
    }


}

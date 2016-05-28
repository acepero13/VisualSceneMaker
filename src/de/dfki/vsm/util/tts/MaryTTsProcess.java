package de.dfki.vsm.util.tts;

import de.dfki.vsm.xtension.stickmanmarytts.util.tts.MaryStickmanPhonemes;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alvaro on 5/24/16.
 */
public class MaryTTsProcess {
    private String maryBase;
    private final String OS = System.getProperty("os.name").toLowerCase();
    final String instanceExecutedName = "marytts.server.Mary";
    public MaryTTsProcess(String pMaryBase){
        maryBase = pMaryBase;
    }

    public boolean startMaryServer() throws Exception {
        if(!isMaryTTSInstalled()){
            throw new FileNotFoundException("MaryTTS Server couldn't be found");
        }
        final String []command = buildMaryTTSCmd();
        final ProcessBuilder processB = new ProcessBuilder(command);
        processB.redirectErrorStream(true);
        Process p;
        try {
            p = processB.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            boolean started = false;
            while (!started) {
                line = br.readLine();
                if (line.contains("started in")) {
                    started = true;
                    is.close();
                    isr.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("MaryTTS Server could not be started");
        }
        return true;
    }

    private String[] buildMaryTTSCmd(){
        String cmd = getMaryTTSExecPath();
        List<String> command = new LinkedList<>();
        if (isUnix() || isMac()) {
            command.add("/bin/bash");
            command.add(cmd);
        } else if (isWindows()) {
            cmd = cmd + ".bat";
            command.add("CMD");
            command.add("/C");
            command.add(cmd);
        }
        return (String[]) command.toArray(new String[command.size()]);
    }

    private boolean isMaryTTSInstalled(){
        String cmd = getMaryTTSExecPath();
        File f = new File(cmd);
        if(f.exists() && !f.isDirectory()) {
            return  true;
        }
        return false;
    }

    private String getMaryTTSExecPath(){
        final String maryttsBaseDir = maryBase;
        String cmd = maryttsBaseDir + File.separator + "bin" + File.separator + "marytts-server";
        return cmd;
    }

    private boolean isInstanceRunning(){
        //TODO: Implement later
        return false;
    }

    public boolean stopMaryServer() throws IOException {
        String killCmd = "";
        Process killer = null;
        if (isUnix() || isMac()) {
            killCmd = "ps aux | grep '" + instanceExecutedName + "' | awk '{print $2}' | xargs kill";
            String[] cmd = {"/bin/sh", "-c", killCmd};
        } else if (isWindows()) {
            killCmd = "wmic Path win32_process Where \"CommandLine Like '%" + instanceExecutedName + "%'\" Call Terminate";
        }
        killer = Runtime.getRuntime().exec(killCmd);
        try {
            killer.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    private boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    private boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

}

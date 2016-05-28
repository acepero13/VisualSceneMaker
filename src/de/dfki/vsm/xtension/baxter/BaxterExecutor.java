package de.dfki.vsm.xtension.baxter;

import de.dfki.action.sequence.TimeMark;
import de.dfki.action.sequence.Word;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.executor.ActivityExecutor;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.runtime.project.RunTimeProject;
import de.dfki.vsm.util.tts.MaryTTsProcess;
import de.dfki.vsm.util.tts.MaryTTsSpeaker;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.VoiceName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by alvaro on 5/16/16.
 */
public class BaxterExecutor extends ActivityExecutor {
    private final HashMap<String, Process> mProcessMap = new HashMap();
    private int maryExecutionId=0;
    private MaryTTsProcess marySelfServer;
    final private String language = "en"; //TODO: Put in config file
    final private VoiceName voice = new VoiceName("dfki-spike");;
    private final HashMap<String, ActivityWorker> mActivityWorkerMap = new HashMap();
    public BaxterExecutor(PluginConfig config, RunTimeProject project) {
        super(config, project);
        marySelfServer = new MaryTTsProcess(mConfig.getProperty("mary.base"));
    }


    private final String getExecutionId() {
        return "mary_" + maryExecutionId++;
    }

    @Override
    public String marker(long id) {
        return "$" + id;
    }

    @Override
    public void execute(AbstractActivity activity) {
        if (activity instanceof SpeechActivity) {
            SpeechActivity sa = (SpeechActivity) activity;
            String executionId = getExecutionId();
            MaryTTsSpeaker marySpeak = new MaryTTsSpeaker(sa, language, voice);
            executeMaryTTSAndWait(executionId, marySpeak);
        }

    }

    private void executeMaryTTSAndWait(String executionId, MaryTTsSpeaker marySpeak){
        synchronized (mActivityWorkerMap) {
            try {
                marySpeak.speak(executionId);
                ActivityWorker cAW = (ActivityWorker) Thread.currentThread();
                mActivityWorkerMap.put(executionId, cAW);
                mLogger.warning("ActivityWorker " + executionId + " waiting ....");
                while (mActivityWorkerMap.containsValue(cAW)) {
                    try {
                        mActivityWorkerMap.wait();
                    } catch (InterruptedException exc) {
                        mLogger.failure(exc.toString());
                    }
                }
                mLogger.warning("ActivityWorker " + executionId + " done ....");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void launch() {
        try {
            launchBaxterServer();
            marySelfServer.startMaryServer(); //TODO: Show info dialog of loading....
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchBaxterServer() throws FileNotFoundException {
        String processName = "imageviwer";
        try {
            String []serverPath = getServerCmdPath();
            mProcessMap.put(processName, Runtime.getRuntime().exec(serverPath));
        } catch (final FileNotFoundException e){
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String[] getServerCmdPath() throws FileNotFoundException {
        String server = mConfig.getProperty("server");
        if(server == null || server.equals("")){
            throw new FileNotFoundException("Baxter Server not found");
        }
        File f = new File(server);
        if(!f.exists()) {
            throw new FileNotFoundException("Baxter Server not found");
        }
        String cmdPath[] = {"python", server};
        return cmdPath;
    }

    @Override
    public void unload() {
        unloadBaxterServer();
        try {
            marySelfServer.stopMaryServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: Unload other stuff
    }

    private void unloadBaxterServer(){
        for (final Map.Entry<String, Process> entry : mProcessMap.entrySet()) {
            final String name = entry.getKey();
            final Process process = entry.getValue();
            Process killer = null;
            final String killCmd = "ps aux | grep '" + name + "' | awk '{print $2}' | xargs kill";
            String[] cmd = {"/bin/sh", "-c", killCmd};
            try {
                killer = Runtime.getRuntime().exec(cmd);
                killer.waitFor();
                process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void handle(String message, final BaxterHandler client){
        System.out.println("Message");
    }

    public void scheduleSpeech(String executionId){

    }
}

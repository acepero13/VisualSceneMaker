package de.dfki.vsm.xtension.baxter;

import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.stickman.StickmanStage;
import de.dfki.stickman.animationlogic.AnimationLoader;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.ActionActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.executor.ActivityExecutor;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.runtime.project.RunTimeProject;
import de.dfki.vsm.util.tts.MaryTTsProcess;
import de.dfki.vsm.util.tts.MaryTTsSpeaker;
import de.dfki.vsm.xtension.baxter.action.BaxterStickman;
import de.dfki.vsm.xtension.baxter.action.SpeakerActivity;
import de.dfki.vsm.xtension.stickmanmarytts.action.ActionMouthActivity;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.VoiceName;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
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
    private final HashMap<String, BaxterHandler> mClientMap = new HashMap();
    private final HashMap<String, ActivityWorker> mActivityWorkerMap = new HashMap();
    private HashMap<String, SpeakerActivity> speechActivities = new HashMap<>();
    private HashMap<String, WordTimeMarkSequence> wtsMap= new HashMap<>();
    private StickmanStage mStickmanStage;
    private BaxterListener mListener;
    private BaxterHandler baxterServerHandler;

    private BaxterStickman baxterStickman;

    public BaxterExecutor(PluginConfig config, RunTimeProject project) {
        super(config, project);
        marySelfServer = new MaryTTsProcess(mConfig.getProperty("mary.base"));
        baxterStickman = new BaxterStickman();
    }

    public void accept(final Socket socket) {
        final BaxterHandler client = new BaxterHandler(socket, this);
        mClientMap.put(client.getName(), client);
        client.start();
        mLogger.warning("Accepting " + client.getName() + "");
    }

    public  String getExecutionId() {
        return "mary_" + maryExecutionId++;
    }

    @Override
    public String marker(long id) {
        return "$" + id;
    }

    @Override
    public void execute(AbstractActivity activity) {
        if (activity instanceof SpeechActivity) {
            actionExecuteSpeech(activity);
        } else if (activity instanceof ActionActivity || activity instanceof ActionMouthActivity) {
            actionLoadAnimation(activity);
        }
    }

    private void actionExecuteSpeech(AbstractActivity activity){
        SpeechActivity sa = (SpeechActivity) activity;
        SpeakerActivity speakerActivity = new SpeakerActivity(sa, language, voice);
        String executionId = getExecutionId();
        WordTimeMarkSequence wts = speakerActivity.getWordTimeSequence();
        speechActivities.put(executionId, speakerActivity);
        wtsMap.put(executionId, wts);
        executeMaryTTSAndWait(executionId);
    }

    private void actionLoadAnimation(AbstractActivity activity) {
        int animationDuration = 500;
        if (activity instanceof ActionMouthActivity) {
            animationDuration = ((ActionMouthActivity) activity).getDuration();
        }
        if (baxterStickman != null) {
            baxterStickman.loadBlockingAnimation(activity.getName(), animationDuration);
            executeAnimation();
        }
    }

    protected void executeAnimation() {
        ArrayList<String> params = new ArrayList<>();
        String headAsString = baxterStickman.getAnimationImage();
        params.add(headAsString);
        String baxterXMLCommand = baxterStickman.buildBaxterCommand(params);
        broadcast(baxterXMLCommand);
    }


    private void executeMaryTTSAndWait(String executionId){
        String spokenText = intentSpeak(executionId);
        if(spokenText.length() > 0 && Thread.currentThread() instanceof  ActivityWorker) { //TODO: Remove later, only for testing
            waitForSpeachToFinish(executionId);
        }
    }

    public String intentSpeak(String  executionId ){
        synchronized (mActivityWorkerMap) {
            String spokenText = "";
            SpeakerActivity speaker = speechActivities.get(executionId);
            try {
                spokenText = speaker.speak(executionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return spokenText;
        }
    }

    private void waitForSpeachToFinish(String  executionId){
        synchronized (mActivityWorkerMap){
            ActivityWorker cAW = (ActivityWorker) Thread.currentThread();
            mActivityWorkerMap.put(executionId, cAW);
            while (mActivityWorkerMap.containsValue(cAW)) {
                try {
                    mActivityWorkerMap.wait();
                } catch (InterruptedException exc) {
                    mLogger.failure(exc.toString());
                }
            }
        }
    }

    public void handle(String message, final BaxterHandler client){
        if (message.contains("#AUDIO#end#")) {
            handleAudio(message);
        }
        else if (message.contains("#ANIM#end#")) {
            handleAnimation(message);
        }
    }

    private void  handleAudio(String message){
        int start = message.lastIndexOf("#") + 1;
        String event_id = message.substring(start);
        System.out.println("Message");
        synchronized (mActivityWorkerMap){
            mActivityWorkerMap.remove(event_id);
            mActivityWorkerMap.notifyAll();
        }
    }

    private void handleAnimation(String message){
        int start = message.lastIndexOf("#") + 1;
        String animId = message.substring(start);
        if (mActivityWorkerMap.containsKey(animId)) {
            mActivityWorkerMap.remove(animId);
        }
        synchronized (mActivityWorkerMap) {
            mActivityWorkerMap.notifyAll();
        }
    }

    public SpeechActivity scheduleSpeech(String executionId){//TODO: Refactorizar
        SpeakerActivity speakerActivity = (SpeakerActivity) speechActivities.remove(executionId);
        MaryTTsSpeaker marySpeak = speakerActivity.getMarySpeak();
        SpeechActivity activity = speakerActivity.getSpeechActivity();
        LinkedList blocks = marySpeak.getSpeechActivityTextBlocs();
        int wordIndex = 0;
        int totalTime = 0;
        WordTimeMarkSequence wts = marySpeak.getWordTimeSequence();
        for (final Object item : blocks) {
            if (!item.toString().contains("$")) {
                LinkedList<Phoneme> wordPhonemes = marySpeak.getWordPhonemeList(wordIndex);
                for (Phoneme p : wordPhonemes) {
                    if (p.getLipPosition() == null) {
                        continue;
                    }
                    mScheduler.schedule((int) p.getmStart(), null, new ActionMouthActivity(activity.getActor(), "face",
                            "Mouth_" + p.getLipPosition(), null, (int) (p.getmEnd() - p.getmStart()), wts), mProject.getAgentDevice(activity.getActor()));
                    totalTime+= (int) (p.getmEnd() - p.getmStart());
                }
                wordIndex++;
            }
        }
        return  activity;
    }

    private void broadcast(final String message) {
        for (final BaxterHandler client : mClientMap.values()) {
            client.send(message);
        }
    }

    @Override
    public void launch() {
        try {
            launchBaxterServer(); //TODO: Hacer como en el MaryTTSServer que espera hasta que se ejecute
            marySelfServer.startMaryServer(); //TODO: Show info dialog of loading....
            mListener = new BaxterListener(8000, this);
            mListener.start();
            connectToBaxterServer();
            launchStickmanClient();
            waitForClients();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForClients(){
        while (mClientMap.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException exc) {
                mLogger.failure("Error while waiting ...");
            }
        }
    }

    public void connectToBaxterServer() throws IOException {
        InetAddress inteAddress = null;
        inteAddress = InetAddress.getByName("localhost");
        SocketAddress socketAddress = new InetSocketAddress(inteAddress, 1313);
        Socket mSocket = new Socket();
        mSocket.connect(socketAddress, 2000); // wait max. 2000ms
        baxterServerHandler = new BaxterHandler(mSocket, this);
        baxterServerHandler.start(); //TODO: Separate for testing
        mClientMap.put("pythonServerBaxter", baxterServerHandler);
    }

    private void launchStickmanClient(){
        final String host = mConfig.getProperty("smhost");
        final String port = mConfig.getProperty("smport");
        mLogger.message("Starting StickmanStage Client Application ...");
        mStickmanStage = StickmanStage.getNetworkInstance(host, Integer.parseInt(port));
    }

    public void launchBaxterServer() throws IOException {
        String processName = "imageviwer";
        String []serverPath = getServerCmdPath();
        mProcessMap.put(processName, Runtime.getRuntime().exec(serverPath));
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
        try {
            unloadBaxterServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            unloadClients();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mActivityWorkerMap.clear();
        try {
            marySelfServer.stopMaryServer();
            if(mListener!= null){
                mListener.abort();
                mListener.join();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void unloadBaxterServer() throws IOException, InterruptedException {
        for (final Map.Entry<String, Process> entry : mProcessMap.entrySet()) {
            final String name = entry.getKey();
            final Process process = entry.getValue();
            Process killer = null;
            final String killCmd = "ps aux | grep '" + name + "' | awk '{print $2}' | xargs kill";
            String[] cmd = {"/bin/sh", "-c", killCmd};
            killer = Runtime.getRuntime().exec(cmd);
            killer.waitFor();
            process.waitFor();
        }
    }

    private void unloadClients() throws InterruptedException {
        for (final BaxterHandler client : mClientMap.values()) {
            client.abort();
            client.join();
        }
        mClientMap.clear();
    }

}

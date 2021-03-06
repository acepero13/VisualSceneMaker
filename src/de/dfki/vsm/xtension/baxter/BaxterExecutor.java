package de.dfki.vsm.xtension.baxter;

import de.dfki.action.sequence.Entry;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.common.interfaces.StageRoom;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.model.scenescript.ActionFeature;
import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.ActionActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.executor.ActivityExecutor;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.runtime.project.RunTimeProject;
import de.dfki.vsm.util.stickman.StickmanRepository;
import de.dfki.vsm.util.tts.TTSFactory;
import de.dfki.vsm.util.tts.marytts.MaryTTsProcess;
import de.dfki.vsm.util.tts.SpeakerTts;
import de.dfki.vsm.xtension.baxter.action.BaxterStickman;
import de.dfki.vsm.xtension.baxter.action.SpeakerActivity;
import de.dfki.vsm.xtension.baxter.action.TimeMarkActivity;
import de.dfki.vsm.xtension.baxter.utils.communication.BaxterCommandSender;
import de.dfki.vsm.xtension.baxter.utils.communication.BaxterCommandServerWrapper;
import de.dfki.vsm.xtension.baxter.utils.communication.BaxterServerProcess;
import de.dfki.vsm.xtension.baxter.utils.messagehandlers.BaxterMessageHandler;
import de.dfki.vsm.xtension.baxter.utils.messagehandlers.VAD.VAD;
import de.dfki.vsm.xtension.stickmanmarytts.action.ActionMouthActivity;
import de.dfki.vsm.xtension.stickmantts.util.tts.sequence.Phoneme;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by alvaro on 5/16/16.
 */
public class BaxterExecutor extends ActivityExecutor {
    public static final String PYTHON_SERVER_BAXTER = "pythonServerBaxter";
    public static java.lang.String sExecutionId = "baxtermary_";
    private final HashMap<String, Process> mProcessMap = new HashMap();
    private BaxterMessageHandler baxterMessageHandler ;
    private int maryExecutionId=0;
    private MaryTTsProcess marySelfServer;
    private BaxterServerProcess baxterServerProcess;
    private final HashMap<String, BaxterHandler> mClientMap = new HashMap();
    private final Map<String, ActivityWorker> mActivityWorkerMap = new HashMap();
    private HashMap<String, SpeakerActivity> speechActivities = new HashMap<>();
    private HashMap<String, WordTimeMarkSequence> wtsMap= new HashMap<>();
    private BaxterListener mListener;
    private BaxterListener speechListener;
    private BaxterHandler baxterServerHandler;

    private BaxterStickman baxterStickman;

    private StageRoom stickmanStageC;
    private StickmanRepository stickmanFactory;
    private Thread stickmanLaunchThread;

    public BaxterExecutor(PluginConfig config, RunTimeProject project) {
        super(config, project);
        marySelfServer = MaryTTsProcess.getsInstance(mConfig.getProperty("mary.base"));
        baxterStickman = new BaxterStickman(mConfig, this);
        baxterServerProcess = new BaxterServerProcess(mConfig.getProperty("server"));
        stickmanFactory = new StickmanRepository(config);
        baxterMessageHandler = new BaxterMessageHandler(mActivityWorkerMap, project);
        VAD.setBaxterMessageHandler(baxterMessageHandler);
        VAD.setProject(project);
        VAD.setBaxterExecutor(this);

    }

    public void accept(final Socket socket) {
        final BaxterHandler client = new BaxterHandler(socket, this);
        mClientMap.put(client.getName(), client);
        client.start();
        mLogger.warning("Accepting " + client.getName() + "");
    }

    public  String getExecutionId() {
        return sExecutionId + maryExecutionId++;
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
            if(activity.getName().equals("Baxter")){
                callBaxterAction(activity);
            } else{
                if(activity.getName().equals("LeaveTurn") && mProject.hasVariable("BaxterWantsLeaveTurn")){
                    mProject.setVariable("BaxterWantsLeaveTurn", true);
                }else {
                    actionLoadAnimation(activity);
                }
            }
        }else if(activity instanceof TimeMarkActivity){
            actionExecuteActionTimeMark((TimeMarkActivity) activity);
        }
    }

    protected void callBaxterAction(AbstractActivity activity) {
        if(activity.getFeatureList() != null) {
            for (ActionFeature feat : activity.getFeatureList()) {
                try {
                    Method method = BaxterCommandSender.class.getMethod(feat.getVal());
                    method.invoke(null);
                } catch (NoSuchMethodException e) {
                    continue;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void actionExecuteActionTimeMark(TimeMarkActivity activity) {
        for (ArrayList<Entry> cluster : activity.getCluster()) {
            handleClusterActions(cluster);
        }
    }

    protected void handleClusterActions(ArrayList<Entry> cluster) {
        if (WordTimeMarkSequence.getClusterType(cluster) == Entry.TYPE.TIMEMARK) {
            for (Entry e : cluster) {
                baxterMessageHandler.handle(e.mContent);
            }
        }
    }

    private void actionExecuteSpeech(AbstractActivity activity){
        SpeechActivity sa = (SpeechActivity) activity;
        TTSFactory factoryTTs = new TTSFactory(mConfig, sa, mProject);
        SpeakerTts speakerTts = factoryTTs.getTTs();
        SpeakerActivity speakerActivity = new SpeakerActivity(speakerTts);
        String executionId = getExecutionId();
        WordTimeMarkSequence wts = speakerActivity.getWordTimeSequence();
        speechActivities.put(executionId, speakerActivity);
        wtsMap.put(executionId, wts);
        executeTTSAndWait(executionId);
    }

    private void actionLoadAnimation(AbstractActivity activity) {
        int animationDuration =200;
        if (activity instanceof ActionMouthActivity) {
            animationDuration = ((ActionMouthActivity) activity).getDuration();
        }
        animationDuration =30;
        if (baxterStickman != null && activity instanceof ActionMouthActivity) {
            baxterStickman.loadNonBlockingAnimation(activity.getName(), animationDuration);
            //executeAnimation();
        }else if(baxterStickman != null){
            baxterStickman.loadNonBlockingAnimation(activity.getName(), animationDuration);
            //executeAnimation();
        }
    }

    protected void executeAnimation() {
        ArrayList<String> params = new ArrayList<>();
        String headAsString = baxterStickman.getAnimationImage();
        params.add(headAsString);
        String baxterXMLCommand = baxterStickman.buildBaxterCommand(params);
        broadcastToSpecificServer(baxterXMLCommand, PYTHON_SERVER_BAXTER);
    }


    private void executeTTSAndWait(String executionId){
        waitForSpeachToFinish(executionId);
    }

    private Thread getSpeakThread(final String executionId) {
        return new Thread(){
            public void run(){
                System.out.println("ExecutionID: " + executionId);
                intentToSpeak(executionId);
            }
        };
    }

    public String intentToSpeak(String  executionId ){

        String spokenText = "";
        SpeakerActivity speaker = speechActivities.get(executionId);
        try {
            spokenText = speaker.speak(executionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spokenText;

    }

    private void waitForSpeachToFinish(String  executionId){
        synchronized (mActivityWorkerMap){
            ActivityWorker cAW = (ActivityWorker) Thread.currentThread();
            mActivityWorkerMap.put(executionId, cAW);
            Thread thread = getSpeakThread(executionId);
            thread.start();
            while (mActivityWorkerMap.containsValue(cAW)) {
                try {
                    System.out.println("Wait: " + executionId);

                    mActivityWorkerMap.wait();
                } catch (InterruptedException exc) {
                    mLogger.failure(exc.toString());
                }
            }
        }
    }

    public void handle(String message, final BaxterHandler client){
        baxterMessageHandler.handle(message);
    }


    public SpeechActivity scheduleSpeech(String executionId){//TODO: Refactorizar
        SpeakerActivity speakerActivity = (SpeakerActivity) speechActivities.remove(executionId);
        if(speakerActivity == null){
            return null;
        }
        SpeakerTts ttsSpeak =  speakerActivity.getTtsSpeak();
        SpeechActivity activity = speakerActivity.getSpeechActivity();
        LinkedList blocks = ttsSpeak.getSpeechActivityTextBlocs();
        int wordIndex = 0;
        int totalTime = 0;
        int timeC= 0;
        WordTimeMarkSequence wts = ttsSpeak.getWordTimeSequence();
        for (final Object item : blocks) {
            if (!item.toString().contains("$")) {
                LinkedList<Phoneme> wordPhonemes = ttsSpeak.getWordPhonemeList(wordIndex);
                for (Phoneme p : wordPhonemes) {
                    if (p.getLipPosition() == null) {
                        continue;
                    }
                    mScheduler.schedule((int) p.getmStart(), null, new ActionMouthActivity(activity.getActor(), "face",
                            "Mouth_" + p.getLipPosition(), null, (int) (p.getmEnd() - p.getmStart()), wts), mProject.getAgentDevice(activity.getActor()));
                    totalTime+= (int) (p.getmEnd() - p.getmStart());
                    timeC = (int) p.getmStart();
                }
                wordIndex++;
            }else{
                mScheduler.schedule(timeC + 100, null, new TimeMarkActivity(activity.getActor(), "", "HandleTimeMark", wts), mProject.getAgentDevice(activity.getActor()));
            }
        }

        mScheduler.schedule(totalTime + 100, null, new ActionMouthActivity(activity.getActor(), "face", "Mouth_Default" , null,  100, wts), mProject.getAgentDevice(activity.getActor()));

        //mScheduler.schedule(totalTime, null, new TimeMarkActivity(activity.getActor(), "", "HandleTimeMark", wts), mProject.getAgentDevice(activity.getActor()));
        return  activity;
    }

    private void broadcast(final String message) {
        for (final BaxterHandler client : mClientMap.values()) {
            client.send(message);
        }
    }


    public void broadcastToSpecificServer(final String message, final String serverName){
        if(mClientMap.containsKey(serverName)){
            final BaxterHandler client = mClientMap.get(serverName);
            client.send(message);
        }

    }

    @Override
    public void launch() {
        try {
            launchMary();
            launchBaxter();
           // launchStickmanClient();
            waitForClients();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not start server. Shutting down");
            //unload();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not start server. Shutting down");
            unload();
        }
    }

    private void launchBaxter() throws Exception {
        speechListener = new BaxterListener(1314, this);
        speechListener.start();
        //baxterServerProcess.launchBaxterServer();
        connectToBaxterServer();

        mListener = new BaxterListener(8001, this);
        mListener.start();
       BaxterCommandSender.BaxterLookCenter();//default position


    }

    private void launchMary() throws Exception {
        marySelfServer.startMaryServer(); //TODO: Show info dialog of loading....
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

    private void connectToBaxterServer() throws IOException {
        Socket mSocket = baxterServerProcess.connectToSocket();
        baxterServerHandler = new BaxterHandler(mSocket, this);
        startBaxterServer();
    }

    public void startBaxterServer() throws IOException {
        baxterServerHandler.start(); //TODO: Separate for testing
        mClientMap.put(PYTHON_SERVER_BAXTER, baxterServerHandler);
        BaxterCommandServerWrapper wrapper = new BaxterCommandServerWrapper(baxterServerHandler);
        BaxterCommandSender.setCommandServer(wrapper);
        BaxterCommandSender.setProject(mProject);
    }

    private void launchStickmanClient(){
        mLogger.message("Starting StickmanStage Client Application ...");
        stickmanStageC = stickmanFactory.createStickman();
        stickmanLaunchThread = new Thread() {
            public void run() {
                try {
                    stickmanStageC.launchStickmanStage(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //stickmanLaunchThread.start();
        // mStickmanStage.getNetworkInstances(host, Integer.parseInt(port));
    }

    @Override
    public void unload() {

        try {
            unloadClients();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        unloadServer();
        mActivityWorkerMap.clear();
       // unloadMary();
    }

    private void unloadMary() {
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

    private void unloadServer() {
        try {
            baxterServerProcess.unloadBaxterServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.vsm.xtension.stickman;

import de.dfki.action.sequence.TimeMark;
import de.dfki.action.sequence.Word;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.stickman.StickmanStage;
import de.dfki.stickman.animationlogic.Animation;
import de.dfki.stickman.animationlogic.AnimationLoader;
import de.dfki.util.xml.XMLUtilities;
import de.dfki.util.ios.IOSIndentWriter;
import de.dfki.vsm.model.project.AgentConfig;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.model.scenescript.ActionFeature;
import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.ActionActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.executor.ActivityExecutor;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.runtime.project.RunTimeProject;
import de.dfki.vsm.util.log.LOGConsoleLogger;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Patrick Gebhard
 */
public class StickmanExecutor extends ActivityExecutor {

    // The stickman stage window
    private static StickmanStage mStickmanStage;
    // The singelton logger instance
    private final LOGConsoleLogger mLogger = LOGConsoleLogger.getInstance();
    // The tworld listener
    private StickmanListener mListener;
    // The map of processes
    private final HashMap<String, Process> mProcessMap = new HashMap();
    // The client thread list
    private final HashMap<String, StickmanHandler> mClientMap = new HashMap();
    // The map of activity worker
    private final HashMap<String, ActivityWorker> mActivityWorkerMap = new HashMap();
    private StickmanStage stickmanStage;

    // Construct the executor
    public StickmanExecutor(final PluginConfig config, final RunTimeProject project) {
        // Initialize the plugin
        super(config, project);
        stickmanStage = new StickmanStage();
    }

    // Accept some socket
    public void accept(final Socket socket) {
        // Make new client thread 
        final StickmanHandler client = new StickmanHandler(socket, this);
        // Add the client to list
        // TODO: Get some reasonable name for references here!
        mClientMap.put(client.getName(), client);
        // Start the client thread
        client.start();
        //
        mLogger.warning("Accepting " + client.getName() + "");
    }

    @Override
    public final String marker(final long id) {
        // Stickman style bookmarks
        return "$" + id;
    }

    @Override
    public void execute(AbstractActivity activity/*, ActivityScheduler scheduler*/) {
        // get action information
        final String actor = activity.getActor();
        final String name = activity.getName();
        final LinkedList<ActionFeature> features = activity.getFeatureList();

        Animation stickmanAnimation = new Animation();

        if (activity instanceof SpeechActivity) {
            SpeechActivity sa = (SpeechActivity) activity;

            // create a new word time mark sequence based on the current utterance blocks
            WordTimeMarkSequence wts = new WordTimeMarkSequence(sa.getTextOnly("$"));

            LinkedList blocks = sa.getBlocks();
            for (final Object item : blocks) {
                if (!item.toString().contains("$")) {
                    wts.add(new Word(item.toString()));
                } else {
                    wts.add(new TimeMark(item.toString()));
                }
            }

            // schedule Mouth_open and Mouth closed activities
            mScheduler.schedule(20, null, new ActionActivity(actor, "face", "Mouth_O", null, null), mProject.getAgentDevice(actor));
            mScheduler.schedule(200, null, new ActionActivity(actor, "face", "Mouth_Default", null, null), mProject.getAgentDevice(actor));

            stickmanAnimation = AnimationLoader.getInstance().loadEventAnimation(mStickmanStage.getStickman(actor), "Speaking", 3000, false);
            stickmanAnimation.mParameter = wts;

            executeAnimationAndWait(activity, stickmanAnimation);
        } else if (activity instanceof ActionActivity) {
            stickmanAnimation = AnimationLoader.getInstance().loadAnimation(mStickmanStage.getStickman(actor), name, 500, false); // TODO: with regard to get a "good" timing, consult the gesticon
            if (stickmanAnimation != null) {
                executeAnimation(stickmanAnimation);
            }
        }
    }

    private void executeAnimation(Animation stickmanAnimation) {
        // executeAnimation command to platform 
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOSIndentWriter iosw = new IOSIndentWriter(out);
        boolean r = XMLUtilities.writeToXMLWriter(stickmanAnimation, iosw);
        broadcast(out.toString().replace("\n", " "));
    }

    private void executeAnimationAndWait(AbstractActivity activity, Animation stickmanAnimation) {
        // executeAnimation command to platform 
        synchronized (mActivityWorkerMap) {
            // executeAnimation command to platform 
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOSIndentWriter iosw = new IOSIndentWriter(out);
            boolean r = XMLUtilities.writeToXMLWriter(stickmanAnimation, iosw);
            broadcast(out.toString().replace("\n", " "));

            // organize wait for feedback
            ActivityWorker cAW = (ActivityWorker) Thread.currentThread();
            mActivityWorkerMap.put(stickmanAnimation.mID, cAW);

            // wait until we got feedback
            mLogger.message("ActivityWorker " + stickmanAnimation.mID + " waiting ....");
            while (mActivityWorkerMap.containsValue(cAW)) {
                try {
                    mActivityWorkerMap.wait();
                } catch (InterruptedException exc) {
                    mLogger.failure(exc.toString());
                }
            }
            mLogger.message("ActivityWorker " + stickmanAnimation.mID + "  done ....");
        }
    }

    @Override
    public void launch() {
        // read config
        final String host = mConfig.getProperty("smhost");
        final String port = mConfig.getProperty("smport");

        // Create the connection
        mListener = new StickmanListener(Integer.parseInt(port), this);
        // Start the connection
        mListener.start();

        final boolean showStickmanNames = mConfig.containsKey("showstickmanname") ? mConfig.getProperty("showstickmanname").equalsIgnoreCase("true") : true;

        // Start the StickmanStage client application 
        mLogger.message("Starting StickmanStage Client Application ...");
        mStickmanStage.setNetWorkProperties(host, Integer.parseInt(port));
        mStickmanStage.showStickmanName(showStickmanNames);

        // Get Stickman agents configuration
        for (String name : mProject.getAgentNames()) {
            AgentConfig ac = mProject.getAgentConfig(name);

            if (ac.getDeviceName().equalsIgnoreCase("stickman")) {
                mStickmanStage.addStickman(name);
            }
        }

        // Wait for stickman stage to be initialized 
        while (mClientMap.isEmpty()) {
            mLogger.message("Waiting for StickmanStage");
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException exc) {
                mLogger.failure("Error while waiting ...");
            }
        }
    }

    @Override
    public void unload() {
        // clear the stage
        mStickmanStage.clearStage();
        // Abort the client threads
        for (final StickmanHandler client : mClientMap.values()) {
            client.abort();
            // Join the client thread
            try {
                client.join();
            } catch (final Exception exc) {
                mLogger.failure(exc.toString());
                // Print some information 
                mLogger.message("Joining client thread");
            }
        }
        // Clear the map of clients 
        mClientMap.clear();
        // Abort the server thread
        try {
            mListener.abort();
            // Join the client thread
            mListener.join();
            // Print some information 
            mLogger.message("Joining server thread");
        } catch (final Exception exc) {
            mLogger.failure(exc.toString());
        }
    }

    // Handle some message
    public void handle(final String message, final StickmanHandler client) {
        mLogger.message("Handling " + message + "");

        synchronized (mActivityWorkerMap) {

            if (message.contains("#ANIM#end#")) {
                int start = message.lastIndexOf("#") + 1;
                String animId = message.substring(start);

                if (mActivityWorkerMap.containsKey(animId)) {
                    mActivityWorkerMap.remove(animId);
                }
                // wake me up ..
                mActivityWorkerMap.notifyAll();
            } else if (message.contains("$")) {
                // wake me up ..
                mActivityWorkerMap.notifyAll();
                // identify the related activity
                //AbstractActivity activity = mProject.getRunTimePlayer().getActivityScheduler().getMarkerActivity(message);
                // play the activity
                //mProject.getRunTimePlayer().getActivityScheduler().handle(new MarkerFeedback(activity, message));
                mProject.getRunTimePlayer().getActivityScheduler().handle(message);

            }
        }
    }

    // Broadcast some message
    private void broadcast(final String message) {
        for (final StickmanHandler client : mClientMap.values()) {
            client.send(message);
        }
    }
}

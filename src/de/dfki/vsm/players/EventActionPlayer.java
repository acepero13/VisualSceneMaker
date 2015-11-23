package de.dfki.vsm.players;

import static de.dfki.vsm.players.ActionPlayer.sActionScheduler;
import de.dfki.vsm.players.action.Action;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Patrick Gebhard
 *
 */
public class EventActionPlayer extends ActionPlayer {

	private EventActionPlayer() {
		super();
		super.setName("VisualSceneMaker Event Action Player");
	}

	public static EventActionPlayer getInstance() {
		if (sInstance == null) {
			sInstance = new EventActionPlayer();
		}
		return (EventActionPlayer) sInstance;
	}

	public static EventActionPlayer getNetworkInstance() {
		mUseNetwork = true;
		return getInstance();
	}

	public void addTimeMarkAction(Action a) {
		// tell the action which player executes it
		a.mActionPlayer = this;
		a.mID = getNextID();
		// add action to the list of to be executed actions
		sActionList.add(a);
	}

	public void runActionAtTimeMark(String timemark) {
		synchronized (sActionList) {
//			mLogger.message("Executing actions at timemark " + timemark);
//			mLogger.message("Actions in queue " + sActionList.size());
//			for (Action a : sActionList) {
//			mLogger.message("\taction " + a.mName + " with id " + a.mID + " with timemark " + a.mTimeMark);
//			}

			for (Action a : sActionList) {
				if (mRunning) {
					// if the timestamp is reached, play all actions that should be played at that timemark immediately
					if (a.mTimeMark != null) {
						if (a.mTimeMark.equalsIgnoreCase(timemark)) {
							a.mStartTime = 0;
							//mLogger.message("\tNow starting action " + a.mID);
							sActionScheduler.schedule(a, 0, TimeUnit.MILLISECONDS);
						}
					}
				}
			}
		}
	}

	@Override
	public void run() {
		while (mRunning) {
			try {
				//mLogger.warning("Event ActionPlayer is ready and wait for go ... " + mRunning);
				// wait for go
				sActionPlaySync.acquire();

				//mLogger.message("Action Player is working  ...");
				// now schedule all existing actions - actions that shoudl be played directly;
				// one of them should contain information for the player about timestamps when to trigger other actions
				if (mRunning) {
					sActionList.stream().forEach((a) -> {
						// start all actions  which do not have a timemark start condition
						if (a.mStartTime != -1) {
							//mLogger.message("Action " + a.mName + " is scheduled ...");

							sActionScheduler.schedule(a, a.mStartTime, TimeUnit.MILLISECONDS);
						}
					});
				}

				// wait for all actions ended
				sActionPlaySync.acquire();
				} catch (InterruptedException ex) {
				mLogger.warning("Event ActionPlayer got interrupted " + ex.getMessage());
			}

			notifyListenersAllActionsFinished();
		}
	}
}
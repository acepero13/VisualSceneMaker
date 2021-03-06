package de.dfki.vsm.xtension.baxter.action;

import de.dfki.action.sequence.Entry;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.runtime.activity.AbstractActivity;

import java.util.ArrayList;

/**
 * Created by alvaro on 6/10/16.
 */
public class TimeMarkActivity extends AbstractActivity {
    WordTimeMarkSequence wts;
    public TimeMarkActivity(Policy type, String actor, String mode, String name) {
        super(type, actor, mode, name);
    }

    public TimeMarkActivity(String actor, String mode, String name, WordTimeMarkSequence w) {
        super(Policy.PARALLEL, actor, mode, name);
        wts = w;
    }

    public  WordTimeMarkSequence getWordTimeSequence(){
        return  wts;
    }

    public ArrayList<ArrayList<Entry>> getCluster(){
        return wts.getClusters();
    }
}

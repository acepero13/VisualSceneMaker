package de.dfki.vsm.xtension.baxter.action;

import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.tts.MaryTTsSpeaker;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.VoiceName;

import java.util.LinkedList;

/**
 * Created by alvaro on 5/31/16.
 */
public class SpeakerActivity {
    private SpeechActivity speechActivity;
    private MaryTTsSpeaker marySpeak;
    private String language;
    private VoiceName voiceName;
    public SpeakerActivity(SpeechActivity sa){
        speechActivity = sa;

    }

    public SpeakerActivity(SpeechActivity sa, String lang, VoiceName voice){
        speechActivity = sa;
        language = lang;
        voiceName = voice;
        marySpeak = new MaryTTsSpeaker(sa, language, voice);
    }

    public WordTimeMarkSequence getWordTimeSequence(){
        return marySpeak.getWordTimeSequence();
    }

    public MaryTTsSpeaker getMarySpeak(){
        return marySpeak;
    }

    public String speak(String executionId) throws Exception {
        return marySpeak.speak(executionId);
    }

    public SpeechActivity getSpeechActivity(){
        return speechActivity;
    }

    public void getPhonemeList(){

    }
}

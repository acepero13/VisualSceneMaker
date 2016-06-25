package de.dfki.vsm.util.tts.factory;

import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.tts.SpeakerTts;

/**
 * Created by alvaro on 25/06/16.
 */
public abstract class TTsAbstractFactory {
    public abstract SpeakerTts createTts(SpeechActivity pSpeech, String pLanguage, String pVoiceName);

}

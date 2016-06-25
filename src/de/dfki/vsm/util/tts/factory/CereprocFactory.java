package de.dfki.vsm.util.tts.factory;

import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.tts.SpeakerTts;
import de.dfki.vsm.util.tts.cereproc.CereProgTTsSpeaker;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.VoiceName;

/**
 * Created by alvaro on 25/06/16.
 */
public class CereprocFactory extends TTsAbstractFactory {
    @Override
    public SpeakerTts createTts(SpeechActivity pSpeech, String pLanguage, String pVoiceName) {
        return new CereProgTTsSpeaker(pSpeech, pLanguage, pVoiceName);
    }
}

package de.dfki.vsm.util.tts.factory;

import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.tts.MaryTTsSpeaker;
import de.dfki.vsm.util.tts.SpeakerTts;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.VoiceName;

/**
 * Created by alvaro on 25/06/16.
 */
public class MaryTtsFactory extends TTsAbstractFactory {
    @Override
    public SpeakerTts createTts(SpeechActivity pSpeech, String pLanguage, String pVoiceName) {
        VoiceName voiceName = new VoiceName(pVoiceName);
        return new MaryTTsSpeaker(pSpeech, pLanguage, voiceName);
    }
}

package de.dfki.vsm.util.tts.cereproc;

import de.dfki.action.sequence.Word;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.tts.SpeakerTts;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * Created by alvaro on 25/06/16.
 */
public class CereProgTTsSpeaker extends SpeakerTts {

    private String langVoice;
    private String voiceName;
    private String gender;
    public CereProgTTsSpeaker(){
        speechClient = new Cereprog();
    }

    public CereProgTTsSpeaker(SpeechActivity pSpeech, String pLanguage, String pVoiceName){
        speech = pSpeech;
        langVoice = pLanguage;
        voiceName = pVoiceName;
        speechClient = new Cereprog();
    }

    @Override
    public LinkedList<Phoneme> getWordPhonemeList(int index) {
        LinkedList<Phoneme> wordPhonemes = null;
        addWords();
        try {
            wordPhonemes = getAsCereproc().getPhonemes();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordPhonemes;
    }

    private Cereprog getAsCereproc() {
        return (Cereprog)speechClient;
    }


    @Override
    public String speak(String executionId) throws Exception {
        addWords();
        return getAsCereproc().speak(executionId);
    }



}

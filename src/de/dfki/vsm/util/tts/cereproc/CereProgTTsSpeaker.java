package de.dfki.vsm.util.tts.cereproc;

import de.dfki.action.sequence.TimeMark;
import de.dfki.action.sequence.Word;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.tts.SpeakerTts;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.VoiceName;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * Created by alvaro on 25/06/16.
 */
public class CereProgTTsSpeaker implements SpeakerTts {
    private Cereprog cereprog;
    private SpeechActivity speech;
    private String langVoice;
    private String voiceName;
    private String gender;

    public CereProgTTsSpeaker(){
        cereprog = new Cereprog();
    }

    public CereProgTTsSpeaker(SpeechActivity pSpeech, String pLanguage, String pVoiceName){
        speech = pSpeech;
        langVoice = pLanguage;
        voiceName = pVoiceName;
        cereprog = new Cereprog();
    }

    private void setText(String text){
        cereprog.setText(text);
    }
    @Override
    public LinkedList<Phoneme> getWordPhonemeList(int index) {
        LinkedList<Phoneme> wordPhonemes = null;
        addWords();
        try {
            wordPhonemes = cereprog.getPhonemes();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordPhonemes;
    }

    @Override
    public String speak(String executionId) throws Exception {
        addWords();
        return cereprog.speak();
    }

    @Override
    public WordTimeMarkSequence getWordTimeSequence() {
        WordTimeMarkSequence wts = new WordTimeMarkSequence(speech.getTextOnly("$"));
        LinkedList blocks = speech.getBlocks();
        for (final Object item : blocks) {
            if (!item.toString().contains("$")) {
                Word w = new Word(item.toString());
                wts.add(w);
            } else {
                wts.add(new TimeMark(item.toString()));
            }
        }
        return wts;
    }

    @Override
    public void addWords() {
        LinkedList blocks = speech.getBlocks();
        for (final Object item : blocks) {
            if (!item.toString().contains("$")) {
                Word w = new Word(item.toString());
                cereprog.addWord(item.toString());
            }
        }
    }
}

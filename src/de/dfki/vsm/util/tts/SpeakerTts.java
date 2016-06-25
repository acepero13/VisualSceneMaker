package de.dfki.vsm.util.tts;

import de.dfki.action.sequence.TimeMark;
import de.dfki.action.sequence.Word;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.SpeechClient;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;

import java.util.LinkedList;

/**
 * Created by alvaro on 25/06/16.
 */
public abstract class SpeakerTts {
    protected SpeechActivity speech;
    protected SpeechClient speechClient;
    public abstract LinkedList<Phoneme> getWordPhonemeList(int index);
    public abstract String speak(String executionId) throws Exception;

    public  WordTimeMarkSequence getWordTimeSequence(){
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

    public void addWords() {
        LinkedList blocks = speech.getBlocks();
        for (final Object item : blocks) {
            if (!item.toString().contains("$")) {
                Word w = new Word(item.toString());
                speechClient.addWord(item.toString());
            }
        }
    }

}

package de.dfki.vsm.util.tts;

import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;

import java.util.LinkedList;

/**
 * Created by alvaro on 25/06/16.
 */
public interface SpeakerTts {
    LinkedList<Phoneme> getWordPhonemeList(int index);
    String speak(String executionId) throws Exception;
    WordTimeMarkSequence getWordTimeSequence();
    void addWords();

}

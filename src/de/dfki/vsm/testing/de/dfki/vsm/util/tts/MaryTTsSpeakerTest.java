package de.dfki.vsm.util.tts;

import de.dfki.action.sequence.Entry;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.MaryStickmanPhonemes;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.VoiceName;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by alvaro on 5/27/16.
 */
public class MaryTTsSpeakerTest {
    private MaryTTsSpeaker marySpeaker;
    @Before
    public void setUp(){
        //SpeechActivity pSpeech, String pLanguage, VoiceName pVoiceName
        getInstance();

    }

    private MaryTTsSpeaker getInstance(){
        HashMap<Integer, LinkedList<Phoneme>> emptyList = new HashMap<>();
        return getInstance(emptyList);
    }

    private MaryTTsSpeaker getInstance(HashMap<Integer, LinkedList<Phoneme>> hashM){
        VoiceName voiceName = new VoiceName("dfki-spike");
        SpeechActivity speech =  new SpeechActivity("actor", new LinkedList(), "$");
        MaryStickmanPhonemes phonemesList = mock(MaryStickmanPhonemes.class);
        when(phonemesList.getPhonemesAndMouthPosition(speech, voiceName, "en")).thenReturn(hashM);
        return  new MaryTTsSpeaker(speech, "en", voiceName, phonemesList);
    }


    @Test
    public void testGetWordPhonemeList() throws Exception {
        HashMap<Integer, LinkedList<Phoneme>> list = new HashMap<>();
        LinkedList phonemeL = new LinkedList();
        phonemeL.add(new Phoneme("test0", 0, 100));
        phonemeL.add(new Phoneme("test1", 100, 200));
        list.put(1, new LinkedList<>());
        list.put(12, phonemeL);
        MaryTTsSpeaker maryS = getInstance(list);
        LinkedList<Phoneme> result = maryS.getWordPhonemeList(12);
        assertEquals(phonemeL, result);
    }

    @Test
    public void testGetWordPhonemeListOutBound(){
        HashMap<Integer, LinkedList<Phoneme>> list = new HashMap<>();
        LinkedList phonemeL = new LinkedList();
        phonemeL.add(new Phoneme("test0", 0, 100));
        list.put(12, phonemeL);
        MaryTTsSpeaker maryS = getInstance(list);
        LinkedList<Phoneme> result = maryS.getWordPhonemeList(155);
        assertEquals(0, result.size());
    }



    @Test
    public void testGetWordTimeSequence() throws Exception {
        LinkedList<String> blocks = new LinkedList();
        blocks.add("$1");
        blocks.add("Hola");
        blocks.add("Mundo");
        blocks.add("$2");
        SpeechActivity speech =  new SpeechActivity("actor", blocks, "$");
        VoiceName voiceName = new VoiceName("dfki-spike");
        MaryStickmanPhonemes phonemesList = mock(MaryStickmanPhonemes.class);
        MaryTTsSpeaker maryS = new MaryTTsSpeaker(speech, "en", voiceName, phonemesList);
        WordTimeMarkSequence wts = maryS.getWordTimeSequence();
        ArrayList seq = wts.getSequence();
        assertEquals(blocks.size(), seq.size());
        assertEquals(blocks.get(0), seq.get(0).toString());
        assertEquals(blocks.get(3), seq.get(3).toString());
        assertEquals("Hola Mundo ", wts.getText());
    }
}
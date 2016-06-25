package de.dfki.vsm.util.tts;

import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.tts.cereproc.CereProgTTsSpeaker;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 25/06/16.
 * Integration test for cereprog library
 */
public class CereProgTTsSpeakerTest {
    public static final String HELLO_TEXT = "Hello";
    private CereProgTTsSpeaker cereprog;

    private void setText(){

    }
    @org.junit.Before
    public void setUp() throws Exception {
        cereprog = null;
        System.setProperty("java.library.path", "/home/alvaro/Documentos/Tesis/cerevoice_sdk_3.2.0_linux_x86_64_python26_10980_academic/cerevoice_eng/javalib");

    }

    public CereProgTTsSpeaker makeCereprog(SpeechActivity sa){
        cereprog = new CereProgTTsSpeaker(sa, "en", "");
        return cereprog;
    }

    @Test
    public void testGetWordPhonemeList_WordTextWiht4Phomenes_4Phonemes() throws Exception {
        LinkedList<String> words = new LinkedList();
        words.add(HELLO_TEXT);
        SpeechActivity speech =  new SpeechActivity("actor", words, "$");
        makeCereprog(speech);
        LinkedList<Phoneme> resultPhonemes =  cereprog.getWordPhonemeList(0);
        assertEquals(4, resultPhonemes.size());
    }

    @Test
    public void testGetWordPhonemeList_WordText_AllPhonemes() throws Exception {
        LinkedList<String> words = new LinkedList();
        words.add(HELLO_TEXT);
        SpeechActivity speech =  new SpeechActivity("actor", words, "$");
        makeCereprog(speech);
        LinkedList<Phoneme> resultPhonemes =  cereprog.getWordPhonemeList(0);
        assertEquals("h", resultPhonemes.get(0).getmValue());
        assertEquals("@", resultPhonemes.get(1).getmValue());
        assertEquals("ou", resultPhonemes.get(3).getmValue());
        assertTrue(resultPhonemes.get(0).getmEnd() > 0);
        assertEquals(4, resultPhonemes.size());
    }

    @Test
    public void testGetWordPhonemeList_EmptyText_EmptyList(){
        LinkedList<String> words = new LinkedList();
        SpeechActivity speech =  new SpeechActivity("actor", words, "$");
        makeCereprog(speech);
        LinkedList<Phoneme> resultPhonemes =  cereprog.getWordPhonemeList(0);
        assertEquals(0, resultPhonemes.size());
    }

    @Test
    public void testSpeak_Text_SpokenText() throws Exception {
        LinkedList<String> words = new LinkedList();
        words.add(HELLO_TEXT);
        SpeechActivity speech =  new SpeechActivity("actor", words, "$");
        makeCereprog(speech);
        String spokenText = cereprog.speak("Id");
        assertTrue(spokenText.contains(HELLO_TEXT));
    }

    @Test
    public void testSpeak_EmptyText_EmptySpoken() throws Exception {
        LinkedList<String> words = new LinkedList();
        SpeechActivity speech =  new SpeechActivity("actor", words, "$");
        makeCereprog(speech);
        String spokenText = cereprog.speak("Id");
        assertEquals("", spokenText);
    }

    @org.junit.Test
    public void testGetWordTimeSequence() throws Exception {

    }

    @org.junit.Test
    public void testAddWordsToMaryClient() throws Exception {

    }
}
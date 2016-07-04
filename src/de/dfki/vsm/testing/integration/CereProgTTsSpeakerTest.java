package integration;

import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.util.evt.EventDispatcher;
import de.dfki.vsm.util.evt.EventListener;
import de.dfki.vsm.util.evt.EventObject;
import de.dfki.vsm.util.tts.cereproc.CereProgTTsSpeaker;
import de.dfki.vsm.xtension.baxter.BaxterExecutor;
import de.dfki.vsm.xtension.stickmanmarytts.action.ActionMouthActivity;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.events.LineStart;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.events.LineStop;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;
import de.dfki.action.sequence.Entry;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 25/06/16.
 * Integration test for cereprog library
 */
public class CereProgTTsSpeakerTest implements EventListener {
    public static final String HELLO_TEXT = "Hello";
    public static final String LONG_TEXT = "This is a longer text with more words. Use this in case you want me to speak longer, but no so much (wink, wink)";
    private CereProgTTsSpeaker cereprog;
    private HashMap<Integer, CereProgTTsSpeaker> cereProgTTsSpeakerHashMap = new HashMap<>();

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

    @Test
    public void testSyncronizationPhonemesVoice_SeveralThreads_NoException() throws Exception {

        EventDispatcher mEventDispatcher = EventDispatcher.getInstance();
        mEventDispatcher.register(this);
        for(int i=0; i< 4; i++) {
            LinkedList<String> words = new LinkedList();
            words.add(LONG_TEXT);
            final SpeechActivity speech = new SpeechActivity("actor", words, "$");
            CereProgTTsSpeaker c = new CereProgTTsSpeaker(speech, "en", "");
            cereProgTTsSpeakerHashMap.put(i, c);
            final int index = i;
            //tryToSpeak(c, index);
            Thread s = new Thread() {
                public void run() {
                    try {
                        tryToSpeak(c, index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            s.start();
            s.join();
        }

    }

    private void tryToSpeak(CereProgTTsSpeaker cere, int id) throws Exception {

        System.out.println("Created");
        String spokenText = cere.speak(Integer.toString(id));
    }

    @Override
    public void update(EventObject event) {
        if(event instanceof LineStart) {
            LinkedList<String> words = new LinkedList();
            words.add(LONG_TEXT);
            String executionId = ((LineStart) event).getExecutionId();
            SpeechActivity speech =  new SpeechActivity("actor", words, "$");
            try {
                int id = Integer.parseInt(executionId);
                UpdatedSpeech(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void UpdatedSpeech(int executionId){

        CereProgTTsSpeaker c = cereProgTTsSpeakerHashMap.get(executionId);
        LinkedList phoneme =  c.getWordPhonemeList(2);
        assertTrue(phoneme.size() > 0);

    }
}
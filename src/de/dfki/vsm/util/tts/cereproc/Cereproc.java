package de.dfki.vsm.util.tts.cereproc;

import com.cereproc.cerevoice_eng.SWIGTYPE_p_CPRCEN_engine;
import com.cereproc.cerevoice_eng.TtsEngineCallback;
import com.cereproc.cerevoice_eng.*;
import de.dfki.vsm.util.evt.EventDispatcher;
import de.dfki.vsm.util.tts.SpeechClient;
import de.dfki.vsm.util.tts.cereproc.util.Audioline;
import de.dfki.vsm.util.tts.cereproc.util.CereprocLoader;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.events.LineStop;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by alvaro on 25/06/16.
 */
public class Cereproc extends SpeechClient {
    private SWIGTYPE_p_CPRCEN_engine eng;
    private int chan_handle;
    private TtsEngineCallback phonemeCallback;
    private TtsEngineCallback genericCallback;
    private PhrasePhonemeCache phonemeCache;
    private final EventDispatcher mEventCaster = EventDispatcher.getInstance();
    private byte[] utf8bytes;
    private  CereprocLoader cereprocLoader;
    static {
        // The cerevoice_eng library must be on the path,
        // specify with eg:
        // java -Djava.library.path=/path/to/library/
        //System.setProperty("java.library.path", "/home/alvaro/Documentos/Tesis/cerevoice_sdk_3.2.0_linux_x86_64_python26_10980_academic/cerevoice_eng");

        System.loadLibrary("cerevoice_eng");
    }


    public Cereproc(final String licenseNamePath, final String voicePath){
        cereprocLoader = new CereprocLoader(voicePath, licenseNamePath);
        phonemeCache = new PhrasePhonemeCache();
        eng = cereprocLoader.getEng();
        chan_handle = cereprocLoader.getChan_handle();
    }

    public Cereproc() {

    }

    public String speak(String executionId){
        Audioline au = initializeSpeak(executionId);
        String spokenText = "";
        try {
            //Notification is sent from callback
            speak(au);
            spokenText = finalWord;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearWordQueue();
            notifyEndOfSpeech(executionId);
        }
        return spokenText;
    }

    private Audioline initializeSpeak(String executionId) {
        final Audioline au = cereprocLoader.openAudioLine();
        getPhrase();
        setGenericCallback(au, executionId);
        return au;
    }

    private void notifyEndOfSpeech(String executionId) {
        mEventCaster.convey(new LineStop(this, executionId));
    }

    public HashMap<Integer, LinkedList<Phoneme>> getPhonemes() throws Exception {
        try {
            if(phonemeCache.isPhraseCached(finalWord)) {
                return phonemeCache.retrieve(finalWord);
            }else{
                getPhrase();
                isTextNonEmpty();
                final Audioline au = initializePhoneme();
                return tryGetPhonemes(au);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }


    private Audioline initializePhoneme() {
        final Audioline au = cereprocLoader.openAudioLine();
        setPhonemeCallback(au);//Callback for capturing phoneme data
        return au;
    }

    private HashMap<Integer, LinkedList<Phoneme>> tryGetPhonemes(Audioline au) throws UnsupportedEncodingException {
            HashMap<Integer, LinkedList<Phoneme>> phonemes = new HashMap<Integer, LinkedList<Phoneme>>();
            utf8bytes = finalWord.getBytes("UTF-8");
            cerevoice_eng.CPRCEN_engine_channel_speak(eng, chan_handle, finalWord + "\n", utf8bytes.length + 1, 0); // Stream data to engine
            cerevoice_eng.CPRCEN_engine_channel_speak(eng, chan_handle, "", 0, 1); // Flush engine
            au.flush();
            phonemeCallback.ClearCallback(eng, chan_handle);
            cerevoice_eng.CPRCEN_engine_channel_close(eng, chan_handle); // Close the channel
            cerevoice_eng.CPRCEN_engine_delete(eng); //Delete the engine
            phonemes = ((PhonemeCallback) phonemeCallback).getPhonemes();
            clearWordQueue();
            return phonemes;
    }

    private void isTextNonEmpty() throws Exception {
        if("".equals(finalWord)){
            throw new Exception("Empty Text, could not speak");
        }
    }

    private void speak(final Audioline au) throws Exception {
        HashMap<Integer, LinkedList<Phoneme>> phonemes = new HashMap<Integer, LinkedList<Phoneme>>();
        isTextNonEmpty();
        utf8bytes = finalWord.getBytes("UTF-8");
        cerevoice_eng.CPRCEN_engine_channel_speak(eng, chan_handle, finalWord + "\n", utf8bytes.length + 1, 0);// Stream data to engine
        cerevoice_eng.CPRCEN_engine_channel_speak(eng, chan_handle, "", 0, 1);// Flush engine
        au.flush();
        genericCallback.ClearCallback(eng, chan_handle);
        cerevoice_eng.CPRCEN_engine_channel_close(eng, chan_handle);// Close the channel
        cerevoice_eng.CPRCEN_engine_delete(eng);//Delete the engine
        phonemes = ((GenericCallback) genericCallback).getPhonemes();
        phonemeCache.add(finalWord, phonemes);
    }


    private void setAudioCallback(Audioline au) {
        TtsEngineCallback speekCallback = new SpeakCallback(au.line());
        speekCallback.SetCallback(eng, chan_handle);
    }

    private void setGenericCallback(Audioline au, String executionId) {
        genericCallback = new GenericCallback(au.line(), executionId, phonemeCache, finalWord);
        genericCallback.SetCallback(eng, chan_handle);
    }

    private void setPhonemeCallback(Audioline au) {
        //The callback function, if set, is fired for every phrase returned by the synthesiser.
        phonemeCallback = new PhonemeCallback(au.line());
        phonemeCallback.SetCallback(eng, chan_handle);
    }


    @Override
    public void addWord(String s) {
        wordQueue.add(s);
    }

    private void clearWordQueue(){
        finalWord = "";
        wordQueue.clear();
    }
}



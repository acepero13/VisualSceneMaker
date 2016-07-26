package de.dfki.vsm.util.tts.cereproc;

import com.cereproc.cerevoice_eng.SWIGTYPE_p_CPRCEN_engine;
import com.cereproc.cerevoice_eng.TtsEngineCallback;
import com.cereproc.cerevoice_eng.*;
import de.dfki.vsm.util.evt.EventDispatcher;
import de.dfki.vsm.util.tts.SpeechClient;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.events.LineStart;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.events.LineStop;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by alvaro on 25/06/16.
 */
public class Cereprog extends SpeechClient {
    private SWIGTYPE_p_CPRCEN_engine eng;
    private int chan_handle, res;
    //TODO: Read from config file
    private String voice_name =   "cerevoice_heather_3.2.0_48k.voice";
    private String license_name = "license.lic";
    private String rate_str;
    TtsEngineCallback speekCallback;
    TtsEngineCallback phonemeCallback;
    TtsEngineCallback genericCallback;
    HashMap<String, HashMap<Integer, LinkedList<Phoneme>>> phrasePhonemes = new HashMap<>();
    private final EventDispatcher mEventCaster = EventDispatcher.getInstance();

    Float rate;
    byte[] utf8bytes;
    static {
        // The cerevoice_eng library must be on the path,
        // specify with eg:
        // java -Djava.library.path=/path/to/library/
        //System.setProperty("java.library.path", "/home/alvaro/Documentos/Tesis/cerevoice_sdk_3.2.0_linux_x86_64_python26_10980_academic/cerevoice_eng");

        System.loadLibrary("cerevoice_eng");
    }

    public Cereprog(){
        init();
        wordQueue =  Collections.synchronizedList(new LinkedList());
        finalWord = "";
    }

    public Cereprog(String licenseNamePath, String voicePath){
        license_name = licenseNamePath;
        voice_name = voicePath;
        init();
        wordQueue =  Collections.synchronizedList(new LinkedList());
        finalWord = "";
    }


    private void init() {
        try {
            initCereproc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCereproc() throws Exception {
        loadVoice();
        openDefaultChannel();
    }

    public void setText(String text){
        finalWord = text;
    }

    public String speak(String executionId){
        Audioline au = initSpeak(executionId);
        String spokenText = "";
        try {
            speak(au);
            spokenText = finalWord;
            mEventCaster.convey(new LineStart(this,  executionId)); //Notify we start speaking
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearWordQueue();
            mEventCaster.convey(new LineStop(this, executionId)); //Notify the speach is done
            return spokenText;
        }
    }

    private Audioline initSpeak(String executionId) {
        Audioline au = openAudioLine(executionId);
        getPhrase();
        //setAudioCallback(au);
        setGenericCallback(au);
        return au;
    }


    public HashMap<Integer, LinkedList<Phoneme>> getPhonemes() throws Exception {
        try {
            if(isPhonemePhraseCached()) {
                return phrasePhonemes.get(finalWord);
            }else{
                getPhrase();
                isTextNonEmpty();
                Audioline au = initPhoneme();
                return tryGetPhonemes(au);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new HashMap<Integer, LinkedList<Phoneme>>();
        }
    }

    private boolean isPhonemePhraseCached() {
        return phrasePhonemes.containsKey(finalWord);
    }

    private Audioline initPhoneme() {
        Audioline au = openAudioLine();
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
        if(finalWord.equals("")){
            throw new Exception("Empty Text, could not speak");
        }
    }

    private void speak(Audioline au) throws Exception {
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
        phrasePhonemes.put(finalWord, phonemes);
    }

    private Audioline openAudioLine(String executionId) {
        Audioline au =  getAudioline();
        return au;
    }

    private Audioline openAudioLine() {
        return getAudioline();
    }

    private Audioline getAudioline() {
        rate_str = cerevoice_eng.CPRCEN_channel_get_voice_info(eng, chan_handle, "SAMPLE_RATE");
        rate = new Float(rate_str);
        Audioline au = new Audioline(rate.floatValue());
        return au;
    }

    private void setAudioCallback(Audioline au) {
        speekCallback = new SpeakCallback(au.line());
        speekCallback.SetCallback(eng, chan_handle);
    }

    private void setGenericCallback(Audioline au) {
        genericCallback = new GenericCallback(au.line());
        genericCallback.SetCallback(eng, chan_handle);
    }

    private void setPhonemeCallback(Audioline au) {
        //The callback function, if set, is fired for every phrase returned by the synthesiser.
        phonemeCallback = new PhonemeCallback(au.line());
        phonemeCallback.SetCallback(eng, chan_handle);
    }

    private void loadVoice() throws Exception {
        eng = cerevoice_eng.CPRCEN_engine_new();
        res = cerevoice_eng.CPRCEN_engine_load_voice(eng, license_name, "", voice_name,
                CPRC_VOICE_LOAD_TYPE.CPRC_VOICE_LOAD);
        if (res == 0) {
            throw new Exception("ERROR: unable to load voice file '" + voice_name + "', exiting");
        }
    }

    private void openDefaultChannel() {
        chan_handle = cerevoice_eng.CPRCEN_engine_open_default_channel(eng);
    }

    public void addWord(String s) {
        wordQueue.add(s);
    }

    private void clearWordQueue(){
        finalWord = "";
        wordQueue.clear();
    }
}

// Simple class to wrap a Java audio line
class Audioline {
    private SourceDataLine line;
    private AudioFormat format;

    public Audioline(float sampleRate){
        format =  getAudioFormat(sampleRate);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                format);
        // Obtain and open the line.
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public SourceDataLine line(){
        return line;
    }

    private static AudioFormat getAudioFormat(float sampleRate){
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }

    public void flush(){
        line.drain();
        line.close();
    }
}


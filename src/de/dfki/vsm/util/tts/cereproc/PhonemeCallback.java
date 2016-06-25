package de.dfki.vsm.util.tts.cereproc;

import com.cereproc.cerevoice_eng.*;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;

import javax.sound.sampled.SourceDataLine;
import java.util.LinkedList;

/**
 * Created by alvaro on 25/06/16.
 */
public class PhonemeCallback extends TtsEngineCallback {
    private SourceDataLine line;
    private LinkedList<Phoneme> phonemes;
    public PhonemeCallback(SourceDataLine line) {
        this.line = line;
        phonemes = new LinkedList<>();
    }

    public void Callback(SWIGTYPE_p_CPRC_abuf abuf) {
        System.out.println("INFO: firing engine callback");
        int i, sz;
        // sz is the number of 16-bits samples
        System.out.println("INFO: checking audio size");
        sz =  cerevoice_eng.CPRC_abuf_wav_sz(abuf);
        byte[] b = new byte[sz * 2];
        byte b1, b2;
        short s;
        SWIGTYPE_p_CPRC_abuf_trans trans;
        CPRC_ABUF_TRANS_TYPE transtype;
        float start, end;
        String name;

        for(i = 0; i < cerevoice_eng.CPRC_abuf_trans_sz(abuf); i++) {
            trans = cerevoice_eng.CPRC_abuf_get_trans(abuf, i);
            transtype = cerevoice_eng.CPRC_abuf_trans_type(trans);
            start = cerevoice_eng. CPRC_abuf_trans_start(trans);
            end = cerevoice_eng.CPRC_abuf_trans_end(trans);
            name = cerevoice_eng.CPRC_abuf_trans_name(trans);
            if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_PHONE) {
                phonemes.add(new Phoneme(name, (long) (start*1000), (long) (end * 1000)));
                System.err.printf("INFO: phoneme: %.3f %.3f %s\n", start, end, name);
            }
            else if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_WORD) {
                System.err.printf("INFO: word: %.3f %.3f %s\n", start, end, name);
            }
            else if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_MARK) {
                System.err.printf("INFO: marker: %.3f %.3f %s\n", start, end, name);
            }
            else if (transtype == CPRC_ABUF_TRANS_TYPE.CPRC_ABUF_TRANS_ERROR) {
                System.err.printf("ERROR: could not retrieve transcription at '%d'", i);
            }
        }

    }

    public LinkedList<Phoneme> getPhonemes(){
        return phonemes;
    }
}

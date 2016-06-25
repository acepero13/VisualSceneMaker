package de.dfki.vsm.xtension.stickmanmarytts.util.tts;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alvaro on 25/06/16.
 */
public abstract class SpeechClient {
    protected List wordQueue = new LinkedList<>();
    protected String finalWord;
    public abstract void addWord(String s);
    protected String getPhrase() {
        synchronized(wordQueue){
            if(finalWord.length() <=0){
                Iterator it = wordQueue.iterator();
                while (it.hasNext()) {
                    String word = (String) it.next();
                    finalWord += word + " ";
                }
            }
        }
        return finalWord;
    }
}

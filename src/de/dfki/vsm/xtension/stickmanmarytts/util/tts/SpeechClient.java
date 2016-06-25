package de.dfki.vsm.xtension.stickmanmarytts.util.tts;


import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by alvaro on 25/06/16.
 */
public abstract class SpeechClient {
    protected LinkedList<String> wordQueue = new LinkedList<>();
    protected String finalWord;
    public abstract void addWord(String s);
    protected String getPhrase() {
        synchronized(wordQueue){
            Iterator it = wordQueue.iterator();
            while (it.hasNext()) {
                String word = (String) it.next();
                finalWord += word + " ";
            }
        }
        return finalWord;
    }
}

package de.dfki.vsm.xtension.voiceover;

import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.ActionActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.util.evt.EventObject;
import de.dfki.vsm.xtension.baxter.BaxterExecutor;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.events.LineStart;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.events.LineStop;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alvaro on 6/4/16.
 */
public class VoiceOverExecutorTest {
    private VoiceOverExecutor executor;
    private EditorProject project = new EditorProject();

    @Before
    public void setUp(){
        executor = null;
        project.parse(new File("/home/alvaro/Documents/WorkHiwi/VSM_stable/Projects/Baxter").getPath());
        executor = new VoiceOverExecutor(project.getPluginConfig("baxter"), project);
    }

    @Test
    public void testMarker() throws Exception {
        assertEquals("$__13", executor.marker(13));
    }

    @Test
    public void testExecute() throws Exception {
        SpeechActivity speech =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        VoiceOverExecutor spyExecutor = Mockito.spy(executor);
        when(spyExecutor.intentToSpeak(any(String.class), any(AbstractActivity.class))).thenReturn("Hello World");
        ActivityWorker task = new ActivityWorker(0, new LinkedList<>(), speech, spyExecutor);
        task.start();
        Thread.sleep(500);
        assertEquals(1, executor.getHowManyWorkers());
    }

    @Test
    public void testExecuteNonSpeechActivity() throws Exception {
        ActionActivity activity = new ActionActivity("actor", "mode", "name", "text");
        executor.execute(activity);
        assertEquals(0, executor.getHowManyWorkers());
    }

    @Test
    public void testExecuteNonThreadActivityWorker() throws Exception {
        SpeechActivity speech =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        VoiceOverExecutor spyExecutor = Mockito.spy(executor);
        when(spyExecutor.intentToSpeak(any(String.class), any(AbstractActivity.class))).thenReturn("Hello World");
        spyExecutor.execute(speech);
        assertEquals(0, spyExecutor.getHowManyWorkers());

        executor.execute(speech);
        assertEquals(0, spyExecutor.getHowManyWorkers());
    }

    @Test
    public void testHandleAudionEnd() throws InterruptedException {
        VoiceOverExecutor spyExecutor = getVoiceOverExecutor();
        assertEquals(1, executor.getHowManyWorkers());
        EventObject eventObject = new LineStop(new Object(), "my_speak_activity");
        spyExecutor.update(eventObject);
        assertEquals(0, executor.getHowManyWorkers());
    }

    @Test
    public void testHandleAudionEndOtherEvent() throws InterruptedException {
        VoiceOverExecutor spyExecutor = getVoiceOverExecutor();
        assertEquals(1, executor.getHowManyWorkers());
        EventObject eventObject = new LineStart(new Object(), "my_speak_activity");
        spyExecutor.update(eventObject);
        assertEquals(1, executor.getHowManyWorkers());
    }

    private VoiceOverExecutor getVoiceOverExecutor() throws InterruptedException {
        SpeechActivity speech =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        VoiceOverExecutor spyExecutor = Mockito.spy(executor);
        when(spyExecutor.getExecutionId()).thenReturn("my_speak_activity");
        when(spyExecutor.intentToSpeak(any(String.class), any(AbstractActivity.class))).thenReturn("Hello World");
        ActivityWorker task = new ActivityWorker(0, new LinkedList<>(), speech, spyExecutor);
        task.start();
        Thread.sleep(500);
        return spyExecutor;
    }

    private LinkedList getHelloWordSpeechBlockForSpeechActivity(){
        LinkedList<String> blocks = new LinkedList();
        blocks.add("$1");
        blocks.add("Hello");
        blocks.add("VoiceOver");
        blocks.add("$2");
        return blocks;
    }
}
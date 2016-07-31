package de.dfki.vsm.xtension.baxter;

import de.dfki.action.sequence.TimeMark;
import de.dfki.action.sequence.Word;
import de.dfki.action.sequence.WordTimeMarkSequence;
import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.runtime.activity.ActionActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.util.tts.marytts.MaryTTsProcess;
import de.dfki.vsm.util.tts.marytts.MaryTTsSpeaker;
import de.dfki.vsm.util.tts.marytts.phonemes.EnglishPhoneme;
import de.dfki.vsm.xtension.baxter.action.SpeakerActivity;
import de.dfki.vsm.xtension.baxter.action.TimeMarkActivity;
import de.dfki.vsm.xtension.baxter.utils.BaxterServerProcess;
import de.dfki.vsm.xtension.stickmanmarytts.action.ActionMouthActivity;
import de.dfki.vsm.xtension.stickmanmarytts.util.tts.sequence.Phoneme;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by alvaro on 5/31/16.
 */
public class BaxterExecutorTest {
    private  BaxterExecutor executor;
    private EditorProject project = new EditorProject();
    private boolean  launched = false;



    @Before
    public void setUp(){
        executor = null;
        project.parse(new File("res/tutorials/6-MaryTTS").getPath());
        executor = new BaxterExecutor(project.getPluginConfig("baxter"), project);
    }
    @Test
    public void testExecute_SpeechActivity_ActivityAdded() throws Exception { //It will throw an execption
        SpeechActivity speech =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        executor.execute(speech);
        HashMap activities = (HashMap) getPrivateExecutorField("speechActivities");
        assertEquals(1, activities.size());
    }

    @Test
    public void testAccept_Socket_AddedToClientList() throws IOException {
        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });

        when(socket.getOutputStream()).thenReturn(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        });
        executor.accept(socket);
        HashMap clients = (HashMap) getPrivateExecutorField("mClientMap");
        assertEquals(1, clients.size());
    }

    @Test
    public void executeActionExecuteActionTimeMark_TimeMark_HandleClusterActionCalled(){
        BaxterExecutor spyExecutor = Mockito.spy(executor);
        WordTimeMarkSequence wts = new WordTimeMarkSequence();
        wts.add(new TimeMark("$3"));
        wts.add(new Word("Hello"));
        doNothing().when(spyExecutor).handleClusterActions(any());
        TimeMarkActivity activity = new TimeMarkActivity("none", "none", "", wts);
        try {
            spyExecutor.execute(activity);

        }catch (NullPointerException e){
        }
        verify(spyExecutor, times(1)).handleClusterActions(activity.getCluster().get(0));
    }


    @Test
    public void testExecuteSpeech() throws Exception {
        HashMap speechActivities = (HashMap) getPrivateExecutorField("speechActivities");
        SpeakerActivity speakerActivity = mock(SpeakerActivity.class);
        speechActivities.put("my_activity", speakerActivity);
        BaxterExecutor spyExecutor = Mockito.spy(executor);
        when(spyExecutor.intentToSpeak("my_speak_activity")).thenReturn("Hello World");
        when(spyExecutor.getExecutionId()).thenReturn("my_speak_activity");
        SpeechActivity speech =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        ActivityWorker task = new ActivityWorker(0, new LinkedList<>(), speech, spyExecutor);
        task.start();
        Thread.sleep(500);
        HashMap mActivityWorkerMap = (HashMap) getPrivateExecutorField("mActivityWorkerMap");
        assertEquals(1, mActivityWorkerMap.size());
        assertTrue(mActivityWorkerMap.containsKey("my_speak_activity"));
        when(spyExecutor.intentToSpeak("my_speak_activity2")).thenReturn("Hello World");
        when(spyExecutor.getExecutionId()).thenReturn("my_speak_activity2");

        SpeechActivity speech3 =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        spyExecutor.execute(speech3);
        HashMap activities = (HashMap) getPrivateExecutorField("speechActivities");
        assertEquals(3, activities.size());
    }

    @Test
    public void testMarker(){
        assertEquals("$13", executor.marker(13));
    }

    @Test
    public void testLaunchNotFoundServer(){
        BaxterServerProcess processMock = mock(BaxterServerProcess.class);
        try {
            doThrow(new FileNotFoundException("Server not found")).when(processMock).launchBaxterServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            assertTrue(false);
            e.printStackTrace();
        }
        setPrivateExecutorField("baxterServerProcess", processMock);
        executor.launch();
        assertTrue(true);

    }

    @Test
    public void testExceptionForUnload(){
        BaxterServerProcess processMock = mock(BaxterServerProcess.class);
        try {
            doThrow(new IOException("IOxpetion")).when(processMock).unloadBaxterServer();
        } catch (IOException e) {
            assertTrue(true);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setPrivateExecutorField("baxterServerProcess", processMock);
        executor.unload();
        assertTrue(true);
    }

    @Test
    public void testExectionBadListener(){
        setPrivateExecutorField("mListener", new BaxterListener(12,null));
        executor.unload();
        assertTrue(true);
    }

    @Test
    public void testExectionBadListenerException(){
        MaryTTsProcess processMock = mock(MaryTTsProcess.class);
        try {
            doThrow(new IOException("IOxpetion")).when(processMock).stopMaryServer();
        } catch (IOException e) {
            assertTrue(true);
            e.printStackTrace();
        }
        setPrivateExecutorField("marySelfServer",processMock);
        executor.unload();
        assertTrue(true);
    }

    @Test
    public void testExceptionInterruptForUnload(){
        BaxterServerProcess processMock = mock(BaxterServerProcess.class);
        try {
            doThrow(new InterruptedException("Interrupted")).when(processMock).unloadBaxterServer();
        } catch (IOException e) {
            assertTrue(true);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setPrivateExecutorField("baxterServerProcess", processMock);
        executor.unload();
        assertTrue(true);
    }

    @Test
    public void testScheduleSpeech() throws Exception {
        SpeechActivity speech =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        HashMap speechActivities = (HashMap) getPrivateExecutorField("speechActivities");
        SpeakerActivity mockSActivity = mock(SpeakerActivity.class);
        MaryTTsSpeaker mockSpeaker = mock(MaryTTsSpeaker.class);
        when(mockSpeaker.getWordTimeSequence()).thenReturn(new WordTimeMarkSequence());
        when(mockSpeaker.getSpeechActivityTextBlocs()).thenReturn(getHelloWordSpeechBlockForSpeechActivity());
        when(mockSpeaker.getWordPhonemeList(any(Integer.class))).thenReturn(getWordPhonemeList());

        when(mockSActivity.getTtsSpeak()).thenReturn(mockSpeaker);
        when(mockSActivity.getSpeechActivity()).thenReturn(speech);
        speechActivities.put("my_activity", mockSActivity);
        assertEquals(speech, executor.scheduleSpeech("my_activity"));
    }

    private LinkedList<Phoneme> getWordPhonemeList() {
        LinkedList<Phoneme> phonemes = new LinkedList<>();
        phonemes.add( new EnglishPhoneme("A", 0, 10));
        phonemes.add(new EnglishPhoneme("O", 10, 20));
        phonemes.add( new EnglishPhoneme("u", 20, 30));
        phonemes.add(new EnglishPhoneme("This is a bad phoneme", 30,40));
        return phonemes;
    }

    @Test
    public void executeEmptySpeech(){
        executor.execute(null);
        assertTrue(true);
    }




    @Test
    public void executeAnimation(){
        ActionActivity activity = new ActionActivity("baxter", "mode", "Smile", "Texto");
        executor.execute(activity);
        HashMap speechActivities = (HashMap) getPrivateExecutorField("speechActivities");
        assertEquals(0, speechActivities.size());
    }

    @Test
    public void testActionMouth(){
        ActionMouthActivity activityMouth = new ActionMouthActivity("baxter", "mode", "Smile", "Texto", 1000);
        executor.execute(activityMouth);
        HashMap speechActivities = (HashMap) getPrivateExecutorField("speechActivities");
        assertEquals(0, speechActivities.size());

    }

    @Test
    public void testEmptyBaxter(){
        ActionMouthActivity activityMouth = new ActionMouthActivity("baxter", "mode", "Smile", "Texto", 1000);
        setPrivateExecutorField("baxterStickman", null);
        executor.execute(activityMouth);
        HashMap speechActivities = (HashMap) getPrivateExecutorField("speechActivities");
        assertEquals(0, speechActivities.size());
    }





    @Test
    public void testHandleAudio(){
        HashMap activityWorker = (HashMap) getPrivateExecutorField("mActivityWorkerMap");
        activityWorker.put("mary_20", null);
        if(activityWorker.containsKey("mary_20")){
            assertTrue(true);
        }
        executor.handle("HOLA MUNDO", null);
        assertEquals(1, activityWorker.size());
        executor.handle("#AUDIO#end#mary_20", null);
        assertEquals(0, activityWorker.size());

    }

    @Test
    public void testHandleAudioAnimMessage(){
        HashMap activityWorker = (HashMap) getPrivateExecutorField("mActivityWorkerMap");
        activityWorker.put("mary_20", null);
        if(activityWorker.containsKey("mary_20")){
            assertTrue(true);
        }
        executor.handle("HOLA MUNDO", null);
        assertEquals(1, activityWorker.size());
        executor.handle("#ANIM#end#anim_20", null);
        assertEquals(1, activityWorker.size());

    }

    @Test
    public void testHandleAnimation(){
        HashMap activityWorker = (HashMap) getPrivateExecutorField("mActivityWorkerMap");
        activityWorker.put("anim_20", null);
        if(activityWorker.containsKey("anim_20")){
            assertTrue(true);
        }
        executor.handle("HOLA MUNDO", null);
        assertEquals(1, activityWorker.size());
        executor.handle("#ANIM#end#anim_20", null);
        assertEquals(0, activityWorker.size());
    }



    private Object getPrivateExecutorField(String fieldName){
        Field field = null;
        try {
            field = BaxterExecutor.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            return field.get(executor);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new Object();
    }

    private void setPrivateExecutorField(String fieldName, Object value){
        Field field = null;
        try {
            field = BaxterExecutor.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            field.set(executor, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private LinkedList getHelloWordSpeechBlockForSpeechActivity(){
        LinkedList<String> blocks = new LinkedList();
        blocks.add("$1");
        blocks.add("Hello");
        blocks.add("World");
        blocks.add("$2");
        return blocks;
    }
}
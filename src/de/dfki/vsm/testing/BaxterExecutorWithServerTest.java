
import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.model.project.PluginConfig;

import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.ActionActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.xtension.baxter.BaxterExecutor;
import de.dfki.vsm.xtension.baxter.BaxterHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alvaro on 5/24/16.
 */
public class BaxterExecutorWithServerTest {
    private  BaxterExecutor executor;
    private EditorProject project = new EditorProject();
    private boolean  launched = false;

    @Before
    public void setUp(){
        executor = null;
        project.parse(new File("/home/alvaro/Documents/WorkHiwi/VSM_stable/Projects/Baxter").getPath());
        executor = new BaxterExecutor(project.getPluginConfig("baxter"), project);
    }


    public void tearDown(){
        unload();
    }

    @Test
    public void testConnection(){
        //No va  a funcionar porque falta separar el .start del cuando inicia el baxter en el metodo launch
        executor.launch();
        HashMap clientMap = (HashMap) getPrivateExecutorField("mClientMap");
        assertTrue(clientMap.size()> 0);
        assertTrue(clientMap.containsKey("pythonServerBaxter"));
        BaxterHandler handler = (BaxterHandler) clientMap.get("pythonServerBaxter");
        BaxterHandler handlerSpy = Mockito.spy(handler);
        //handlerSpy.send("Test\n");
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object mock = invocation.getMock();
                String message = ((BaxterHandler)mock).getmInStream().readLine();
                System.out.println("Answer!!");
                System.out.println("Message:"  + message);
                assertEquals("CLIENTID#BAXTER1", message);
                ((BaxterHandler)mock).abort();

                return message;
            }
        }).when(handlerSpy).recv();

        handlerSpy.start();
        System.out.println("Sending..!!");
        handlerSpy.send("Test\n");

    }

    private void unload(){
        try {
            executor.unload();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
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

    private LinkedList getTestSpeechBlockForSpeechActivity(){
        LinkedList<String> blocks = new LinkedList();
        blocks.add("Test");
        blocks.add("Alvaro");
        return blocks;
    }


    @Test
    public void testLaunchWrongServerPath() throws Exception {
        Iterator projIterator = project.getProjectConfig().getPluginConfigList().iterator();
        while (projIterator.hasNext()){
            PluginConfig plugin = (PluginConfig) projIterator.next();
            plugin.setProperty("server", "wrong_path");
        }
        Method method = BaxterExecutor.class.getDeclaredMethod("getServerCmdPath", null);
        method.setAccessible(true);
        try {
            method.invoke(executor, null);
        }catch (Exception e){
            if(e.getCause().getMessage().equals("Baxter Server not found")){
                assertTrue(true);
            }else {
                assertTrue(false);
            }
        }
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

    @Test
    public void testSpeechToMouth(){
        unload();
        executor.launch();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, AbstractActivity> speechActivitiesCopy = new HashMap<>();
        SpeechActivity speech =  new SpeechActivity("baxter", getHelloWordSpeechBlockForSpeechActivity(), "$");
        speechActivitiesCopy.put("id1", speech);
        setPrivateExecutorField("speechActivities", speechActivitiesCopy );
        assertEquals(speech, executor.scheduleSpeech("id1"));
        unload();
    }


    private String executeAndReturnMsg(String[] cmd) throws IOException {
        ProcessBuilder   ps=new ProcessBuilder(cmd);
        String res = "";
        ps.redirectErrorStream(true);

        Process pr = null;
        try {
            pr = ps.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            res+= line;
        }
        try {
            pr.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ok!");

        in.close();
        System.out.println(res);
        return  res;
    }



}
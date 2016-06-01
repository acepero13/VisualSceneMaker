
import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.model.project.PluginConfig;

import de.dfki.vsm.xtension.baxter.BaxterExecutor;
import de.dfki.vsm.xtension.baxter.BaxterHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void testConnection() throws IOException {
        //No va  a funcionar porque falta separar el .start del cuando inicia el baxter en el metodo launch
        unload();
        BaxterExecutor executorSpy = Mockito.spy(executor);
        doNothing().when(executorSpy).startBaxterServer();
        try {
            executorSpy.launch();
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        BaxterHandler serverHandler = (BaxterHandler) getPrivateSpiedExecutorField(executorSpy, "baxterServerHandler");
        HashMap clientMap = (HashMap) getPrivateSpiedExecutorField(executorSpy,"mClientMap");
        clientMap.put("pythonServerBaxter", serverHandler);
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

    private Object getPrivateSpiedExecutorField(BaxterExecutor execut, String fieldName){
        Field field = null;
        try {
            field = BaxterExecutor.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        try {
            return field.get(execut);
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
    public void testUnload()  {
        String[] cmdForBaxterServer = {"/bin/sh","-c","ps -e -o cmd"};
        executor.launch();
        //Thread.sleep(1000);
        String res = null;
        try {
            res = executeAndReturnMsg(cmdForBaxterServer);
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(false);
        }
        if(res.contains("imageviwer")){
            assertTrue(true);
        }else {
            assertTrue(false);
        }
        unload();
        try {
            res = executeAndReturnMsg(cmdForBaxterServer);
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(false);
        }
        if(!res.contains("imageviwer")){
            assertTrue(true);
        }else {
            assertTrue(false);
        }

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
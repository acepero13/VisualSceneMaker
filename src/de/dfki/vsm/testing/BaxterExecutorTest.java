
import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.model.sceneflow.command.expression.condition.constant.Object;
import de.dfki.vsm.xtension.baxter.BaxterExecutor;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 5/24/16.
 */
public class BaxterExecutorTest {
    private BaxterExecutor executor;
    private EditorProject project = new EditorProject();
    @Before
    public void setUp(){
        project.parse(new File("/home/alvaro/Documents/WorkHiwi/VSM_stable/Projects/Baxter").getPath());
        executor = new BaxterExecutor(project.getPluginConfig("baxter"), project);
    }

    @Test
    public void testMarker() throws Exception {

    }

    @Test
    public void testExecute() throws Exception {

    }

    @Test
    public void testLaunch() throws Exception {
        executor.launch();
        assertTrue(true);
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



    @Test
    public void testUnload() throws Exception {
        String[] cmdForBaxterServer = {"/bin/sh","-c","ps -e -o cmd"};
        executor.launch();
        //Thread.sleep(1000);
        String res = executeAndReturnMsg(cmdForBaxterServer);
        if(res.contains("imageviwer")){
            assertTrue(true);
        }else {
            assertTrue(false);
        }
        executor.unload();
        //Thread.sleep(1000);
        res = executeAndReturnMsg(cmdForBaxterServer);
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
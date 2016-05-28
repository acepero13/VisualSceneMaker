package de.dfki.vsm.xtension.stickmanmarytts;

import de.dfki.vsm.editor.project.EditorProject;
import de.dfki.vsm.xtension.baxter.BaxterExecutor;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 5/28/16.
 */
public class StickmanMaryttsExecutorTest {
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
    public void testScheduleSpeech() throws Exception {

    }

    @Test
    public void testExecuteAnimation() throws Exception {

    }

    @Test
    public void testExecuteSpeachAndWait() throws Exception {

    }

    @Test
    public void testLaunch() throws Exception {

    }

    @Test
    public void testUnload() throws Exception {

    }

    @Test
    public void testHandle() throws Exception {

    }
}
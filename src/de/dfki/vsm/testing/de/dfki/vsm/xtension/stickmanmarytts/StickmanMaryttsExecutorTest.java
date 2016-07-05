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
    private StickmanMaryttsExecutor executor;
    private EditorProject project = new EditorProject();
    @Before
    public void setUp(){
        project.parse(new File("res/tutorials/6-MaryTTS").getPath());
        executor = new StickmanMaryttsExecutor(project.getPluginConfig("stickmanmarytts"), project);
    }
    @Test
    public void testMarker() throws Exception {
        String res = executor.marker(1);
        assertEquals("$1", res);
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
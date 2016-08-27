package de.dfki.vsm.xtension.baxter.utils;
import de.dfki.vsm.util.ios.IOSIndentWriter;
import de.dfki.vsm.util.xml.XMLUtilities;
import de.dfki.vsm.xtension.baxter.command.BaxterCommand;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by alvaro on 8/27/16.
 */
public class BaxterCommandSenderTest extends BaxterCommandServerWrapper{
    private BaxterCommandServerWrapper sender;

    public BaxterCommandSenderTest(){
        super(null);
    }

    @Before
    public void setUp() throws Exception {
        sender = this;
    }

    @Test
    public void testExecuteBaxterCommand_CommandNameNoParam_XML(){
        BaxterCommand command = sender.BaxterBuildCommand("look_left", new ArrayList<String>());
        String expected = "<?xmlversion=\"1.0\"encoding=\"UTF-8\"?><Commandmethod=\"look_left\"id=\"ID\"><params></params></Command>#END";
        String actual = getCommandAsString(command);
        assertEquals(expected, actual.replaceAll("\\s+",""));

    }


    @Override
    protected  void isHandlerNull() {
        int a = 0;
    }
}
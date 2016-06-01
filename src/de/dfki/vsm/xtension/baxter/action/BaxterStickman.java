package de.dfki.vsm.xtension.baxter.action;

import de.dfki.stickman.Stickman;
import de.dfki.stickman.animationlogic.AnimationLoader;
import de.dfki.vsm.runtime.activity.ActionActivity;
import de.dfki.vsm.util.ios.IOSIndentWriter;
import de.dfki.vsm.util.xml.XMLUtilities;
import de.dfki.vsm.xtension.baxter.command.BaxterCommand;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alvaro on 6/1/16.
 */
public class BaxterStickman {
    final private Stickman mBaxterStickman = new Stickman("Baxter", Stickman.TYPE.MALE, 2.0f, new Dimension(640, 480), false);

    //TODO: Refactorizar en nueva clase
    private String getImageHeadFromStickmanAnimation() {
        BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        BufferedImage head;
        Graphics g = image.createGraphics();
        mBaxterStickman.mShowStage = false;
        mBaxterStickman.paint(g);
        g.dispose();
        head = cropHead(image, (int) mBaxterStickman.mHead.getWidth(), (int) mBaxterStickman.mHead.getHeight(), 2.0f);
        String headAsString = transformStickmanToImage(head);
        return headAsString;
    }

    //TODO: Refactorizar en nueva clase
    private String transformStickmanToImage(BufferedImage head) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String imageString = "";
        try {
            ImageIO.write(head, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    //TODO: Refactorizar en nueva clase
    public String buildBaxterCommand(ArrayList<String> params) {
        BaxterCommand command = new BaxterCommand("paint", "testId", params);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOSIndentWriter iosw = new IOSIndentWriter(out);
        boolean r = XMLUtilities.writeToXMLWriter(command, iosw);
        String toSend = new String(out.toByteArray());
        toSend += "#END\n";
        return toSend;
    }

    //TODO: Refactorizar en nueva clase
    private BufferedImage cropHead(BufferedImage src, int width, int height, float scale) {
        BufferedImage dest = src.getSubimage(0, 0, width + 70, height - 50);
        return dest;
    }

    public String getAnimationImage() {
        return getImageHeadFromStickmanAnimation();
    }

    public void loadNonBlockingAnimation(String animationName, int animationDuration){
        AnimationLoader.getInstance().loadAnimation(mBaxterStickman, animationName, animationDuration, false);
        mBaxterStickman.doAnimation(animationName, 500, false);
    }

    public void loadBlockingAnimation(String animationName, int animationDuration){
        AnimationLoader.getInstance().loadAnimation(mBaxterStickman, animationName, animationDuration, true);
        mBaxterStickman.doAnimation(animationName, 500, false);
    }
}

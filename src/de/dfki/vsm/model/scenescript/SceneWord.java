package de.dfki.vsm.model.scenescript;

//~--- non-JDK imports --------------------------------------------------------

import de.dfki.vsm.util.ios.IOSIndentWriter;
import de.dfki.vsm.util.xml.XMLParseError;
import de.dfki.vsm.util.xml.XMLWriteError;

import org.w3c.dom.Element;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * @author Not me
 */
public class SceneWord extends UtteranceElement {

    // The Word Text
    private String mText;

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public SceneWord() {}

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public SceneWord(final int lower, final int upper, final String content) {

        // Initialize Boundary
        super(lower, upper);

        // Initialize Members
        mText = content;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final void setText(final String text) {
        mText = text;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final String getText() {
        return mText;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final String getText(final HashMap<String, String> args) {
        return mText;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final void writeXML(final IOSIndentWriter stream) throws XMLWriteError {
        stream.print("<SceneWord " + "lower=\"" + mLower + "\" " + "upper=\"" + mUpper + "\" " + "text=\"" + mText
                     + "\"/>");
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final void parseXML(final Element element) throws XMLParseError {

        // Parse The Boundary
        mLower = Integer.parseInt(element.getAttribute("lower"));
        mUpper = Integer.parseInt(element.getAttribute("upper"));

        // Parse The Text Content
        mText = element.getAttribute("text");
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final SceneWord getCopy() {
        return new SceneWord(mLower, mUpper, mText);
    }
}

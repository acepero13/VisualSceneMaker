/* The following code was generated by JFlex 1.4.3 on 7/25/16 9:29 PM */

////////////////////////////////////////////////////////////////////////////////
// Start User Code /////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// Package Definition
package de.dfki.vsm.model.scenescript;
// All Import Directives
import de.dfki.vsm.util.syn.*;
// Java Cup Runtime
import java.io.*;

////////////////////////////////////////////////////////////////////////////////
// End User Code ///////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 7/25/16 9:29 PM from the specification file
 * <tt>/home/alvaro/Documents/Universitat/TesisProject/VisualSceneMaker/src/de/dfki/vsm/model/scenescript/lexxer.jflex</tt>
 */
public final class ScriptLexxer extends SyntaxDocLexxer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YY_SCENE_NAME = 8;
  public static final int YY_TURN_BODY = 18;
  public static final int YY_SCENE_LANG = 6;
  public static final int YY_ACTION_BODY = 24;
  public static final int YY_TURN_HEAD = 12;
  public static final int YY_TURN_FOOT = 20;
  public static final int YY_SCENE_BODY = 10;
  public static final int YYCOMMENT = 28;
  public static final int YY_TURN_INIT = 16;
  public static final int YYINITIAL = 0;
  public static final int YY_TURN_NAME = 14;
  public static final int YY_SCENE_HEAD = 2;
  public static final int YY_SCENE_UNDL = 4;
  public static final int YY_ERROR_STATE = 26;
  public static final int YY_VARIABLE = 22;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8,  8,  9,  9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\16\1\20\1\0\1\16\1\17\22\0\1\16\1\13\1\35"+
    "\1\12\1\7\2\12\1\3\1\12\1\12\1\14\1\12\1\13\1\15"+
    "\1\10\1\40\1\32\11\31\1\2\1\13\1\33\1\6\1\37\1\13"+
    "\23\11\1\43\7\11\1\4\1\12\1\5\1\0\1\1\1\0\1\26"+
    "\1\11\1\41\1\11\1\24\1\25\1\36\1\34\3\11\1\27\1\11"+
    "\1\42\3\11\1\22\1\30\1\21\1\23\5\11\1\0\1\12\1\0"+
    "\1\12\66\0\1\11\12\0\3\11\1\0\1\11\1\0\6\11\2\0"+
    "\2\11\4\0\1\11\1\0\1\11\5\0\1\11\2\0\2\11\1\0"+
    "\1\11\1\0\1\11\1\0\6\11\2\0\2\11\4\0\1\11\1\0"+
    "\1\11\4\0\2\11\2\0\1\11\uff00\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\17\0\1\1\1\2\2\3\2\1\1\4\2\1\1\5"+
    "\1\6\1\7\2\10\1\11\2\12\1\13\1\1\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\15\1\22\1\15"+
    "\2\21\1\15\1\23\1\24\1\25\1\15\2\25\1\15"+
    "\1\26\1\15\2\27\1\30\1\31\1\32\1\33\1\27"+
    "\1\34\1\14\2\33\2\35\1\27\2\0\1\36\3\0"+
    "\1\37\2\33\5\0\2\33\1\40\1\0\1\41\2\0"+
    "\1\42\1\43\50\0\1\21\1\25";

  private static int [] zzUnpackAction() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\44\0\110\0\154\0\220\0\264\0\330\0\374"+
    "\0\u0120\0\u0144\0\u0168\0\u018c\0\u01b0\0\u01d4\0\u01f8\0\u021c"+
    "\0\u021c\0\u0240\0\u021c\0\u0264\0\u0288\0\u021c\0\u02ac\0\u02d0"+
    "\0\u02f4\0\u021c\0\u021c\0\u0318\0\u021c\0\u033c\0\u0360\0\u021c"+
    "\0\u021c\0\u0384\0\u021c\0\u021c\0\u021c\0\u021c\0\u021c\0\u03a8"+
    "\0\u03cc\0\u021c\0\u03f0\0\u0414\0\u021c\0\u0438\0\u021c\0\u021c"+
    "\0\u045c\0\u0480\0\u04a4\0\u021c\0\u04c8\0\u04ec\0\u0384\0\u021c"+
    "\0\u0510\0\u021c\0\u021c\0\u021c\0\u0534\0\u0558\0\u021c\0\u0384"+
    "\0\u057c\0\u05a0\0\u05c4\0\u05e8\0\u060c\0\u0630\0\u0654\0\u021c"+
    "\0\u0678\0\u069c\0\u0510\0\u021c\0\u06c0\0\u06e4\0\u0708\0\u072c"+
    "\0\u0750\0\u0774\0\u0798\0\u07bc\0\u07e0\0\u0708\0\u0804\0\u0654"+
    "\0\u0828\0\u084c\0\u0534\0\u021c\0\u0870\0\u0894\0\u08b8\0\u08dc"+
    "\0\u0900\0\u0924\0\u0948\0\u096c\0\u0990\0\u09b4\0\u09d8\0\u09fc"+
    "\0\u0a20\0\u0a44\0\u0a68\0\u0a8c\0\u0ab0\0\u0ad4\0\u0af8\0\u0b1c"+
    "\0\u0b40\0\u0b64\0\u0b88\0\u0bac\0\u0bd0\0\u0bf4\0\u0c18\0\u0c3c"+
    "\0\u0c60\0\u0c84\0\u0ca8\0\u0ccc\0\u0cf0\0\u0d14\0\u0d38\0\u0d5c"+
    "\0\u0d80\0\u0da4\0\u0dc8\0\u0dec\0\u0cf0\0\u0d14";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\16\20\1\21\1\22\1\23\7\20\1\24\7\20\1\25"+
    "\2\20\1\24\1\20\1\26\15\20\1\27\35\20\1\30"+
    "\5\20\1\27\1\20\10\30\3\20\1\30\1\20\1\30"+
    "\2\20\3\30\11\20\1\31\4\20\1\21\1\27\1\20"+
    "\10\31\3\20\1\31\1\20\1\31\2\20\3\31\2\20"+
    "\1\32\13\20\1\33\1\27\43\20\1\34\1\35\34\20"+
    "\1\36\4\20\1\33\1\37\1\40\10\36\3\20\1\36"+
    "\1\20\1\36\2\20\3\36\2\20\1\41\13\20\1\33"+
    "\1\42\1\43\23\20\3\44\1\45\1\46\2\44\1\47"+
    "\1\44\1\50\3\44\1\51\1\52\1\53\1\44\10\50"+
    "\1\54\1\55\1\56\1\50\1\44\1\50\2\44\3\50"+
    "\3\44\1\57\1\46\2\44\1\47\1\60\1\61\1\44"+
    "\1\60\1\44\1\62\1\33\1\53\1\44\10\61\1\63"+
    "\1\64\1\65\1\61\1\44\1\61\2\44\3\61\3\44"+
    "\1\45\1\46\2\44\1\47\1\44\1\50\3\44\1\51"+
    "\1\52\1\34\1\35\10\50\1\54\1\55\1\56\1\50"+
    "\1\44\1\50\2\44\3\50\11\44\1\66\5\44\1\67"+
    "\1\43\10\66\3\44\1\66\1\44\1\66\2\44\3\66"+
    "\3\70\1\71\1\70\1\72\1\73\1\74\1\70\1\75"+
    "\3\70\1\76\1\77\1\100\1\43\1\101\3\75\1\102"+
    "\3\75\1\103\1\104\1\70\1\75\1\70\1\75\2\70"+
    "\3\75\17\70\1\105\42\70\1\77\1\100\1\43\23\70"+
    "\64\0\1\23\64\0\1\106\16\0\1\107\47\0\1\20"+
    "\34\0\1\110\7\0\10\110\3\0\1\110\1\0\1\110"+
    "\2\0\3\110\1\0\1\31\7\0\1\31\7\0\12\31"+
    "\1\0\1\31\1\0\1\31\2\0\3\31\20\0\1\35"+
    "\24\0\1\36\7\0\1\36\7\0\12\36\1\0\1\36"+
    "\1\0\1\36\2\0\3\36\20\0\1\40\43\0\1\43"+
    "\34\0\1\50\7\0\10\50\3\0\1\50\1\0\1\50"+
    "\2\0\3\50\31\0\1\54\1\55\31\0\1\44\54\0"+
    "\2\54\37\0\1\111\26\0\1\61\7\0\10\61\3\0"+
    "\1\61\1\0\1\61\2\0\3\61\31\0\1\63\1\64"+
    "\42\0\2\63\37\0\1\112\16\0\1\66\7\0\1\66"+
    "\7\0\12\66\1\0\1\66\1\0\1\66\2\0\3\66"+
    "\1\0\2\113\1\114\13\113\2\0\12\113\1\0\1\113"+
    "\1\0\1\113\1\0\4\113\1\0\1\75\7\0\1\75"+
    "\7\0\12\75\1\0\1\75\1\0\1\75\2\0\3\75"+
    "\31\0\1\103\1\104\12\0\1\75\7\0\1\75\7\0"+
    "\1\75\1\115\10\75\1\0\1\75\1\0\1\75\2\0"+
    "\3\75\1\0\1\75\7\0\1\75\7\0\5\75\1\116"+
    "\4\75\1\0\1\75\1\0\1\75\2\0\3\75\10\0"+
    "\1\117\20\0\2\103\21\0\1\117\53\0\1\70\47\0"+
    "\1\120\17\0\14\107\1\121\3\107\1\0\23\107\16\0"+
    "\1\122\43\0\1\123\26\0\1\75\7\0\1\75\7\0"+
    "\2\75\1\124\7\75\1\0\1\75\1\0\1\75\2\0"+
    "\3\75\1\0\1\75\7\0\1\75\7\0\6\75\1\125"+
    "\3\75\1\0\1\75\1\0\1\75\2\0\3\75\31\0"+
    "\2\126\53\0\1\127\1\0\14\107\1\121\3\107\1\0"+
    "\17\107\1\130\3\107\16\0\1\122\15\0\1\131\25\0"+
    "\1\123\15\0\1\132\10\0\1\75\7\0\1\75\7\0"+
    "\3\75\1\133\6\75\1\0\1\75\1\0\1\75\2\0"+
    "\3\75\1\0\1\75\7\0\1\75\7\0\7\75\1\124"+
    "\2\75\1\0\1\75\1\0\1\75\2\0\3\75\24\0"+
    "\1\134\41\0\1\135\43\0\1\136\45\0\1\137\43\0"+
    "\1\140\44\0\1\141\43\0\1\142\24\0\1\143\43\0"+
    "\1\144\72\0\1\145\43\0\1\146\6\0\20\145\1\0"+
    "\14\145\1\147\6\145\20\146\1\0\14\146\1\150\6\146"+
    "\16\145\1\151\1\145\1\0\14\145\1\147\6\145\16\146"+
    "\1\152\1\146\1\0\14\146\1\150\6\146\16\145\1\151"+
    "\1\145\1\0\1\153\13\145\1\147\6\145\16\146\1\152"+
    "\1\146\1\0\1\154\13\146\1\150\6\146\20\145\1\0"+
    "\5\145\1\155\6\145\1\147\6\145\20\146\1\0\5\146"+
    "\1\156\6\146\1\150\6\146\20\145\1\0\1\145\1\157"+
    "\12\145\1\147\6\145\20\146\1\0\1\146\1\160\12\146"+
    "\1\150\6\146\20\145\1\0\14\145\1\147\1\161\5\145"+
    "\20\146\1\0\14\146\1\150\1\162\5\146\20\145\1\0"+
    "\3\145\1\163\10\145\1\147\6\145\20\146\1\0\3\146"+
    "\1\164\10\146\1\150\6\146\20\145\1\0\1\165\13\145"+
    "\1\147\6\145\20\146\1\0\1\166\13\146\1\150\6\146"+
    "\6\145\1\167\11\145\1\0\14\145\1\147\6\145\6\146"+
    "\1\170\11\146\1\0\14\146\1\150\6\146\20\145\1\0"+
    "\14\145\1\171\6\145\20\146\1\0\14\146\1\172\6\146"+
    "\20\171\1\0\14\171\1\173\6\171\20\172\1\0\14\172"+
    "\1\174\6\172\20\171\1\0\14\171\1\173\1\171\1\175"+
    "\4\171\20\172\1\0\14\172\1\174\1\172\1\176\4\172"+
    "\20\175\1\0\12\175\1\177\10\175\20\176\1\0\12\176"+
    "\1\200\10\176\20\175\1\0\12\175\1\177\4\175\1\201"+
    "\3\175\20\176\1\0\12\176\1\200\4\176\1\202\3\176"+
    "\20\175\1\0\5\175\1\203\4\175\1\177\10\175\20\176"+
    "\1\0\5\176\1\204\4\176\1\200\10\176\20\175\1\0"+
    "\12\175\1\177\3\175\1\205\4\175\20\176\1\0\12\176"+
    "\1\200\3\176\1\206\4\176";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3600];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\17\0\2\11\1\1\1\11\2\1\1\11\3\1\2\11"+
    "\1\1\1\11\2\1\2\11\1\1\5\11\2\1\1\11"+
    "\2\1\1\11\1\1\2\11\3\1\1\11\3\1\1\11"+
    "\1\1\3\11\2\1\1\11\6\1\2\0\1\11\3\0"+
    "\1\11\2\1\5\0\3\1\1\0\1\1\2\0\1\1"+
    "\1\11\50\0\2\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    //////////////////////////////////////////////////////////////////////////////
    // Start User Code ///////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    // Reset The Token Scanner 
    @Override
    public final void init(
            final Reader reader,
            final boolean comment,
            final boolean newline,
            final boolean whitespace) {
        // Reset The Internal Stuff
        yyreset(reader);
        // Initialize The Comment Flag
        mComment = comment;
        // Initialize The Comment Flag
        mNewline = newline; //comment
        // Initialize The Whitespace Flag
        mWhiteSpace = whitespace;
        // Reset  Token Object Index
        mTokenIndex = -1;
        // Reset  Last Type Of Token
        mLastToken = -1;
        // Reset  Last Lexxer State
        mLastState = YYINITIAL;
    }
    // The Token Scanner Construction
    public ScriptLexxer(
        Reader reader,
        boolean comment, 
        boolean newline,
        boolean whitespace) {
        // Initialize The Reader
        init(
            reader,
            comment,
            newline,
            whitespace);
    }
    // The Token Scanner Construction
    public ScriptLexxer(
        boolean comment,
        boolean newline,
        boolean whitespace) {
        // Initialize The Reader
        init(
            null,
            comment,
            newline,
            whitespace);
    }
    // Creating A New Token
    public final ScriptSymbol create(final int type) {
        // Set The Last Token Type
        mLastToken = type;
        // Increment The Token Index
        ++mTokenIndex;
        // Compute The Field Value
        final String field = ScriptSymbol.getField(type);
        // Compute The State Value
        final String lexic = ScriptSymbol.getState(yystate());
        // Create The New Token
        final SyntaxDocToken token = new SyntaxDocToken(
            mTokenIndex,    // Set The Index
            yystate(),      // Set The State
            type,           // Set The Token
            yychar,         // Set Left Bound
            yychar + yytext().length(), 
            yyline,         // Set Line
            yycolumn,       // Set Column
            yytext(),       // Set Content
            field,          // Set Field
            lexic);         // Set State
        // Create The New Symbol
        final ScriptSymbol symbol = 
            new ScriptSymbol(type, yychar, yychar + yytext().length(), token);
        // Print Some Information
        //mLogger.message("Scanning Symbol " + symbol + "");    
        // Return The New Symbol
        return symbol;
    }
////////////////////////////////////////////////////////////////////////////////
// End User Code ///////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ScriptLexxer(java.io.Reader in) {
      // Do Nothing Here
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public ScriptLexxer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 192) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
        // Do Nothing
  yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public ScriptSymbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 15: 
          { // Enter The Action Tag Scope
    yybegin(YY_ACTION_BODY);
    // Return The New Token Object
    return create(ScriptFields.ACTIONHEAD);
          }
        case 36: break;
        case 4: 
          { // Enter The Scene Undl Scope
    yybegin(YY_SCENE_UNDL);
    // Return The Scene Name Token
    return create(ScriptFields.UNDERLINE);
          }
        case 37: break;
        case 1: 
          { // Enter The Error State Scope
    yybegin(YY_ERROR_STATE);
    // Return The Scene Head Token
    return create(ScriptFields.ERRORSTATE);
          }
        case 38: break;
        case 2: 
          { if(mWhiteSpace) {
        // Return The Whitespace Token
        return create(ScriptFields.WHITESPACE);
    }
          }
        case 39: break;
        case 10: 
          { // Enter The Turn Head Scope
    yybegin(YYINITIAL);
    // Return The Turn Head Token
    if(mWhiteSpace) {
        // Return The Scene Lang Token
        return create(ScriptFields.NEWLINE);
    }
          }
        case 40: break;
        case 17: 
          { //
    yybegin(YY_TURN_BODY);
    // Return The Simple Word Token
    return create(ScriptFields.SIMPLEWORD);
          }
        case 41: break;
        case 24: 
          { // Leave The Action Tag Scope 
    yybegin(YY_TURN_BODY);
    // Return The New Token Object
    return create(ScriptFields.ACTIONFOOT);
          }
        case 42: break;
        case 20: 
          { // Enter The Action Tag Scope
    yybegin(YY_TURN_FOOT);
    // Return The Punctuation Token
    return create(ScriptFields.PUNCTUATION);
          }
        case 43: break;
        case 16: 
          { // Enter The Placeholder Scope 
    yybegin(YY_VARIABLE);
    // Remember The Last Lexxer State
    mLastState = YY_TURN_BODY;
    // Return The Parameter Token 
    return create(ScriptFields.PLACEHOLDER);
          }
        case 44: break;
        case 27: 
          { return create(ScriptFields.IDENTIFIER);
          }
        case 45: break;
        case 12: 
          { if(mWhiteSpace) {
        return create(ScriptFields.NEWLINE);
    }
          }
        case 46: break;
        case 6: 
          { // Enter The Scene Body Scope
    yybegin(YY_SCENE_BODY);
    // Return The Scene Foot Token
    return create(ScriptFields.COLONMARK);
          }
        case 47: break;
        case 11: 
          { // Enter The Turn Head Scope
    yybegin(YY_TURN_INIT);
    // Return The Turn Head Token
    return create(ScriptFields.COLONMARK);
          }
        case 48: break;
        case 35: 
          { // Enter The Scene Head Scope
    yybegin(YY_SCENE_HEAD);
    // Return The Scene Head Token
    return create(ScriptFields.SCENEHEADER);
          }
        case 49: break;
        case 33: 
          { if(mComment) {
        // Return The Comment Token
        return create(ScriptFields.COMMENT);
    }
          }
        case 50: break;
        case 30: 
          { // Enter The Scene Lang Scope
    yybegin(YY_SCENE_LANG);
    // Return The Scene Lang Token
    return create(ScriptFields.LANGUAGE);
          }
        case 51: break;
        case 22: 
          { // Leave The Placeholder Scope 
    yybegin(mLastState);
    // Return The Parameter Token 
    return create(ScriptFields.VARIABLE);
          }
        case 52: break;
        case 19: 
          { // Return The Abbreviation Token
    return create(ScriptFields.ABBREVIATION);
          }
        case 53: break;
        case 9: 
          { // Enter The Error State Scope
    yybegin(YY_TURN_NAME);
    // Return The Turn Head Token
    return create(ScriptFields.IDENTIFIER);
          }
        case 54: break;
        case 25: 
          { return create(ScriptFields.ASSIGNMENT);
          }
        case 55: break;
        case 3: 
          { if(mWhiteSpace) {
        // Return The Newline Token
        return create(ScriptFields.NEWLINE);
    }
          }
        case 56: break;
        case 29: 
          { return create(ScriptFields.INTEGER);
          }
        case 57: break;
        case 23: 
          { // Create An Error Here
    return create(ScriptFields.ERRORSTATE);
          }
        case 58: break;
        case 14: 
          { //
    yybegin(YY_TURN_BODY);
    // Return The Abbreviation Token
    return create(ScriptFields.ABBREVIATION);
          }
        case 59: break;
        case 26: 
          { // Enter The Placeholder Scope 
    yybegin(YY_VARIABLE);
    // Remember The Last Lexxer State
    mLastState = YY_ACTION_BODY;
    // Return The Parameter Token 
    return create(ScriptFields.PLACEHOLDER);
          }
        case 60: break;
        case 28: 
          { if(mWhiteSpace) {
        return create(ScriptFields.WHITESPACE);
    }
          }
        case 61: break;
        case 5: 
          { // Enter The Scene Lang Scope
    yybegin(YY_SCENE_NAME);
    // Return The Scene Name Token
    return create(ScriptFields.IDENTIFIER);
          }
        case 62: break;
        case 32: 
          { return create(ScriptFields.FLOATING);
          }
        case 63: break;
        case 31: 
          { return create(ScriptFields.STRING);
          }
        case 64: break;
        case 7: 
          { if(mWhiteSpace) {
        // Return The Scene Lang Token
        return create(ScriptFields.WHITESPACE);
    }
          }
        case 65: break;
        case 21: 
          { // Return The Simple Word Token
    return create(ScriptFields.SIMPLEWORD);
          }
        case 66: break;
        case 18: 
          { //
    yybegin(YY_TURN_BODY);
    if(mWhiteSpace) {
        // Return The Scene Lang Token
        return create(ScriptFields.WHITESPACE);
    }
          }
        case 67: break;
        case 13: 
          { // Leave The Placeholder Scope 
    yybegin(YY_ERROR_STATE);
    // Create An Error Here
    return create(ScriptFields.ERRORSTATE);
          }
        case 68: break;
        case 34: 
          { return create(ScriptFields.BOOLEAN);
          }
        case 69: break;
        case 8: 
          { // Enter The Turn Head Scope
    yybegin(YY_TURN_HEAD);
    // Return The Turn Head Token
    if(mWhiteSpace) {
        // Return The Scene Lang Token
        return create(ScriptFields.NEWLINE);
    }
          }
        case 70: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {     // Return NULL At End Of File
    return null;
    // Return End Of File Token EOF At End                                    
    //return create(ScriptFields.EOF);
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}

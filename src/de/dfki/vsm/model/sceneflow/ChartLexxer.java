/* The following code was generated by JFlex 1.4.3 on 9/10/16 21:34 */

////////////////////////////////////////////////////////////////////////////////
// Start User Code /////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// Local Package Definition
package de.dfki.vsm.model.sceneflow;
// Import Java Cup Runtime
import java_cup.runtime.Symbol;
////////////////////////////////////////////////////////////////////////////////
// End User Code ///////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 9/10/16 21:34 from the specification file
 * <tt>/home/alvaro/Documentos/TESISVSM/VisualSceneMaker/src/de/dfki/vsm/model/sceneflow/lexxer.jflex</tt>
 */
public final class ChartLexxer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\10\0\1\1\1\14\1\14\1\0\1\14\1\14\22\0\1\14\1\75"+
    "\1\15\3\1\1\73\1\1\1\12\1\13\1\5\1\4\1\102\1\6"+
    "\1\3\1\72\12\2\1\103\1\1\1\76\1\74\1\77\1\67\1\71"+
    "\1\36\1\70\1\52\1\33\1\57\1\50\1\26\1\61\1\55\2\70"+
    "\1\51\1\70\1\56\1\63\1\16\1\70\1\44\1\22\1\60\1\40"+
    "\1\62\4\70\1\10\1\1\1\11\1\0\1\70\1\0\1\20\1\41"+
    "\1\23\1\47\1\24\1\54\1\35\1\64\1\34\1\70\1\42\1\17"+
    "\1\45\1\25\1\30\1\32\1\66\1\27\1\43\1\37\1\31\1\46"+
    "\1\65\1\70\1\21\1\53\1\100\1\7\1\101\1\1\66\0\1\1"+
    "\12\0\3\1\1\0\1\1\1\0\6\1\2\0\2\1\4\0\1\1"+
    "\1\0\1\1\5\0\1\1\2\0\2\1\1\0\1\1\1\0\1\1"+
    "\1\0\6\1\2\0\2\1\4\0\1\1\1\0\1\1\4\0\2\1"+
    "\2\0\1\1\uff00\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\1"+
    "\1\7\1\10\1\11\1\12\1\13\1\1\24\14\1\15"+
    "\1\16\1\17\1\1\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\0\1\30\1\0\1\31\6\14"+
    "\1\32\17\14\1\33\1\34\1\35\1\36\1\37\1\40"+
    "\1\41\2\14\1\42\1\14\1\43\20\14\1\44\1\45"+
    "\1\46\3\14\1\47\4\14\1\50\17\14\1\51\1\52"+
    "\1\14\1\53\3\14\1\33\6\14\1\54\1\55\6\14"+
    "\1\56\1\14\1\57\4\14\1\60\1\14\1\61\2\14"+
    "\1\62\4\14\1\63\26\14\1\64\12\14\1\65\15\14"+
    "\1\66\13\14\1\67\7\14\1\70\1\71\2\14\1\72"+
    "\5\14\1\73\1\74\1\14\1\75\7\14\1\76\1\77";

  private static int [] zzUnpackAction() {
    int [] result = new int[257];
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
    "\0\0\0\104\0\210\0\104\0\104\0\104\0\104\0\314"+
    "\0\104\0\104\0\104\0\104\0\104\0\u0110\0\u0154\0\u0198"+
    "\0\u01dc\0\u0220\0\u0264\0\u02a8\0\u02ec\0\u0330\0\u0374\0\u03b8"+
    "\0\u03fc\0\u0440\0\u0484\0\u04c8\0\u050c\0\u0550\0\u0594\0\u05d8"+
    "\0\u061c\0\u0660\0\u06a4\0\104\0\104\0\u06e8\0\u072c\0\u0770"+
    "\0\u07b4\0\u07f8\0\104\0\104\0\104\0\104\0\u083c\0\104"+
    "\0\u0110\0\104\0\u0880\0\u08c4\0\u0908\0\u094c\0\u0990\0\u09d4"+
    "\0\u0198\0\u0a18\0\u0a5c\0\u0aa0\0\u0ae4\0\u0b28\0\u0b6c\0\u0bb0"+
    "\0\u0bf4\0\u0c38\0\u0c7c\0\u0cc0\0\u0d04\0\u0d48\0\u0d8c\0\u0dd0"+
    "\0\104\0\104\0\104\0\104\0\104\0\104\0\u083c\0\u0e14"+
    "\0\u0e58\0\u0198\0\u0e9c\0\u0198\0\u0ee0\0\u0f24\0\u0f68\0\u0fac"+
    "\0\u0ff0\0\u1034\0\u1078\0\u10bc\0\u1100\0\u1144\0\u1188\0\u11cc"+
    "\0\u1210\0\u1254\0\u1298\0\u12dc\0\u1320\0\u0198\0\u0198\0\u1364"+
    "\0\u13a8\0\u13ec\0\u0198\0\u1430\0\u1474\0\u14b8\0\u14fc\0\u0198"+
    "\0\u1540\0\u1584\0\u15c8\0\u160c\0\u1650\0\u1694\0\u16d8\0\u171c"+
    "\0\u1760\0\u17a4\0\u17e8\0\u182c\0\u1870\0\u18b4\0\u18f8\0\u0198"+
    "\0\u0198\0\u193c\0\u0198\0\u1980\0\u19c4\0\u1a08\0\u0198\0\u1a4c"+
    "\0\u1a90\0\u1ad4\0\u1b18\0\u1b5c\0\u1ba0\0\u0198\0\u1be4\0\u1c28"+
    "\0\u1c6c\0\u1cb0\0\u1cf4\0\u1d38\0\u1d7c\0\u0198\0\u1dc0\0\u0198"+
    "\0\u1e04\0\u1e48\0\u1e8c\0\u1ed0\0\u0198\0\u1f14\0\u0198\0\u1f58"+
    "\0\u1f9c\0\u0198\0\u1fe0\0\u2024\0\u2068\0\u20ac\0\u0198\0\u20f0"+
    "\0\u2134\0\u2178\0\u21bc\0\u2200\0\u2244\0\u2288\0\u22cc\0\u2310"+
    "\0\u2354\0\u2398\0\u23dc\0\u2420\0\u2464\0\u24a8\0\u24ec\0\u2530"+
    "\0\u2574\0\u25b8\0\u25fc\0\u2640\0\u2684\0\u0198\0\u26c8\0\u270c"+
    "\0\u2750\0\u2794\0\u27d8\0\u281c\0\u2860\0\u28a4\0\u28e8\0\u292c"+
    "\0\u0198\0\u2970\0\u29b4\0\u29f8\0\u2a3c\0\u2a80\0\u2ac4\0\u2b08"+
    "\0\u2b4c\0\u2b90\0\u2bd4\0\u2c18\0\u2c5c\0\u2ca0\0\u0198\0\u2ce4"+
    "\0\u2d28\0\u2d6c\0\u2db0\0\u2df4\0\u2e38\0\u2e7c\0\u2ec0\0\u2f04"+
    "\0\u2f48\0\u2f8c\0\u0198\0\u2fd0\0\u3014\0\u3058\0\u309c\0\u30e0"+
    "\0\u3124\0\u3168\0\u0198\0\u0198\0\u31ac\0\u31f0\0\u0198\0\u3234"+
    "\0\u3278\0\u32bc\0\u3300\0\u3344\0\u0198\0\u0198\0\u3388\0\u0198"+
    "\0\u33cc\0\u3410\0\u3454\0\u3498\0\u34dc\0\u3520\0\u3564\0\u0198"+
    "\0\u0198";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[257];
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
    "\2\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\16\1\17\3\20\1\21"+
    "\2\20\1\22\1\23\4\20\1\24\1\25\1\20\1\26"+
    "\1\27\1\30\3\20\1\31\3\20\1\32\1\33\1\34"+
    "\1\20\1\35\1\25\1\20\1\36\1\37\1\40\1\41"+
    "\3\20\1\42\1\43\1\20\1\44\1\45\1\46\1\47"+
    "\1\50\1\51\1\52\1\53\1\54\1\55\1\56\106\0"+
    "\1\3\1\57\107\0\1\60\75\0\14\61\1\62\62\61"+
    "\2\0\2\61\2\0\1\20\13\0\1\20\1\63\47\20"+
    "\1\0\1\20\15\0\1\20\13\0\51\20\1\0\1\20"+
    "\15\0\1\20\13\0\16\20\1\64\32\20\1\0\1\20"+
    "\15\0\1\20\13\0\6\20\1\65\4\20\1\66\35\20"+
    "\1\0\1\20\15\0\1\20\13\0\6\20\1\67\42\20"+
    "\1\0\1\20\15\0\1\20\13\0\6\20\1\70\42\20"+
    "\1\0\1\20\15\0\1\20\13\0\7\20\1\71\30\20"+
    "\1\71\10\20\1\0\1\20\15\0\1\20\13\0\31\20"+
    "\1\72\17\20\1\0\1\20\15\0\1\20\13\0\11\20"+
    "\1\73\37\20\1\0\1\20\15\0\1\20\13\0\7\20"+
    "\1\74\41\20\1\0\1\20\15\0\1\20\13\0\2\20"+
    "\1\75\3\20\1\76\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\16\20\1\77\32\20\1\0\1\20\15\0\1\20"+
    "\13\0\2\20\1\100\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\101\10\20\1\102\36\20\1\0\1\20"+
    "\15\0\1\20\13\0\2\20\1\103\46\20\1\0\1\20"+
    "\15\0\1\20\13\0\27\20\1\104\21\20\1\0\1\20"+
    "\15\0\1\20\13\0\16\20\1\105\32\20\1\0\1\20"+
    "\15\0\1\20\13\0\16\20\1\106\32\20\1\0\1\20"+
    "\15\0\1\20\13\0\2\20\1\107\46\20\1\0\1\20"+
    "\15\0\1\20\13\0\13\20\1\110\35\20\1\0\1\20"+
    "\21\0\1\111\170\0\1\112\104\0\1\113\103\0\1\114"+
    "\103\0\1\115\103\0\1\116\11\0\1\117\103\0\1\20"+
    "\13\0\2\20\1\120\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\35\20\1\121\13\20\1\0\1\20\15\0\1\20"+
    "\13\0\47\20\1\122\1\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\123\47\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\124\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\36\20\1\125\12\20\1\0\1\20\15\0\1\20"+
    "\13\0\31\20\1\126\17\20\1\0\1\20\15\0\1\20"+
    "\13\0\13\20\1\127\35\20\1\0\1\20\15\0\1\20"+
    "\13\0\23\20\1\130\25\20\1\0\1\20\15\0\1\20"+
    "\13\0\7\20\1\131\41\20\1\0\1\20\15\0\1\20"+
    "\13\0\27\20\1\132\21\20\1\0\1\20\15\0\1\20"+
    "\13\0\11\20\1\133\37\20\1\0\1\20\15\0\1\20"+
    "\13\0\25\20\1\134\23\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\135\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\7\20\1\136\41\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\137\47\20\1\0\1\20\15\0\1\20"+
    "\13\0\14\20\1\140\34\20\1\0\1\20\15\0\1\20"+
    "\13\0\27\20\1\141\21\20\1\0\1\20\15\0\1\20"+
    "\13\0\25\20\1\142\23\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\143\47\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\144\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\3\20\1\145\45\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\146\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\147\47\20\1\0\1\20\15\0\1\20"+
    "\13\0\2\20\1\150\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\32\20\1\151\1\152\15\20\1\0\1\20\15\0"+
    "\1\20\13\0\6\20\1\153\42\20\1\0\1\20\15\0"+
    "\1\20\13\0\1\20\1\154\47\20\1\0\1\20\15\0"+
    "\1\20\13\0\31\20\1\155\17\20\1\0\1\20\15\0"+
    "\1\20\13\0\12\20\1\156\36\20\1\0\1\20\15\0"+
    "\1\20\13\0\25\20\1\157\23\20\1\0\1\20\15\0"+
    "\1\20\13\0\21\20\1\160\27\20\1\0\1\20\15\0"+
    "\1\20\13\0\2\20\1\161\46\20\1\0\1\20\15\0"+
    "\1\20\13\0\21\20\1\162\27\20\1\0\1\20\15\0"+
    "\1\20\13\0\25\20\1\127\23\20\1\0\1\20\15\0"+
    "\1\20\13\0\21\20\1\163\27\20\1\0\1\20\15\0"+
    "\1\20\13\0\6\20\1\164\42\20\1\0\1\20\15\0"+
    "\1\20\13\0\21\20\1\165\27\20\1\0\1\20\15\0"+
    "\1\20\13\0\13\20\1\166\35\20\1\0\1\20\15\0"+
    "\1\20\13\0\11\20\1\167\37\20\1\0\1\20\15\0"+
    "\1\20\13\0\4\20\1\170\10\20\1\171\33\20\1\0"+
    "\1\20\15\0\1\20\13\0\13\20\1\172\35\20\1\0"+
    "\1\20\15\0\1\20\13\0\16\20\1\173\32\20\1\0"+
    "\1\20\15\0\1\20\13\0\2\20\1\174\46\20\1\0"+
    "\1\20\15\0\1\20\13\0\12\20\1\175\36\20\1\0"+
    "\1\20\15\0\1\20\13\0\12\20\1\176\36\20\1\0"+
    "\1\20\15\0\1\20\13\0\30\20\1\177\20\20\1\0"+
    "\1\20\15\0\1\20\13\0\21\20\1\200\27\20\1\0"+
    "\1\20\15\0\1\20\13\0\11\20\1\201\37\20\1\0"+
    "\1\20\15\0\1\20\13\0\2\20\1\202\46\20\1\0"+
    "\1\20\15\0\1\20\13\0\3\20\1\203\45\20\1\0"+
    "\1\20\15\0\1\20\13\0\12\20\1\204\36\20\1\0"+
    "\1\20\15\0\1\20\13\0\12\20\1\205\36\20\1\0"+
    "\1\20\15\0\1\20\13\0\6\20\1\206\42\20\1\0"+
    "\1\20\15\0\1\20\13\0\3\20\1\207\45\20\1\0"+
    "\1\20\15\0\1\20\13\0\5\20\1\210\43\20\1\0"+
    "\1\20\15\0\1\20\13\0\16\20\1\211\32\20\1\0"+
    "\1\20\15\0\1\20\13\0\1\20\1\212\47\20\1\0"+
    "\1\20\15\0\1\20\13\0\11\20\1\213\37\20\1\0"+
    "\1\20\15\0\1\20\13\0\25\20\1\214\23\20\1\0"+
    "\1\20\15\0\1\20\13\0\5\20\1\215\43\20\1\0"+
    "\1\20\15\0\1\20\13\0\27\20\1\216\21\20\1\0"+
    "\1\20\15\0\1\20\13\0\6\20\1\217\42\20\1\0"+
    "\1\20\15\0\1\20\13\0\16\20\1\220\32\20\1\0"+
    "\1\20\15\0\1\20\13\0\13\20\1\221\35\20\1\0"+
    "\1\20\15\0\1\20\13\0\11\20\1\222\37\20\1\0"+
    "\1\20\15\0\1\20\13\0\45\20\1\223\3\20\1\0"+
    "\1\20\15\0\1\20\13\0\6\20\1\224\42\20\1\0"+
    "\1\20\15\0\1\20\13\0\2\20\1\225\46\20\1\0"+
    "\1\20\15\0\1\20\13\0\21\20\1\226\27\20\1\0"+
    "\1\20\15\0\1\20\13\0\25\20\1\227\23\20\1\0"+
    "\1\20\15\0\1\20\13\0\21\20\1\230\27\20\1\0"+
    "\1\20\15\0\1\20\13\0\24\20\1\231\24\20\1\0"+
    "\1\20\15\0\1\20\13\0\32\20\1\232\1\233\15\20"+
    "\1\0\1\20\15\0\1\20\13\0\7\20\1\234\41\20"+
    "\1\0\1\20\15\0\1\20\13\0\21\20\1\235\27\20"+
    "\1\0\1\20\15\0\1\20\13\0\3\20\1\236\45\20"+
    "\1\0\1\20\15\0\1\20\13\0\36\20\1\237\12\20"+
    "\1\0\1\20\15\0\1\20\13\0\7\20\1\240\41\20"+
    "\1\0\1\20\15\0\1\20\13\0\1\20\1\241\47\20"+
    "\1\0\1\20\15\0\1\20\13\0\21\20\1\242\27\20"+
    "\1\0\1\20\15\0\1\20\13\0\4\20\1\243\13\20"+
    "\1\244\30\20\1\0\1\20\15\0\1\20\13\0\16\20"+
    "\1\245\32\20\1\0\1\20\15\0\1\20\13\0\2\20"+
    "\1\246\46\20\1\0\1\20\15\0\1\20\13\0\25\20"+
    "\1\247\23\20\1\0\1\20\15\0\1\20\13\0\4\20"+
    "\1\250\10\20\1\251\10\20\1\252\5\20\1\253\7\20"+
    "\1\254\4\20\1\0\1\20\15\0\1\20\13\0\6\20"+
    "\1\255\42\20\1\0\1\20\15\0\1\20\13\0\12\20"+
    "\1\256\36\20\1\0\1\20\15\0\1\20\13\0\5\20"+
    "\1\257\43\20\1\0\1\20\15\0\1\20\13\0\1\20"+
    "\1\260\47\20\1\0\1\20\15\0\1\20\13\0\11\20"+
    "\1\261\37\20\1\0\1\20\15\0\1\20\13\0\25\20"+
    "\1\262\23\20\1\0\1\20\15\0\1\20\13\0\6\20"+
    "\1\263\42\20\1\0\1\20\15\0\1\20\13\0\6\20"+
    "\1\264\42\20\1\0\1\20\15\0\1\20\13\0\13\20"+
    "\1\265\35\20\1\0\1\20\15\0\1\20\13\0\1\20"+
    "\1\266\10\20\1\267\36\20\1\0\1\20\15\0\1\20"+
    "\13\0\2\20\1\270\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\10\20\1\271\40\20\1\0\1\20\15\0\1\20"+
    "\13\0\17\20\1\272\31\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\273\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\274\47\20\1\0\1\20\15\0\1\20"+
    "\13\0\25\20\1\275\23\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\276\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\277\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\300\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\7\20\1\301\41\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\302\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\7\20\1\303\41\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\304\47\20\1\0\1\20\15\0\1\20"+
    "\13\0\11\20\1\305\37\20\1\0\1\20\15\0\1\20"+
    "\13\0\13\20\1\306\35\20\1\0\1\20\15\0\1\20"+
    "\13\0\7\20\1\307\41\20\1\0\1\20\15\0\1\20"+
    "\13\0\4\20\1\310\44\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\311\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\15\20\1\312\33\20\1\0\1\20\15\0\1\20"+
    "\13\0\14\20\1\313\34\20\1\0\1\20\15\0\1\20"+
    "\13\0\42\20\1\314\6\20\1\0\1\20\15\0\1\20"+
    "\13\0\2\20\1\315\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\316\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\13\20\1\317\35\20\1\0\1\20\15\0\1\20"+
    "\13\0\12\20\1\320\36\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\321\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\322\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\5\20\1\323\43\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\324\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\34\20\1\325\14\20\1\0\1\20\15\0\1\20"+
    "\13\0\16\20\1\326\32\20\1\0\1\20\15\0\1\20"+
    "\13\0\11\20\1\327\37\20\1\0\1\20\15\0\1\20"+
    "\13\0\2\20\1\330\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\331\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\13\20\1\332\35\20\1\0\1\20\15\0\1\20"+
    "\13\0\20\20\1\333\30\20\1\0\1\20\15\0\1\20"+
    "\13\0\10\20\1\334\40\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\335\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\14\20\1\336\34\20\1\0\1\20\15\0\1\20"+
    "\13\0\1\20\1\337\47\20\1\0\1\20\15\0\1\20"+
    "\13\0\27\20\1\340\21\20\1\0\1\20\15\0\1\20"+
    "\13\0\16\20\1\341\32\20\1\0\1\20\15\0\1\20"+
    "\13\0\45\20\1\342\3\20\1\0\1\20\15\0\1\20"+
    "\13\0\14\20\1\343\34\20\1\0\1\20\15\0\1\20"+
    "\13\0\5\20\1\344\43\20\1\0\1\20\15\0\1\20"+
    "\13\0\11\20\1\345\37\20\1\0\1\20\15\0\1\20"+
    "\13\0\7\20\1\346\41\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\347\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\350\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\351\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\7\20\1\352\41\20\1\0\1\20\15\0\1\20"+
    "\13\0\36\20\1\353\12\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\354\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\12\20\1\355\36\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\356\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\46\20\1\357\2\20\1\0\1\20\15\0\1\20"+
    "\13\0\2\20\1\360\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\45\20\1\361\3\20\1\0\1\20\15\0\1\20"+
    "\13\0\25\20\1\362\23\20\1\0\1\20\15\0\1\20"+
    "\13\0\13\20\1\363\35\20\1\0\1\20\15\0\1\20"+
    "\13\0\10\20\1\364\40\20\1\0\1\20\15\0\1\20"+
    "\13\0\11\20\1\365\37\20\1\0\1\20\15\0\1\20"+
    "\13\0\36\20\1\366\12\20\1\0\1\20\15\0\1\20"+
    "\13\0\4\20\1\367\44\20\1\0\1\20\15\0\1\20"+
    "\13\0\14\20\1\370\34\20\1\0\1\20\15\0\1\20"+
    "\13\0\11\20\1\371\37\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\372\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\12\20\1\373\36\20\1\0\1\20\15\0\1\20"+
    "\13\0\2\20\1\374\46\20\1\0\1\20\15\0\1\20"+
    "\13\0\13\20\1\375\35\20\1\0\1\20\15\0\1\20"+
    "\13\0\21\20\1\376\27\20\1\0\1\20\15\0\1\20"+
    "\13\0\14\20\1\377\34\20\1\0\1\20\15\0\1\20"+
    "\13\0\6\20\1\u0100\42\20\1\0\1\20\15\0\1\20"+
    "\13\0\25\20\1\u0101\23\20\1\0\1\20\13\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[13736];
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
    "\1\0\1\11\1\1\4\11\1\1\5\11\26\1\2\11"+
    "\5\1\4\11\1\0\1\11\1\0\1\11\26\1\6\11"+
    "\263\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[257];
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


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ChartLexxer(java.io.Reader in) {
      // Do Nothing Here
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public ChartLexxer(java.io.InputStream in) {
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
    while (i < 246) {
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
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
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
        case 52: 
          { return new Symbol(ChartFields.REMOVELAST);
          }
        case 64: break;
        case 24: 
          { return new Symbol(ChartFields.OR);
          }
        case 65: break;
        case 26: 
          { return new Symbol(ChartFields.IN);
          }
        case 66: break;
        case 62: 
          { return new Symbol(ChartFields.HISTORYCONTAINSSTATE);
          }
        case 67: break;
        case 14: 
          { return new Symbol(ChartFields.AT);
          }
        case 68: break;
        case 22: 
          { return new Symbol(ChartFields.COMMA);
          }
        case 69: break;
        case 63: 
          { return new Symbol(ChartFields.UASG);
          }
        case 70: break;
        case 19: 
          { return new Symbol(ChartFields.GREATER);
          }
        case 71: break;
        case 49: 
          { return new Symbol(ChartFields.VALUEOF);
          }
        case 72: break;
        case 61: 
          { return new Symbol(ChartFields.USG);
          }
        case 73: break;
        case 7: 
          { return new Symbol(ChartFields.LBRACK);
          }
        case 74: break;
        case 46: 
          { return new Symbol(ChartFields.DEFAULT);
          }
        case 75: break;
        case 9: 
          { return new Symbol(ChartFields.LPAREN);
          }
        case 76: break;
        case 53: 
          { return new Symbol(ChartFields.REMOVEFIRST);
          }
        case 77: break;
        case 11: 
          { /* ignore white space. */
          }
        case 78: break;
        case 43: 
          { return new Symbol(ChartFields.EMPTY);
          }
        case 79: break;
        case 21: 
          { return new Symbol(ChartFields.RBRACE);
          }
        case 80: break;
        case 32: 
          { return new Symbol(ChartFields.GREATEREQUAL);
          }
        case 81: break;
        case 58: 
          { return new Symbol(ChartFields.HISTORYSETDEPTH);
          }
        case 82: break;
        case 44: 
          { return new Symbol(ChartFields.RANDOM);
          }
        case 83: break;
        case 33: 
          { return new Symbol(ChartFields.FLOAT, new java.lang.Float(yytext()));
          }
        case 84: break;
        case 13: 
          { return new Symbol(ChartFields.QUESTION);
          }
        case 85: break;
        case 34: 
          { return new Symbol(ChartFields.NEW);
          }
        case 86: break;
        case 55: 
          { return new Symbol(ChartFields.PSG);
          }
        case 87: break;
        case 50: 
          { return new Symbol(ChartFields.ADDFIRST);
          }
        case 88: break;
        case 12: 
          { return new Symbol(ChartFields.VARIABLE, new java.lang.String(yytext()));
          }
        case 89: break;
        case 38: 
          { return new Symbol(ChartFields.NULL);
          }
        case 90: break;
        case 8: 
          { return new Symbol(ChartFields.RBRACK);
          }
        case 91: break;
        case 39: 
          { return new Symbol(ChartFields.BOOLEAN, new java.lang.Boolean(yytext()));
          }
        case 92: break;
        case 3: 
          { return new Symbol(ChartFields.DOT);
          }
        case 93: break;
        case 59: 
          { return new Symbol(ChartFields.HISTORYDEEPCLEAR);
          }
        case 94: break;
        case 5: 
          { return new Symbol(ChartFields.TIMES);
          }
        case 95: break;
        case 23: 
          { return new Symbol(ChartFields.COLON);
          }
        case 96: break;
        case 10: 
          { return new Symbol(ChartFields.RPAREN);
          }
        case 97: break;
        case 27: 
          { return new Symbol(ChartFields.QUERY);
          }
        case 98: break;
        case 45: 
          { return new Symbol(ChartFields.REMOVE);
          }
        case 99: break;
        case 16: 
          { return new Symbol(ChartFields.EQUAL);
          }
        case 100: break;
        case 35: 
          { return new Symbol(ChartFields.GET);
          }
        case 101: break;
        case 48: 
          { return new Symbol(ChartFields.TIMEOUT);
          }
        case 102: break;
        case 54: 
          { return new Symbol(ChartFields.HISTORYCLEAR);
          }
        case 103: break;
        case 31: 
          { return new Symbol(ChartFields.LESSEQUAL);
          }
        case 104: break;
        case 56: 
          { return new Symbol(ChartFields.HISTORYVALUEOF);
          }
        case 105: break;
        case 47: 
          { return new Symbol(ChartFields.ADDLAST);
          }
        case 106: break;
        case 42: 
          { return new Symbol(ChartFields.CLEAR);
          }
        case 107: break;
        case 20: 
          { return new Symbol(ChartFields.LBRACE);
          }
        case 108: break;
        case 29: 
          { return new Symbol(ChartFields.EQUALEQUAL);
          }
        case 109: break;
        case 1: 
          { System.err.println("Illegal character: "+yytext());
          }
        case 110: break;
        case 51: 
          { return new Symbol(ChartFields.CONTAINS);
          }
        case 111: break;
        case 41: 
          { return new Symbol(ChartFields.FIRST);
          }
        case 112: break;
        case 17: 
          { return new Symbol(ChartFields.NOT);
          }
        case 113: break;
        case 28: 
          { return new Symbol(ChartFields.AND);
          }
        case 114: break;
        case 60: 
          { return new Symbol(ChartFields.HISTORYRUNTIMEOF);
          }
        case 115: break;
        case 25: 
          { return new Symbol(ChartFields.STRING, new java.lang.String(yytext()));
          }
        case 116: break;
        case 6: 
          { return new Symbol(ChartFields.MINUS);
          }
        case 117: break;
        case 15: 
          { return new Symbol(ChartFields.DIV);
          }
        case 118: break;
        case 4: 
          { return new Symbol(ChartFields.PLUS);
          }
        case 119: break;
        case 40: 
          { return new Symbol(ChartFields.LAST);
          }
        case 120: break;
        case 18: 
          { return new Symbol(ChartFields.LESS);
          }
        case 121: break;
        case 37: 
          { return new Symbol(ChartFields.SIZE);
          }
        case 122: break;
        case 2: 
          { return new Symbol(ChartFields.INTEGER, new java.lang.Integer(yytext()));
          }
        case 123: break;
        case 30: 
          { return new Symbol(ChartFields.NOTEQUAL);
          }
        case 124: break;
        case 57: 
          { return new Symbol(ChartFields.PDA);
          }
        case 125: break;
        case 36: 
          { return new Symbol(ChartFields.PLAY);
          }
        case 126: break;
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

// Generated from /home/stan/imag/ensimag/GL/gl32/src/main/antlr4/fr/ensimag/deca/syntax/DecaLexer.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DecaLexer extends AbstractDecaLexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		MINUS=1, PLUS=2, OBRACE=3, CBRACE=4, COMMA=5, EQUALS=6, PRINT=7, PRINTLN=8, 
		PRINTX=9, PRINTLNX=10, OPARENT=11, CPARENT=12, SEMI=13, IF=14, ELSE=15, 
		WHILE=16, RETURN=17, AND=18, EQEQ=19, OR=20, NEQ=21, LEQ=22, GEQ=23, GT=24, 
		LT=25, INSTANCEOF=26, EXCLAM=27, DOT=28, TRUE=29, FALSE=30, THIS=31, NULL=32, 
		CLASS=33, EXTENDS=34, PROTECTED=35, TIMES=36, FMA=37, SLASH=38, PERCENT=39, 
		READINT=40, READFLOAT=41, NEW=42, ASM=43, ESPACE=44, TAB=45, IDENT=46, 
		INT=47, SIGN=48, EXP=49, FLOAT=50, EOL=51, STRING_CAR=52, STRING=53, MULTI_LINE_STRING=54, 
		COMMENTAIRE=55, INCLUDE=56;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"MINUS", "PLUS", "OBRACE", "CBRACE", "COMMA", "EQUALS", "PRINT", "PRINTLN", 
			"PRINTX", "PRINTLNX", "OPARENT", "CPARENT", "SEMI", "IF", "ELSE", "WHILE", 
			"RETURN", "AND", "EQEQ", "OR", "NEQ", "LEQ", "GEQ", "GT", "LT", "INSTANCEOF", 
			"EXCLAM", "DOT", "TRUE", "FALSE", "THIS", "NULL", "CLASS", "EXTENDS", 
			"PROTECTED", "TIMES", "FMA", "SLASH", "PERCENT", "READINT", "READFLOAT", 
			"NEW", "ASM", "ESPACE", "TAB", "DIGIT", "LETTER", "IDENT", "NUM", "POSITIVE_DIGIT", 
			"INT", "SIGN", "EXP", "DEC", "FLOATDEC", "DIGITHEX", "NUMHEX", "FLOATHEX", 
			"FLOAT", "EOL", "STRING_CAR", "STRING", "MULTI_LINE_STRING", "COMMENTAIRE", 
			"FILENAME", "INCLUDE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'-'", "'+'", "'{'", "'}'", "','", "'='", "'print'", "'println'", 
			"'printx'", "'printlnx'", "'('", "')'", "';'", "'if'", "'else'", "'while'", 
			"'return'", "'&&'", "'=='", "'||'", "'!='", "'<='", "'>='", "'>'", "'<'", 
			"'instanceof'", "'!'", "'.'", "'true'", "'false'", "'this'", "'null'", 
			"'class'", "'extends'", "'protected'", "'*'", "'fma'", "'/'", "'%'", 
			"'readInt'", "'readFloat'", "'new'", "'asm'", null, "'\t'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "MINUS", "PLUS", "OBRACE", "CBRACE", "COMMA", "EQUALS", "PRINT", 
			"PRINTLN", "PRINTX", "PRINTLNX", "OPARENT", "CPARENT", "SEMI", "IF", 
			"ELSE", "WHILE", "RETURN", "AND", "EQEQ", "OR", "NEQ", "LEQ", "GEQ", 
			"GT", "LT", "INSTANCEOF", "EXCLAM", "DOT", "TRUE", "FALSE", "THIS", "NULL", 
			"CLASS", "EXTENDS", "PROTECTED", "TIMES", "FMA", "SLASH", "PERCENT", 
			"READINT", "READFLOAT", "NEW", "ASM", "ESPACE", "TAB", "IDENT", "INT", 
			"SIGN", "EXP", "FLOAT", "EOL", "STRING_CAR", "STRING", "MULTI_LINE_STRING", 
			"COMMENTAIRE", "INCLUDE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}




	public DecaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DecaLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 43:
			ESPACE_action((RuleContext)_localctx, actionIndex);
			break;
		case 44:
			TAB_action((RuleContext)_localctx, actionIndex);
			break;
		case 59:
			EOL_action((RuleContext)_localctx, actionIndex);
			break;
		case 63:
			COMMENTAIRE_action((RuleContext)_localctx, actionIndex);
			break;
		case 65:
			INCLUDE_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ESPACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 skip();
			break;
		}
	}
	private void TAB_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			skip();
			break;
		}
	}
	private void EOL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 skip(); 
			break;
		}
	}
	private void COMMENTAIRE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 skip(); 
			break;
		}
	}
	private void INCLUDE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			doInclude(getText());
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2:\u01ed\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27"+
		"\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3%\3"+
		"%\3&\3&\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*"+
		"\3*\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3\60\3\60"+
		"\3\61\3\61\5\61\u014f\n\61\3\61\3\61\3\61\7\61\u0154\n\61\f\61\16\61\u0157"+
		"\13\61\3\62\6\62\u015a\n\62\r\62\16\62\u015b\3\63\3\63\3\64\3\64\3\64"+
		"\7\64\u0163\n\64\f\64\16\64\u0166\13\64\5\64\u0168\n\64\3\65\3\65\3\66"+
		"\3\66\3\66\3\66\3\67\3\67\3\67\3\67\38\38\38\38\58\u0178\n8\38\38\58\u017c"+
		"\n8\39\39\3:\6:\u0181\n:\r:\16:\u0182\3;\3;\3;\3;\5;\u0189\n;\3;\3;\3"+
		";\3;\3;\3;\3;\3;\5;\u0193\n;\3<\3<\5<\u0197\n<\3=\3=\3=\3>\3>\3?\3?\3"+
		"?\3?\3?\3?\7?\u01a4\n?\f?\16?\u01a7\13?\3?\3?\3@\3@\3@\3@\3@\3@\3@\7@"+
		"\u01b2\n@\f@\16@\u01b5\13@\3@\3@\3A\3A\3A\3A\7A\u01bd\nA\fA\16A\u01c0"+
		"\13A\3A\3A\3A\3A\3A\3A\7A\u01c8\nA\fA\16A\u01cb\13A\3A\5A\u01ce\nA\3A"+
		"\3A\3B\3B\3B\6B\u01d5\nB\rB\16B\u01d6\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\7"+
		"C\u01e3\nC\fC\16C\u01e6\13C\3C\3C\3C\3C\3C\3C\4\u01be\u01c9\2D\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\2_\2a\60c\2e\2g\61i\62k\63m\2o\2q\2s\2u\2"+
		"w\64y\65{\66}\67\1778\u00819\u0083\2\u0085:\3\2\13\4\2C\\c|\4\2&&aa\4"+
		"\2--//\4\2GGgg\4\2HHhh\5\2\62;CHch\4\2RRrr\5\2\f\f$$^^\4\2/\60aa\2\u01fd"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2a\3\2\2\2\2g\3\2\2\2\2i\3"+
		"\2\2\2\2k\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3"+
		"\2\2\2\2\u0081\3\2\2\2\2\u0085\3\2\2\2\3\u0087\3\2\2\2\5\u0089\3\2\2\2"+
		"\7\u008b\3\2\2\2\t\u008d\3\2\2\2\13\u008f\3\2\2\2\r\u0091\3\2\2\2\17\u0093"+
		"\3\2\2\2\21\u0099\3\2\2\2\23\u00a1\3\2\2\2\25\u00a8\3\2\2\2\27\u00b1\3"+
		"\2\2\2\31\u00b3\3\2\2\2\33\u00b5\3\2\2\2\35\u00b7\3\2\2\2\37\u00ba\3\2"+
		"\2\2!\u00bf\3\2\2\2#\u00c5\3\2\2\2%\u00cc\3\2\2\2\'\u00cf\3\2\2\2)\u00d2"+
		"\3\2\2\2+\u00d5\3\2\2\2-\u00d8\3\2\2\2/\u00db\3\2\2\2\61\u00de\3\2\2\2"+
		"\63\u00e0\3\2\2\2\65\u00e2\3\2\2\2\67\u00ed\3\2\2\29\u00ef\3\2\2\2;\u00f1"+
		"\3\2\2\2=\u00f6\3\2\2\2?\u00fc\3\2\2\2A\u0101\3\2\2\2C\u0106\3\2\2\2E"+
		"\u010c\3\2\2\2G\u0114\3\2\2\2I\u011e\3\2\2\2K\u0120\3\2\2\2M\u0124\3\2"+
		"\2\2O\u0126\3\2\2\2Q\u0128\3\2\2\2S\u0130\3\2\2\2U\u013a\3\2\2\2W\u013e"+
		"\3\2\2\2Y\u0142\3\2\2\2[\u0145\3\2\2\2]\u0148\3\2\2\2_\u014a\3\2\2\2a"+
		"\u014e\3\2\2\2c\u0159\3\2\2\2e\u015d\3\2\2\2g\u0167\3\2\2\2i\u0169\3\2"+
		"\2\2k\u016b\3\2\2\2m\u016f\3\2\2\2o\u0177\3\2\2\2q\u017d\3\2\2\2s\u0180"+
		"\3\2\2\2u\u0188\3\2\2\2w\u0196\3\2\2\2y\u0198\3\2\2\2{\u019b\3\2\2\2}"+
		"\u019d\3\2\2\2\177\u01aa\3\2\2\2\u0081\u01cd\3\2\2\2\u0083\u01d4\3\2\2"+
		"\2\u0085\u01d8\3\2\2\2\u0087\u0088\7/\2\2\u0088\4\3\2\2\2\u0089\u008a"+
		"\7-\2\2\u008a\6\3\2\2\2\u008b\u008c\7}\2\2\u008c\b\3\2\2\2\u008d\u008e"+
		"\7\177\2\2\u008e\n\3\2\2\2\u008f\u0090\7.\2\2\u0090\f\3\2\2\2\u0091\u0092"+
		"\7?\2\2\u0092\16\3\2\2\2\u0093\u0094\7r\2\2\u0094\u0095\7t\2\2\u0095\u0096"+
		"\7k\2\2\u0096\u0097\7p\2\2\u0097\u0098\7v\2\2\u0098\20\3\2\2\2\u0099\u009a"+
		"\7r\2\2\u009a\u009b\7t\2\2\u009b\u009c\7k\2\2\u009c\u009d\7p\2\2\u009d"+
		"\u009e\7v\2\2\u009e\u009f\7n\2\2\u009f\u00a0\7p\2\2\u00a0\22\3\2\2\2\u00a1"+
		"\u00a2\7r\2\2\u00a2\u00a3\7t\2\2\u00a3\u00a4\7k\2\2\u00a4\u00a5\7p\2\2"+
		"\u00a5\u00a6\7v\2\2\u00a6\u00a7\7z\2\2\u00a7\24\3\2\2\2\u00a8\u00a9\7"+
		"r\2\2\u00a9\u00aa\7t\2\2\u00aa\u00ab\7k\2\2\u00ab\u00ac\7p\2\2\u00ac\u00ad"+
		"\7v\2\2\u00ad\u00ae\7n\2\2\u00ae\u00af\7p\2\2\u00af\u00b0\7z\2\2\u00b0"+
		"\26\3\2\2\2\u00b1\u00b2\7*\2\2\u00b2\30\3\2\2\2\u00b3\u00b4\7+\2\2\u00b4"+
		"\32\3\2\2\2\u00b5\u00b6\7=\2\2\u00b6\34\3\2\2\2\u00b7\u00b8\7k\2\2\u00b8"+
		"\u00b9\7h\2\2\u00b9\36\3\2\2\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7n\2\2\u00bc"+
		"\u00bd\7u\2\2\u00bd\u00be\7g\2\2\u00be \3\2\2\2\u00bf\u00c0\7y\2\2\u00c0"+
		"\u00c1\7j\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7n\2\2\u00c3\u00c4\7g\2\2"+
		"\u00c4\"\3\2\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7v"+
		"\2\2\u00c8\u00c9\7w\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb\7p\2\2\u00cb$\3"+
		"\2\2\2\u00cc\u00cd\7(\2\2\u00cd\u00ce\7(\2\2\u00ce&\3\2\2\2\u00cf\u00d0"+
		"\7?\2\2\u00d0\u00d1\7?\2\2\u00d1(\3\2\2\2\u00d2\u00d3\7~\2\2\u00d3\u00d4"+
		"\7~\2\2\u00d4*\3\2\2\2\u00d5\u00d6\7#\2\2\u00d6\u00d7\7?\2\2\u00d7,\3"+
		"\2\2\2\u00d8\u00d9\7>\2\2\u00d9\u00da\7?\2\2\u00da.\3\2\2\2\u00db\u00dc"+
		"\7@\2\2\u00dc\u00dd\7?\2\2\u00dd\60\3\2\2\2\u00de\u00df\7@\2\2\u00df\62"+
		"\3\2\2\2\u00e0\u00e1\7>\2\2\u00e1\64\3\2\2\2\u00e2\u00e3\7k\2\2\u00e3"+
		"\u00e4\7p\2\2\u00e4\u00e5\7u\2\2\u00e5\u00e6\7v\2\2\u00e6\u00e7\7c\2\2"+
		"\u00e7\u00e8\7p\2\2\u00e8\u00e9\7e\2\2\u00e9\u00ea\7g\2\2\u00ea\u00eb"+
		"\7q\2\2\u00eb\u00ec\7h\2\2\u00ec\66\3\2\2\2\u00ed\u00ee\7#\2\2\u00ee8"+
		"\3\2\2\2\u00ef\u00f0\7\60\2\2\u00f0:\3\2\2\2\u00f1\u00f2\7v\2\2\u00f2"+
		"\u00f3\7t\2\2\u00f3\u00f4\7w\2\2\u00f4\u00f5\7g\2\2\u00f5<\3\2\2\2\u00f6"+
		"\u00f7\7h\2\2\u00f7\u00f8\7c\2\2\u00f8\u00f9\7n\2\2\u00f9\u00fa\7u\2\2"+
		"\u00fa\u00fb\7g\2\2\u00fb>\3\2\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe\7j\2"+
		"\2\u00fe\u00ff\7k\2\2\u00ff\u0100\7u\2\2\u0100@\3\2\2\2\u0101\u0102\7"+
		"p\2\2\u0102\u0103\7w\2\2\u0103\u0104\7n\2\2\u0104\u0105\7n\2\2\u0105B"+
		"\3\2\2\2\u0106\u0107\7e\2\2\u0107\u0108\7n\2\2\u0108\u0109\7c\2\2\u0109"+
		"\u010a\7u\2\2\u010a\u010b\7u\2\2\u010bD\3\2\2\2\u010c\u010d\7g\2\2\u010d"+
		"\u010e\7z\2\2\u010e\u010f\7v\2\2\u010f\u0110\7g\2\2\u0110\u0111\7p\2\2"+
		"\u0111\u0112\7f\2\2\u0112\u0113\7u\2\2\u0113F\3\2\2\2\u0114\u0115\7r\2"+
		"\2\u0115\u0116\7t\2\2\u0116\u0117\7q\2\2\u0117\u0118\7v\2\2\u0118\u0119"+
		"\7g\2\2\u0119\u011a\7e\2\2\u011a\u011b\7v\2\2\u011b\u011c\7g\2\2\u011c"+
		"\u011d\7f\2\2\u011dH\3\2\2\2\u011e\u011f\7,\2\2\u011fJ\3\2\2\2\u0120\u0121"+
		"\7h\2\2\u0121\u0122\7o\2\2\u0122\u0123\7c\2\2\u0123L\3\2\2\2\u0124\u0125"+
		"\7\61\2\2\u0125N\3\2\2\2\u0126\u0127\7\'\2\2\u0127P\3\2\2\2\u0128\u0129"+
		"\7t\2\2\u0129\u012a\7g\2\2\u012a\u012b\7c\2\2\u012b\u012c\7f\2\2\u012c"+
		"\u012d\7K\2\2\u012d\u012e\7p\2\2\u012e\u012f\7v\2\2\u012fR\3\2\2\2\u0130"+
		"\u0131\7t\2\2\u0131\u0132\7g\2\2\u0132\u0133\7c\2\2\u0133\u0134\7f\2\2"+
		"\u0134\u0135\7H\2\2\u0135\u0136\7n\2\2\u0136\u0137\7q\2\2\u0137\u0138"+
		"\7c\2\2\u0138\u0139\7v\2\2\u0139T\3\2\2\2\u013a\u013b\7p\2\2\u013b\u013c"+
		"\7g\2\2\u013c\u013d\7y\2\2\u013dV\3\2\2\2\u013e\u013f\7c\2\2\u013f\u0140"+
		"\7u\2\2\u0140\u0141\7o\2\2\u0141X\3\2\2\2\u0142\u0143\7\"\2\2\u0143\u0144"+
		"\b-\2\2\u0144Z\3\2\2\2\u0145\u0146\7\13\2\2\u0146\u0147\b.\3\2\u0147\\"+
		"\3\2\2\2\u0148\u0149\4\62;\2\u0149^\3\2\2\2\u014a\u014b\t\2\2\2\u014b"+
		"`\3\2\2\2\u014c\u014f\5_\60\2\u014d\u014f\t\3\2\2\u014e\u014c\3\2\2\2"+
		"\u014e\u014d\3\2\2\2\u014f\u0155\3\2\2\2\u0150\u0154\5_\60\2\u0151\u0154"+
		"\5]/\2\u0152\u0154\t\3\2\2\u0153\u0150\3\2\2\2\u0153\u0151\3\2\2\2\u0153"+
		"\u0152\3\2\2\2\u0154\u0157\3\2\2\2\u0155\u0153\3\2\2\2\u0155\u0156\3\2"+
		"\2\2\u0156b\3\2\2\2\u0157\u0155\3\2\2\2\u0158\u015a\5]/\2\u0159\u0158"+
		"\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c"+
		"d\3\2\2\2\u015d\u015e\4\63;\2\u015ef\3\2\2\2\u015f\u0168\7\62\2\2\u0160"+
		"\u0164\5e\63\2\u0161\u0163\5]/\2\u0162\u0161\3\2\2\2\u0163\u0166\3\2\2"+
		"\2\u0164\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0168\3\2\2\2\u0166\u0164"+
		"\3\2\2\2\u0167\u015f\3\2\2\2\u0167\u0160\3\2\2\2\u0168h\3\2\2\2\u0169"+
		"\u016a\t\4\2\2\u016aj\3\2\2\2\u016b\u016c\t\5\2\2\u016c\u016d\5i\65\2"+
		"\u016d\u016e\5c\62\2\u016el\3\2\2\2\u016f\u0170\5c\62\2\u0170\u0171\7"+
		"\60\2\2\u0171\u0172\5c\62\2\u0172n\3\2\2\2\u0173\u0178\5m\67\2\u0174\u0175"+
		"\5m\67\2\u0175\u0176\5k\66\2\u0176\u0178\3\2\2\2\u0177\u0173\3\2\2\2\u0177"+
		"\u0174\3\2\2\2\u0178\u017b\3\2\2\2\u0179\u017c\t\6\2\2\u017a\u017c\3\2"+
		"\2\2\u017b\u0179\3\2\2\2\u017b\u017a\3\2\2\2\u017cp\3\2\2\2\u017d\u017e"+
		"\t\7\2\2\u017er\3\2\2\2\u017f\u0181\5q9\2\u0180\u017f\3\2\2\2\u0181\u0182"+
		"\3\2\2\2\u0182\u0180\3\2\2\2\u0182\u0183\3\2\2\2\u0183t\3\2\2\2\u0184"+
		"\u0185\7\62\2\2\u0185\u0189\7z\2\2\u0186\u0187\7\62\2\2\u0187\u0189\7"+
		"Z\2\2\u0188\u0184\3\2\2\2\u0188\u0186\3\2\2\2\u0189\u018a\3\2\2\2\u018a"+
		"\u018b\5s:\2\u018b\u018c\7\60\2\2\u018c\u018d\5s:\2\u018d\u018e\t\b\2"+
		"\2\u018e\u018f\5i\65\2\u018f\u0192\5c\62\2\u0190\u0193\t\6\2\2\u0191\u0193"+
		"\3\2\2\2\u0192\u0190\3\2\2\2\u0192\u0191\3\2\2\2\u0193v\3\2\2\2\u0194"+
		"\u0197\5o8\2\u0195\u0197\5u;\2\u0196\u0194\3\2\2\2\u0196\u0195\3\2\2\2"+
		"\u0197x\3\2\2\2\u0198\u0199\7\f\2\2\u0199\u019a\b=\4\2\u019az\3\2\2\2"+
		"\u019b\u019c\n\t\2\2\u019c|\3\2\2\2\u019d\u01a5\7$\2\2\u019e\u01a4\5{"+
		">\2\u019f\u01a0\7^\2\2\u01a0\u01a4\7$\2\2\u01a1\u01a2\7^\2\2\u01a2\u01a4"+
		"\7^\2\2\u01a3\u019e\3\2\2\2\u01a3\u019f\3\2\2\2\u01a3\u01a1\3\2\2\2\u01a4"+
		"\u01a7\3\2\2\2\u01a5\u01a3\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01a8\3\2"+
		"\2\2\u01a7\u01a5\3\2\2\2\u01a8\u01a9\7$\2\2\u01a9~\3\2\2\2\u01aa\u01b3"+
		"\7$\2\2\u01ab\u01b2\5{>\2\u01ac\u01b2\5y=\2\u01ad\u01ae\7^\2\2\u01ae\u01b2"+
		"\7$\2\2\u01af\u01b0\7^\2\2\u01b0\u01b2\7^\2\2\u01b1\u01ab\3\2\2\2\u01b1"+
		"\u01ac\3\2\2\2\u01b1\u01ad\3\2\2\2\u01b1\u01af\3\2\2\2\u01b2\u01b5\3\2"+
		"\2\2\u01b3\u01b1\3\2\2\2\u01b3\u01b4\3\2\2\2\u01b4\u01b6\3\2\2\2\u01b5"+
		"\u01b3\3\2\2\2\u01b6\u01b7\7$\2\2\u01b7\u0080\3\2\2\2\u01b8\u01b9\7\61"+
		"\2\2\u01b9\u01ba\7,\2\2\u01ba\u01be\3\2\2\2\u01bb\u01bd\13\2\2\2\u01bc"+
		"\u01bb\3\2\2\2\u01bd\u01c0\3\2\2\2\u01be\u01bf\3\2\2\2\u01be\u01bc\3\2"+
		"\2\2\u01bf\u01c1\3\2\2\2\u01c0\u01be\3\2\2\2\u01c1\u01c2\7,\2\2\u01c2"+
		"\u01ce\7\61\2\2\u01c3\u01c4\7\61\2\2\u01c4\u01c5\7\61\2\2\u01c5\u01c9"+
		"\3\2\2\2\u01c6\u01c8\13\2\2\2\u01c7\u01c6\3\2\2\2\u01c8\u01cb\3\2\2\2"+
		"\u01c9\u01ca\3\2\2\2\u01c9\u01c7\3\2\2\2\u01ca\u01cc\3\2\2\2\u01cb\u01c9"+
		"\3\2\2\2\u01cc\u01ce\7\f\2\2\u01cd\u01b8\3\2\2\2\u01cd\u01c3\3\2\2\2\u01ce"+
		"\u01cf\3\2\2\2\u01cf\u01d0\bA\5\2\u01d0\u0082\3\2\2\2\u01d1\u01d5\5_\60"+
		"\2\u01d2\u01d5\5]/\2\u01d3\u01d5\t\n\2\2\u01d4\u01d1\3\2\2\2\u01d4\u01d2"+
		"\3\2\2\2\u01d4\u01d3\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d6"+
		"\u01d7\3\2\2\2\u01d7\u0084\3\2\2\2\u01d8\u01d9\7%\2\2\u01d9\u01da\7k\2"+
		"\2\u01da\u01db\7p\2\2\u01db\u01dc\7e\2\2\u01dc\u01dd\7n\2\2\u01dd\u01de"+
		"\7w\2\2\u01de\u01df\7f\2\2\u01df\u01e0\7g\2\2\u01e0\u01e4\3\2\2\2\u01e1"+
		"\u01e3\7\"\2\2\u01e2\u01e1\3\2\2\2\u01e3\u01e6\3\2\2\2\u01e4\u01e2\3\2"+
		"\2\2\u01e4\u01e5\3\2\2\2\u01e5\u01e7\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e7"+
		"\u01e8\7$\2\2\u01e8\u01e9\5\u0083B\2\u01e9\u01ea\7$\2\2\u01ea\u01eb\3"+
		"\2\2\2\u01eb\u01ec\bC\6\2\u01ec\u0086\3\2\2\2\31\2\u014e\u0153\u0155\u015b"+
		"\u0164\u0167\u0177\u017b\u0182\u0188\u0192\u0196\u01a3\u01a5\u01b1\u01b3"+
		"\u01be\u01c9\u01cd\u01d4\u01d6\u01e4\7\3-\2\3.\3\3=\4\3A\5\3C\6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
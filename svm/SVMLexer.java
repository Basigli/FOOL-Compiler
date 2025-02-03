// Generated from /Users/gabe/Documents/GitHub/FOOL-Compiler/svm/SVM.g4 by ANTLR 4.13.2
package svm;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class SVMLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PUSH=1, POP=2, ADD=3, SUB=4, MULT=5, DIV=6, STOREW=7, LOADW=8, BRANCH=9, 
		BRANCHEQ=10, BRANCHLESSEQ=11, JS=12, LOADRA=13, STORERA=14, LOADTM=15, 
		STORETM=16, LOADFP=17, STOREFP=18, COPYFP=19, LOADHP=20, STOREHP=21, PRINT=22, 
		HALT=23, COL=24, LABEL=25, INTEGER=26, COMMENT=27, WHITESP=28, ERR=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PUSH", "POP", "ADD", "SUB", "MULT", "DIV", "STOREW", "LOADW", "BRANCH", 
			"BRANCHEQ", "BRANCHLESSEQ", "JS", "LOADRA", "STORERA", "LOADTM", "STORETM", 
			"LOADFP", "STOREFP", "COPYFP", "LOADHP", "STOREHP", "PRINT", "HALT", 
			"COL", "LABEL", "INTEGER", "COMMENT", "WHITESP", "ERR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'push'", "'pop'", "'add'", "'sub'", "'mult'", "'div'", "'sw'", 
			"'lw'", "'b'", "'beq'", "'bleq'", "'js'", "'lra'", "'sra'", "'ltm'", 
			"'stm'", "'lfp'", "'sfp'", "'cfp'", "'lhp'", "'shp'", "'print'", "'halt'", 
			"':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PUSH", "POP", "ADD", "SUB", "MULT", "DIV", "STOREW", "LOADW", 
			"BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "JS", "LOADRA", "STORERA", "LOADTM", 
			"STORETM", "LOADFP", "STOREFP", "COPYFP", "LOADHP", "STOREHP", "PRINT", 
			"HALT", "COL", "LABEL", "INTEGER", "COMMENT", "WHITESP", "ERR"
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


	public int lexicalErrors=0;


	public SVMLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

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
		case 28:
			ERR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ERR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 System.out.println("Invalid char: "+getText()+" at line "+getLine()); lexicalErrors++; 
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u0000\u001d\u00c8\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0005\u0018"+
		"\u009d\b\u0018\n\u0018\f\u0018\u00a0\t\u0018\u0001\u0019\u0001\u0019\u0003"+
		"\u0019\u00a4\b\u0019\u0001\u0019\u0001\u0019\u0005\u0019\u00a8\b\u0019"+
		"\n\u0019\f\u0019\u00ab\t\u0019\u0003\u0019\u00ad\b\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u00b3\b\u001a\n\u001a\f\u001a"+
		"\u00b6\t\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001b\u0004\u001b\u00be\b\u001b\u000b\u001b\f\u001b\u00bf\u0001"+
		"\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u00b4\u0000\u001d\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b"+
		"\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013"+
		"\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d"+
		"\u0001\u0000\u0003\u0002\u0000AZaz\u0003\u000009AZaz\u0003\u0000\t\n\r"+
		"\r  \u00cd\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000"+
		"\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000"+
		"\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%"+
		"\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001"+
		"\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000"+
		"\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0000"+
		"3\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001"+
		"\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0001;\u0001\u0000\u0000"+
		"\u0000\u0003@\u0001\u0000\u0000\u0000\u0005D\u0001\u0000\u0000\u0000\u0007"+
		"H\u0001\u0000\u0000\u0000\tL\u0001\u0000\u0000\u0000\u000bQ\u0001\u0000"+
		"\u0000\u0000\rU\u0001\u0000\u0000\u0000\u000fX\u0001\u0000\u0000\u0000"+
		"\u0011[\u0001\u0000\u0000\u0000\u0013]\u0001\u0000\u0000\u0000\u0015a"+
		"\u0001\u0000\u0000\u0000\u0017f\u0001\u0000\u0000\u0000\u0019i\u0001\u0000"+
		"\u0000\u0000\u001bm\u0001\u0000\u0000\u0000\u001dq\u0001\u0000\u0000\u0000"+
		"\u001fu\u0001\u0000\u0000\u0000!y\u0001\u0000\u0000\u0000#}\u0001\u0000"+
		"\u0000\u0000%\u0081\u0001\u0000\u0000\u0000\'\u0085\u0001\u0000\u0000"+
		"\u0000)\u0089\u0001\u0000\u0000\u0000+\u008d\u0001\u0000\u0000\u0000-"+
		"\u0093\u0001\u0000\u0000\u0000/\u0098\u0001\u0000\u0000\u00001\u009a\u0001"+
		"\u0000\u0000\u00003\u00ac\u0001\u0000\u0000\u00005\u00ae\u0001\u0000\u0000"+
		"\u00007\u00bd\u0001\u0000\u0000\u00009\u00c3\u0001\u0000\u0000\u0000;"+
		"<\u0005p\u0000\u0000<=\u0005u\u0000\u0000=>\u0005s\u0000\u0000>?\u0005"+
		"h\u0000\u0000?\u0002\u0001\u0000\u0000\u0000@A\u0005p\u0000\u0000AB\u0005"+
		"o\u0000\u0000BC\u0005p\u0000\u0000C\u0004\u0001\u0000\u0000\u0000DE\u0005"+
		"a\u0000\u0000EF\u0005d\u0000\u0000FG\u0005d\u0000\u0000G\u0006\u0001\u0000"+
		"\u0000\u0000HI\u0005s\u0000\u0000IJ\u0005u\u0000\u0000JK\u0005b\u0000"+
		"\u0000K\b\u0001\u0000\u0000\u0000LM\u0005m\u0000\u0000MN\u0005u\u0000"+
		"\u0000NO\u0005l\u0000\u0000OP\u0005t\u0000\u0000P\n\u0001\u0000\u0000"+
		"\u0000QR\u0005d\u0000\u0000RS\u0005i\u0000\u0000ST\u0005v\u0000\u0000"+
		"T\f\u0001\u0000\u0000\u0000UV\u0005s\u0000\u0000VW\u0005w\u0000\u0000"+
		"W\u000e\u0001\u0000\u0000\u0000XY\u0005l\u0000\u0000YZ\u0005w\u0000\u0000"+
		"Z\u0010\u0001\u0000\u0000\u0000[\\\u0005b\u0000\u0000\\\u0012\u0001\u0000"+
		"\u0000\u0000]^\u0005b\u0000\u0000^_\u0005e\u0000\u0000_`\u0005q\u0000"+
		"\u0000`\u0014\u0001\u0000\u0000\u0000ab\u0005b\u0000\u0000bc\u0005l\u0000"+
		"\u0000cd\u0005e\u0000\u0000de\u0005q\u0000\u0000e\u0016\u0001\u0000\u0000"+
		"\u0000fg\u0005j\u0000\u0000gh\u0005s\u0000\u0000h\u0018\u0001\u0000\u0000"+
		"\u0000ij\u0005l\u0000\u0000jk\u0005r\u0000\u0000kl\u0005a\u0000\u0000"+
		"l\u001a\u0001\u0000\u0000\u0000mn\u0005s\u0000\u0000no\u0005r\u0000\u0000"+
		"op\u0005a\u0000\u0000p\u001c\u0001\u0000\u0000\u0000qr\u0005l\u0000\u0000"+
		"rs\u0005t\u0000\u0000st\u0005m\u0000\u0000t\u001e\u0001\u0000\u0000\u0000"+
		"uv\u0005s\u0000\u0000vw\u0005t\u0000\u0000wx\u0005m\u0000\u0000x \u0001"+
		"\u0000\u0000\u0000yz\u0005l\u0000\u0000z{\u0005f\u0000\u0000{|\u0005p"+
		"\u0000\u0000|\"\u0001\u0000\u0000\u0000}~\u0005s\u0000\u0000~\u007f\u0005"+
		"f\u0000\u0000\u007f\u0080\u0005p\u0000\u0000\u0080$\u0001\u0000\u0000"+
		"\u0000\u0081\u0082\u0005c\u0000\u0000\u0082\u0083\u0005f\u0000\u0000\u0083"+
		"\u0084\u0005p\u0000\u0000\u0084&\u0001\u0000\u0000\u0000\u0085\u0086\u0005"+
		"l\u0000\u0000\u0086\u0087\u0005h\u0000\u0000\u0087\u0088\u0005p\u0000"+
		"\u0000\u0088(\u0001\u0000\u0000\u0000\u0089\u008a\u0005s\u0000\u0000\u008a"+
		"\u008b\u0005h\u0000\u0000\u008b\u008c\u0005p\u0000\u0000\u008c*\u0001"+
		"\u0000\u0000\u0000\u008d\u008e\u0005p\u0000\u0000\u008e\u008f\u0005r\u0000"+
		"\u0000\u008f\u0090\u0005i\u0000\u0000\u0090\u0091\u0005n\u0000\u0000\u0091"+
		"\u0092\u0005t\u0000\u0000\u0092,\u0001\u0000\u0000\u0000\u0093\u0094\u0005"+
		"h\u0000\u0000\u0094\u0095\u0005a\u0000\u0000\u0095\u0096\u0005l\u0000"+
		"\u0000\u0096\u0097\u0005t\u0000\u0000\u0097.\u0001\u0000\u0000\u0000\u0098"+
		"\u0099\u0005:\u0000\u0000\u00990\u0001\u0000\u0000\u0000\u009a\u009e\u0007"+
		"\u0000\u0000\u0000\u009b\u009d\u0007\u0001\u0000\u0000\u009c\u009b\u0001"+
		"\u0000\u0000\u0000\u009d\u00a0\u0001\u0000\u0000\u0000\u009e\u009c\u0001"+
		"\u0000\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f2\u0001\u0000"+
		"\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a1\u00ad\u00050\u0000"+
		"\u0000\u00a2\u00a4\u0005-\u0000\u0000\u00a3\u00a2\u0001\u0000\u0000\u0000"+
		"\u00a3\u00a4\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a9\u000219\u0000\u00a6\u00a8\u000209\u0000\u00a7\u00a6\u0001"+
		"\u0000\u0000\u0000\u00a8\u00ab\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001"+
		"\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000\u00aa\u00ad\u0001"+
		"\u0000\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ac\u00a1\u0001"+
		"\u0000\u0000\u0000\u00ac\u00a3\u0001\u0000\u0000\u0000\u00ad4\u0001\u0000"+
		"\u0000\u0000\u00ae\u00af\u0005/\u0000\u0000\u00af\u00b0\u0005*\u0000\u0000"+
		"\u00b0\u00b4\u0001\u0000\u0000\u0000\u00b1\u00b3\t\u0000\u0000\u0000\u00b2"+
		"\u00b1\u0001\u0000\u0000\u0000\u00b3\u00b6\u0001\u0000\u0000\u0000\u00b4"+
		"\u00b5\u0001\u0000\u0000\u0000\u00b4\u00b2\u0001\u0000\u0000\u0000\u00b5"+
		"\u00b7\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u00b7"+
		"\u00b8\u0005*\u0000\u0000\u00b8\u00b9\u0005/\u0000\u0000\u00b9\u00ba\u0001"+
		"\u0000\u0000\u0000\u00ba\u00bb\u0006\u001a\u0000\u0000\u00bb6\u0001\u0000"+
		"\u0000\u0000\u00bc\u00be\u0007\u0002\u0000\u0000\u00bd\u00bc\u0001\u0000"+
		"\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf\u00bd\u0001\u0000"+
		"\u0000\u0000\u00bf\u00c0\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u0006\u001b\u0000\u0000\u00c28\u0001\u0000\u0000"+
		"\u0000\u00c3\u00c4\t\u0000\u0000\u0000\u00c4\u00c5\u0006\u001c\u0001\u0000"+
		"\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c7\u0006\u001c\u0000\u0000"+
		"\u00c7:\u0001\u0000\u0000\u0000\u0007\u0000\u009e\u00a3\u00a9\u00ac\u00b4"+
		"\u00bf\u0002\u0000\u0001\u0000\u0001\u001c\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
// Generated from /Users/gabe/Documents/GitHub/FOOL-Compiler/compiler/FOOL.g4 by ANTLR 4.13.2
package compiler;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FOOLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, TIMES=3, LPAR=4, RPAR=5, CLPAR=6, CRPAR=7, SEMIC=8, COLON=9, 
		COMMA=10, EQ=11, ASS=12, TRUE=13, FALSE=14, IF=15, THEN=16, ELSE=17, PRINT=18, 
		LET=19, IN=20, VAR=21, FUN=22, INT=23, BOOL=24, NUM=25, ID=26, WHITESP=27, 
		COMMENT=28, ERR=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PLUS", "MINUS", "TIMES", "LPAR", "RPAR", "CLPAR", "CRPAR", "SEMIC", 
			"COLON", "COMMA", "EQ", "ASS", "TRUE", "FALSE", "IF", "THEN", "ELSE", 
			"PRINT", "LET", "IN", "VAR", "FUN", "INT", "BOOL", "NUM", "ID", "WHITESP", 
			"COMMENT", "ERR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'('", "')'", "'{'", "'}'", "';'", "':'", 
			"','", "'=='", "'='", "'true'", "'false'", "'if'", "'then'", "'else'", 
			"'print'", "'let'", "'in'", "'var'", "'fun'", "'int'", "'bool'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PLUS", "MINUS", "TIMES", "LPAR", "RPAR", "CLPAR", "CRPAR", "SEMIC", 
			"COLON", "COMMA", "EQ", "ASS", "TRUE", "FALSE", "IF", "THEN", "ELSE", 
			"PRINT", "LET", "IN", "VAR", "FUN", "INT", "BOOL", "NUM", "ID", "WHITESP", 
			"COMMENT", "ERR"
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


	public FOOLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FOOL.g4"; }

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
			 System.out.println("Invalid char "+getText()+" at line "+getLine()); lexicalErrors++; 
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u0000\u001d\u00b5\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0005\u0018\u008e\b\u0018\n\u0018\f\u0018\u0091"+
		"\t\u0018\u0003\u0018\u0093\b\u0018\u0001\u0019\u0001\u0019\u0005\u0019"+
		"\u0097\b\u0019\n\u0019\f\u0019\u009a\t\u0019\u0001\u001a\u0004\u001a\u009d"+
		"\b\u001a\u000b\u001a\f\u001a\u009e\u0001\u001a\u0001\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0005\u001b\u00a7\b\u001b\n\u001b"+
		"\f\u001b\u00aa\t\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0001\u00a8\u0000\u001d\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'"+
		"\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d"+
		"\u0001\u0000\u0003\u0002\u0000AZaz\u0003\u000009AZaz\u0003\u0000\t\n\r"+
		"\r  \u00b9\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
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
		"\u0000\u0003=\u0001\u0000\u0000\u0000\u0005?\u0001\u0000\u0000\u0000\u0007"+
		"A\u0001\u0000\u0000\u0000\tC\u0001\u0000\u0000\u0000\u000bE\u0001\u0000"+
		"\u0000\u0000\rG\u0001\u0000\u0000\u0000\u000fI\u0001\u0000\u0000\u0000"+
		"\u0011K\u0001\u0000\u0000\u0000\u0013M\u0001\u0000\u0000\u0000\u0015O"+
		"\u0001\u0000\u0000\u0000\u0017R\u0001\u0000\u0000\u0000\u0019T\u0001\u0000"+
		"\u0000\u0000\u001bY\u0001\u0000\u0000\u0000\u001d_\u0001\u0000\u0000\u0000"+
		"\u001fb\u0001\u0000\u0000\u0000!g\u0001\u0000\u0000\u0000#l\u0001\u0000"+
		"\u0000\u0000%r\u0001\u0000\u0000\u0000\'v\u0001\u0000\u0000\u0000)y\u0001"+
		"\u0000\u0000\u0000+}\u0001\u0000\u0000\u0000-\u0081\u0001\u0000\u0000"+
		"\u0000/\u0085\u0001\u0000\u0000\u00001\u0092\u0001\u0000\u0000\u00003"+
		"\u0094\u0001\u0000\u0000\u00005\u009c\u0001\u0000\u0000\u00007\u00a2\u0001"+
		"\u0000\u0000\u00009\u00b0\u0001\u0000\u0000\u0000;<\u0005+\u0000\u0000"+
		"<\u0002\u0001\u0000\u0000\u0000=>\u0005-\u0000\u0000>\u0004\u0001\u0000"+
		"\u0000\u0000?@\u0005*\u0000\u0000@\u0006\u0001\u0000\u0000\u0000AB\u0005"+
		"(\u0000\u0000B\b\u0001\u0000\u0000\u0000CD\u0005)\u0000\u0000D\n\u0001"+
		"\u0000\u0000\u0000EF\u0005{\u0000\u0000F\f\u0001\u0000\u0000\u0000GH\u0005"+
		"}\u0000\u0000H\u000e\u0001\u0000\u0000\u0000IJ\u0005;\u0000\u0000J\u0010"+
		"\u0001\u0000\u0000\u0000KL\u0005:\u0000\u0000L\u0012\u0001\u0000\u0000"+
		"\u0000MN\u0005,\u0000\u0000N\u0014\u0001\u0000\u0000\u0000OP\u0005=\u0000"+
		"\u0000PQ\u0005=\u0000\u0000Q\u0016\u0001\u0000\u0000\u0000RS\u0005=\u0000"+
		"\u0000S\u0018\u0001\u0000\u0000\u0000TU\u0005t\u0000\u0000UV\u0005r\u0000"+
		"\u0000VW\u0005u\u0000\u0000WX\u0005e\u0000\u0000X\u001a\u0001\u0000\u0000"+
		"\u0000YZ\u0005f\u0000\u0000Z[\u0005a\u0000\u0000[\\\u0005l\u0000\u0000"+
		"\\]\u0005s\u0000\u0000]^\u0005e\u0000\u0000^\u001c\u0001\u0000\u0000\u0000"+
		"_`\u0005i\u0000\u0000`a\u0005f\u0000\u0000a\u001e\u0001\u0000\u0000\u0000"+
		"bc\u0005t\u0000\u0000cd\u0005h\u0000\u0000de\u0005e\u0000\u0000ef\u0005"+
		"n\u0000\u0000f \u0001\u0000\u0000\u0000gh\u0005e\u0000\u0000hi\u0005l"+
		"\u0000\u0000ij\u0005s\u0000\u0000jk\u0005e\u0000\u0000k\"\u0001\u0000"+
		"\u0000\u0000lm\u0005p\u0000\u0000mn\u0005r\u0000\u0000no\u0005i\u0000"+
		"\u0000op\u0005n\u0000\u0000pq\u0005t\u0000\u0000q$\u0001\u0000\u0000\u0000"+
		"rs\u0005l\u0000\u0000st\u0005e\u0000\u0000tu\u0005t\u0000\u0000u&\u0001"+
		"\u0000\u0000\u0000vw\u0005i\u0000\u0000wx\u0005n\u0000\u0000x(\u0001\u0000"+
		"\u0000\u0000yz\u0005v\u0000\u0000z{\u0005a\u0000\u0000{|\u0005r\u0000"+
		"\u0000|*\u0001\u0000\u0000\u0000}~\u0005f\u0000\u0000~\u007f\u0005u\u0000"+
		"\u0000\u007f\u0080\u0005n\u0000\u0000\u0080,\u0001\u0000\u0000\u0000\u0081"+
		"\u0082\u0005i\u0000\u0000\u0082\u0083\u0005n\u0000\u0000\u0083\u0084\u0005"+
		"t\u0000\u0000\u0084.\u0001\u0000\u0000\u0000\u0085\u0086\u0005b\u0000"+
		"\u0000\u0086\u0087\u0005o\u0000\u0000\u0087\u0088\u0005o\u0000\u0000\u0088"+
		"\u0089\u0005l\u0000\u0000\u00890\u0001\u0000\u0000\u0000\u008a\u0093\u0005"+
		"0\u0000\u0000\u008b\u008f\u000219\u0000\u008c\u008e\u000209\u0000\u008d"+
		"\u008c\u0001\u0000\u0000\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f"+
		"\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090"+
		"\u0093\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0092"+
		"\u008a\u0001\u0000\u0000\u0000\u0092\u008b\u0001\u0000\u0000\u0000\u0093"+
		"2\u0001\u0000\u0000\u0000\u0094\u0098\u0007\u0000\u0000\u0000\u0095\u0097"+
		"\u0007\u0001\u0000\u0000\u0096\u0095\u0001\u0000\u0000\u0000\u0097\u009a"+
		"\u0001\u0000\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0098\u0099"+
		"\u0001\u0000\u0000\u0000\u00994\u0001\u0000\u0000\u0000\u009a\u0098\u0001"+
		"\u0000\u0000\u0000\u009b\u009d\u0007\u0002\u0000\u0000\u009c\u009b\u0001"+
		"\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u009c\u0001"+
		"\u0000\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a0\u00a1\u0006\u001a\u0000\u0000\u00a16\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a3\u0005/\u0000\u0000\u00a3\u00a4\u0005*\u0000\u0000"+
		"\u00a4\u00a8\u0001\u0000\u0000\u0000\u00a5\u00a7\t\u0000\u0000\u0000\u00a6"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a9\u0001\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a9"+
		"\u00ab\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00ab"+
		"\u00ac\u0005*\u0000\u0000\u00ac\u00ad\u0005/\u0000\u0000\u00ad\u00ae\u0001"+
		"\u0000\u0000\u0000\u00ae\u00af\u0006\u001b\u0000\u0000\u00af8\u0001\u0000"+
		"\u0000\u0000\u00b0\u00b1\t\u0000\u0000\u0000\u00b1\u00b2\u0006\u001c\u0001"+
		"\u0000\u00b2\u00b3\u0001\u0000\u0000\u0000\u00b3\u00b4\u0006\u001c\u0000"+
		"\u0000\u00b4:\u0001\u0000\u0000\u0000\u0006\u0000\u008f\u0092\u0098\u009e"+
		"\u00a8\u0002\u0000\u0001\u0000\u0001\u001c\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
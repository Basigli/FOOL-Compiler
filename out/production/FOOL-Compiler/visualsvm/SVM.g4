grammar SVM;

@parser::header {
import java.util.*;
}

@lexer::members {
public int lexicalErrors=0;
}

@parser::members {
public int[] code = new int[ExecuteVM.CODESIZE];
public int[] sourceMap = new int[ExecuteVM.CODESIZE];
private int i = 0;
private Map<String,Integer> labelDef = new HashMap<>();
private Map<Integer,String> labelRef = new HashMap<>();
private void codem(int line, int ... c) {
   	for (int x : c) {
   		this.code[i] = x;
   		this.sourceMap[i] = line-1;
   		i++;
   	}
} }

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

assembly: instruction* EOF 	{ for (Integer j: labelRef.keySet())
								code[j]=labelDef.get(labelRef.get(j));
							} ;

instruction:
        t=PUSH n=INTEGER        { codem($t.line, PUSH, Integer.parseInt($n.text)); } //push NUMBER on the stack
	  | t=PUSH l=LABEL         { codem($t.line, PUSH); labelRef.put(i++,$l.text); } //push the location address pointed by LABEL on the stack
	  | t=POP                  { codem($t.line, POP); } //pop the top of the stack
	  | t=ADD	                 { codem($t.line, ADD); } //replace the two values on top of the stack with their sum
	  | t=SUB	                 { codem($t.line, SUB); } //pop the two values v1 and v2 (respectively) and push v2-v1
	  | t=MULT                 { codem($t.line, MULT); } //replace the two values on top of the stack with their product
	  | t=DIV	                 { codem($t.line, DIV); }//pop the two values v1 and v2 (respectively) and push v2/v1
	  | t=STOREW               { codem($t.line, STOREW); } //pop two values: the second one is written at the memory address pointed by the first one
	  | t=LOADW                { codem($t.line, LOADW); } //read the content of the memory cell pointed by the top of the stack and replace the top of the stack with such value
	  | l=LABEL COL          { labelDef.put($l.text, i); } //LABEL points at the location of the subsequent instruction
	  | t=BRANCH l=LABEL       { codem($t.line, BRANCH); labelRef.put(i++,$l.text); } //jump at the instruction pointed by LABEL
	  | t=BRANCHEQ l=LABEL     { codem($t.line, BRANCHEQ); labelRef.put(i++,$l.text); } //pop two values and jump if they are equal
	  | t=BRANCHLESSEQ l=LABEL { codem($t.line, BRANCHLESSEQ); labelRef.put(i++,$l.text); } //pop two values and jump if the second one is less or equal to the first one
	  | t=JS                   { codem($t.line, JS); } //pop one value from the stack: copy the instruction pointer in the RA register and jump to the popped value
	  | t=LOADRA               { codem($t.line, LOADRA); } //push in the stack the content of the RA register
	  | t=STORERA              { codem($t.line, STORERA); } //pop the top of the stack and copy it in the RA register
	  | t=LOADTM               { codem($t.line, LOADTM); } //push in the stack the content of the TM register
	  | t=STORETM              { codem($t.line, STORETM); } //pop the top of the stack and copy it in the TM register
	  | t=LOADFP               { codem($t.line, LOADFP); } //push in the stack the content of the FP register
	  | t=STOREFP              { codem($t.line, STOREFP); } //pop the top of the stack and copy it in the FP register
	  | t=COPYFP               { codem($t.line, COPYFP); } //copy in the FP register the currest stack pointer
	  | t=LOADHP               { codem($t.line, LOADHP); } //push in the stack the content of the HP register
	  | t=STOREHP              { codem($t.line, STOREHP); } //pop the top of the stack and copy it in the HP register
	  | t=PRINT                { codem($t.line, PRINT); } //visualize the top of the stack without removing it
	  | t=HALT                 { codem($t.line, HALT); } //terminate the execution
	  ;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

PUSH		: 'push' ;
POP	 		: 'pop' ;
ADD	 		: 'add' ;
SUB	 		: 'sub' ;
MULT	 	: 'mult' ;
DIV	 		: 'div' ;
STOREW	 	: 'sw' ;
LOADW	 	: 'lw' ;
BRANCH	 	: 'b' ;
BRANCHEQ 	: 'beq' ;
BRANCHLESSEQ: 'bleq' ;
JS	 		: 'js' ;
LOADRA	 	: 'lra' ;
STORERA  	: 'sra' ;
LOADTM	 	: 'ltm' ;
STORETM  	: 'stm' ;
LOADFP	 	: 'lfp' ;
STOREFP	 	: 'sfp' ;
COPYFP   	: 'cfp' ;
LOADHP	 	: 'lhp' ;
STOREHP	 	: 'shp' ;
PRINT	 	: 'print' ;
HALT	 	: 'halt' ;

COL	 		: ':' ;
LABEL	 	: ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
INTEGER	 	: '0' | ('-')?(('1'..'9')('0'..'9')*) ;

COMMENT : '/*' .*? '*/' -> channel(HIDDEN) ;

WHITESP  	: (' '|'\t'|'\n'|'\r')+ -> channel(HIDDEN) ;

ERR	     	: . { System.out.println("Invalid char: "+ getText()); lexicalErrors++; } -> channel(HIDDEN);
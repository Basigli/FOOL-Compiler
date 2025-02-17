package compiler;

import compiler.AST.*;
import compiler.lib.*;
import compiler.exc.*;
import visualsvm.ExecuteVM;

import java.util.ArrayList;
import java.util.List;

import static compiler.lib.FOOLlib.*;

public class CodeGenerationASTVisitor extends BaseASTVisitor<String, VoidException> {
	List<List<String>> dispatchTables;

	CodeGenerationASTVisitor() {
	}

	CodeGenerationASTVisitor(boolean debug) {
		super(false, debug);
	} //enables print for debugging


	@Override
	public String visitNode(ProgLetInNode n) {
		if (print) printNode(n);
		String declCode = null;
		for (Node dec : n.declist) declCode = nlJoin(declCode, visit(dec));
		return nlJoin(
				"push 0",
				declCode, // generate code for declarations (allocation)
				visit(n.exp),
				"halt",
				getCode()
		);
	}

	@Override
	public String visitNode(ProgNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.exp),
				"halt"
		);
	}

	@Override
	public String visitNode(FunNode n) {
		if (print) printNode(n, n.id);
		String declCode = null, popDecl = null, popParl = null;
		for (Node dec : n.declist) {
			declCode = nlJoin(declCode, visit(dec));
			popDecl = nlJoin(popDecl, "pop");
		}
		for (int i = 0; i < n.parlist.size(); i++) popParl = nlJoin(popParl, "pop");
		String funl = freshFunLabel();
		System.out.println("FunNode: " + n.id + " -> " + funl);
		putCode(
				nlJoin(
						funl + ":",
						"cfp", // set $fp to $sp value
						"lra", // load $ra value
						declCode, // generate code for local declarations (they use the new $fp!!!)
						visit(n.exp), // generate code for function body expression
						"stm", // set $tm to popped value (function result)
						popDecl, // remove local declarations from stack
						"sra", // set $ra to popped value
						"pop", // remove Access Link from stack
						popParl, // remove parameters from stack
						"sfp", // set $fp to popped value (Control Link)
						"ltm", // load $tm value (function result)
						"lra", // load $ra value
						"js"  // jump to to popped address
				)
		);
		return "push " + funl;
	}

	@Override
	public String visitNode(VarNode n) {
		if (print) printNode(n, n.id);
		return visit(n.exp);
	}

	@Override
	public String visitNode(PrintNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.exp),
				"print"
		);
	}

	@Override
	public String visitNode(IfNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.cond),
				"push 1",
				"beq " + l1,
				visit(n.el),
				"b " + l2,
				l1 + ":",
				visit(n.th),
				l2 + ":"
		);
	}

	@Override
	public String visitNode(EqualNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"beq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":"
		);
	}

	@Override
	public String visitNode(TimesNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"mult"
		);
	}

	@Override
	public String visitNode(PlusNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"add"
		);
	}

	@Override
	public String visitNode(CallNode n) {
		if (print) printNode(n, n.id);
		String argCode = null;
		String getAR = null;

		for (int i = n.arglist.size() - 1; i >= 0; i--) argCode = nlJoin(argCode, visit(n.arglist.get(i)));
		for (int i = 0; i < n.nl - n.entry.nl; i++) getAR = nlJoin(getAR, "lw");

		if (n.entry.offset < 0) {
			return nlJoin(
					"lfp", 		// load Control Link (pointer to frame of function "id" caller)
					argCode, 				// generate code for argument expressions in reversed order
					"lfp", 					// retrieve address of frame containing "id" declaration
					getAR, 					// by following the static chain (of Access Links)
					"stm", 					// set $tm to popped value (with the aim of duplicating top of stack)
					"ltm", 					// load Access Link (pointer to frame of function "id" declaration)
					"ltm", 					// duplicate top of stack
					"push "+n.entry.offset, // push method offset
					"add", 					// compute address of "id" declaration
					"lw", 					// load address of "id" function
					"js"  					// jump to popped address (saving address of subsequent instruction in $ra)
			);
		} else {	// method case
			return nlJoin(
					"lfp", 							// load Control Link (pointer to frame of function "id" caller)
					argCode, 						// generate code for argument expressions in reversed order
					"lfp", 							// retrieve address of frame containing "id" declaration
					getAR, 							// by following the static chain (of Access Links)
					"stm" ,							// save top of the stack - containing AR
					"ltm" ,							// load that address
					"push " + n.entry.offset,		// push function declaration offset
					"add",							// calculate function declaration address
					// "lw",							// put the value on the stack from memory
					"ltm" ,							// put AR on stack again
					"push " + (n.entry.offset-1),	// push offset-1 - where the label is -
					"add",							// calculate function's label address
					"lw",							// put the address on stack (label of function's subroutine)
					"js");
		}
	}

	@Override
	public String visitNode(IdNode n) {
		if (print) printNode(n, n.id);
		String getAR = null;
		for (int i = 0; i < n.nl - n.entry.nl; i++) getAR = nlJoin(getAR, "lw");
		return nlJoin(
				"lfp", getAR, // retrieve address of frame containing "id" declaration
				// by following the static chain (of Access Links)
				"push " + n.entry.offset, "add", // compute address of "id" declaration
				"lw" // load value of "id" variable
		);
	}

	@Override
	public String visitNode(BoolNode n) {
		if (print) printNode(n, n.val.toString());
		return "push " + (n.val ? 1 : 0);
	}

	@Override
	public String visitNode(IntNode n) {
		if (print) printNode(n, n.val.toString());
		return "push " + n.val;
	}

	@Override
	public String visitNode(MinusNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"sub"
		);
	}

	@Override
	public String visitNode(DivisionNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"div"
		);
	}

	public String visitNode(NotNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.exp),
				"push 0",
				"beq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":"
		);
	}

	@Override
	public String visitNode(LessEqualNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"bleq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":"
		);
	}

	@Override
	public String visitNode(GreaterEqualNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		String l3 = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"beq " + l1,
				visit(n.left),
				visit(n.right),
				"bleq " + l2,
				"push 1",
				"b " + l3,
				l1 + ":",
				"push 1",
				"b " + l3,
				l2 + ":",
				"push 0",
				l3 + ":"
		);
	}


	@Override
	public String visitNode(AndNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"add ",
				"push 2",
				"beq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":"
		);
	}


	@Override
	public String visitNode(OrNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left),
				"push 1",
				"beq " + l1,
				visit(n.right),
				"push 1",
				"beq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":"
		);
	}

	@Override
	public String visitNode(MethodNode n) {
		if (print) printNode(n);

		String declCode = null, popDecl = null, popParl = null;
		for (Node dec : n.declist) {
			declCode = nlJoin(declCode, visit(dec));
			popDecl = nlJoin(popDecl, "pop");
		}
		for (int i = 0; i < n.parlist.size(); i++) popParl = nlJoin(popParl, "pop");
		String label = freshFunLabel();
		n.label = label;
		System.out.println(n.id + " associated with label: " + n.label);

		putCode(
				nlJoin(
						label + ":",
						"cfp", // set $fp to $sp value
						"lra", // load $ra value
						declCode, // generate code for local declarations (they use the new $fp!!!)
						visit(n.exp), // generate code for function body expression
						"stm", // set $tm to popped value (function result)
						popDecl, // remove local declarations from stack
						"sra", // set $ra to popped value
						"pop", // remove Access Link from stack
						popParl, // remove parameters from stack
						"sfp", // set $fp to popped value (Control Link)
						"ltm", // load $tm value (function result)
						"lra", // load $ra value
						"js"  // jump to to popped address
				)
		);
		return "";
	}

	@Override
	public String visitNode(ClassNode n) {
		List<String> dispatchTable = new ArrayList<>();

		for (MethodNode dec : n.methodList)
			System.out.println("MethodList " + dec.id);

		for (MethodNode dec : n.methodList) {
			visit(dec);
			//dispatchTable.add(dec.label);
			dispatchTable.add(dec.offset, dec.label);
		}

		// dispatchTables.add(dispatchTable);

		String pushCode = "";
		for (String method : dispatchTable) {
			pushCode = nlJoin(
					pushCode,
						"push " + method,
						"lhp",
						"sw",
						"lhp",
						"push 1",
						"add",
						"shp"
					);
		}
		return nlJoin(
				"lhp",
				pushCode

		);
	}

	@Override
	public String visitNode(EmptyNode n) {
		return nlJoin(
				"push -1"
		);
	}

	@Override
	public String visitNode(ClassCallNode n) {

		System.out.println("ClassCallNode: " + n.nl);

		String argCode = null, getAR = null;
		for (int i = n.arglist.size() - 1; i >= 0; i--) argCode = nlJoin(argCode, visit(n.arglist.get(i)));
		for (int i = 0; i < n.nl - n.entry.nl; i++) getAR = nlJoin(getAR, "lw");

		return nlJoin(
				"lfp",
				argCode, 						// generate code for argument expressions in reversed order
				"lfp",
				getAR, // Retrieve address of frame containing ID1 declaration
				"push " + n.entry.offset, "add", // Compute address of ID1 declaration
				"lw", // Load object pointer (ID1)
				"stm", // Set $tm to popped value (object pointer)
				"ltm", // Load Access Link (object pointer)
				"ltm", // Duplicate top of stack (object pointer)
				"lw", // load value on stack from memory
				"push " + n.methodEntry.offset, "add", // Compute address of method in dispatch table
				"lw", // Load address of method
				"js"  // Jump to popped address (saving address of subsequent instruction in $ra)
		);
	}

	@Override
	public String visitNode(NewNode n) {
		String argCode = null;
		for (Node arg: n.arglist) argCode = nlJoin(argCode, visit(arg));
		// Store arguments in the heap and increment heap pointer
		for (int i = 0; i < n.arglist.size(); i++) {
			argCode = nlJoin(
					argCode,
					"lhp", // load heap pointer
					"sw",  // store word at heap pointer
					"lhp", // load heap pointer
					"push 1",
					"add", // increment heap pointer
					"shp"  // store updated heap pointer
			);
		}

		int address = ExecuteVM.MEMSIZE + n.entry.offset;

		return nlJoin(
				argCode,
				"push " + address,	// load on the stack the address
				"lw", 				// put on the stack the value in 'address' from memory
				"lhp", 				// load on the stack the hp value (as dispatch pointer address)
				"sw", 				// store at address 'hp' the dispatch pointer
				"lhp", 				// load on the stack hp value
				"lhp",				// load hp with the aim of increment it
				"push 1",			// push 1
				"add",				// calculate new hp value
				"shp"				// pop the new value and put it into hp
		);

	}

}
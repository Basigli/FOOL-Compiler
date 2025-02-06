package compiler;

import java.util.*;
import compiler.AST.*;
import compiler.exc.*;
import compiler.lib.*;

public class SymbolTableASTVisitor extends BaseASTVisitor<Void,VoidException> {
	
	private List<Map<String, STentry>> symTable = new ArrayList<>();
	private int nestingLevel=0; // current nesting level
	private int decOffset=-2; // counter for offset of local declarations at current nesting level 
	int stErrors=0;

	private Map<String, Map<String,STentry>> classTable = new HashMap<>();

	SymbolTableASTVisitor() {}
	SymbolTableASTVisitor(boolean debug) {super(debug);} // enables print for debugging

	private STentry stLookup(String id) {
		int j = nestingLevel;
		STentry entry = null;
		while (j >= 0 && entry == null) 
			entry = symTable.get(j--).get(id);	
		return entry;
	}

	@Override
	public Void visitNode(ProgLetInNode n) {
		if (print) printNode(n);
		Map<String, STentry> hm = new HashMap<>();
		symTable.add(hm);
	    for (Node dec : n.declist) visit(dec);
		visit(n.exp);
		symTable.remove(0);
		return null;
	}

	@Override
	public Void visitNode(ProgNode n) {
		if (print) printNode(n);
		visit(n.exp);
		return null;
	}
	
	@Override
	public Void visitNode(FunNode n) {
		if (print) printNode(n);

		Map<String, STentry> hm = symTable.get(nestingLevel);
		List<TypeNode> parTypes = new ArrayList<>();

		for (ParNode par : n.parlist) parTypes.add(par.getType());
		STentry entry = new STentry(nestingLevel, new ArrowTypeNode(parTypes,n.retType), decOffset--);

		//inserimento di ID nella symtable
		if (hm.put(n.id, entry) != null) {
			System.out.println("Fun id " + n.id + " at line "+ n.getLine() +" already declared");
			stErrors++;
		} 

		//creare una nuova hashmap per la symTable
		nestingLevel++;
		Map<String, STentry> hmn = new HashMap<>();
		symTable.add(hmn);

		int prevNLDecOffset=decOffset; // stores counter for offset of declarations at previous nesting level
		decOffset=-2;
		
		int parOffset=1;
		for (ParNode par : n.parlist)
			if (hmn.put(par.id, new STentry(nestingLevel,par.getType(),parOffset++)) != null) {
				System.out.println("Par id " + par.id + " at line "+ n.getLine() +" already declared");
				stErrors++;
			}

		for (Node dec : n.declist) visit(dec);
		visit(n.exp);

		//rimuovere la hashmap corrente poiche' esco dallo scope
		symTable.remove(nestingLevel--);
		decOffset=prevNLDecOffset; // restores counter for offset of declarations at previous nesting level 
		return null;
	}
	
	@Override
	public Void visitNode(VarNode n) {
		if (print) printNode(n);
		visit(n.exp);

		Map<String, STentry> hm = symTable.get(nestingLevel);
		STentry entry = new STentry(nestingLevel,n.getType(),decOffset--);

		//inserimento di ID nella symtable
		if (hm.put(n.id, entry) != null) {
			System.out.println("Var id " + n.id + " at line "+ n.getLine() +" already declared");
			stErrors++;
		}
		return null;
	}

	@Override
	public Void visitNode(PrintNode n) {
		if (print) printNode(n);
		visit(n.exp);
		return null;
	}

	@Override
	public Void visitNode(IfNode n) {
		if (print) printNode(n);
		visit(n.cond);
		visit(n.th);
		visit(n.el);
		return null;
	}
	
	@Override
	public Void visitNode(EqualNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(GreaterEqualNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(LessEqualNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(AndNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(OrNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(NotNode n) {
		if (print) printNode(n);
		visit(n.exp);
		return null;
	}


	@Override
	public Void visitNode(TimesNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}
	
	@Override
	public Void visitNode(PlusNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(MinusNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(DivisionNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(CallNode n) {
		if (print) printNode(n);
		STentry entry = stLookup(n.id);
		if (entry == null) {
			System.out.println("Fun id " + n.id + " at line "+ n.getLine() + " not declared");
			stErrors++;
		} else {
			n.entry = entry;
			n.nl = nestingLevel;
		}
		for (Node arg : n.arglist) visit(arg);
		return null;
	}

	@Override
	public Void visitNode(IdNode n) {
		if (print) printNode(n);
		STentry entry = stLookup(n.id);
		if (entry == null) {
			System.out.println("Var or Par id " + n.id + " at line "+ n.getLine() + " not declared");
			stErrors++;
		} else {
			n.entry = entry;
			n.nl = nestingLevel;
		}
		return null;
	}

	@Override
	public Void visitNode(BoolNode n) {
		if (print) printNode(n, n.val.toString());
		return null;
	}

	@Override
	public Void visitNode(IntNode n) {
		if (print) printNode(n, n.val.toString());
		return null;
	}

	// ------------- OO NODES -------------

	@Override
	public Void visitNode(ClassNode n) {
		if (print) printNode(n);
		Map<String, STentry> hm = symTable.get(0);

		// Create a new ClassTypeNode with empty lists for fields and methods
		ClassTypeNode classType = new ClassTypeNode(n.id, new ArrayList<>(), new ArrayList<>());

		// Create a new STentry for the class
		STentry entry = new STentry(0, classType, decOffset--);

		// Insert the class ID into the symbol table
		if (hm.put(n.id, entry) != null) {
			System.out.println("Class id " + n.id + " at line " + n.getLine() + " already declared");
			stErrors++;
		}

		// Create a new hashmap for the class members
		Map<String, STentry> vtable = new HashMap<>();
		classTable.put(n.id, vtable);

		//creare una nuova hashmap per la symTable
		nestingLevel++;
		symTable.add(vtable);
		int prevNLDecOffset=decOffset; // stores counter for offset of declarations at previous nesting level
		decOffset=-2;

		int fieldOffset = -1;
		int methodOffset = 0;

		// Visit fields
		if(!n.fieldList.isEmpty()) for (FieldNode field : n.fieldList) {
			System.out.println(field.id + " " + field.toString());
			STentry fieldEntry = new STentry(nestingLevel, field.getType(), fieldOffset--);
			if (vtable.put(field.id, fieldEntry) != null) {
				System.out.println("Field id " + field.id + " at line " + n.getLine() + " already declared");
				stErrors++;
			}
			classType.allFields.add(-fieldEntry.offset - 1, field.getType());
		}

		// VISIT METHODs
		if(!n.methodList.isEmpty()) for (MethodNode method : n.methodList) {
			List<TypeNode> parTypes = new ArrayList<>();
			for (ParNode par : method.parlist) parTypes.add(par.getType());

			STentry methodEntry = new STentry(nestingLevel, new ArrowTypeNode(parTypes, method.retType), decOffset--);
			if (vtable.put(method.id, methodEntry) != null) {
				System.out.println("Method id " + method.id + " at line " + n.getLine() + " already declared");
				stErrors++;
			}
			method.offset = methodOffset++;
			classType.allMethods.add(method.offset, new ArrowTypeNode(parTypes, method.retType));
		}

		symTable.remove(nestingLevel--);
		decOffset=prevNLDecOffset;
		return null;
	}

	@Override
	public Void visitNode(ClassCallNode n) {
		if (print) printNode(n);
		Map<String, STentry> vtable = classTable.get(n.id1);

		if (vtable == null) {
			System.out.println("Class id " + n.id1 + " at line " + n.getLine() + " not declared");
			stErrors++;
		} else {
			n.entry = vtable.get(n.id1);
			n.nl = nestingLevel;
		}

		if (vtable.get(n.id2) == null) {
			System.out.println("Method id " + n.id2 + " at line " + n.getLine() + " not declared");
			stErrors++;
		} else {
			n.methodEntry = vtable.get(n.id2);
		}

		for (Node arg : n.arglist) visit(arg);
		return null;
	}

	@Override
	public Void visitNode(NewNode n) {
		if (print) printNode(n);

		// Retrieve the STentry of the class ID from the class table
		Map<String, STentry> classEntries = classTable.get(n.id);
		if (classEntries == null) {
			System.out.println("Class id " + n.id + " at line " + n.getLine() + " not declared");
			stErrors++;
			return null;
		}

		// Retrieve the STentry from level 0 of the symbol table
		STentry classEntry = symTable.get(0).get(n.id);
		if (classEntry == null) {
			System.out.println("Class id " + n.id + " at line " + n.getLine() + " not found in symbol table");
			stErrors++;
			return null;
		}

		// Set the entry field of the NewNode to the retrieved STentry
		n.entry = classEntry;

		return null;
	}

	@Override
	public Void visitNode(EmptyNode n) {
		if (print) printNode(n);
		// No specific actions needed for EmptyNode
		return null;
	}

	public Void visitNode(RefTypeNode n) {
		if (print) printNode(n);
		// No specific actions needed for RefTypeNode
		return null;
	}
}
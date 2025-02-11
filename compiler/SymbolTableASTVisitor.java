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
		STentry entry = new STentry(nestingLevel, n.getType(), decOffset--);

		//inserimento di ID nella symtable
		if (hm.put(n.id, entry) != null) {
			System.out.println("Var id " + n.id + " at line "+ n.getLine() +" already declared");
			stErrors++;
		}

//		if (n.getType() instanceof  IntTypeNode) {
//			System.out.println("Var Dec: IntTypeNode");
//		}
//
//		if (n.getType() instanceof  BoolTypeNode) {
//			System.out.println("Var Dec: BoolTypeNode");
//		}
//
//		if (n.getType() instanceof ClassTypeNode){
//			System.out.println("Var Dec: ClassTypeNode");
//			ClassTypeNode classType = (ClassTypeNode) n.getType();
//			Map<String, STentry> ctable = classTable.get(classType.id);
//			if (ctable == null){
//				System.out.println("Class id " + n.id + " at line "+ n.getLine() +" not declared");
//				stErrors++;
//			}
//
//			RefTypeNode refType = new RefTypeNode(classType.id);
//			hm.put(n.id, new STentry(nestingLevel, refType, decOffset--));
//		}else {
//			// ClassTypeNode classType = (ClassTypeNode) n.getType();
//			RefTypeNode refType = (RefTypeNode) n.getType();
//			hm.put(n.id, new STentry(nestingLevel, refType, decOffset--));
//		}

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
	public Void visitNode(MethodNode n) {
		if (print) printNode(n);

		Map<String, STentry> hm = symTable.get(nestingLevel);
		List<TypeNode> parTypes = new ArrayList<>();

		for (ParNode par : n.parlist) parTypes.add(par.getType());

		n.offset = decOffset;
		STentry entry = new STentry(nestingLevel, new ArrowTypeNode(parTypes, n.retType), decOffset++);

		// Insert method ID into the symbol table
		if (hm.put(n.id, entry) != null) {
			System.out.println("Method id " + n.id + " at line " + n.getLine() + " already declared");
			stErrors++;
		}

		// Create a new symbol table for the method's scope
		nestingLevel++;
		Map<String, STentry> hmn = new HashMap<>();
		symTable.add(hmn);

		int prevNLDecOffset = decOffset; // Store counter for offset of declarations at previous nesting level
		decOffset = -2;

		int parOffset = 1;
		for (ParNode par : n.parlist)
			if (hmn.put(par.id, new STentry(nestingLevel, par.getType(), parOffset++)) != null) {
				System.out.println("Par id " + par.id + " at line " + n.getLine() + " already declared");
				stErrors++;
			}

		for (Node dec : n.declist)
			visit(dec);
		visit(n.exp);

		// Remove the symbol table for the method's scope
		symTable.remove(nestingLevel--);
		decOffset = prevNLDecOffset; // Restore counter for offset of declarations at previous nesting level
		return null;
	}

	@Override
	public Void visitNode(ClassNode n) {
		if (print) printNode(n);
		Map<String, STentry> hm = symTable.get(0);

		// Create a new ClassTypeNode with empty lists for fields and methods
		ClassTypeNode classType = new ClassTypeNode(n.id, new ArrayList<>(), new ArrayList<>());
		STentry entry = new STentry(0, classType, decOffset--);

		// Insert the class ID into the symbol table
		if (hm.put(n.id, entry) != null) {
			System.out.println("Class id " + n.id + " at line " + n.getLine() + " already declared");
			stErrors++;
		}

		// Create a new hashmap for the class members
		Map<String, STentry> vtable = new HashMap<>();
		classTable.put(n.id, vtable);
		symTable.add(vtable);

		//creare una nuova hashmap per la symTable
		nestingLevel++;
		int prevNLDecOffset = decOffset; // stores counter for offset of declarations at previous nesting level
		decOffset = -2;

		int fieldOffset  = -1;
		int methodOffset =  0;

		// Visit fields
		if(!n.fieldList.isEmpty()) for (FieldNode field : n.fieldList) {
			STentry fieldEntry = new STentry(nestingLevel, field.getType(), fieldOffset);
			if (vtable.put(field.id, fieldEntry) != null) {
				System.out.println("Field id " + field.id + " at line " + n.getLine() + " already declared");
				stErrors++;
			}
			classType.allFields.add(-fieldEntry.offset - 1, field.type);
			fieldOffset--;
		}

		// VISIT METHODs
		int savedOffset = decOffset;
		decOffset = methodOffset;
		if(!n.methodList.isEmpty())
			for (MethodNode method : n.methodList) {
				visit(method);
				classType.allMethods.add(method.offset,  (ArrowTypeNode)method.getType());
		}

		decOffset = savedOffset;
		symTable.remove(nestingLevel--);
		decOffset = prevNLDecOffset;
		return null;
	}

	@Override
	public Void visitNode(ClassCallNode n) {
		if (print) printNode(n);

		STentry entry = stLookup(n.id1);
		if (entry.type == null) {
			System.out.println("Class of id1 " + n.id1 + " at line " + n.getLine() + " not declared");
			stErrors++;
			return null;
		}

		RefTypeNode ref = (RefTypeNode) entry.type;
		System.out.println("RefTypeNode ID: " + ref);
		Map<String, STentry> vtable = classTable.get(ref.id);

		if (vtable == null) {
			System.out.println("Class of id1 " + n.id1 + " at line " + n.getLine() + " not declared");
			stErrors++;
			return null;
		}

		if(stLookup(n.id1) == null){
			// n.entry = symTable.get(nestingLevel).get(n.id1);
			System.out.println("ID1: " + n.id1 + " at line " + n.getLine() + " not found in vtable");
			return null;
		}else{
			n.nl = nestingLevel;
			n.entry = stLookup(n.id1);
		}

		STentry methodEntry = vtable.get(n.id2);
		if (methodEntry == null) {
			System.out.println("Method id " + n.id1 + " at line " + n.getLine() + " not declared in class " + ref.id);
			stErrors++;
			return null;
		}

		n.methodEntry = methodEntry;

		for (Node arg : n.arglist) visit(arg);
		return null;
	}

	@Override
	public Void visitNode(NewNode n) {
		if (print) printNode(n);
//      RefTypeNode refType = new RefTypeNode(n.id);
//		System.out.println(n.arglist.get(1));
//		Map<String, STentry> hm = symTable.get(nestingLevel);
//		hm.put(n.id, new STentry(nestingLevel, refType, decOffset--));

		System.out.println("NEW NODE ID: "    + n.id);
		System.out.println("NEW NODE ARG: "   + n.arglist);

		// Retrieve the STentry of the class ID from the class table
		Map<String, STentry> vtable = classTable.get(n.id);
		if (vtable == null) {
			// System.out.println("Class id " + n.id);
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
		}else {
			System.out.println("SYMTABLE ENTRY: " + classEntry.type);
			n.entry = classEntry;
		}

		for (Node arg : n.arglist)
			visit(arg);
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
package compiler;

import compiler.AST.*;
import compiler.exc.*;
import compiler.lib.*;
import static compiler.TypeRels.*;

//visitNode(n) fa il type checking di un Node n e ritorna:
//- per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
//- per una dichiarazione, "null"; controlla la correttezza interna della dichiarazione
//(- per un tipo: "null"; controlla che il tipo non sia incompleto) 
//
//visitSTentry(s) ritorna, per una STentry s, il tipo contenuto al suo interno
public class TypeCheckEASTVisitor extends BaseEASTVisitor<TypeNode,TypeException> {

	TypeCheckEASTVisitor() { super(true); } // enables incomplete tree exceptions 
	TypeCheckEASTVisitor(boolean debug) { super(true,debug); } // enables print for debugging

	//checks that a type object is visitable (not incomplete) 
	private TypeNode ckvisit(TypeNode t) throws TypeException {
		visit(t);
		return t;
	} 
	
	@Override
	public TypeNode visitNode(ProgLetInNode n) throws TypeException {
		if (print) printNode(n);
		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (IncomplException e) { 
			} catch (TypeException e) {
				System.out.println("ProgLetInNode -> " + "Type checking error in a declaration: " + e.text + " " + dec.toString());
			}
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(ProgNode n) throws TypeException {
		if (print) printNode(n);
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(FunNode n) throws TypeException {
		if (print) printNode(n,n.id);

		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (IncomplException e) { 
			} catch (TypeException e) {
				System.out.println("FunNode -> " + "Type checking error in a declaration: " + e.text);
			}
		if ( !isSubtype(visit(n.exp), ckvisit(n.retType)) )
			throw new TypeException("Wrong return type for function " + n.id,n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(VarNode n) throws TypeException {
		if (print) printNode(n,n.id);
		if ( !isSubtype(visit(n.exp), ckvisit(n.getType())) )
			throw new TypeException("Incompatible value for variable " + n.id,n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(PrintNode n) throws TypeException {
		if (print) printNode(n);
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(IfNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(isSubtype(visit(n.cond), new BoolTypeNode())) )
			throw new TypeException("Non boolean condition in if",n.getLine());

		TypeNode t = visit(n.th);
		TypeNode e = visit(n.el);

		if (isSubtype(t, e)) return e;
		if (isSubtype(e, t)) return t;
		throw new TypeException("Incompatible types in then-else branches",n.getLine());
	}

	@Override
	public TypeNode visitNode(EqualNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, r) || isSubtype(r, l)) )
			throw new TypeException("Incompatible types in equal",n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(GreaterEqualNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new IntTypeNode())
				&& isSubtype(r, new IntTypeNode())) )
			throw new TypeException("Incompatible types in greater equal", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(LessEqualNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new IntTypeNode())
				&& isSubtype(r, new IntTypeNode())) )
			throw new TypeException("Incompatible types in less equal", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(OrNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new BoolTypeNode())
				&& isSubtype(r, new BoolTypeNode())) )
			throw new TypeException("Non boolean types in or", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(AndNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new BoolTypeNode())
				&& isSubtype(r, new BoolTypeNode())) )
			throw new TypeException("Non boolean types in and", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(TimesNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new IntTypeNode())
				&& isSubtype(r, new IntTypeNode())) )
			throw new TypeException("Non integers in multiplication",n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(PlusNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new IntTypeNode())
				&& isSubtype(r, new IntTypeNode())) )
			throw new TypeException("Non integers in sum",n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(MinusNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new IntTypeNode())
				&& isSubtype(r, new IntTypeNode())) )
			throw new TypeException("Non integers in minus",n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(NotNode n) throws TypeException {
		if (print) printNode(n);

		if ( !(isSubtype(visit(n.exp), new BoolTypeNode())))
			throw new TypeException("Non boolean in not",n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(DivisionNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, new IntTypeNode())
				&& isSubtype(r, new IntTypeNode())) )
			throw new TypeException("Non integers in division",n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(CallNode n) throws TypeException {
		if (print) printNode(n,n.id);
		TypeNode t = visit(n.entry); 
		if ( !(t instanceof ArrowTypeNode) )
			throw new TypeException("Invocation of a non-function "+n.id,n.getLine());
		ArrowTypeNode at = (ArrowTypeNode) t;
		if ( !(at.parlist.size() == n.arglist.size()) )
			throw new TypeException("Wrong number of parameters in the invocation of "+n.id,n.getLine());
		for (int i = 0; i < n.arglist.size(); i++)
			if ( !(isSubtype(visit(n.arglist.get(i)),at.parlist.get(i))) )
				throw new TypeException("Wrong type for "+(i+1)+"-th parameter in the invocation of "+n.id,n.getLine());
		return at.ret;
	}

	@Override
	public TypeNode visitNode(IdNode n) throws TypeException {
		if (print) printNode(n,n.id);
		TypeNode t = visit(n.entry);

		if (t instanceof ArrowTypeNode)
			throw new TypeException("Wrong usage of function identifier " + n.id,n.getLine());

		if (t instanceof ClassTypeNode)
			throw new TypeException("Wrong usage of class identifier " + n.id,n.getLine());

		return t;
	}

	@Override
	public TypeNode visitNode(BoolNode n) {
		if (print) printNode(n,n.val.toString());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(IntNode n) {
		if (print) printNode(n,n.val.toString());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(ArrowTypeNode n) throws TypeException {
		if (print) printNode(n);
		for (Node par: n.parlist) visit(par);
		visit(n.ret,"->"); //marks return type
		return null;
	}

	@Override
	public TypeNode visitNode(BoolTypeNode n) {
		if (print) printNode(n);
		return null;
	}

	@Override
	public TypeNode visitNode(IntTypeNode n) {
		if (print) printNode(n);
		return null;
	}

// STentry (ritorna campo type)

	@Override
	public TypeNode visitSTentry(STentry entry) throws TypeException {
		if (print) printSTentry("type");
		return ckvisit(entry.type); 
	}

	/*  ----- OO EXTENSION -----  */
	@Override
	public TypeNode visitNode(MethodNode n) throws TypeException {
		if (print) printNode(n,n.id);
		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("MethodNode -> " + "Type checking error in a declaration: " + e.text);
			}
		if ( !isSubtype(visit(n.exp), ckvisit(n.retType)) )
			throw new TypeException("Wrong return type for function " + n.id,n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(ClassNode n) throws TypeException {
		if (print) printNode(n,n.id);
		for (Node dec : n.methodList)
			try {
				visit(dec);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("ClassNode/method -> " + "Type checking error in a declaration: " + e.text);
			}

		for(Node dec : n.fieldList)
			try {
				visit(dec);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("ClassNode/field -> " + "Type checking error in a declaration: " + e.text);
			}

		return null;
	}

	@Override
	public TypeNode visitNode(FieldNode n) throws TypeException {
		if (print) printNode(n,n.id);
		if ( !isSubtype(visit(n.getType()), ckvisit(n.getType())) )
			throw new TypeException("Incompatible value for variable " + n.id,n.getLine());
		return null;
	}


	@Override
	public TypeNode visitNode(ClassCallNode n) throws TypeException {
		if (print) printNode(n,n.id1);
		TypeNode t = visit(n.entry);

		if ( !(t instanceof RefTypeNode) )
			throw new TypeException("Invocation of a non-class " + n.id1,n.getLine());


		if (!(n.methodEntry.type instanceof ArrowTypeNode))
			throw new TypeException("Invocation of a non-function " + n.id2, n.getLine());
		ArrowTypeNode at = (ArrowTypeNode) n.methodEntry.type;
		if (!(at.parlist.size() == n.arglist.size()))
			throw new TypeException("Wrong number of parameters in the invocation of " + n.id2, n.getLine());
		for (int i = 0; i < n.arglist.size(); i++)
			if (!(isSubtype(visit(n.arglist.get(i)), at.parlist.get(i))))
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + n.id2,
						n.getLine());
		return at.ret;

	}


	public TypeNode visitNode(NewNode n) throws TypeException {
		if (print) printNode(n, n.id);

		// Retrieve the ClassTypeNode from the entry field
		if (!(n.entry.type instanceof ClassTypeNode)) {
			throw new TypeException("New expression with non-class type " + n.id, n.getLine());
		}

		// Check if the number of arguments matches the number of fields
		ClassTypeNode classType = (ClassTypeNode) n.entry.type;

		if (n.arglist.size() != classType.allFields.size()) {
			throw new TypeException("Wrong number of parameters in the instantiation of " + n.id, n.getLine());
		}

		// Verify that each argument's type is a subtype of the corresponding field's type
		for (int i = 0; i < n.arglist.size(); i++) {
			TypeNode argType = visit(n.arglist.get(i));
			TypeNode fieldType = classType.allFields.get(i);
			if (!isSubtype(argType, fieldType)) {
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the instantiation of " + n.id, n.getLine());
			}
		}

		return new RefTypeNode(n.id);
	}

	@Override
	public TypeNode visitNode(ClassTypeNode n) throws TypeException {
		return null;
	}
	@Override
	public TypeNode visitNode(RefTypeNode n) throws TypeException {
		return null;
	}
	@Override
	public TypeNode visitNode(EmptyNode n){
		if (print) printNode(n);
		return new EmptyTypeNode();
	}
}
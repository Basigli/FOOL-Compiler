package compiler;

import java.util.*;

import compiler.exc.FOOLBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import compiler.AST.*;
import compiler.exc.FOOLParser.*;
import compiler.lib.*;
import static compiler.lib.FOOLlib.*;

public class ASTGenerationSTVisitor extends FOOLBaseVisitor<Node> {

	String indent;
    public boolean print;
	
    ASTGenerationSTVisitor() {}    
    ASTGenerationSTVisitor(boolean debug) { print=debug; }
        
    private void printVarAndProdName(ParserRuleContext ctx) {
        String prefix="";        
    	Class<?> ctxClass=ctx.getClass(), parentClass=ctxClass.getSuperclass();
        if (!parentClass.equals(ParserRuleContext.class)) // parentClass is the var context (and not ctxClass itself)
        	prefix=lowerizeFirstChar(extractCtxName(parentClass.getName()))+": production #";
    	System.out.println(indent+prefix+lowerizeFirstChar(extractCtxName(ctxClass.getName())));                               	
    }
        
    @Override
	public Node visit(ParseTree t) {
    	if (t==null) return null;
        String temp=indent;
        indent=(indent==null)?"":indent+"  ";
        Node result = super.visit(t);
        indent=temp;
        return result; 
	}

	@Override
	public Node visitProg(ProgContext c) {
		if (print) printVarAndProdName(c);
		return visit(c.progbody());
	}

	@Override
	public Node visitLetInProg(LetInProgContext c) {
		if (print) printVarAndProdName(c);
		List<DecNode> declist = new ArrayList<>();
		for (DecContext dec : c.dec()) declist.add((DecNode) visit(dec));
		return new ProgLetInNode(declist, visit(c.exp()));
	}

	@Override
	public Node visitNoDecProg(NoDecProgContext c) {
		if (print) printVarAndProdName(c);
		return new ProgNode(visit(c.exp()));
	}

	@Override
	public Node visitPrint(PrintContext c) {
		if (print) printVarAndProdName(c);
		return new PrintNode(visit(c.exp()));
	}

	// ------------- OPERATORS CONTEXTS -------------
	@Override
	public Node visitTimes(TimesContext c) {
		if (print) printVarAndProdName(c);
		Node n = new TimesNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.TIMES().getSymbol().getLine());		// setLine added
        return n;		
	}

	@Override
	public Node visitPlus(PlusContext c) {
		if (print) printVarAndProdName(c);
		Node n = new PlusNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.PLUS().getSymbol().getLine());	
        return n;		
	}

	@Override
	public Node visitMinus(MinusContext c) {
		if (print) printVarAndProdName(c);
		Node n = new MinusNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.MINUS().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitEq(EqContext c) {
		if (print) printVarAndProdName(c);
		Node n = new EqualNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.EQ().getSymbol().getLine());		
        return n;		
	}

	@Override
	public Node visitGeq(GeqContext c) {
		if (print) printVarAndProdName(c);
		Node n = new GreaterEqualNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.GEQ().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitLeq(LeqContext c) {
		if (print) printVarAndProdName(c);
		Node n = new LessEqualNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.LEQ().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitNot(NotContext c) {
		if (print) printVarAndProdName(c);
		Node n = new NotNode(visit(c.exp()));
		n.setLine(c.NOT().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitAnd(AndContext c) {
		if (print) printVarAndProdName(c);
		Node n = new AndNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.AND().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitOr(OrContext c) {
		if (print) printVarAndProdName(c);
		Node n = new OrNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.OR().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitDivision(DivisionContext c) {
		if (print) printVarAndProdName(c);
		Node n = new DivisionNode(visit(c.exp(0)), visit(c.exp(1)));
		n.setLine(c.DIVISION().getSymbol().getLine());
		return n;
	}

	// ------------- DECS CONTEXTS -------------
	@Override
	public Node visitVardec(VardecContext c) {
		if (print) printVarAndProdName(c);
		Node n = null;
		if (c.ID()!=null) { //non-incomplete ST
			n = new VarNode(c.ID().getText(), (TypeNode) visit(c.type()), visit(c.exp()));
			n.setLine(c.VAR().getSymbol().getLine());
		}
        return n;
	}

	@Override
	public Node visitFundec(FundecContext c) {
		if (print) printVarAndProdName(c);
		List<ParNode> parList = new ArrayList<>();
		for (int i = 1; i < c.ID().size(); i++) { 
			ParNode p = new ParNode(c.ID(i).getText(),(TypeNode) visit(c.type(i)));
			p.setLine(c.ID(i).getSymbol().getLine());
			parList.add(p);
		}
		List<DecNode> decList = new ArrayList<>();
		for (DecContext dec : c.dec()) decList.add((DecNode) visit(dec));
		Node n = null;
		if (c.ID().size()>0) { //non-incomplete ST
			n = new FunNode(c.ID(0).getText(),(TypeNode)visit(c.type(0)),parList,decList,visit(c.exp()));
			n.setLine(c.FUN().getSymbol().getLine());
		}
        return n;
	}



	// ------------- TYPE CONTEXTS -------------
	@Override
	public Node visitIntType(IntTypeContext c) {
		if (print) printVarAndProdName(c);
		return new IntTypeNode();
	}

	@Override
	public Node visitBoolType(BoolTypeContext c) {
		if (print) printVarAndProdName(c);
		return new BoolTypeNode();
	}

	@Override
	public Node visitClassType(ClassTypeContext c) {
		if (print) printVarAndProdName(c);
		return new ClassTypeNode();
	}

	// ------------- VALUES CONTEXTS -------------
	@Override
	public Node visitInteger(IntegerContext c) {
		if (print) printVarAndProdName(c);
		int v = Integer.parseInt(c.NUM().getText());
		return new IntNode(c.MINUS()==null?v:-v);
	}

	@Override
	public Node visitTrue(TrueContext c) {
		if (print) printVarAndProdName(c);
		return new BoolNode(true);
	}

	@Override
	public Node visitFalse(FalseContext c) {
		if (print) printVarAndProdName(c);
		return new BoolNode(false);
	}

	// ------------- IF/PAR/CALL CONTEXTS -------------
	@Override
	public Node visitPars(ParsContext c) {
		if (print) printVarAndProdName(c);
		return visit(c.exp());
	}

	@Override
	public Node visitId(IdContext c) {
		if (print) printVarAndProdName(c);
		Node n = new IdNode(c.ID().getText());
		n.setLine(c.ID().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitIf(IfContext c) {
		if (print) printVarAndProdName(c);
		Node ifNode = visit(c.exp(0));
		Node thenNode = visit(c.exp(1));
		Node elseNode = visit(c.exp(2));
		Node n = new IfNode(ifNode, thenNode, elseNode);
		n.setLine(c.IF().getSymbol().getLine());			
        return n;		
	}

	@Override
	public Node visitCall(CallContext c) {
		if (print) printVarAndProdName(c);		
		List<Node> arglist = new ArrayList<>();
		for (ExpContext arg : c.exp()) arglist.add(visit(arg));
		Node n = new CallNode(c.ID().getText(), arglist);
		n.setLine(c.ID().getSymbol().getLine());
		return n;
	}

	// ------------- OO CONTEXTS -------------
	@Override
	public Node visitEmpty(EmptyContext c) {
		if (print) printVarAndProdName(c);
		return new EmptyNode();
	}

	@Override
	public Node visitNew(NewContext c) {
		if (print) printVarAndProdName(c);
		List<Node> arglist = new ArrayList<>();
		for (ExpContext arg : c.exp()) arglist.add(visit(arg));
		Node n = new NewNode(c.ID().get(0).getText(), arglist);
		n.setLine(c.NEW().getSymbol().getLine());
		return n;
	}


	@Override
	public Node visitClassCall(ClassCallContext c) {
		if (print) printVarAndProdName(c);
		List<Node> arglist = new ArrayList<>();
		for (ExpContext arg : c.exp()) arglist.add(visit(arg));
		Node n = new ClassCallNode(c.ID().get(0).getText(), arglist);
		n.setLine(c.ID().get(0).getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitClassdec(ClassdecContext c) {
		if (print) printVarAndProdName(c);
		List<FieldNode> fieldList = new ArrayList<>();
		for (int i = 1; i < c.ID().size(); i++) {
			FieldNode f = new FieldNode(c.ID(i).getText(), (TypeNode) visit(c.type(i)));
			f.setLine(c.ID(i).getSymbol().getLine());
			fieldList.add(f);
		}

		List<MethodNode> methodList = new ArrayList<>();
		for (DecContext dec : c.dec()) methodList.add((MethodNode) visit(dec));
		Node n = null;
		if (c.ID().size() > 0) { // non-incomplete ST
			n = new ClassNode(c.ID(0).getText(), fieldList, methodList);
			n.setLine(c.CLASS().getSymbol().getLine());
		}
		return n;
	}

}

// Generated from C:/Users/muham/FOOL-Compiler/compiler/FOOL.g4 by ANTLR 4.13.2
package compiler.exc;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FOOLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FOOLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FOOLParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(FOOLParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code letInProg}
	 * labeled alternative in {@link FOOLParser#progbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetInProg(FOOLParser.LetInProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code noDecProg}
	 * labeled alternative in {@link FOOLParser#progbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoDecProg(FOOLParser.NoDecProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code vardec}
	 * labeled alternative in {@link FOOLParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardec(FOOLParser.VardecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fundec}
	 * labeled alternative in {@link FOOLParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFundec(FOOLParser.FundecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code minus}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinus(FOOLParser.MinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code or}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(FOOLParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pars}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPars(FOOLParser.ParsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(FOOLParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integer}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(FOOLParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eq}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq(FOOLParser.EqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plus}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlus(FOOLParser.PlusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(FOOLParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code division}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivision(FOOLParser.DivisionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(FOOLParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(FOOLParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code times}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimes(FOOLParser.TimesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code geq}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeq(FOOLParser.GeqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code and}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(FOOLParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(FOOLParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code leq}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeq(FOOLParser.LeqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(FOOLParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(FOOLParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link FOOLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(FOOLParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link FOOLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(FOOLParser.BoolTypeContext ctx);
}
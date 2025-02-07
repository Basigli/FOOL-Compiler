package compiler;

import compiler.AST.*;
import compiler.lib.*;

public class TypeRels {

	// valuta se il tipo "a" e' <= al tipo "b", dove "a" e "b" sono tipi di base: IntTypeNode o BoolTypeNode
	public static boolean isSubtype(TypeNode a, TypeNode b) {
		System.out.println("isSubtype: " + a.getClass() + " " + b.getClass());

		return a.getClass().equals(b.getClass())
				|| ((a instanceof BoolTypeNode) && (b instanceof IntTypeNode))
				|| ((a instanceof EmptyTypeNode) && (b instanceof ClassTypeNode));
	}

}

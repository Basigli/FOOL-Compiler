# Type Checking

## Overview

`TypeCheckEASTVisitor` traverses the Enriched AST and verifies that every expression has a well-formed type. It returns a `TypeNode` for each node, propagating types bottom-up through the tree.

## Subtype Relations

Type compatibility is determined by `TypeRels.isSubtype(a, b)`:

- `BoolTypeNode` is a subtype of `IntTypeNode` (booleans can be used as integers).
- A class type `C` is a subtype of `D` if `C` extends `D` (directly or transitively).
- `EmptyTypeNode` (`null`) is a subtype of any `RefTypeNode`.

## Key Rules

| Construct | Type Rule |
|---|---|
| `IntNode` | `IntTypeNode` |
| `BoolNode` | `BoolTypeNode` |
| `PlusNode`, `MinusNode`, `TimesNode`, `DivNode` | Both operands must be `int`; result is `IntTypeNode` |
| `AndNode`, `OrNode` | Both operands must be `bool`; result is `BoolTypeNode` |
| `NotNode` | Operand must be `bool`; result is `BoolTypeNode` |
| `EqualNode` | Operands must share a common subtype |
| `GreaterEqualNode`, `LessEqualNode` | Both operands must be `int`; result is `BoolTypeNode` |
| `IfNode` | Condition must be `bool`; result is the **lowest common ancestor** of the two branch types |
| `CallNode` | Argument types must be subtypes of the declared parameter types; result is the function's return type |
| `NewNode` | Argument types must match class field types; result is `RefTypeNode(classID)` |
| `ClassCallNode` | Like `CallNode` but resolves through the virtual table |
| `VarNode` | Declared type must be a supertype of the initialiser type |
| `FunNode` / `MethodNode` | Body type must be a subtype of the declared return type |

## Lowest Common Ancestor

For `if / then / else`, the result type is the lowest common ancestor (LCA) of the two branch types in the subtype hierarchy. For example, if one branch has type `C` and another has type `D` where both extend `Base`, the result type is `Base`.

## Error Reporting

Type errors are printed to standard error and counted. Compilation continues after a type error so that further errors can be reported in the same run.

# Symbol Table Analysis

## Overview

`SymbolTableASTVisitor` traverses the AST and builds a **symbol table** that maps every identifier to its declaration. It also *enriches* each `IdNode`, `CallNode`, and `ClassCallNode` with a reference to its `STentry`, producing the **Enriched AST (EAST)**.

## Symbol Table Structure

The symbol table is a stack of hash-maps (one map per nesting level):

```
Level 0 (global)   { Counter → STentry(ClassTypeNode, 0, 0), … }
Level 1 (function) { x → STentry(IntTypeNode, 1, -1), … }
```

Each `STentry` stores:

| Field | Description |
|---|---|
| `nl` | Nesting level of the declaration |
| `type` | Declared type (`TypeNode`) |
| `offset` | Memory offset in the activation record |

## Static Scoping

The visitor enforces two static-scoping rules:

1. A use of identifier `x` resolves to the declaration in the **most closely enclosing scope** that precedes the use.
2. An inner-scope declaration of `x` **hides** any outer-scope declaration of the same name.

`stLookup(id)` searches from the innermost (current) scope outward, returning the first matching `STentry`.

## Class Table & Virtual Table

For object-oriented features the visitor maintains an additional **class table** (a map from class name to its `STentry`) and, for each class, a **virtual table** (a map from method name to its `STentry`). These are used to:

- Resolve field and method accesses in `ClassCallNode`.
- Build the dispatch table layout used by the code generator.

## Error Handling

The visitor reports semantic errors without aborting, accumulating them in a counter. Detected errors include:

- Undeclared identifier
- Identifier declared twice in the same scope
- Calling a variable as a function
- Using an undeclared class type

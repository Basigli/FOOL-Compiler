# AST Generation

## Overview

After ANTLR parses the source file it produces a **Parse Tree** whose nodes are ANTLR `Context` objects. `ASTGenerationSTVisitor` traverses this Parse Tree and converts it into a typed **Abstract Syntax Tree (AST)** built from the static inner classes defined in `AST.java`.

## AST Node Hierarchy

All nodes extend the abstract `Node` interface. Each concrete node carries the children and data that are relevant for later phases:

| Node | Description |
|---|---|
| `ProgLetInNode` | Top-level `let … in` program |
| `ProgNode` | Top-level expression-only program |
| `ClassNode` | Class declaration (fields + methods) |
| `FieldNode` | A class field (like a parameter) |
| `MethodNode` | A class method (like a function) |
| `FunNode` | Function declaration |
| `ParNode` | Function parameter |
| `VarNode` | Variable declaration |
| `IdNode` | Identifier use |
| `CallNode` | Function call |
| `ClassCallNode` | Method call (`obj.method(...)`) |
| `NewNode` | Object instantiation (`new C(...)`) |
| `EmptyNode` | `null` literal |
| `PlusNode` / `MinusNode` | Addition / subtraction |
| `TimesNode` / `DivNode` | Multiplication / division |
| `EqualNode` / `GreaterEqualNode` / `LessEqualNode` | Comparison |
| `AndNode` / `OrNode` / `NotNode` | Logical operators |
| `IfNode` | `if / then / else` |
| `PrintNode` | `print(exp)` |
| `IntNode` | Integer literal |
| `BoolNode` | Boolean literal |

## Type Nodes

Type information is represented separately as `TypeNode` subtypes that appear in symbol-table entries:

| Type Node | Meaning |
|---|---|
| `IntTypeNode` | `int` |
| `BoolTypeNode` | `bool` |
| `RefTypeNode` | Reference to a class |
| `EmptyTypeNode` | Type of `null` |
| `ArrowTypeNode` | Function type (only in STentry) |
| `MethodTypeNode` | Method type (only in STentry) |
| `ClassTypeNode` | Class type (maps field/method indices to types) |

## Accept–Visit Cycle

The visitor pattern uses a two-step dispatch:

1. The visitor calls `visit(node)` → delegates to `node.accept(this)`.
2. The node calls `visitor.visitNode(this)` passing its specific type.

This double dispatch allows the visitor to select the correct `visitNode` overload at runtime without explicit casts.

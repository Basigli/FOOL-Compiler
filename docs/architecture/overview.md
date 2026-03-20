# Architecture Overview

The FOOL compiler is structured as a classic multi-phase pipeline. Each phase transforms its input and passes the result to the next phase.

```
Source text
    │
    ▼
┌──────────────┐
│   Lexer &    │  ANTLR-generated from FOOL.g4
│   Parser     │
└──────┬───────┘
       │  Parse Tree
       ▼
┌──────────────┐
│  AST         │  ASTGenerationSTVisitor
│  Generation  │
└──────┬───────┘
       │  AST (Node hierarchy)
       ▼
┌──────────────┐
│  Symbol      │  SymbolTableASTVisitor
│  Table       │
└──────┬───────┘
       │  Enriched AST (EAST) – nodes annotated with STentry
       ▼
┌──────────────┐
│  Type        │  TypeCheckEASTVisitor
│  Checking    │
└──────┬───────┘
       │  Type-checked EAST
       ▼
┌──────────────┐
│  Code        │  CodeGenerationASTVisitor
│  Generation  │
└──────┬───────┘
       │  SVM assembly
       ▼
   .fool.asm
```

## Key Classes

| Class | Role |
|---|---|
| `FOOL.g4` | ANTLR grammar defining the language syntax |
| `AST.java` | Defines all AST node types as static inner classes |
| `ASTGenerationSTVisitor` | Visits the Parse Tree and builds the AST |
| `SymbolTableASTVisitor` | Builds the symbol table and enriches the AST |
| `TypeCheckEASTVisitor` | Performs type checking on the enriched AST |
| `CodeGenerationASTVisitor` | Generates SVM assembly from the AST |
| `PrintEASTVisitor` | Pretty-prints the enriched AST (debugging aid) |
| `STentry` | Holds a symbol-table entry (type, nesting level, offset) |
| `TypeRels` | Encodes subtype relationships between types |
| `Test` | Entry point: orchestrates all compiler phases |

## Visitor Pattern

All tree-processing phases implement the **Visitor** pattern via `BaseASTVisitor<S, E>`.
Each AST node has an `accept(BaseASTVisitor)` method that calls back the corresponding `visitNode` overload on the visitor, enabling double-dispatch without casts.

See the individual phase pages for deeper details:

- [AST Generation](ast.md)
- [Symbol Table Analysis](symbol-table.md)
- [Type Checking](type-checking.md)
- [Code Generation](code-generation.md)

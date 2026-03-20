# FOOL Compiler

**FOOL** (Functional Object-Oriented Language) is a custom programming language with a compiler built on the [ANTLR](https://www.antlr.org/) framework. It supports integers, booleans, functions, and classes (with inheritance).

## Features

- **Primitive types**: `int` and `bool`
- **Arithmetic & logical operators**: `+`, `-`, `*`, `/`, `>=`, `<=`, `==`, `||`, `&&`, `!`
- **Control flow**: `if / then / else`
- **First-class functions** with static scoping
- **Object-oriented classes** with fields and methods
- **Inheritance** via `extends`
- **Null references** with a dedicated `null` literal
- **Built-in `print`** expression

## Compiler Phases

| Phase | Class | Description |
|---|---|---|
| Parsing | ANTLR-generated | Converts source text into a Parse Tree |
| AST Generation | `ASTGenerationSTVisitor` | Builds the Abstract Syntax Tree |
| Symbol Table Analysis | `SymbolTableASTVisitor` | Resolves identifiers and enforces scoping |
| Type Checking | `TypeCheckEASTVisitor` | Validates type correctness |
| Code Generation | `CodeGenerationASTVisitor` | Emits stack-machine assembly |

## Quick Links

- [Getting Started](getting-started.md)
- [Grammar Reference](language/grammar.md)
- [Architecture Overview](architecture/overview.md)

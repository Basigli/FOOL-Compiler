# FOOL-Compiler

Compiler based on the ANTLR framework. The custom language support intergers, booleans, functions and classes. It doesn't support inheritance for classes yet.

## Phases of the Compiler
### Grammar Parsing
The grammar for the FOOL language is defined in the FOOL.g4 file using ANTLR. This file specifies the syntax rules for the language. ANTLR generates a lexer and parser from this grammar, which are used to convert the source code into a parse tree.
<br><br>

### Visitor Pattern
The visitor pattern is used to traverse the AST (Abstract Syntax Tree). Each node in the AST has an accept method that takes a visitor. The visitor then calls the appropriate visit method for the node type. This pattern allows for separation of operations from the object structure.
<br><br>

### Symbol Table Analysis
Symbol table analysis is performed by the SymbolTableASTVisitor class. This phase involves building a symbol table that maps variable and function names to their declarations. It ensures that each identifier is declared before it is used and handles scoping rules.
<br><br>

### Type Checking
Type checking is handled by the TypeCheckEASTVisitor class. This phase ensures that the types of expressions are consistent and that operations are performed on compatible types. It checks for type errors and ensures that the program adheres to the language's type rules.
<br><br>

### Code Generation
The CodeGenerationASTVisitor class is responsible for generating the target code from the AST. This phase translates the high-level language constructs into assembly instructions that are executed by a custom stack-based virtual machine.

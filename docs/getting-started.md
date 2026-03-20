# Getting Started

## Prerequisites

- **Java 8+** (JDK)
- **ANTLR 4** runtime (included in `compiler/lib/`)
- An IDE such as IntelliJ IDEA (optional but recommended)

## Building the Compiler

The project is structured as an IntelliJ IDEA project. Open the root directory in IntelliJ and build with the standard build action, or compile the sources manually:

```bash
javac -cp compiler/lib/antlr-4.7.2-complete.jar compiler/*.java -d out/
```

## Running the Compiler

To compile a `.fool` source file:

```bash
java -cp out/:compiler/lib/antlr-4.7.2-complete.jar Test <source-file>.fool
```

The compiler will:

1. Parse the source file using the ANTLR-generated lexer/parser.
2. Build an AST and enrich it with symbol-table information.
3. Perform type checking.
4. Emit assembly code for the custom stack-based virtual machine (SVM).

The generated assembly is written to `<source-file>.fool.asm`.

## Running the Virtual Machine

Execute the generated assembly with the SVM interpreter:

```bash
java -cp out/:compiler/lib/antlr-4.7.2-complete.jar ExecuteVM <source-file>.fool.asm
```

## Example Programs

Several example `.fool` programs are included in the repository root:

| File | Description |
|---|---|
| `prova.fool` | Basic language features |
| `prova2.fool` | Additional feature tests |
| `quicksort.fool` | Quicksort algorithm |
| `bankloan.fool` | Bank-loan class example |
| `testClass.fool` | Class and method usage |

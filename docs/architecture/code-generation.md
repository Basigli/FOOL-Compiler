# Code Generation

## Overview

`CodeGenerationASTVisitor` translates the type-checked Enriched AST into assembly instructions for the **Stack-based Virtual Machine (SVM)**. The generated code is written to a `.fool.asm` file.

## Target Architecture

The SVM is a simple stack machine with:

- A **stack** for operands and activation records.
- A **heap** for dynamically allocated objects and dispatch tables.
- A **frame pointer (fp)** and **stack pointer (sp)**.
- A **heap pointer (hp)** that grows upward.

## Activation Record Layout

Each function/method call pushes an **activation record (AR)** onto the stack:

```
high address
┌──────────────────┐
│  arguments       │  (pushed by caller, right-to-left)
├──────────────────┤
│  return address  │
├──────────────────┤
│  control link    │  (saved fp of caller)
├──────────────────┤
│  access link     │  (fp of statically enclosing scope)
├──────────────────┤
│  local variables │  (pushed by callee)
└──────────────────┘
low address
```

## Object Layout (Heap)

Each object allocated with `new` is stored on the heap:

```
[ dispatch-table pointer ][ field_0 ][ field_1 ] …
```

The **dispatch table** is also stored on the heap and contains one entry per method (the code address of each method).

## Key Code Patterns

### Variable Access

A local variable at nesting level `nl` and offset `off` is reached by following `(current_level − nl)` access links and then loading from `fp + off`.

### Function Call

1. Push arguments.
2. Push the access link (fp of the statically enclosing scope).
3. Jump to the function label.
4. On return, pop the AR.

### Method Dispatch

1. Load the object reference.
2. Load the dispatch-table pointer from offset 0 of the object.
3. Load the method address from the dispatch table at the method's index.
4. Call the method address.

### Object Creation (`new`)

1. Push field values onto the heap.
2. Store the dispatch-table pointer at the base of the new object.
3. Return the object address.

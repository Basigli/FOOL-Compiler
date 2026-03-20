# Grammar Reference

The FOOL grammar is defined in `compiler/FOOL.g4` using ANTLR 4.

## Program Structure

A FOOL program is either a single expression or a `let … in` block that introduces declarations before the main expression.

```
prog : progbody EOF ;

progbody : LET ( cldec+ dec* | dec+ ) IN exp SEMIC   # letInProg
         | exp SEMIC                                  # noDecProg
         ;
```

## Class Declarations

```
cldec : CLASS ID (EXTENDS ID)?
            LPAR (ID COLON type (COMMA ID COLON type)* )? RPAR
            CLPAR
                 methdec*
            CRPAR ;
```

A class can optionally extend another class. Its body contains field declarations (in the parameter list) and method declarations.

## Method & Function Declarations

```
methdec : FUN ID COLON type
              LPAR (ID COLON type (COMMA ID COLON type)* )? RPAR
                   (LET dec+ IN)? exp
              SEMIC ;

dec : VAR ID COLON type ASS exp SEMIC   # vardec
    | FUN ID COLON type
          LPAR (ID COLON type (COMMA ID COLON type)* )? RPAR
               (LET dec+ IN)? exp
          SEMIC                         # fundec
    ;
```

## Expressions

```
exp : exp (TIMES | DIV) exp          # timesDiv
    | exp (PLUS | MINUS) exp         # plusMinus
    | exp (EQ | GE | LE) exp         # comp
    | exp (AND | OR) exp             # andOr
    | NOT exp                        # not
    | LPAR exp RPAR                  # pars
    | MINUS? NUM                     # integer
    | TRUE                           # true
    | FALSE                          # false
    | NULL                           # null
    | NEW ID LPAR (exp (COMMA exp)* )? RPAR                    # new
    | IF exp THEN CLPAR exp CRPAR ELSE CLPAR exp CRPAR         # if
    | PRINT LPAR exp RPAR                                      # print
    | ID                                                       # id
    | ID LPAR (exp (COMMA exp)* )? RPAR                        # call
    | ID DOT ID LPAR (exp (COMMA exp)* )? RPAR                 # dotCall
    ;
```

## Types

```
type : INT    # intType
     | BOOL   # boolType
     | ID     # idType   (class reference)
     ;
```

## Keywords & Operators

| Token | Value |
|---|---|
| `INT` | `int` |
| `BOOL` | `bool` |
| `TRUE` / `FALSE` | `true` / `false` |
| `NULL` | `null` |
| `IF` / `THEN` / `ELSE` | `if` / `then` / `else` |
| `LET` / `IN` | `let` / `in` |
| `VAR` | `var` |
| `FUN` | `fun` |
| `CLASS` / `EXTENDS` | `class` / `extends` |
| `NEW` | `new` |
| `PRINT` | `print` |
| Arithmetic | `+` `-` `*` `/` |
| Comparison | `==` `>=` `<=` |
| Logical | `\|\|` `&&` `!` |

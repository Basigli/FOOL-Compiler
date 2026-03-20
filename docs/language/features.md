# Language Features

## Primitive Types

FOOL supports two primitive types:

- **`int`** – integer numbers (e.g. `42`, `-7`)
- **`bool`** – boolean values: `true` or `false`

`bool` is a subtype of `int`: a `bool` value can be used wherever an `int` is expected.

## Variables

Variables are declared with `var` inside a `let … in` block:

```fool
let
  var x : int = 5;
  var flag : bool = true;
in
  print(x);
```

## Functions

Functions are declared with `fun` and can contain nested `let` declarations:

```fool
let
  fun double : int (n : int)
    n + n;
in
  print(double(21));
```

Functions support **static scoping**: an inner function may reference variables from its enclosing scope.

## Control Flow

The only control-flow construct is `if / then / else`. Both branches must be present:

```fool
if x >= 0 then { x } else { 0 - x };
```

The result type is the *lowest common ancestor* of the two branch types.

## Classes

Classes bundle fields and methods together. Fields are declared in the parameter list; methods follow the `fun` keyword inside the class body.

```fool
let
  class Counter (value : int) {
    fun increment : Counter ()
      new Counter(value + 1);
    fun get : int ()
      value;
  }
in
  let
    var c : Counter = new Counter(0);
  in
    print(c.get());
```

### Inheritance

A class can extend another class with `extends`:

```fool
class TaggedCounter (tag : int) extends Counter {
  fun getTag : int ()
    tag;
}
```

The subclass inherits all fields and methods of the parent class.

## Null

The `null` literal represents an absent object reference. Its type is compatible with any class type.

## Print

The `print` expression evaluates its argument, prints it to standard output, and returns the value:

```fool
print(42);
```

## Functional Programming in Java

### What is FP?

Functional Programming (FP) is a programming paradigm, and it is about programming with functions. Functional programs are built by composing _functions_ that take an argument and return a value. 

Functional programming is considered to be a set of techniques such as 

- First-class Functions
- Anonymous Functions
- Closures
- Currying (via Functional Interfaces, Monads)
- Lazy Evaluation
- Parametric Polymorphism (via Generics)
- Algebraic data types

**Functional paradigm advocates the following:**

- **No side effects**
- **Enforces Referential Transparency**


#### No Side Effects

In Imperative programming paradigm, programs are composed of elements that **"Do something"** . That **"Doing Something"** implies an _initiate state_, _a transaction_ and an _end state_. This is called **State mutation**.

One major difference between Imperative and FP is that, there are **no side effects in FP**.
**No side effects** means **no observable side effects**. 
It means:
- No mutation of variables i.e. **no _state mutation_** 
- No printing to the console or to any device
- No writing to the files, databases, networks or whatever
- No exception throwing

#### Enforces Referential Transparency

Functional programs must also not be effected by external world that means, 
**the output of a functional program must depend only on its argument**.
It means functional code may not read data from console, a file, a remote URL,
a database, or even from the program. 

Referential transparency code has several properties:

- **It is self-contained** : Doesn't depend on external device/program to work.
- **it is deterministic** : Always return the same value for the same argument.
- **It never throws an exception** : never throws an exception. But it's ok if the error is OOME (OutOfMemoryError) or SOE (Stack Overflow) as these errors are thrown by JVM and they are not supposed to handle by the code.
- **It won't create conditions cause other code to unexpectedly fail** : It won't mutate arguments or some other external data, causing the caller to find itself with stale data or concurrent access exception.

### Benefits of FP

- **Deterministic** : Because of referential transparency i.e. one input always give same output
- **Easy to test** : Because of no side effects and no mocks are needed.
- **Modular** : programs built from functions that have only input and output.
- **Easy composition and recombination** : Start by writing base functions first, then combine these base functions to higher-level ones. Repeat this process until we have a single function. All these functions are referentially transparent which makes it easy to build other programs without modifications.
- **Functional programs are Thread-safe** : Functional programs are inherently thread-safe because they avoid _mutation of shared state_.

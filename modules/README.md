# **Java 9 Modules (Java Platform Module System - JPMS)**

<div style="text-align: justify;">

## **Table of contents**

1. [JPMS Overview](#overview) </br>
2. [Modularity](#modularity) </br>
3. [Modules](#modules) </br>
   1. [Reusing a Module](#reusing-a-module) </br>
4. [Accessibility (Access control)](#access-control) </br>
5. [Module Descriptor](#module-descriptor) </br>
6. [Classpath and Module path](#class-path-and-module-path) </br>
   1. [Classpath](#classpath) </br>
   2. [Unnamed module](#unnamed-module) </br>
   3. [Module path](#module-path) </br>
   4. [Automatic module](#automatic-module) </br>
   5. [Explicitly named module](#explicitly-named-module) </br>
   6. [Summary of modules rules](#rules-for-modules) </br>
7. [Important commands](#important-commands) </br>
8. [Useful Links](#useful-links) </br>

## **Overview**

---

[Table of contents](#table-of-contents) </br>

- Java modules introduced in Java 9 via **Java Platform Module System (JPMS)** and it is sometimes referred as **Project Jigsaw** (It's main objective is to bring Modularity into Java Platform).
- A key motivation of the module system is **strong encapsulation**.

## **Modularity**

---

[Table of contents](#table-of-contents) </br>

Simply put, modularity is a design principle that helps us to achieve:

- loose coupling between components.
- clear contracts and dependencies between components.
- hidden implementation using **strong encapsulation**.
- In Java, **modularity adds a higher level aggregation above packages**.

## **Modules**

---

[Table of contents](#table-of-contents) </br>

In Java, **modules adds a higher level aggregation above packages** or **a module is a set of packages designed for reuse**.

- Programs are packages not classes. Therefore, **In Java 9, programs are modules**.
- A ***module*** is **a uniquely named, reusable group of related packages as well as resources (such as images and XML files) and a *module descriptor*** (module-info.java).

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/modules/module_design.png" alt="Java 9 Module System" width="500" height="300"></img> </br>

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/modules/module_intro.png" alt="Java 9 java.base Module structure" width="500" height="300"></img> </br>

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/modules/hello_world_module.png" alt="Hello World Module Example" width="500" height="300"></img> </br>

### **Reusing a Module**

[Table of contents](#table-of-contents) </br>

Important to note that module exports packages but requires modules (dependencies). The reason is due to old software engineering principle i.e. **The Unit of reuse is the unit of release**. The principle is all about **separation of concerns**. The ***requires*** directive ensures reusing a module (reliable dependencies).

## **Access control**

---

[Table of contents](#table-of-contents) </br>

**Accessibility** (Access control) is more powerful in JDK 9 and it is described below.

|**JDK 1-8**  |**JDK 9**  |
|---------|---------|
|- public </br> - protected </br> - package </br> - private     |- *public to everyone* </br> - *public but only to friend modules* </br> - *public only within a module* </br> - protected </br> - package </br> - private         |

As per above illustration, **public packages are categorized into exported packages** and **concealed packages**.

- **Exported packages**: public classes of exported packages are accessible outside the module. The ***exports*** keyword is used to export a public package outside the module.
- **Concealed packages**: public classes of concealed packages are **NOT** accessible outside the module.

## **Module Descriptor**

---

[Table of contents](#table-of-contents) </br>

A module must provide a *module descriptor*. A module descriptor is the compiled version of a module declaration that’s defined in a file named `module-info.java`. After compiling the module declaration creates the module descriptor, which is stored in a file named `module-info.class` in the module’s root folder.

The module declaration’s body can be empty or may contain various **module directives**.

A *module descriptor* is a *meta data* that specifies:

- the module's name
- the module's dependencies (i.e. other modules this module depend on)
- the packages it explicitly makes available to other modules (all other packages in the module are implicitly unavailable to other modules)
- the service it offers
- the service it consumes
- to what other modules it allows reflection

Module directives are:

|Directive  |Description  |
|---------|---------|
|***module***     | Each module declaration begins with the keyword module, followed by a unique module name and a module body enclosed in braces. </br> e.g. `module modulename { ... }`         |
|***requires***    | Each module must explicitly state its dependencies. It ensures reusability (reusing a module — reliable dependencies). </br> It specifies that this module depends on another module — this relationship is called a **module dependency**. </br> e.g. `require modulename;` or `requires java.base;`         |
|***requires static***     | directive to indicate that a module is required at compile time, but is optional at runtime. This is known as an **optional dependency**. </br> e.g. `require static modulename;`        |
|***requires transitive***     | **requires transitive — implied readability**. To specify a dependency on another module and to ensure that other modules reading your module also read that dependency is known as **implied readability**. </br> e.g. `require transitive modulename;` or `require transitive java.xml;`         |
|***exports***     | An `exports` module directive specifies one of the module’s packages whose `public` types (and their nested `public` and `protected` types) should be accessible to code in all other modules. </br> e.g. `exports com.example.hello;`        |
|***exports...to***     | An `exports…to` directive enables you to specify in a comma-separated list precisely which module’s or modules’ code can access the exported package — this is known as a **qualified export**.        |
|***uses***     | A `uses` module directive specifies a service used by this module — making the module a service consumer. </br> A *service* is an object of a class that implements the interface or extends the `abstract` class specified in the `uses` directive.        |
|***provides...with***     | It specifies that a module provides a service implementation — making the module a *service provider*. </br> The `provides` part of the directive specifies an interface or `abstract` class listed in a module’s `uses` directive and the `with` part of the directive specifies the name of the service provider class that `implements` the interface or `extends` the `abstract` class.         |
|***open, opens, and opens…to***     | Before Java 9, reflection could be used to learn about all types in a package and all members of a type — even its `private` members—whether you wanted to allow this capability or not. Thus, nothing was truly encapsulated. </br> A key motivation of the module system is strong encapsulation. By default, a type in a module is not accessible to other modules unless it’s a public type and you export its package. You expose only the packages you want to expose. With Java 9, this also applies to reflection. </br></br> `opens package`: </br> Allowing runtime-only access to a package. It indicates that a specific package’s `public` types (and their nested `public` and `protected` types) are accessible to code in other modules at runtime only. Also, all the types in the specified package (and all of the types’ members) are accessible via reflection. e.g. `opens package` </br> `opens...to`: </br> Allowing runtime-only access to a package by specific modules. It indicates that a specific package’s `public` types (and their nested `public` and `protected` types) are accessible to code in the listed modules at runtime only. All of the types in the specified package (and all of the types’ members) are accessible via reflection to code in the specified modules. </br> e.g. `opens package to comma-separated-list-of-modules` </br> `open`: </br> Allowing runtime-only access to all packages in a module. If all the packages in a given module should be accessible at runtime and via reflection to all other modules, we may open the entire module. </br> e.g. `open module modulename { // module directives }`      |

### **Reflection Defaults**

---

[Table of contents](#table-of-contents) </br>

By default, a module with runtime reflective access to a package can see the package’s `public` types (and their nested `public` and `protected` types). However, the code in other modules can access *all* types in the exposed package and *all* members within those types, including `private` members via `setAccessible`, as in earlier Java versions.

## **Class Path and Module Path**

---

[Table of contents](#table-of-contents) </br>

In Java 9, there is both a **classpath** and a **module path**.
From Java 9, a JAR file becomes modularized (and becomes a module) when it contains a file module-info.class (compiled from module-info.java) at the JAR file root. Modules are stronger than the traditional classpath mechanism.

In Java 9, It has **THREE types of modules**:

1. Unnamed module
2. Automatic module
3. Explicitly named module

### **Classpath**

---

[Table of contents](#table-of-contents) </br>

classpaths are not checked ahead of time, and errors due to missing classes only occur when the classes are actually needed. That means that an incorrect classpath might be discovered only after an application has been run for a long time, or after it has run many times.

#### **Unnamed module**

---

[Table of contents](#table-of-contents) </br>

Also, note that the collection of all JAR files in the **classpath are considered to be part of a *single unnamed module***. The ***unnamed module*** is considered a regular module, but **it exports everything to other modules, and it can access all other modules**. This means that, if we have a Java application that’s modularized, but have some old libraries that haven’t been modularized yet, we can just put those libraries in the classpath and everything will just work.

***Important***: We can have many modules but Java 9 has **only ONE unnamed module**.

Note: *From Java 9, Class and resource files previously stored in lib/rt.jar, lib/tools.jar, lib/dt.jar and various other internal JAR files are stored in a more efficient format in implementation-specific files in the lib directory.*

- Any JAR running in the *classpath* is called an **unnamed module**.
- Any **JAR with a module-descriptor** running in the classpath **is an unnamed module**.
- From Java 9, there is exactly ONE unnamed module. Therefore, Unnamed modules can talk to other unnamed modules. It means that classes with in ONE unnamed module can talk to each other.
- unnamed modules **CANNOT** talk to automatic modules.

### **Module path**

---

[Table of contents](#table-of-contents) </br>

Java 9 contains a **module path that works alongside the classpath**. Using the modules in the module path, the JVM can check, both at compile time and at run time, that all necessary modules are present, and can report an error if any are missing. All JAR files in the classpath, **as members of the unnamed module, are accessible to the modules in the module path and vice versa**.

#### **Automatic module**

---

[Table of contents](#table-of-contents) </br>

If we do not want to modularize a JAR file, or that the JAR file belongs to someone else, so we can’t modularize it ourself. In that case, we can still put the JAR file into the module path; **it becomes an automatic module.**

An automatic module is considered a module even though it doesn’t have a module-info.class file. The module’s name is the same as the name of the JAR file containing it, and can be explicitly required by other modules. It automatically exports all of its publicly accessible APIs, and reads (that is, requires) every other named module, as well as the unnamed modules.

Note: *Not every unmodularized JAR file can be moved to the module path and made an automatic module. There is a rule that **a package can only be part of one named module**. So if a package is in more than one JAR file, then only one of the JAR files containing that package can be made into an automatic module—the other can be left in the classpath and remain part of the unnamed module.*

- Any traditional JAR (e.g. third-party) running in the *module path* (WITHOUT module descriptor) is called **automatic module**.
- Automatic modules can talk to other automatic modules.
- Automatic modules can talk to unnamed modules.
- An automatic module automatically exports all its packages.
- An automatic module CANNOT talk to an explicitly named module (Because of the rule that an explicitly named module exports only what it specifically declares to export).

#### **Explicitly named module**

---

[Table of contents](#table-of-contents) </br>

Any JAR with a module descriptor running in the module path is called explicitly named module.

- Any **JAR with a module-descriptor** running in the module path is an **explicitly named module**.
- An explicitly named module can talk to another explicitly named module.
- An explicitly named module can talk to an automatic module.
- An explicitly named module CANNOT talk to an unnamed module.
- An explicitly named module must use *requires* directive for any module it needs (Incl. automatic modules).
- An explicitly named module exports only what it specifically declares to export.

## **Rules for Modules**

---

[Table of contents](#table-of-contents) </br>

- Any JAR running in the *classpath* is called an **unnamed module**.
- Any traditional JAR (e.g. third-party) running in the *module path* is called **automatic module**.
- Any **JAR with a module-descriptor** running:
  - the classpath is an **unnamed module**.
  - the module path is an **explicitly named module**.
- Modules cannot share packages.
- From Java 9, there is exactly ONE unnamed module. Therefore, Unnamed modules can talk to other unnamed modules. It means that classes with in ONE unnamed module can talk to each other.
- Automatic modules can talk to other automatic modules.
- Automatic modules can talk to unnamed modules.
- unnamed modules CANNOT talk to automatic modules.
- An explicitly named module can talk to another explicitly named module.
- An explicitly named module can talk to an automatic module.
- An explicitly named module CANNOT talk to an unnamed module.
- An explicitly named module must use *requires* directive for:
  - Any module it needs (Incl. automatic modules)
- An explicitly named module exports only what it is specifically export.
- An automatic named module automatically exports all its packages.
- An automatic module CANNOT talk to an explicitly name module (Because of the rule that an explicitly named module exports only what it is specifically export).

## **Important commands**

---

[Table of contents](#table-of-contents) </br>

List all modules: `java --list-modules` </br>
Creating a module: `javac -d classes -sourcepath src module-info.java com/example/hello.SayHello.java` </br>
Running a module: `java -p mods -m hello.world` </br>

## Useful Links

---

[Table of contents](#table-of-contents) </br>

http://openjdk.java.net/projects/jigsaw/quick-start </br>
https://blog.andresteingress.com/2017/09/29/java-9-modules.html </br>
https://techbeacon.com/app-dev-testing/legacy-developers-guide-java-9

</div>
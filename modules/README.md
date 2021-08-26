# **Java 9 Modules (Java Platform Module System - JPMS)**

<div style="text-align: justify;">

## **Overview**

---

- Java modules introduced in Java 9 via **Java Platform Module System (JPMS)** and it is sometimes referred as **Project Jigsaw** (It's main objective is to bring Modularity into Java Platform).
- A key motivation of the module system is **strong encapsulation**.

## **Modularity**

---

- **Modularity adds a higher level aggregation above packages**.
- Programs are packages not classes. **A module is a set of packages designed for reuse. Therefore in Java 9, programs are modules**.
- A ***module*** is **a uniquely named, reusable group of related packages as well as resources (such as images and XML files) and a *module descriptor*** (module-info.java).

### **Reusing a Module**

Important to note that module exports packages but requires modules (dependencies). The reason is due to old software engineering principle i.e. **The Unit of reuse is the unit of release**. The principle is all about **separation of concerns**. The ***requires*** directive ensures reusing a module (reliable dependencies).

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/modules/module_design.png" alt="Java 9 Module System" width="500" height="300"></img> </br>

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/modules/module_intro.png" alt="Java 9 java.base Module structure" width="500" height="300"></img> </br>

## **Access control**

---

**Accessibility** (Access control) is more powerful in JDK 9 and it is described below.

|**JDK 1-8**  |**JDK 9**  |
|---------|---------|
|- public </br> - protected </br> - package </br> - private     |- *public to everyone* </br> - *public but only to friend modules* </br> - *public only within a module* </br> - protected </br> - package </br> - private         |

As per above illustration, **public packages are categorized into exported packages** and **concealed packages**.

- **Exported packages**: public classes of exported packages are accessible outside the module. The ***exports*** keyword is used to export a public package outside the module.
- **Concealed packages**: public classes of concealed packages are **NOT** accessible outside the module.

## **Module Descriptor**

---

Module descriptor specifies:

- the module's name
- the module's dependencies (i.e. other modules this module depend on)
- the packages it explicitly makes available to other modules (all other packages in the module are implicitly unavailable to other modules)
- the service it offers
- the service it consumes
- to what other modules it allows reflection

### **Important commands**

Creating a module: `javac -d classes -sourcepath src module-info.java com/example/hello.SayHello.java` </br>
Running a module: `java -p mods -m hello.world` </br>


</div>
# **Java Modules**


## **Overview**

---

Java modules introduced in Java 9 via **Java Platform Module System (JPMS)** and it is sometimes referred as **Project Jigsaw** (It's main objective is to bring Modularity into Java Platform). A key motivation of the module system is strong encapsulation.

### **Modularity**

Modularity adds a higher level aggregation above packages. The key new language element is *module* which is a uniquely named, reusable group of related packages as well as resources (such as images and XML files) and a *module descriptor*.

Module descriptor specifies:

- the module's name
- the module's dependencies (i.e. other modules this module depend on)
- the packages it explicitly makes available to other modules (all other packages in the module are implicitly unavailable to other modules)
- the service it offers
- the service it consumes
- to what other modules it allows reflection

<img src="https://github.com/srikanthkakumanu/DSA/blob/main/modules/module_design.png" alt="Java 9 Module System" width="500" height="300"></img> </br>


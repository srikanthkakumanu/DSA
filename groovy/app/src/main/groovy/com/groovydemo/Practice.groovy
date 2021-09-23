package com.groovydemo;

class Practice {
  static void main(String[] args) {
    // basicOps()
    // println "a + b = " + sum(10, 20)
    // switchcase()
    // loops()
    // basicIO()
    // lists()
    // maps()
    // range()
    // def name = "Paul"
    // passByValue(name)
    // println "Name value in main() : " + name

    // simple example of closure
    // def aList = [1,2,3,4]
    // aList = getList(aList)
    // println aList

    // varargs i.e. unknown no. of values to a function
    // def nums = sumAll(1,2,3,4)
    // println "Sum : " + nums

    // Closures
    closures()
  }

  /**
  * Simple function
  */
  static def sum(a=0, b=0) {
    // return a+b or
    return "Sum = " + (a+b)
  }

  /**
  * Groovy does pass by value
  */
  static def passByValue(name) {
    name = "Sally"
    println "Name value in passValue() function : " + name
  }

  /**
  * Simple example of closures
  */
  static def getList(aList) {
    // basic example of closures in Groovy
    def newList = aList.collect { it * 2 };
    return newList
  }

  /**
  * sending unknown number of values into a function like varargs
  */
  static def sumAll(int... num) {
    def sum = 0;
    num.each { sum += it;}
    return sum
  }

  /**
  * Basic Operations of variables, strings
  */
  static def basicOps() {
    println "hello srikanth";
    def age = "Dog";
    age = 40;

    println("Intger Operations\n-----------------------")
    println(3-2)
    println(6.intdiv(2))
    println("Float Operations\n-----------------------")
    println(5.2.plus(4.4))
    println(5.2.minus(4.4))
    println(5.2.multiply(4.4))
    println(5.2 / 4.4)
    println("Largest values\n-----------------------")
    println "Biggest Int: " + Integer.MAX_VALUE
    println "Smallest Int: " + Integer.MIN_VALUE
    println "Biggest Float: " + Float.MAX_VALUE
    println "Smallest Float: " + Float.MIN_VALUE
    println "Biggest Double: " + Double.MAX_VALUE
    println "Smallest Double: " + Double.MIN_VALUE

    println "Decimal point accuracy is accurate in Groovy:\n----------------------- "
    println "1.1000000001111 + 1.10000000001111 = " +
            (1.1000000001111 + 1.10000000001111)
    def randNum = 2.0
    println "Math.abs = " + Math.abs(-2.45)

    println "String Operations\n-----------------------"
    def name = "Srikanth"
    // single quotes does not translate name variable and you can follow ${name} old style
    println 'I am $name \n'
    // double quotes does the translate name variable
    println "I am $name \n"

    println "3rd index of name: " + name[3]
    println "index of r: " + name.indexOf('r')
    println "first three characters of name: " + name[0..2]
    println "Every other character of name: " + name[0,2,4]
    println "Sub string at 1: " + name.substring(1)
    println "Sub string at 1 to 4: " + name.substring(1,4)
    println "cancatenate strings : " + "My Name ".concat(name)
    println "Srikanth == Srikanth : " + ('Srikanth'.equals('Srikanth'))

    def repeatStr = "what i said is " * 3
    println "Repeat string : " + repeatStr
    println "Remove what word : " + (repeatStr - "what")
    println "Split string : " + repeatStr.split(' ')
    println "string to list : " + repeatStr.toList()
    println "replace string : " + repeatStr.replaceAll('i', 'she')
    println "Ant <=> Banana : " + ('Ant' <=> 'Banana')
    println "banana <=> Banana : " + ('banana' <=> 'Banana')
    println "Banana <=> Banana : " + ('Banana' <=> 'Banana')
    println "printf Operations : \n-------------------"
    // %-10s = 10 spaces padding on rightside to value
    // %d decimal integer
    // %.2f float of max 2 decimal places
    // %10s 10 spaces padding on leftside to value
    printf "%-10s %d %.2f %10s \n", ['Stuff', 10, 1.234, 'Random']

    def multiline = ''' I am a String
    that goes on
    form many lines '''

    println "Multiline string: \n" + multiline

  }

  /**
  * switch case statements
  */
  static void switchcase() {
    println "Swtich case statements advanced : \n--------------------"
    def ageOld = 8
    switch(ageOld) {
      case 0..6 : println "Child"; break;
      case 7..12 : println "Teenager"; break;
      case 13..18 : println "Young Adult"; break;
      default : println "Adult"
    }
  }

  /**
  * loops
  */
  static def loops() {
    println "loops advanced : \n--------------------"

    for(j in 2..6)
      println j

    def aList = [10, 12, 13, 14]
    for(j in aList)
      println j

    def customers = [
      100 : "Paul",
      101 : "Sally",
      102 : "Sue"
    ]

    for(cust in customers)
      println "$cust.value : $cust.key"
  }

  /**
  * Basic Input/Output Operations
  */
  static def basicIO() {
    print "Whats your name : "
    def fName = System.console().readLine
    println "Hello " + fName
    print "Enter a Number : "
    def num = System.console().readLine().toDouble()
    print "Enter another Number : "
    def anotherNum =  System.console().readLine.toDouble()
    printf "%.2f + %.2f = %.2f \n", [num, anotherNum, (num + anotherNum)]
  }

  /**
  * Basic list Operations
  */
  static void lists() {
    def primes = [2,3,5,7,11,13]

    println "2nd Prime : " + primes[2]
    println "3rd Prime : " + primes.get(2)
    println "Length : " + primes.size()

    primes.add(17)
    primes<<19
    primes.add(23)
    primes + [29,31]
    primes - 31
    println "Is Primes Empty : " + primes.isEmpty()
    println "first 3 of primes : " + primes[0..2]
    println primes

    println "Primes match with another list : " + primes.intersect([2,3,7])
    println "Primes Reverse : " + primes.reverse()
    println "Primes Sort : " + primes.sort()
    println "Primes last item : " + primes.pop()

    // employee variable can hold any type of value
    def employee = ['Srikanth', 40, 6.25, [1,2,3]]

    println "2nd Number of employee : " + employee[3][1]
  }

  /**
  * Basic map Operations
  */
  static void maps() {

    def personMap = [
      'name' : 'Gregory',
      'age' : 41,
      'address' : '123 Main St',
      'codes' : [3243,3743,6647]
    ]

    println "Map Operations : \n----------------------"
    personMap.put('city', 'Newyork')
    println "Name : " + personMap['name']
    println "Age : " + personMap.get('name')
    println "List of Codes : " + personMap['codes'][1]
    println "Has City : " + personMap.containsKey('city')
    println "Size : " + personMap.size()
  }

  /**
  * Basic Range Operations
  */
  static void range() {

    def oneTo10 = 1..10
    def aToz = 'a'..'z'
    def zToa = 'z'..'a'

    println "Range Operations : \n----------------------"
    println "oneTo10 : " + oneTo10
    println "aToz : " + aToz
    println "zToa : " + zToa
    println "size : " + oneTo10
    println "index : " + oneTo10.size()
    println " contains 11 : " + oneTo10.contains(11)
    println "Get Last : " + oneTo10.getTo()
    println "Get First : " + oneTo10.getFrom()
  }

  /**
  *  -----------Closures----------------------
  * Closures: a block of code that accept parameters and that closure
  * can also be passed to methods.
  */
  static def closures() {
    def getFactorial = { num -> (num <= 1 ? 1 : num * call(num-1))}
    println "Factorial 4 : " + getFactorial(4)

    // greeting declared outside the closure
    def greeting = "Greeting"
    def sayGoodBye = {theName -> println "variable declared outside closure : $greeting $theName"}
    sayGoodBye("Paul")

    // closure with lists
    def numList = [1,2,3,4]
    println "Closure with lists : "
    numList.each { println it}

    // closure with maps
    def employees = [
      "Paul" : 42,
      "Sally" : 35,
      "Sue" : 45
    ]
    println "Closure with maps"
    employees.each { println "$it.key : $it.value"}

    def randomNums = [1,2,3,4,5,6]
    randomNums.each { num -> if(num%2 == 0) println(num) }

    def nameList = ["Paul", "Sally", "Emma"]
    def matchEle = nameList.find { item -> item == "Emma"}
    println "Match : " + matchEle

    def numMatches = randomNums.findAll { item -> item > 4 }
    println "findAll : " + numMatches

    println "Match Any > 5 : " + randomNums.any { item -> item > 5 }
    println "Match Every > 1 : " + randomNums.every { item -> item > 1 }
    println "Collect : " + randomNums.collect { item -> item * 2 }

    // pass closure to a function
    def getEven = { num -> return (num%2 == 0) }
    def evenNums = listEdit(randomNums, getEven)
    println "Evens : " + evenNums
  }

  /**
  * Passing closure to a function
  */
  static def listEdit(list, closure) {
    return list.findAll(closure)
  }


}

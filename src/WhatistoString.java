
//To string is used for getting all value when we use object of class
// it doesnot require to call all getter method to get value just call to string funtions


//The goal of encapsulation is to hide the internal details of an object
//and to control access to its data. By declaring variables as private,
// you prevent direct access to them from outside the class.
// This forces users of the class to interact with the object only
// through public getter and setter methods, which can impose rules or
// validations before accessing or modifying the data.


//Canonical classes and record classes are used primarily for representing simple data structures, where the main focus is on the data itself rather than the behavior


import java.util.Objects;

//class Bagh{
//private final String name;
//private final int id;
//private final double salary;
//
//    public Bagh(String name, int id, double salary) {
//        this.name = name;
//        this.id = id;
//        this.salary = salary;
//    }
//
//    public String getName() {
//        return name;
//    }
//    public int getId() {
//        return id;
//    }
//
//    public double getSalary() {
//        return salary;
//
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Bagh bagh = (Bagh) o;
//        return id == bagh.id && Double.compare(salary, bagh.salary) == 0 && Objects.equals(name, bagh.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, id, salary);
//    }
//
//    // @Override
//    public String toString() {
//        return "Bagh{" +
//                "name='" + name + '\'' +
//                ", id=" + id +
//                ", salary=" + salary +
//                '}';
//    }
//
//}

//no need to write all this code

//data carrier class- record class
record Bagh(String name, int id, double salary)//canonical constructor
 {}

//no change require in main
//Record classes automatically provide commonly used methods such as toString(),
// equals(), and hashCode() without requiring you to manually define them.

public class WhatistoString {
    public static void main(String[] args) {
        Bagh b1 = new Bagh("Sijan", 1, 20000.0);
        Bagh b2 = new Bagh("Sijan", 1, 20000.0);


        //to compare objects not values

        //System.out.println(b1.equals(b2)); // false 2 different objects
        //to campare values in objects
        //overriding or generate equals() and hash code() in the class
        System.out.println(b1.equals(b2)); // true


        System.out.println(b1.toString());
    }
}

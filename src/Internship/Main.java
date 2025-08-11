package Internship;

public class Main{
    public static void main(String[] args) {
        Citizen c1 = new Citizen(12,"Ram");
        Citizen c2 = new Citizen(12,"Hari");
        System.out.println(c1.equals(c2));//true
//        System.out.println(c1 == c2); //false
//        System.out.println(c1 == c1);
    }
}

class Citizen{
    int citizenNo;
    String name;
    public Citizen(int citizenNo, String name) {
        this.citizenNo = citizenNo;
        this.name = name;
    }

//    @Override
//    public boolean equals(Object c1){
//        Citizen c = (Citizen) c1;
//        return this.citizenNo == c.citizenNo;
//    }
}
class Student {
    int rollNo;
    int classNo;

}
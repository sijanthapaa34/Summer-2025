package CollectionPractice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class Teacher{
    int id;
    String name;
    int age;
    double salary;

    public Teacher(int id, String name, int age, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    public int getId() {
        return id;
    }

}

class Subjects extends Teacher{
    String subject;
    public Subjects(int id, String name, int age, double salary, String subject) {
        super(id, name, age, salary);
        this.subject = subject;
    }
}
public class Agesorting {
    public static void main(String[] args) {
        List <String> teachers = Arrays.asList("ram","Sijan","Shyam", "hari", "krishna");
//        teachers.add(new Teacher(10, "Ram", 23, 24400.0));
//        teachers.add(new Teacher(4,"Krishna", 25 , 25000));
//        teachers.add(new Teacher(2, "Hari", 26 , 26000));
//        teachers.add(new Teacher(9, "Shyam", 27 , 27000));
//        teachers.add(new Teacher(7, "Om", 28 , 28000));
//
//        Comparator <Teacher> com = (id1, id2) -> id1.id > id2.id? 1:-1;

 //       teachers.sort(com);
//        for(Teacher t: teachers) {
//            System.out.println(t);
//        }

//        teachers.stream()
//                .distinct()
//                .sorted(Comparator.comparingInt(Teacher::getId))
//                .forEach(i -> System.out.println(i));
        System.out.println(teachers);

        teachers.stream()
                .filter(s -> s.toLowerCase().startsWith("s"))
                .map(s -> s + " Thapa")
                .forEach(s -> System.out.println(s));


    }
}

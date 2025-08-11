package CollectionPractice;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Student implements Comparable<Student>{//this is one method to sort or compare collection : natural way without using com
    String name;
    int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
//     ps.setNull(3, java.sql.Types.DOUBLE);
//        ps.setBoolean(4, false);

    @Override
    public String toString() { // generate bata lera aako
        return "Student{" + "name='" + name + '\'' + ", age=" + age + '}';
    }

    @Override
    public int compareTo(Student that) {//this is required when we use comparable, no need for comparator

        if(this.age>that.age){
            return 1;
        }
        else{
            return -1;
        }
    }
}
public class Demo1 {
    public static void main(String[] args) {
//        //this is another method to sort collection: Comparator
//        Comparator <Student >com = new Comparator<Student>() { //we can use lambda expression because
//            //comparator is functional interface
//            @Override
//            public int compare(Integer i, Integer j) {
//                  if(i%10>j%10){
//                      return 1;
//                  }
//                  else{
//                      return -1;
//                  }


//            public int compare(Student i, Student j) {
//                if(i.age>j.age){
//                    return 1;
//                }
//                else{
//                    return -1;
//                }
//            }
//        };

        //with using lambda expression and conditional expression
         Comparator <Student>com = ( i,  j)->  i.age>j.age? 1:-1;




        List<Student>   nums = new ArrayList<Student>();
        nums.add(new Student("Sijan", 19));
        nums.add(new Student("Sam", 21));
        nums.add(new Student("Samina", 24));
        nums.add(new Student("Samrana", 26));

        Collections.sort(nums, com);
//        for(Student sunt: nums) {
//            System.out.println(sunt);
//
//        }

        //if not want com in .sort(nums, com) and want natural sorting we use comparable
        //Collections.sort(nums);
        for(Student sunt: nums) {
            System.out.println(sunt);

        }
    }
}

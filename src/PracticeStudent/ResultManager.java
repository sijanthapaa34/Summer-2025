package PracticeStudent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class ResultManager {
Scanner sc = new Scanner(System.in);
StudentDAO dao = new StudentDAO();
Student student = null;
public void setGPA(int id){
    double totalGP = 0;
    DecimalFormat df = new DecimalFormat("#.00");
    HashMap<Subjects,Double> GPAs=new HashMap<>();
    for(Subjects sub: Subjects.values()){
        System.out.println("Enter GP of "+ sub +": " );
        double gp = sc.nextDouble();
        sc.nextLine();
        GPAs.put(sub, gp);
        totalGP +=gp;
    }
    double avgGPA = totalGP/5;
    Student stud = dao.getStudentsbyID(id);
    StudentManager sm = new StudentManager();
    dao.applyScholarship(id, sm.isEligibleForScholarship(stud));
    dao.addResultinDB(id,GPAs);
    stud.setGPA( Double.parseDouble(df.format(avgGPA)));
    dao.setNewGPA(id, Double.parseDouble(df.format(avgGPA)));
}
public void Result(int id)
{
    //The lambda expression (subject, gpa) -> System.out.println("GPA for " + subject + ": " + gpa)
    // is a Consumer that takes two parameters (in this case, the key and value from the HashMap).
    // However, the forEach method in a Map actually requires a BiConsumer, which is a specialized
    // form of Consumer that takes two arguments. The BiConsumer interface has the method void accept(T t, U u),
    // allowing it to accept two arguments, such as the key and value in a map.
    //        @FunctionalInterface
    //        public interface BiConsumer<T, U>
    //Represents an operation that accepts two input arguments and returns no result.
    // This is the two-arity specialization of Consumer. Unlike most other functional interfaces,
    // BiConsumer is expected to operate via side-effects.
    // This is a functional interface whose functional method is accept(Object, Object).


    Student stud = dao.getStudentsbyID(id);
    String name = stud.getStudentName();
    String level = String.valueOf(stud.getLevel());

    System.out.println("Name: "+ name);
    System.out.println("ID: "+id+"     "+"Level: "+level);
    System.out.println("Subject                    |GP");

    dao.showResult(id);
//        double avgGPA = stud.getSubjectGPAs().values().stream()
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElse(0.0); // Default to 0.0 if no GPAs are present
    System.out.println("Thankyou!!!!");
}}

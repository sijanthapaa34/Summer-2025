package PracticeStudent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ResultManager rm = new ResultManager();
    private static StudentDAO dao = new StudentDAO();
    //private static List<Student> student = new ArrayList<>();
    public static void main(String[] args) {
        boolean exit = false;
        while(!exit){
            System.out.println("\nWelcome Student Management System!");
            System.out.println("1. View Students");
            System.out.println("2. View Result");
            System.out.println("3. Principal/Class Teacher");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                    viewAllStudents();
                    break;
                case 2:
                    showResult();
                    break;
                case 3:
                    forTeacher();
                    break;
                case 4:
                    System.out.println("Exiting.....");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid");
                    break;
            }

        }
    }


    public static void viewAllStudents() {
        dao.viewStudents();
    }
    public static void showResult() {
        System.out.println("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        rm.Result(id);

    }
    public static void forTeacher() {
        StudentLevel objlevel = null;
        System.out.println("Enter password: ");
        int pass = sc.nextInt();
        sc.nextLine();
        if(pass== 1738){
            boolean exit = false;
            while (!exit)
            {
                System.out.println("1. Add Students");
                System.out.println("2. Update Students");
                System.out.println("3. Delete Students");
                System.out.println("4. Set GPA");
                System.out.println("5. Exit");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice)
                {
                    case 1:
                        System.out.println("Enter name of student:");
                        String name = sc.nextLine();
                        System.out.println("Enter level of student:" +
                                "\n(FRESHMAN, SOPHOMORE, JUNIOR, SENIOR) ");
                        String level = sc.next().toUpperCase();
                        try{
                            objlevel = StudentLevel.valueOf(level);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid choice");
                        }
                        sc.nextLine();
                        Student student = new Student(name, objlevel);
                        try {
                            dao.addNew(student);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
//                        student.add(stud);
//                        student.sort(Comparator.comparing(Student::getStudentName));
                        break;
                    case 2:
                        System.out.println("Enter Student ID: ");
                        int toupdateid = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter New Level: ");
                        String newLevel = sc.nextLine();
                        dao.updateStudent(toupdateid, StudentLevel.valueOf(newLevel));
                        break;
                    case 3:
                        System.out.println("Enter Student ID: ");
                        int todeleteid = sc.nextInt();
                        sc.nextLine();
                        dao.delete(todeleteid);
                        break;
                    case 4:
                        System.out.println("Enter Student ID: ");
                        int idforgpa = sc.nextInt();
                        sc.nextLine();
                        rm.setGPA(idforgpa);
                    case 5:
//                        try {
//                            dao.addNew(student);
//                        } catch (SQLException e) {
//                            throw new RuntimeException(e);
//                        }
                        System.out.println("Exiting .. ");
                        exit = true;
                        break;
                }
            }
        }

    }
}

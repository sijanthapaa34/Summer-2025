package PracticeStudent;

import java.util.HashMap;

public class Student{
    private int id;
    private String name;
    private double GPA;
    private StudentLevel level;

    public Student( String name, StudentLevel level) {
        this.name = name;
        this.level = level;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getStudentId() {
//        return id;
//    }

    public String getStudentName() {
        return name;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public double getGPA() {
        return GPA;
    }

    public StudentLevel getLevel() {
        return level;
    }

    public void setLevel(StudentLevel level) {
        this.level = level;
    }
}


package PracticeStudent;

import java.util.Collections;
import java.util.HashMap;

public class StudentManager {
    public boolean isEligibleForScholarship(Student student){
        StudentProcessor sp = scholar -> scholar > 3.7;
        return sp.isEligibleForScholarship(student.getGPA());
    }

}

package PracticeStudent;

import java.sql.*;
import java.util.HashMap;

public class StudentDAO {

    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/practice";
        String user = "root";
        String password = "sijan.@34";
        return DriverManager.getConnection(url, user, password);
    }
    public Student getStudentsbyID(int id){
        Student stud = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from student where id =  "+id;
            Statement st = connect().createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                String name = rs.getString("name");
                StudentLevel level = StudentLevel.valueOf(rs.getString("level"));
                stud = new Student(name, level);
                stud.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stud;
    }
    public void addNew(Student student) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String query = "insert into student(name, level) values (?,?)";
        PreparedStatement ps = connect().prepareStatement(query);
            ps.setString(1, student.getStudentName());
            ps.setString(2, String.valueOf(student.getLevel()));
        ps.executeUpdate();

    }
    public void updateStudent(int id,StudentLevel level ){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update student set level = ? where id = ?";
            PreparedStatement ps = connect().prepareStatement(query);
            ps.setString(1, String.valueOf(level));
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Updated");
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void delete(int id){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "delete from student where id = ?";
            PreparedStatement ps = connect().prepareStatement(query);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(" Updated");
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void viewStudents(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select id, name, level, GPA, scholarship from student order by name asc";
            Statement st = connect().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Student Name: " + rs.getString("name") + " Level: " + rs.getString("level") +
                        " Grade Point Average (GPA): " + rs.getDouble("GPA") + " Scholarship: " + rs.getBoolean("Scholarship"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNewGPA(int id, double GPA) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "update student set GPA = ? where id = ?";
            PreparedStatement ps = connect().prepareStatement(query);
            ps.setDouble(1, GPA);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Updated");
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
public void applyScholarship(int id, boolean what){
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "update student set scholarship = ? where id = ?";
        PreparedStatement ps = connect().prepareStatement(query);
        ps.setBoolean(1, what);
        ps.setInt(2, id);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Updated");
        } else {
            System.out.println("Student not found with ID: " + id);
        }
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
}


    public void addResultinDB(int id, HashMap<Subjects, Double> gpAs)  {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "insert into results(id, name, physics_gpa,chemistry_gpa,math_gpa,computer_gpa,english_gpa) values (?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        ps = connect().prepareStatement(query);
        Student stud = getStudentsbyID(id);
        ps.setInt(1, id);
        ps.setString(2, stud.getStudentName());
        ps.setDouble(3, gpAs.getOrDefault(Subjects.PHYSICS, 0.0));
        ps.setDouble(4, gpAs.getOrDefault(Subjects.CHEMISTRY, 0.0));
        ps.setDouble(5, gpAs.getOrDefault(Subjects.MATH, 0.0));
        ps.setDouble(6, gpAs.getOrDefault(Subjects.COMPUTER, 0.0));
        ps.setDouble(7, gpAs.getOrDefault(Subjects.ENGLISH, 0.0));
        ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void showResult(int id){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from results where id =  "+id;
            Statement st = connect().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                System.out.println("Physics                    |" + rs.getDouble("physics_gpa"));
                System.out.println("Chemistry                  |" + rs.getDouble("chemistry_gpa"));
                System.out.println("Math                       |" + rs.getDouble("math_gpa"));
                System.out.println("Computer                   |" + rs.getDouble("computer_gpa"));
                System.out.println("English                    |" + rs.getDouble("english_gpa"));
                double avgGPA = (rs.getDouble("physics_gpa") +
                        rs.getDouble("chemistry_gpa") +
                        rs.getDouble("math_gpa") +
                        rs.getDouble("computer_gpa") +
                        rs.getDouble("english_gpa")) / 5;

                System.out.printf("                           (Grade Point Average) GPA: %.2f%n", avgGPA);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


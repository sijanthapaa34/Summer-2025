package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Demo1 {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/student";
        String user = "root";
        String password = "sijan.@34";
        //String query = "select * from students";

        int id = 9;
        String name = "ram";
        int age= 19;
        String major = "math";
        //String query = "insert into students values(" +id+ ", '"+name+ "' ,"+ age+",'"+ major+"')";
        String query = "insert into students values(?,?,?,?)";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = null;
        Statement st = null;
try{
    con  = DriverManager.getConnection(url, user, password);
    st = con.createStatement();
//when inserting in lots of data in column use Prepared statement insteadof createStatement




    //ResultSet rs = st.executeQuery(query);

    //DDL Commands: CREATE, ALTER, DROP, TRUNCATE (executeUpdate()-return int,execute()-returns boolean),
    // DML Commands: INSERT, UPDATE, DELETE(executeUpdate-return int),
    // DQL Commands: SELECT (executeQuery-return object of ResultSet)

    int count = st.executeUpdate(query);
    System.out.println(count + "rows affected");
//    while(rs.next()){
////        int id = rs.getInt("student_id");
////        String name = rs.getString("name");
////        int age = rs.getInt("age");
////        String major = rs.getString("major");
//
//        String details = rs.getInt("student_id") +", "+ rs.getString("name")+", "+ rs.getInt("age")+ ", "+rs.getString("major");
//        System.out.println(details);

    //}
}
catch(Exception e){
    e.printStackTrace();
        }
finally {
    st.close();
    con.close();
}}
}

package JDBC;
/*
1.import ---. java.sql
2.load and register driver  --> com.mysql.jdbc.driver
3. create connection
4. create a statement
5. execute the query
6. process the result
7. close
 */

import java.sql.*;
public class Demo {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/demo_db";
        String uname = "root";
        String pass = "sijan.@34";
        //String query = "select name from users where id = 2";
        String query = "select id, name, age, email from users";

        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection con = DriverManager.getConnection(url,uname,pass);
//
//        Statement st = con.createStatement();//createStatement returns object of Statement
//        ResultSet rs = st.executeQuery(query); //ResultSet store data in table

        Connection con = null;
        Statement st = null;

        try {
            // Establish the connection to the database
            con = DriverManager.getConnection(url, uname, pass);
            // Create a statement object to execute the query
            st = con.createStatement();
            // Execute the query and get the result set
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {//.next() takes cursor to next value and checks if there is any data in column
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                System.out.println(id + " | " + name + " | " + age + " | " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

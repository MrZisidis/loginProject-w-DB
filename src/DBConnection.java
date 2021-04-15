/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author stefanos.zisidis
 */
public class DBConnection {

//    final static String className = "org.sqlite.JDBC";
//    final static String database = "users.db";
    /**
     * Connect to a sample database
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/users.db";    //jdbc:sqlite:sqlite_database_file_path
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * select all rows in the users table
     */
    public void selectAll() {
        System.out.println("Connecting to database...");
        String sql = "SELECT * FROM users;";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt("userID");
                String username = rs.getString("username");
                String pw = rs.getString("password");
                System.out.println("Printing the users details...");
                System.out.println(id + "\t" + username + "\t" + pw);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean findUser(String usr, String pwd) {
        String sql = "SELECT username,password FROM users WHERE username = ? AND password =  ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the value
            pstmt.setString(1, usr);
            pstmt.setString(2, pwd);
            //
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()){
                return true; 
                        }
            
//            String username = rs.getString("username");
//            String email = rs.getString("Email");
//            String pw = rs.getString("password");
//            int blacklist = rs.getInt("Blacklist");
//            String usertype = rs.getString("Usertype");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnection app = new DBConnection();
        app.selectAll();
    }
}

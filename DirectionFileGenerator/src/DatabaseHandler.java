

import javafx.application.Platform;
import org.h2.tools.Server;

import java.sql.*;
import java.util.LinkedList;

public class DatabaseHandler {

    public static String ServerIP = "127.0.0.1";
    public static String Port = "8082";
    public static String ClientIP = "";
    public static Server server;
    public static Connection conn;
    public static Statement stmt;


    public static boolean updateUsers() {
        try{
            //Run this block when running this in testing mode
            /*DatabaseHandler.stmt.executeUpdate("DELETE FROM USER WHERE UID='U191'");
            DatabaseHandler.stmt.executeUpdate("INSERT INTO USER VALUES('" +
                    Main.USER.getUID() + "', '" +
                    Main.USER.getPost() + "', '" +
                    Main.USER.getName() + "', '" +
                    Main.USER.getPassword() + "', '" +
                    Main.USER.getContact() + "', '" +
                    Main.USER.getHometown() + "' " +
                    ")");

             */
            ResultSet rs = DatabaseHandler.stmt.executeQuery("Select * from User;");
            System.out.println(rs);
            rs.first();
            System.out.println(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4));
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }



    public static int getCount(String table) {
        if(table.equalsIgnoreCase("User")){
            try {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(UID) FROM User;");
                if(rs.first()){
                    return rs.getInt(1);
                }else{
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(table.equalsIgnoreCase("Event")){
            try {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(EID) FROM Event;");
                if(rs.first()){
                    return rs.getInt(1);
                }else{
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(table.equalsIgnoreCase("Student")){
            try {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(SID) FROM Student;");
                if(rs.first()){
                    return rs.getInt(1);
                }else{
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

}

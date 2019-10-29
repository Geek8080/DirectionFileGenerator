import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the Post");
        String post = scan.nextLine().trim();
        try {
            Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers").start();
            Class.forName("org.h2.Driver");
            DatabaseHandler.conn = DriverManager.getConnection("jdbc:h2:tcp://127.0.0.1/~/Documents/Direction/Data/test", "", "");
            DatabaseHandler.stmt = DatabaseHandler.conn.createStatement();
            String sql = "SHOW TABLES";
            ResultSet resultSet = DatabaseHandler.stmt.executeQuery(sql);
            System.out.println("Executed the Statement");
            if(!resultSet.first()){
                FileReader reader = new FileReader("db.sql");
                System.out.println("Tables does not exist... Creating Tables");
                RunScript.execute(DatabaseHandler.conn, new FileReader("db.sql"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (post.equalsIgnoreCase("0")) {
            new AppView().main(null);
        } else {

            User us = new User();
            us.setPost(post);
            String res = "";
            String file = "user-" + post + ".ser";
            String dir = "/Direction/Data/Other/";
            res = dir + file;
            if (!new File(res).exists()) {
                System.out.println("Files does not exists... Making the directories");
                makeit(file);
            }
            try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(res));
                System.out.println(new File(res).getAbsoluteFile());
                os.writeObject(us);
                os.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            try {
                DatabaseHandler.stmt.executeUpdate("INSERT INTO User(UID, Post) VALUES('" +
                        us.getUID() + "', '" +
                        us.getPost() + "')");
                //String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_SCHEMA='test'";


            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(us.getUID());
            try {
                DatabaseHandler.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.exit(0);
        }
    }

    private static void makeit(String file) {
        File theDir = new File("/Direction/Data/Other");
        if (!theDir.exists()) {

            try {
                theDir.mkdirs();
                BufferedWriter out = new BufferedWriter(new FileWriter(file));
                out.close();
            } catch (Exception ex) {
                System.out.println("Some error occured while setting up the application...");
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }
    }
}

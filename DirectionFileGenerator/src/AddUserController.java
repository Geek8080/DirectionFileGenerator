import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;


public class AddUserController {

    @FXML
    private JFXTextField uid;

    @FXML
    private JFXTextField post;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField hometown;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton saveNext;
    /*
    FileReader reader = new FileReader("db.sql");

    RunScript.execute(DatabaseHandler.conn, new FileReader("db.sql"));
     */

    public void Save(MouseEvent evt){

        String Uid = uid.getText().trim();
        String Post = post.getText().trim();
        String Contact = contact.getText().trim();
        String Hometown = hometown.getText().trim();
        if(!(Uid.equals("")||Post.equals("")||Contact.equals("")||Hometown.equals(""))) {
            User us = new User(Uid);
            us.setPost(Post);
            String res = "";
            String file = "user-" + Post + ".ser";
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
                int a = DatabaseHandler.stmt.executeUpdate("INSERT INTO User(UID, Post, Contact, Hometown) VALUES('" +
                        Uid + "', '" +
                        Post + "', '" +
                        Contact + "', '" +
                        Hometown + "')");
                System.out.println(a + " Record Added...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (evt.getSource().equals(save)) {
                System.out.println("Save");
                ((Stage) save.getScene().getWindow()).close();
            } else {
                System.out.println("Save Next");
                uid.setText("");
                uid.requestFocus();
                post.setText("");
                contact.setText("");
                hometown.setText("");
            }
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

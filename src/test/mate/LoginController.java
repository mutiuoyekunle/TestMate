/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.mate;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import static test.mate.TESTMATE.theStage;

/**
 * FXML Controller class
 *
 * @author omobamhuteew and mubylee
 */

public class LoginController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField matric_no;
    @FXML
    private Label error;
  
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

    int a = 0;
    int dbNo = 10009;
    int i = 0; // useful to go back nd increase d no in d name of d database
    
    ResultSet rs;
    Statement smt3;
    Connection conn;
    Properties p = System.getProperties();  
    int no;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        connect();
    }    
    
    public void connect() {
        String dbURL = "org.apache.derby.jdbc.EmbeddedDriver";
             conn = null;
		
		try {
		Class.forName(dbURL).newInstance();
                    conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true");
                    p.put("derby.system.home", "c:/databases/testmateDB");
		} catch (Exception except) {
                    except.printStackTrace(); 
		}
		
                
                if (conn != null) {
                    rs = null;
                    smt3 = null;
                    a = 0;
                    try {

                    smt3 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

                    rs = smt3.executeQuery("select * from TestMateDB" +dbNo+ "");
                    rs.next();

                    }// end of try
                    catch (Exception e) {
                    e.printStackTrace();
                    }// end of catch

		}// end of if
    }

    @FXML
    public void Verify(ActionEvent event) {
        String Username = name.getText().trim();
        String Password = matric_no.getText().trim();


        if(Username.equals("")){
            error.setStyle("-fx-text-fill: red");
            error.setText("");
            error.setText("Username cannot be empty");
        }

        else if(Password.equals("")){
            error.setStyle("-fx-text-fill: red");
            error.setText("");
            error.setText("Matric_no cannot be empty");
        }

        else if(Username.equalsIgnoreCase("admin")&& Password.equalsIgnoreCase("admin")){

            try {
                conn.close();
                Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
                theStage.setTitle("TestMate");
                theStage.setScene(scene);
                theStage.show();

            } catch (IOException ex) {
                System.out.println("Exception :" + ex);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        else{
            try { 
                rs.updateString(3,rs.getString(3));
                rs.updateString(4,rs.getString(4));
                rs.updateString(5,rs.getString(5));
                rs.updateString(6,rs.getString(6));
                rs.updateString(7,rs.getString(7));
                rs.updateString(12,rs.getString(12));
                rs.updateString(13,rs.getString(13));

                
                rs.updateInt(1, Integer.parseInt( rs.getString(1) ) );
                rs.updateString(2,rs.getString(2));
                rs.updateString(8,Password);
                rs.updateInt(10, Integer.parseInt( rs.getString(10) ) );
                rs.updateInt(11, Integer.parseInt( rs.getString(11) ) );
                rs.updateString(9,Username);

                rs.updateRow();

                System.out.println("updated...");
                smt3.close();
                rs.close();
                conn.close();
            }
            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
            
            try {  
                
                Parent root = FXMLLoader.load(getClass().getResource("take_test.fxml"));
                Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
                theStage.setTitle("TestMate");
                theStage.setScene(scene);
                theStage.show();

            } catch (IOException ex) {
                System.out.println("Exception :" + ex);
            } 
            
        }
        
    }    
}

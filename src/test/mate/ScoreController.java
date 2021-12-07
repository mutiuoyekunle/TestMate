/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.mate;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ScoreController implements Initializable {
    @FXML
    private Label textTestName;
    @FXML
    private Label testP;

    int a = 0;
    int dbNo = 10020;
    int i = 0; // useful to go back nd increase d no in d name of d database
    
    ResultSet rs;
    Statement smt1;
    Statement smt2;
    Statement smt3;
    Connection conn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect();
        try {
            Take_test T1 = new Take_test();
            textTestName.setText("Congratulations, you just finished " + "your" /*rs.getString(2) */+ " TEST");
            testP.setText(rs.getString(13) + "%");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public void connect() {
        String dbURL = "org.apache.derby.jdbc.EmbeddedDriver";
             conn = null;
                Properties p = System.getProperties();
                p.put("derby.system.home", "C:/databases/TestmateDB");    
		try {
		Class.forName(dbURL).newInstance();
		conn = DriverManager.getConnection("jdbc:derby:"
		+ "derbyDB;create=true");
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
                    //e.printStackTrace();
                    }// end of catch

                    
		
		}// end of if
    }
    
    
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package test.mate;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import static test.mate.Add_questionController.curRow;
import static test.mate.TESTMATE.theStage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class Take_test implements Initializable {
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    @FXML
    private RadioButton optionA;
    @FXML
    private ToggleGroup option;
    @FXML
    private RadioButton optionB;
    @FXML
    private RadioButton optionC;
    @FXML
    private RadioButton optionD;
    
    public Label textOption_A;
    public Label textOption_B;
    public Label textOption_C;
    public Label textOption_D;
    public TextArea textQuestion;
    public Label testNumb;
    public Label time;
    
    URL url;
    ResourceBundle rb;
    Connection conn;
    ResultSet rs;
    Statement smt1;
    Statement smt2;
    Statement smt3;
    
    public int test = 35;
    int a = 0;
    int dbNo = 10020;
    int i = 0; // useful to go back nd increase d no in d name of d database
    int lastNo;
    //private float totalP=0.0f;
    int score=0;
    public float scoreP =0.0f;
    
    Boolean disable_btn = false;
    
    
    private Timeline timeline =  new Timeline();
    
    private int totalMins;
    private int min=1;
    private int hour=0;
    private static int startTimeSec =0, startTimeMin =5,startTimeHour =0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        String dbURL = "org.apache.derby.jdbc.EmbeddedDriver";
                conn = null;
		try {
		Class.forName(dbURL).newInstance();
		conn = DriverManager.getConnection("jdbc:derby:"
		+ "derbyDB;create=true");
		} catch (Exception except) {
		except.printStackTrace(); 
		}
                Properties p = System.getProperties();
                p.put("derby.system.home", "C:/databases/TestmateDB");    
                if (conn != null) {
		rs = null;
		smt1 = null;
		smt2 = null;
		smt3 = null;  
		a = 0;
                try {
                    smt3 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

                    /* Create a resultset for the time and get the fucking value */
                    rs = smt3.executeQuery("select * from TestMateDB" +dbNo);
                    rs.next();

                    //totalMins = Integer.parseInt(rs.getString(12));
                    //hour = totalMins/60;
                    //min = totalMins%60;

                    curRow = rs.getRow();
                    lastNo = getLastNo();

                    textQuestion.setText(rs.getString(3));
                    textOption_A.setText(rs.getString(4));
                    textOption_B.setText(rs.getString(5));
                    textOption_C.setText(rs.getString(6));
                    textOption_D.setText(rs.getString(7));
                    testNumb.setText( "Question " + rs.getString(1) +" out of " + lastNo);

                    }// end of try


                    catch (Exception e) {
                    e.printStackTrace();
              
		}// end of catch
               
		
		}// end of if
        
        
                
                
                
        
        if (!(startTimeMin < 0)) {
                startTimeSec = 60; // Change to 60!
                startTimeMin = min - 1;
                if (min == 0)
                    startTimeHour = hour -1;
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                    new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                        startTimeSec--;
                        boolean isSecondsZero = startTimeSec == 0;
                        boolean timeToChangeBackground = startTimeSec == 0 && startTimeMin == 0;
                        boolean oneHourF = startTimeSec == 0 && startTimeMin == 0 && startTimeHour == 0;
                        
                        if (isSecondsZero) {
                            startTimeMin--;
                            startTimeSec = 60;
                        }
                        if (timeToChangeBackground) {
                            startTimeHour--;
                            startTimeMin=60;
                            startTimeSec = 60;
                            

                        }
                        if (oneHourF) {
                            timeline.stop();
                            startTimeMin = 0;
                            startTimeSec = 0;
                            startTimeHour = 0;
                            try {
                                int adminA=rs.getInt(11);
                                int userA=rs.getInt(10);
                                rs.first();
                                lastNo = getLastNo();
                                int totalP =2*lastNo;

                                while(rs.next()) {
                                    curRow = rs.getRow();

                                    if( (userA == adminA) && userA!=0 && adminA!=0 ) {
                                        score += 2;
                                    }
                                    if (curRow == lastNo) break;
                                }
                                scoreP = (score/totalP)*100;


                            } catch (SQLException ex) {
                                Logger.getLogger(Take_test.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("score.fxml"));
                                Scene scene = new Scene(root,visualBounds.getWidth(), visualBounds.getHeight());
                                theStage.setTitle("TestMate");
                                theStage.setScene(scene);
                                theStage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(Take_test.class.getName()).log(Level.SEVERE, null, ex);
                            }
                
                            //System.exit(1);

                        }

                        time.setText(String.format("%d : %d : %02d ",startTimeHour,startTimeMin, startTimeSec));
                        
                    }
                }));
                
                timeline.playFromStart();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not entered a time!");
                alert.showAndWait();
            }        
                
                
    }    

    int getLastNo() {
        int no=0;
        try {
            rs.last();
            no = rs.getRow();
            rs.absolute(curRow);
        } catch (SQLException ex) {
            Logger.getLogger(Add_questionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return no;
    }
    
    public void alertbox(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Submit");
        alert.setHeaderText("Confirm submit");
        alert.setContentText("ARE YOU SURE YOU WANT TO SUBMIT?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //giveTotalP();
            try {
                try {
                    int adminA=rs.getInt(11);
                    int userA=rs.getInt(10);
                    rs.first();
                    lastNo = getLastNo();
                    int totalP =2*lastNo;
                           
                    while(rs.next()) {
                        curRow = rs.getRow();
                        
                        if( (userA == adminA) && userA!=0 && adminA!=0 ) {
                            score += 2;
                        }
                        if (curRow == lastNo) break;
                    }
                    scoreP = (score/totalP)*100;
                    //scoreP =35.6f;//for testing
                    //below update the score percentage in every row
                    while(rs.next()){
                        rs.updateString(13, String.valueOf(scoreP));
                        
                        rs.updateString(3,rs.getString(3));
                        rs.updateString(4,rs.getString(4));
                        rs.updateString(5,rs.getString(5));
                        rs.updateString(6,rs.getString(6));
                        rs.updateString(7,rs.getString(7));

                        rs.updateString(2,rs.getString(2));
                        rs.updateString(8,rs.getString(8));
                        rs.updateInt(10, 0);//refresh the userSelected column column
                        rs.updateInt(11, rs.getInt(11) );
                        rs.updateString(9,rs.getString(9));
                        rs.updateString(12,rs.getString(12));
                        rs.updateInt(1, rs.getInt(1));
                        rs.updateRow();
                    }
                    conn.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Take_test.class.getName()).log(Level.SEVERE, null, ex);
                }
                Parent root = FXMLLoader.load(getClass().getResource("score.fxml"));
                Scene scene = new Scene(root,visualBounds.getWidth(), visualBounds.getHeight());
                theStage.setTitle("TestMate");
                theStage.setScene(scene);
                theStage.show();
                    
            } catch (IOException ex) {
                System.out.println("Exception :" + ex);
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    } 
    
    
    
    public void btnNext() {
        if (disable_btn == false) {
            try { 
                
                if (optionA.isSelected() == true) {
                    rs.updateInt(10, 1 );
                }
                else if (optionB.isSelected() == true) {
                    rs.updateInt(10, 2 );
                }
                else if (optionC.isSelected() == true) {
                    rs.updateInt(10, 3 );
                }
                else if (optionD.isSelected() == true) {
                    rs.updateInt(10, 4 );
                }
                else {
                    rs.updateInt(10, 0 );
                }
                
                rs.updateString(3,rs.getString(3));
                rs.updateString(4,rs.getString(4));
                rs.updateString(5,rs.getString(5));
                rs.updateString(6,rs.getString(6));
                rs.updateString(7,rs.getString(7));

                rs.updateString(2,rs.getString(2));
                rs.updateString(8,rs.getString(8));
                rs.updateInt(1, rs.getInt(1) );
                rs.updateInt(11, rs.getInt(11) );
                rs.updateString(9,rs.getString(9));
                rs.updateString(12,rs.getString(12));
                rs.updateString(13, rs.getString(13));

                rs.updateRow();
                // don't forget to write the d program dat will save d file in a particular row e.g the first

                System.out.println("updated...");
            }

            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
            try { 
                if (rs.next() ) {
                    curRow = rs.getRow();
                    lastNo = getLastNo();
                    textQuestion.setText(rs.getString(3));
                    textOption_A.setText(rs.getString(4));
                    textOption_B.setText(rs.getString(5));
                    textOption_C.setText(rs.getString(6));
                    textOption_D.setText(rs.getString(7));
                    testNumb.setText("Question " + rs.getString(1) + " of "+ lastNo);
                    
                    if (Integer.parseInt(rs.getString(10)) == 1 ) {
                        optionA.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(10)) == 2 ) {
                        optionB.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(10)) == 3 ) {
                        optionC.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(10)) == 4 ) {
                        optionD.setSelected(true);
                    }
                    else{
                        optionA.setSelected(false);
                        optionB.setSelected(false);
                        optionC.setSelected(false);
                        optionD.setSelected(false);

                    }
                    
                    
                    
                }
                else { 
                    //btnPrev();
                    System.out.println("end of file");
                }
            }
            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
        }
    }
    
    public void btnPrev() {
        if (disable_btn == false) {
            try { 
                
                if (optionA.isSelected() == true) {
                    rs.updateInt(10, 1 );
                }
                else if (optionB.isSelected() == true) {
                    rs.updateInt(10, 2 );
                }
                else if (optionC.isSelected() == true) {
                    rs.updateInt(10, 3 );
                }
                else if (optionD.isSelected() == true) {
                    rs.updateInt(10, 4 );
                }
                else {
                    rs.updateInt(10, 0 );
                }
                rs.updateString(3,rs.getString(3));
                rs.updateString(4,rs.getString(4));
                rs.updateString(5,rs.getString(5));
                rs.updateString(6,rs.getString(6));
                rs.updateString(7,rs.getString(7));

                rs.updateString(2,rs.getString(2));
                rs.updateString(8,rs.getString(8));
                rs.updateInt(1, rs.getInt(1) );
                rs.updateInt(11, rs.getInt(11) );
                rs.updateString(9,rs.getString(9));
                rs.updateString(12,rs.getString(12));
                rs.updateString(13, rs.getString(13));

                rs.updateRow();
                // don't forget to write the d program dat will save d file in a particular row e.g the first

                System.out.println("updated...");
            }

            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
            try {
                if (rs.previous() ) {
                    curRow = rs.getRow();
                    lastNo = getLastNo();
                    textQuestion.setText(rs.getString(3));
                    textOption_A.setText(rs.getString(4));
                    textOption_B.setText(rs.getString(5));
                    textOption_C.setText(rs.getString(6));
                    textOption_D.setText(rs.getString(7));
                    testNumb.setText("Question " + rs.getString(1) + " of " + lastNo);
                    // below brings out what d user had clicked before
                    if (Integer.parseInt(rs.getString(10)) == 1 ) {
                        optionA.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(10)) == 2 ) {
                        optionB.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(10)) == 3 ) {
                        optionC.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(10)) == 4 ) {
                        optionD.setSelected(true);
                    }
                    else{
                        optionA.setSelected(false);
                        optionB.setSelected(false);
                        optionC.setSelected(false);
                        optionD.setSelected(false);

                    }
                }
                else { 
                    btnNext();
                    System.out.println("start of file");
                }
            }
            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
        }
    }
    
}

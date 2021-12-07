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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import static test.mate.TESTMATE.theStage;

/**
 * FXML Controller class
 *
 * @author OMOBAMHUTEEW
 */
public class Add_questionController implements Initializable {
    @FXML
    private Label iConn;
    @FXML
    private Label testNumb;
    @FXML
    private TextArea textQuestion;
    @FXML
    private TextField textOption_A;
    @FXML
    private TextField textOption_B;
    @FXML
    private TextField textOption_C;
    @FXML
    private TextField textOption_D;
    @FXML
    private TextField textTTimeH;
    @FXML
    private TextField textTTimeM;
    
    @FXML
    private RadioButton A;
    @FXML
    private ToggleGroup group;
    @FXML
    private RadioButton B;
    @FXML
    private RadioButton C;
    @FXML
    private RadioButton D;
    @FXML
    private Button iFirst;
    @FXML
    private Button iPrev;
    @FXML
    private Button iNext;
    @FXML
    private Button iLast;
    @FXML
    private Button iUpdate;
    @FXML
    private Button iDelete;
    @FXML
    private Button NQuestion;
    @FXML
    private Button SNQuestion;
    @FXML
    private Button CNQuestion;
    
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

    public Paint gray,blue,red,black;
    
    Connection conn;
    ResultSet rs;
    Statement smt1;
    Statement smt2;
    Statement smt3;
    static int curRow = 0;
    
    int a = 0;
    int dbNo = 10020;
    int i = 0; // useful to go back nd increase d no in d name of d database
    
    int no;
    int lastNo;//The total no of columns i.e the no of questions
    Boolean disable_btn = false;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get the color of this button for later use
        gray = CNQuestion.getTextFill();
        blue = iUpdate.getTextFill();
        red = iDelete.getTextFill();
        black = iNext.getTextFill();
        
        String dbURL = "org.apache.derby.jdbc.EmbeddedDriver";
        conn = null;
        Properties p = System.getProperties();
        p.put("derby.system.home", "C:/databases/TestmateDB");                 
        try {
            Class.forName(dbURL).newInstance();
            conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true");
        } catch (Exception except) {
            except.printStackTrace(); 
        }
        OptionChooserController optionChooser = new OptionChooserController();
        if(optionChooser.checkNewOrOld == 0) {
            connectNew();
        }
        else if (optionChooser.checkNewOrOld == 1) {
            connectOld();
        }

    }   
    public void connectNew() {
        
        if (conn != null) {
            iConn.setText("DB connected!");

            rs = null;
            smt1 = null;
            smt2 = null;
            smt3 = null;

            a = 0;

            try {

                smt1 = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

                smt2 = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

                smt3 = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);


                a = smt1.executeUpdate("CREATE TABLE TestMateDB"+ dbNo +" ("
                        + " ID                      INTEGER NOT NULL"
                        + " ,"//1
                        + " TestName                VARCHAR(20)," // 2
                        + " Question                VARCHAR(250)," // 3
                        + " Option_A                VARCHAR(100)," //4
                        + " Option_B                VARCHAR(100)," //5
                        + " Option_C                VARCHAR(100)," //6
                        + " Option_D                VARCHAR(100)," //7
                        + " Matric_No               VARCHAR(20)," //8
                        + " UserName                VARCHAR(30)," //9
                        + " radioSelectedUser       INTEGER," //10
                        + " radioSelectedAdmin      INTEGER," //11
                        + " minutes                 VARCHAR(10),"// 12
                        + " testPercent             VARCHAR(5)"//13
                        + ")"); // end of stmt1

                System.out.println("Table created");

                a = smt2.executeUpdate("insert into TestMateDB" +dbNo+ " values (1, 'test name...', 'question...', 'option A...', 'Option B...', 'Option C...', 'Option D...', 'Matric No ', 'Users name',0,0,'minutes','0'),"
                        + " (2, 'test name2...', 'question2...', 'option A2...', 'Option B2...', 'Option C2...', 'Option D2...', 'Matric No2 ', 'Users name2',1,1,'minutes2','1')");

                System.out.println("Values inserted");

                rs = smt3.executeQuery("select * from TestMateDB" + dbNo + "");

                rs.next();
                curRow = rs.getRow();
                lastNo = getLastNo();
                textQuestion.setText(rs.getString(3));
                textOption_A.setText(rs.getString(4));
                textOption_B.setText(rs.getString(5));
                textOption_C.setText(rs.getString(6));
                textOption_D.setText(rs.getString(7));
                testNumb.setText("Question " +curRow+" of "+lastNo+":");

                if (Integer.parseInt(rs.getString(11)) == 1 ) {
                    A.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 2 ) {
                    B.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 3 ) {
                    C.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 4 ) {
                    D.setSelected(true);
                }
                else{
                    A.setSelected(false);
                    B.setSelected(false);
                    C.setSelected(false);
                    D.setSelected(false);
                }
            }// end of try

            catch (Exception e) {
            //e.printStackTrace();
            //dbNo++;
            System.out.println(e.getMessage());

            }// end of catch
        }// end of if

    }
    public void connectOld() {
        
            if (conn != null) {
            iConn.setText("DB connected!");
            Properties p = System.getProperties();
            p.put("derby.system.home", "C:/databases/TestmateDB");    
            rs = null;
            smt1 = null;
            smt2 = null;
            smt3 = null;

            a = 0;

            try {  
                smt3 = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

                rs = smt3.executeQuery("select * from TestMateDB" + dbNo + "");

                rs.next();
                curRow = rs.getRow();
                lastNo = getLastNo();
                textQuestion.setText(rs.getString(3));
                textOption_A.setText(rs.getString(4));
                textOption_B.setText(rs.getString(5));
                textOption_C.setText(rs.getString(6));
                textOption_D.setText(rs.getString(7));
                testNumb.setText("Question " +curRow+" of "+lastNo+":");

                if (Integer.parseInt(rs.getString(11)) == 1 ) {
                    A.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 2 ) {
                    B.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 3 ) {
                    C.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 4 ) {
                    D.setSelected(true);
                }
                else{
                    A.setSelected(false);
                    B.setSelected(false);
                    C.setSelected(false);
                    D.setSelected(false);
                }

            }// end of try

            catch (Exception e) {
            //e.printStackTrace();
            //dbNo++;
            System.out.println(e.getMessage());
            }// end of catch

		}// end of if         
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

    @FXML
    private void btnFirst(ActionEvent event) {
        if (disable_btn == false) {
            try {    
                rs.first();
                curRow = rs.getRow();
                lastNo = getLastNo();
                textQuestion.setText(rs.getString(3));
                textOption_A.setText(rs.getString(4));
                textOption_B.setText(rs.getString(5));
                textOption_C.setText(rs.getString(6));
                textOption_D.setText(rs.getString(7));
                testNumb.setText("Question " +curRow+" of "+lastNo+":");  
                
                if (Integer.parseInt(rs.getString(11)) == 1 ) {
                    A.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 2 ) {
                    B.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 3 ) {
                    C.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 4 ) {
                    D.setSelected(true);
                }
                else{
                    A.setSelected(false);
                    B.setSelected(false);
                    C.setSelected(false);
                    D.setSelected(false);
                }
                
            }
            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
        }
    }

    @FXML
    private void btnPrev(ActionEvent event) {
        if (disable_btn == false) {
            try { 
                if (rs.previous() ) {
                    curRow = rs.getRow();
                    lastNo = getLastNo();
                    textQuestion.setText(rs.getString(3));
                    textOption_A.setText(rs.getString(4));
                    textOption_B.setText(rs.getString(5));
                    textOption_C.setText(rs.getString(6));
                    textOption_D.setText(rs.getString(7));
                    testNumb.setText("Question " +curRow+" of "+lastNo+":");

                    
                    if (Integer.parseInt(rs.getString(11)) == 1 ) {
                        A.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(11)) == 2 ) {
                        B.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(11)) == 3 ) {
                        C.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(11)) == 4 ) {
                        D.setSelected(true);
                    }
                    else{
                        A.setSelected(false);
                        B.setSelected(false);
                        C.setSelected(false);
                        D.setSelected(false);
                    }
                    
                }
                else { 
                    rs.next();
                    System.out.println("start of file");
                }
            }
            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
        }
    }

    @FXML
    private void btnNext(ActionEvent event) {
        if (disable_btn == false) {
            try {
               
                if (rs.next() ) {
                    curRow = rs.getRow();
                    lastNo = getLastNo();
                    textQuestion.setText(rs.getString(3));
                    textOption_A.setText(rs.getString(4));
                    textOption_B.setText(rs.getString(5));
                    textOption_C.setText(rs.getString(6));
                    textOption_D.setText(rs.getString(7));
                    testNumb.setText("Question " +curRow+" of "+lastNo+":");
                    
                    if (Integer.parseInt(rs.getString(11)) == 1 ) {
                        A.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(11)) == 2 ) {
                        B.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(11)) == 3 ) {
                        C.setSelected(true);
                    }
                    else if (Integer.parseInt(rs.getString(11)) == 4 ) {
                        D.setSelected(true);
                    }
                    else{
                        A.setSelected(false);
                        B.setSelected(false);
                        C.setSelected(false);
                        D.setSelected(false);
                    }
                    
               }   
            }
            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
        }
    }

    @FXML
    private void btnLast(ActionEvent event) {
        if (disable_btn == false) {
            try { 

                rs.last();
                curRow = rs.getRow();
                lastNo = getLastNo();
                textQuestion.setText(rs.getString(3));
                textOption_A.setText(rs.getString(4));
                textOption_B.setText(rs.getString(5));
                textOption_C.setText(rs.getString(6));
                textOption_D.setText(rs.getString(7));
                testNumb.setText("Question " +curRow+" of "+lastNo+":");  
                
                if (Integer.parseInt(rs.getString(11)) == 1 ) {
                    A.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 2 ) {
                    B.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 3 ) {
                    C.setSelected(true);
                }
                else if (Integer.parseInt(rs.getString(11)) == 4 ) {
                    D.setSelected(true);
                }
                else{
                    A.setSelected(false);
                    B.setSelected(false);
                    C.setSelected(false);
                    D.setSelected(false);
                }
                
            }
            catch (SQLException err) {
                System.out.print(err.getMessage());
            }   
        }
    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        if (disable_btn == false) {
            
            String question = textQuestion.getText();
            String optionA = textOption_A.getText();
            String optionB = textOption_B.getText();
            String optionC = textOption_C.getText();
            String optionD = textOption_D.getText();
            
            //int totalMins = Integer.parseInt(textTTimeH.getText())*60 + Integer.parseInt(textTTimeM.getText());
            // don't forget to write the code to test for the users mistake

            try { 
                
                rs.updateString(3,question);
                rs.updateString(4,optionA);
                rs.updateString(5,optionB);
                rs.updateString(6,optionC);
                rs.updateString(7,optionD);

                //rs.updateInt(1, rs );
                rs.updateString(2,rs.getString(2));
                rs.updateString(8,rs.getString(8));
                rs.updateInt(10, rs.getInt(10) );
                rs.updateInt(11, rs.getInt(11) );
                rs.updateString(9,rs.getString(9));
                rs.updateString(12,rs.getString(12));
                rs.updateString(13, rs.getString(13));

                if (A.isSelected() == true) {
                     rs.updateInt(11, 1 );
                }else if (B.isSelected() == true) {
                     rs.updateInt(11, 2 );
                }else if (C.isSelected() == true) {
                     rs.updateInt(11, 3 );
                }else if (D.isSelected() == true) {
                     rs.updateInt(11, 4 );
                }
                else {
                    rs.updateInt(11, Integer.parseInt( rs.getString(11) ) );
                }
                rs.updateRow();
                // don't forget to write the d program dat will save d file in a particular row e.g the first

                System.out.println("updated...");
            }

            catch (SQLException err) {
                System.out.print(err.getMessage());
            }
        }
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        if (disable_btn == false) {
            try {
               rs.deleteRow();
               smt3.close();
               rs.close();

               smt3 = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
               rs = smt3.executeQuery("select * from TestMateDB" +dbNo+ "");

               rs.next();
               curRow = rs.getRow();
               lastNo = getLastNo();
               textQuestion.setText(rs.getString(3));
               textOption_A.setText(rs.getString(4));
               textOption_B.setText(rs.getString(5));
               textOption_C.setText(rs.getString(6));
               textOption_D.setText(rs.getString(7));
               testNumb.setText("Question " +rs.getRow()+" of "+lastNo+":");

            } catch (SQLException ex) {
                Logger.getLogger(Add_questionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnNQuestion(ActionEvent event) {
        disable_btn = true;
                
        CNQuestion.setTextFill(red);
        SNQuestion.setTextFill(blue);
        iNext.setTextFill(gray);
        iUpdate.setTextFill(gray);
        iDelete.setTextFill(gray);
        iPrev.setTextFill(gray);
        iLast.setTextFill(gray);
        iFirst.setTextFill(gray);
        
        try {
            curRow = rs.getRow();
            lastNo = getLastNo();
            rs.last();
            no = rs.getRow()+1;
            
            
            textQuestion.setText("");
            textOption_A.setText("");
            textOption_B.setText("");
            textOption_C.setText("");
            textOption_D.setText("");
            testNumb.setText("Question " +String.valueOf(no)+" of "+(lastNo+1)+":");// then take d val of no to b d next possible id
            
            A.setSelected(false);
            B.setSelected(false);
            C.setSelected(false);
            D.setSelected(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(Add_questionController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    @FXML
    private void btnSNQuestion(ActionEvent event) {
        disable_btn = false;
        CNQuestion.setTextFill(gray);
        SNQuestion.setTextFill(gray);
        
        iNext.setTextFill(black);
        iUpdate.setTextFill(blue);
        iDelete.setTextFill(red);
        iPrev.setTextFill(black);
        iLast.setTextFill(black);
        iFirst.setTextFill(black);
        
        String question = textQuestion.getText();
        String optionA = textOption_A.getText();
        String optionB = textOption_B.getText();
        String optionC = textOption_C.getText();
        String optionD = textOption_D.getText();
        
        try {
           curRow = rs.getRow();
           lastNo = getLastNo();
           rs.moveToInsertRow();
           
           rs.updateString(3,question);
           rs.updateString(4,optionA);
           rs.updateString(5,optionB);
           rs.updateString(6,optionC);
           rs.updateString(7,optionD);
           
           rs.updateInt(1, lastNo +1 );
           rs.updateString(2,rs.getString(2));
           rs.updateString(8,rs.getString(8));
           rs.updateInt(10, rs.getInt(10) );
           rs.updateInt(11, rs.getInt(11) );
           rs.updateString(9,rs.getString(9));
           rs.updateString(12,rs.getString(12));
           rs.updateString(13, rs.getString(13));
           
           if (A.isSelected() == true) {
                rs.updateInt(11, 1 );
           }else if (B.isSelected() == true) {
                rs.updateInt(11, 2 );
           }else if (C.isSelected() == true) {
                rs.updateInt(11, 3 );
           }else if (D.isSelected() == true) {
                rs.updateInt(11, 4 );
           }
           else {
               rs.updateInt(11, Integer.parseInt( rs.getString(11) ) );
           }
           
           rs.insertRow();
           
           smt3.close();
           rs.close();

            smt3 = conn.createStatement(
                 ResultSet.TYPE_SCROLL_INSENSITIVE,
                 ResultSet.CONCUR_UPDATABLE);

            rs = smt3.executeQuery("select * from TestMateDB" +dbNo+ "");

            rs.last();
            curRow = rs.getRow();
            lastNo = getLastNo();
            textQuestion.setText(rs.getString(3));
            textOption_A.setText(rs.getString(4));
            textOption_B.setText(rs.getString(5));
            textOption_C.setText(rs.getString(6));
            textOption_D.setText(rs.getString(7));
            testNumb.setText("Question " +rs.getRow()+" of "+lastNo+":");
           
           
           
        } catch (SQLException ex) {
            Logger.getLogger(Add_questionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCNQuestion(ActionEvent event) {
        disable_btn = false;
        CNQuestion.setTextFill(gray);
        SNQuestion.setTextFill(gray);
        
        iNext.setTextFill(black);
        iUpdate.setTextFill(blue);
        iDelete.setTextFill(red);
        iPrev.setTextFill(black);
        iLast.setTextFill(black);
        iFirst.setTextFill(black);
        
        try {
            rs.absolute( curRow );// abeg move am back to where it is
            
            textQuestion.setText(rs.getString(3));
            textOption_A.setText(rs.getString(4));
            textOption_B.setText(rs.getString(5));
            textOption_C.setText(rs.getString(6));
            textOption_D.setText(rs.getString(7));
            testNumb.setText("Question " +rs.getRow()+" of "+getLastNo()+":"); 
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Add_questionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void btnBTLPage() {
        
        try {
            conn.close();
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
            theStage.setTitle("TestMate");
            theStage.setScene(scene);
            theStage.show();

        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        } catch (SQLException ex) {
            Logger.getLogger(Add_questionController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}

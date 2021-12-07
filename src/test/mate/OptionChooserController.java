/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.mate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import static test.mate.TESTMATE.theStage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class OptionChooserController implements Initializable {
    public static int checkNewOrOld;
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    public void newDB() {
        checkNewOrOld = 0;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("add_question.fxml"));
            Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
            theStage.setTitle("TestMate");
            theStage.setScene(scene);
            theStage.show();

        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
    
    public void oldDB() {
        checkNewOrOld = 1;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("add_question.fxml"));
            Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
            theStage.setTitle("TestMate");
            theStage.setScene(scene);
            theStage.show();

        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }
    
}

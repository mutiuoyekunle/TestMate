package test.mate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import java.io.IOException;

import static test.mate.TESTMATE.theStage;

public class Admin {
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

    @FXML
    HBox manage_test_result,manage_question,manage_student,add_question,manage_profile,manage_subject;


    @FXML
    public void test_result(){try {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        theStage.setTitle("TestMate");
        theStage.setScene(scene);
        theStage.show();

    } catch (IOException ex) {
        System.out.println("Exception :" + ex);
    }}
    @FXML
    public void manage_question(){try {
        Parent root = FXMLLoader.load(getClass().getResource("manage_question.fxml"));
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        theStage.setTitle("TestMate");
        theStage.setScene(scene);
        theStage.show();

    } catch (IOException ex) {
        System.out.println("Exception :" + ex);
    }}
    @FXML
    public void manage_student(){try {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        theStage.setTitle("TestMate");
        theStage.setScene(scene);
        theStage.show();

    } catch (IOException ex) {
        System.out.println("Exception :" + ex);
    }}
    @FXML
    public void add_question(){try {
        Parent root = FXMLLoader.load(getClass().getResource("optionChooser.fxml"));
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        theStage.setTitle("TestMate");
        theStage.setScene(scene);
        theStage.show();

    } catch (IOException ex) {
        System.out.println("Exception :" + ex);
    }}
    
    @FXML
    public void manage_profile(){try {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        theStage.setTitle("TestMate");
        theStage.setScene(scene);
        theStage.show();

    } catch (IOException ex) {
        System.out.println("Exception :" + ex);
    }}
    @FXML
    public void manage_subject(){try {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        theStage.setTitle("TestMate");
        theStage.setScene(scene);
        theStage.show();

    } catch (IOException ex) {
        System.out.println("Exception :" + ex);
    }}


}


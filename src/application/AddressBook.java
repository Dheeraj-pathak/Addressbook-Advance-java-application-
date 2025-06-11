package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddressBook extends Application {

   @Override
   public void start(Stage stage) throws Exception {
      // Correct the FXML loader syntax
	   FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AddressBook/AddressBook.fxml"));


      
      // Load the FXML file
      Parent root = loader.load();
      
      // Set the scene and show the stage
      Scene scene = new Scene(root);
      stage.setTitle("Address Book");
      stage.setScene(scene);
      stage.show();
   }

   public static void main(String[] args) {
      launch(args);
}}

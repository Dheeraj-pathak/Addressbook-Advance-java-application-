package controller;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Person;
import database.PersonQueries;

public class AddressBookController {
   @FXML private ListView<Person> listView; // displays contact names
   @FXML private TextField firstNameTextField;
   @FXML private TextField lastNameTextField;
   @FXML private TextField emailTextField;
   @FXML private TextField phoneTextField;
   @FXML private TextField addressTextField;
   @FXML private ComboBox<String> departmentComboBox;  // Updated to ComboBox
   @FXML private TextField findByLastNameTextField;

   // interacts with the database
   private final PersonQueries personQueries = new PersonQueries();

   // stores list of Person objects that result from a database query
   private final ObservableList<Person> contactList = 
      FXCollections.observableArrayList();
   
   // Department values (matching ENUM in database)
   private final ObservableList<String> departments = 
      FXCollections.observableArrayList("Mkt", "HR", "Finance", "Admin");

   // populate listView and set up listener for selection events
   public void initialize() {
      listView.setItems(contactList); // bind to contactsList
      departmentComboBox.setItems(departments); // Set department choices
      getAllEntries(); // populates contactList, which updates listView 

      // when ListView selection changes, display selected person's data
      listView.getSelectionModel().selectedItemProperty().addListener(
         (observableValue, oldValue, newValue) -> {
            displayContact(newValue);
         }
      );     
   }

   // get all the entries from the database to populate contactList
   private void getAllEntries() {
      contactList.setAll(personQueries.getAllPeople()); 
      selectFirstEntry();
   }

   // select first item in listView
   private void selectFirstEntry() {
      listView.getSelectionModel().selectFirst();          
   }

   // display contact information
   private void displayContact(Person person) {
      if (person != null) {
         firstNameTextField.setText(person.getFirstName());
         lastNameTextField.setText(person.getLastName());
         emailTextField.setText(person.getEmail());
         phoneTextField.setText(person.getPhoneNumber());
         addressTextField.setText(person.getAddress());
         departmentComboBox.setValue(person.getDepartment()); // Update ComboBox
      }
      else {
         firstNameTextField.clear();
         lastNameTextField.clear();
         emailTextField.clear();
         phoneTextField.clear();
         addressTextField.clear();
         departmentComboBox.getSelectionModel().clearSelection();
      }
   }

   // add a new entry
   @FXML
   void addEntryButtonPressed(ActionEvent event) {
      String department = departmentComboBox.getValue();
      if (department == null) {
         displayAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a department.");
         return;
      }

      int result = personQueries.addPerson(
         firstNameTextField.getText(), lastNameTextField.getText(), 
         emailTextField.getText(), phoneTextField.getText(),
         addressTextField.getText(), department);                                     
      
      if (result == 1) {
         displayAlert(Alert.AlertType.INFORMATION, "Entry Added", 
            "New entry successfully added.");
      }
      else {
         displayAlert(Alert.AlertType.ERROR, "Entry Not Added", 
            "Unable to add entry.");
      }
      
      getAllEntries();
   }

   // find entries with the specified last name
   @FXML
   void findButtonPressed(ActionEvent event) {
      List<Person> people = personQueries.getPeopleByLastName(
         findByLastNameTextField.getText() + "%");

      if (people.size() > 0) { // display all entries
         contactList.setAll(people);
         selectFirstEntry();
      }
      else {
         displayAlert(Alert.AlertType.INFORMATION, "Lastname Not Found", 
            "There are no entries with the specified last name.");
      }
   }

   // browse all the entries
   @FXML
   void browseAllButtonPressed(ActionEvent event) {
      getAllEntries();
   }

   // display an Alert dialog
   private void displayAlert(Alert.AlertType type, String title, String message) {
      Alert alert = new Alert(type);
      alert.setTitle(title);
      alert.setContentText(message);
      alert.showAndWait();
   }
}

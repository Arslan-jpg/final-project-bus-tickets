package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminControl {
	 
	
	  private Stage stage; 
	  private Scene scene; 
	  private Parent root;
	 
	
	  @FXML
		private void citiesButtonClicked(ActionEvent event) throws IOException {
			
			  root = FXMLLoader.load(getClass().getResource("Cities.fxml")); 
			  stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			  scene = new Scene(root); 
			  stage.setScene(scene); 
			  stage.show();
		}
	  
	  @FXML
		private void usersButtonClicked(ActionEvent event) throws IOException {
			
			  root = FXMLLoader.load(getClass().getResource("Users.fxml")); 
			  stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			  scene = new Scene(root); 
			  stage.setScene(scene); 
			  stage.show();
		}
	  
	  @FXML
		private void companiesButtonClicked(ActionEvent event) throws IOException {
			
			  root = FXMLLoader.load(getClass().getResource("Companies.fxml")); 
			  stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			  scene = new Scene(root); 
			  stage.setScene(scene); 
			  stage.show();
		}
	  
	  @FXML
		private void busLinesButtonClicked(ActionEvent event) throws IOException {
			
			  root = FXMLLoader.load(getClass().getResource("BusLines.fxml")); 
			  stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			  scene = new Scene(root); 
			  stage.setScene(scene); 
			  stage.show();
		}
	  
	  @FXML
		private void scheduleButtonClicked(ActionEvent event) throws IOException {
			
			  root = FXMLLoader.load(getClass().getResource("Schedule.fxml")); 
			  stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			  scene = new Scene(root); 
			  stage.setScene(scene); 
			  stage.show();
		}
	  
	  @FXML
		private void dateButtonClicked(ActionEvent event) throws IOException {
			
			  root = FXMLLoader.load(getClass().getResource("DateSchedule.fxml")); 
			  stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			  scene = new Scene(root); 
			  stage.setScene(scene); 
			  stage.show();
		}
	  
	
}
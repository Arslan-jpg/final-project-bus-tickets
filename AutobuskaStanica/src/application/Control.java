package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Control{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	TextField txtUser, txtPassword;
	
	@FXML
	CheckBox cbAdmin;
	
	@FXML
	Label lblCheck;

	@FXML
	private void loginButtonClicked(ActionEvent event) throws IOException {
		
		int adm = cbAdmin.isSelected() ? 1 : 0;
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("select * from korisnici WHERE BINARY ime='" + txtUser.getText() + "' AND BINARY sifra='" + txtPassword.getText() + "' AND admin='" + adm + "'");
			
			if (rs.next()) {
				lblCheck.setText("Uspješna autentifikacija");
				if (adm == 1) {
					root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
				} else {
					root = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
				}
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			else {
				lblCheck.setText("Pogreška");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
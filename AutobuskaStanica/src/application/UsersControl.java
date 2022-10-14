package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class UsersControl implements Initializable{

	@FXML
	Label lblError;
	
	@FXML
	TableView<Users> tblUser;
	
	@FXML
	TableColumn<Users, String> colIdUser, colNameUser, colPassUser, colPrivilegeUser;
	
	@FXML
	TextField txtSelectedUser, txtInputUser, txtInputUserPass, txtInputUserPass2;
	
	@FXML
	CheckBox chkPrivilege;
	
	ObservableList<Users> obuser = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colIdUser.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNameUser.setCellValueFactory(new PropertyValueFactory<>("ime"));
		colPassUser.setCellValueFactory(new PropertyValueFactory<>("pass"));
		colPrivilegeUser.setCellValueFactory(new PropertyValueFactory<>("privilege"));
		
		userTableLoad();
		
	}
	
	@FXML
	private void userTableSelect(MouseEvent event) {
		Users user = tblUser.getSelectionModel().getSelectedItem();
		txtSelectedUser.setText(user.getId());
	}
	
	private void userTableLoad() {
		
		tblUser.getItems().clear();
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM stanicatuzla.korisnici;");
			
			while (rs.next()) {
				obuser.add(new Users(rs.getString("idkorisnici"), rs.getString("ime"), rs.getString("sifra"), rs.getString("admin")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tblUser.setItems(obuser);
	}
	
	@FXML
	private void userBackButton(ActionEvent event) {
		LoadPanel load = new LoadPanel();
		load.load(event);
	}
	
	@FXML
	private void userInsert(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			if (txtInputUserPass.getText().equals(txtInputUserPass2.getText())) {
			con.createStatement().executeUpdate("INSERT INTO stanicatuzla.korisnici(ime, sifra, admin) VALUES('" + txtInputUser.getText() +"'"
					+ ", '" + txtInputUserPass.getText() +"', " + chkPrivilege.isSelected() + ");");
			userTableLoad();
			} else {
				lblError.setText("Pogreška u kreiranju lozinke");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void userDelete(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("DELETE FROM stanicatuzla.korisnici WHERE idkorisnici = '" + txtSelectedUser.getText() + "'");
			userTableLoad();
			txtSelectedUser.setText(null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

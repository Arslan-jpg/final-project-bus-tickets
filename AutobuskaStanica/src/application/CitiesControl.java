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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CitiesControl implements Initializable{

	@FXML
	TableView<Cities> tblCities;
	
	@FXML
	TableColumn<Cities, String> colIdCities, colNameCities;
	
	@FXML
	TextField txtSelectedCity, txtInputCity;
	
	ObservableList<Cities> obcities = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colIdCities.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNameCities.setCellValueFactory(new PropertyValueFactory<>("ime"));
		
		citiesTableLoad();
		
	}
	
	@FXML
	private void citiesTableSelect(MouseEvent event) {
		Cities city = tblCities.getSelectionModel().getSelectedItem();
		if (city == null) {
			citiesClearSelection();
			txtSelectedCity.clear();
		} else {
		txtSelectedCity.setText(city.getId());
		}
	}
	
	private void citiesTableLoad() {
		
		tblCities.getItems().clear();
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM stanicatuzla.gradovi;");
			
			while (rs.next()) {
				obcities.add(new Cities(rs.getString("idGradovi"), rs.getString("imeGrada")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tblCities.setItems(obcities);
	}
	
	@FXML
	private void citiesSelectedTextC(MouseEvent event) {
		citiesClearSelection();
	}
	
	private void citiesClearSelection() {
		tblCities.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void citiesBackButton(ActionEvent event) {
		LoadPanel load = new LoadPanel();
		load.load(event);
	}
	
	@FXML
	private void citiesInsert(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("INSERT INTO stanicatuzla.gradovi(imeGrada) VALUES('" + txtInputCity.getText() +"');");
			citiesTableLoad();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void citiesDelete(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("DELETE FROM stanicatuzla.gradovi WHERE idGradovi = '" + txtSelectedCity.getText() + "'");
			citiesTableLoad();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

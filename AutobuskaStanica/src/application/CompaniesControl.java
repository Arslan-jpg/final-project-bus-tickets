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

public class CompaniesControl implements Initializable{
	
	@FXML
	TableView<Companies> tblCompany;
	
	@FXML
	TableColumn<Companies, String> colIdCompany, colNameCompany;
	
	@FXML
	TextField txtCompany, txtSelectedCompany;
	
	ObservableList<Companies> obcomp = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colIdCompany.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNameCompany.setCellValueFactory(new PropertyValueFactory<>("ime"));
		
		companyTableLoad();
	}
	
	private void companyTableLoad() {
			
			tblCompany.getItems().clear();
			
			try {
				Connection con = DBConnector.getConnection();
				
				ResultSet rs = con.createStatement().executeQuery("SELECT * FROM stanicatuzla.prijevoznici;");
				
				while (rs.next()) {
					obcomp.add(new Companies(rs.getString("idprijevoznici"), rs.getString("imePrijevoznik")));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tblCompany.setItems(obcomp);
		}
	
	@FXML
	private void companyBackButton(ActionEvent event) {
		LoadPanel load = new LoadPanel();
		load.load(event);
	}
	
	@FXML
	private void companyInsert(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("INSERT INTO stanicatuzla.prijevoznici(imePrijevoznik) VALUES('" + txtCompany.getText() +"');");
			companyTableLoad();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void companyTableSelect(MouseEvent event) {
		Companies comp = tblCompany.getSelectionModel().getSelectedItem();
		txtSelectedCompany.setText(comp.getId());
	}
	
	@FXML
	private void companyDelete(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("DELETE FROM stanicatuzla.prijevoznici WHERE idprijevoznici = '" + txtSelectedCompany.getText() + "'");
			companyTableLoad();
			txtSelectedCompany.setText(null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class BusLinesControl implements Initializable{
	
	@FXML
	TableView<BusLines> tblBusLines;
	
	@FXML
	TableColumn<BusLines, String> colNum, colStart, colEnd, colThrough;
	
	@FXML
	ComboBox<String> cbStart, cbEnd;
	
	@FXML
	ListView<String> lvCities, lvThrough;
	
	@FXML
	Button btnLeft;
	
	@FXML
	TextField txtLineNumber, txtSelectedLine;
	
	@FXML
	Label lblError;
	
	ObservableList<BusLines> obline = FXCollections.observableArrayList();
	ObservableList<String> combo = FXCollections.observableArrayList(); 
	
	ObservableList<String> cities = FXCollections.observableArrayList(); 
	ObservableList<String> through = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colNum.setCellValueFactory(new PropertyValueFactory<>("id"));
		colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
		colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
		colThrough.setCellValueFactory(new PropertyValueFactory<>("through"));
		
		linesTableLoad();
		citiesLoad();
		
		btnLeft.disableProperty().bind(lvCities.getSelectionModel().selectedItemProperty().isNull());
		
	}
	
	private void linesTableLoad() {
			
			tblBusLines.getItems().clear();
			
			try {
				Connection con = DBConnector.getConnection();
				
				ResultSet rs = con.createStatement().executeQuery("SELECT idLinije, p.imeGrada AS polaziste, o.imeGrada AS odrediste, kroz FROM linije "
						+ "INNER JOIN gradovi p ON polazisteFK=p.idGradovi INNER JOIN gradovi o ON odredisteFK=o.idGradovi;");
				
				while (rs.next()) {
					obline.add(new BusLines(rs.getString("idLinije"), rs.getString("polaziste"), rs.getString("odrediste"), rs.getString("kroz")));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tblBusLines.setItems(obline);
		}

	private void citiesLoad() {
		
		cbStart.getItems().clear();
		cbEnd.getItems().clear();
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM stanicatuzla.gradovi;");
			
			while (rs.next()) {
				combo.add(new String(rs.getString("imeGrada")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cities = combo;
		cbStart.setItems(combo);
		cbEnd.setItems(combo);
		lvCities.setItems(cities);
		
		cbStart.getSelectionModel().select(0);
	}
	
	@FXML
	private void lineInsert() {
		
		if (cbEnd.getSelectionModel().isEmpty()) {
			lblError.setText("Odaberite odrediste!");
		} else if (txtLineNumber.getText() == "") {
			lblError.setText("Unesite redni broj linije!");
		}
		else {
			
			String joined = lvThrough.getItems().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
			
			try {
				Connection con = DBConnector.getConnection();
				
				con.createStatement().executeUpdate("INSERT INTO stanicatuzla.linije(idLinije, polazisteFK, odredisteFK, kroz)"
						+ " VALUES (" + txtLineNumber.getText() + ", (SELECT idGradovi FROM stanicatuzla.gradovi WHERE imeGrada = '" + cbStart.getSelectionModel().getSelectedItem() + "'),"
								+ " (SELECT idGradovi FROM stanicatuzla.gradovi WHERE imeGrada = '" + cbEnd.getSelectionModel().getSelectedItem() + "'), '" + joined + "');");
				linesTableLoad();
				lblError.setText(null);
			} catch (SQLException e) {
				e.printStackTrace();
				if (e.getErrorCode() == 1062) {
					lblError.setText("Linija sa rednim brojem '" + txtLineNumber.getText() + "' vec postoji!");
				}
			}
		}
		
	}
	
	@FXML
	private void lineDelete(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("DELETE FROM stanicatuzla.linije WHERE idlinije = '" + txtSelectedLine.getText() + "'");
			linesTableLoad();
			txtSelectedLine.setText(null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void btnAddLeft() {
		
		if(!through.contains(lvCities.getSelectionModel().getSelectedItem())) {
			
			through.add(lvCities.getSelectionModel().getSelectedItem());
			cities.remove(lvCities.getSelectionModel().getSelectedItem());
			lvThrough.setItems(through);
			
		}
	}
	
	@FXML
	private void btnClear() {
		cities.addAll(through);
		through.clear();
	}
	
	@FXML
	private void btnPop() {
		if (through.size() > 0) {
			cities.add(through.get(through.size()-1));
			through.remove(through.size()-1);
		}
	}
	
	@FXML
	private void lineTableSelect(MouseEvent event) {
		BusLines line = tblBusLines.getSelectionModel().getSelectedItem();
		txtSelectedLine.setText(line.getId());
	}
		
	@FXML
	private void lineBackButton(ActionEvent event) {
		LoadPanel load = new LoadPanel();
		load.load(event);
	}
}
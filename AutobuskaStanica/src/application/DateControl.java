package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DateControl implements Initializable{

	@FXML
	DatePicker dtpDate;
	
	@FXML
	Label lblCheck;
	
	@FXML
	Button btnDateCreate, btnBack, btnDateDelete;
	
	@FXML
	TableView<DateItem> tblDateS;
	
	@FXML
	TableColumn<DateItem, String> colRide, colSchedule, colDate;
	
	ObservableList<DateItem> obd = FXCollections.observableArrayList();
	
	String[] days = {"ned", "pon", "uto", "sri", "cet", "pet", "sub"};
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colRide.setCellValueFactory(new PropertyValueFactory<>("idRide"));
		colSchedule.setCellValueFactory(new PropertyValueFactory<>("idSch"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		lblCheck.setAlignment(Pos.CENTER);
	}
	
	public void tableLoad(Date date) {
		
		obd.clear();
		tblDateS.setItems(null);
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM stanicatuzla.voznja WHERE datum='" + date.toString() + "';");
			
			if (!rs.isBeforeFirst()) {
				lblCheck.setText("Bez podataka za odabrani datum");
				lblCheck.setStyle("-fx-text-fill: red");
				btnDateCreate.setDisable(false);
				btnDateDelete.setDisable(true);
			} else {
				btnDateCreate.setDisable(true);
				btnDateDelete.setDisable(false);
				while (rs.next()) {
					obd.add(new DateItem(rs.getString("idvoznja"), rs.getString("rasporedFK"), rs.getString("datum")));
					lblCheck.setText("Podaci sa odabranim datumom pronadeni");
					lblCheck.setStyle("-fx-text-fill: green");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tblDateS.setItems(obd);
		
	}
	
	@FXML
	private void checkClicked(ActionEvent event) {
		
		Date date = Date.valueOf(dtpDate.getValue().toString());
		
		tableLoad(date);
		
	}
	
	@FXML
	private void dateAddClicked(ActionEvent event) {
		
		Date date = Date.valueOf(dtpDate.getValue().toString());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		try {
			Connection con = DBConnector.getConnection();
			con.createStatement().executeUpdate("INSERT INTO voznja (rasporedFK, datum)\r\n"
					+ "SELECT rasporedsedmica.idrasporedSedmica, '" + date + "'\r\n"
					+ "FROM rasporedsedmica\r\n"
					+ "WHERE rasporedsedmica." + days[cal.get(Calendar.DAY_OF_WEEK)-1] + " = 'X'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tableLoad(date);
		
	}
	
	@FXML
	private void dateDeleteClicked(ActionEvent event) {
		
		Date date = Date.valueOf(dtpDate.getValue().toString());
		
		try {
			Connection con = DBConnector.getConnection();
			con.createStatement().executeUpdate("DELETE FROM voznja WHERE datum = '" + date + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tableLoad(date);
		
	}
	
	@FXML
	private void dateBackButton(ActionEvent event) {
		LoadPanel load = new LoadPanel();
		load.load(event);
	}
	
	
}

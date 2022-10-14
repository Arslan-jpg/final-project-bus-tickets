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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AppUserControl implements Initializable{
	
	@FXML
	TextField txtDestination, txtSelected, txtName, txtTicket, txtSelectedT;
	
	@FXML
	TableView<ScheduleItem> tblSchedule;
	
	@FXML
	TableView<Ticket> tblTickets;
	
	@FXML
	TableColumn<ScheduleItem, String> colDestination, colLine, colStart, colReturn, colCompany, colMon, colTue, colWed, colThu, colFri, colSat, colSun, colHol;
	
	@FXML
	TableColumn<Ticket, String> colTicket, colTicketRide, colName, colTicketDate;	
	
	@FXML
	CheckBox cbDayOfWeek, cbReturn;
	
	@FXML
	Label lblInfo;
	
	@FXML
	ComboBox<String> cmbDaysOfWeek;
	
	@FXML
	DatePicker dtpDate, dtpDate2, dtpTicket;
	
	ObservableList<String> combo = FXCollections.observableArrayList();
	ObservableList<ScheduleItem> obs = FXCollections.observableArrayList();
	ObservableList<Ticket> obt = FXCollections.observableArrayList();
	
	String[] days = {"ned", "pon", "uto", "sri", "cet", "pet", "sub"};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		combo.add("pon");
		combo.add("uto");
		combo.add("sri");
		combo.add("cet");
		combo.add("pet");
		combo.add("sub");
		combo.add("ned");
		combo.add("praznik");
		
		cmbDaysOfWeek.setItems(combo);
		
		colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
		colLine.setCellValueFactory(new PropertyValueFactory<>("line"));
		colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		colReturn.setCellValueFactory(new PropertyValueFactory<>("returnTime"));
		colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
		colMon.setCellValueFactory(new PropertyValueFactory<>("mon"));
		colTue.setCellValueFactory(new PropertyValueFactory<>("tue"));
		colWed.setCellValueFactory(new PropertyValueFactory<>("wed"));
		colThu.setCellValueFactory(new PropertyValueFactory<>("thu"));
		colFri.setCellValueFactory(new PropertyValueFactory<>("fri"));
		colSat.setCellValueFactory(new PropertyValueFactory<>("sat"));
		colSun.setCellValueFactory(new PropertyValueFactory<>("sun"));
		colHol.setCellValueFactory(new PropertyValueFactory<>("hol"));
		
		colTicket.setCellValueFactory(new PropertyValueFactory<>("idticket"));
		colTicketRide.setCellValueFactory(new PropertyValueFactory<>("ride"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colTicketDate.setCellValueFactory(new PropertyValueFactory<>("date"));
	}
	
	@FXML
	private void searchClicked(ActionEvent event) {
		
		tblSchedule.getItems().clear();
		lblInfo.setText(null);
		
		Date date;
		Calendar cal = Calendar.getInstance();
		
		String query = "SELECT idrasporedSedmica, gradovi.imeGrada, linijaFK, vrPolaska, vrPovratka, prijevoznici.imePrijevoznik, pon, uto, sri, cet, pet, sub, ned, praznik\r\n"
				+ "FROM rasporedsedmica \r\n"
				+ "INNER JOIN linije ON linijaFK = linije.idLinije\r\n"
				+ "INNER JOIN gradovi ON linije.odredisteFK = gradovi.idGradovi\r\n"
				+ "INNER JOIN prijevoznici ON prevoznikFK=prijevoznici.idprijevoznici ";
		
		if(txtDestination.getText() != null || !txtDestination.getText().trim().isEmpty()) {
			query += "WHERE imeGrada = '" + txtDestination.getText().toString() + "' OR linije.kroz LIKE '%" + txtDestination.getText().toString() + "%'";
			if(dtpDate.getValue() != null) {
				date = Date.valueOf(dtpDate.getValue().toString());
				cal.setTime(date);
				query += "AND " + days[cal.get(Calendar.DAY_OF_WEEK) - 1] + "='X'";
			} else if (!cmbDaysOfWeek.getSelectionModel().isEmpty()) {
				query += "AND " + cmbDaysOfWeek.getValue() + "='X'";
			}
		}
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery(query);
			
			while (rs.next()) {
				obs.add(new ScheduleItem(rs.getString("idrasporedSedmica"), rs.getString("imeGrada"), rs.getString("linijaFK"), rs.getString("vrPolaska"), rs.getString("vrPovratka"), rs.getString("imePrijevoznik"), rs.getString("pon").charAt(0), rs.getString("uto").charAt(0), rs.getString("sri").charAt(0), rs.getString("cet").charAt(0), rs.getString("pet").charAt(0), rs.getString("sub").charAt(0), rs.getString("ned").charAt(0), rs.getString("praznik").charAt(0)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tblSchedule.setItems(obs);
		
	}
	
	@FXML
	private void checkBoxClicked(ActionEvent event) {
		if (cbDayOfWeek.isSelected()) {
			cmbDaysOfWeek.setDisable(false);
			dtpDate.setValue(null);
			dtpDate.setDisable(true);
		} else {
			cmbDaysOfWeek.setValue(null);
			cmbDaysOfWeek.setDisable(true);
			dtpDate.setDisable(false);
		}
	}
	
	@FXML
	private void createClicked(ActionEvent event) {
		
		int ride;
		Date date = Date.valueOf(dtpDate2.getValue().toString());
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("SELECT idvoznja FROM voznja WHERE rasporedFK=" + txtSelected.getText() + " AND datum='" + date + "'");
			rs.next();
			ride = Integer.valueOf(rs.getString("idvoznja"));
			
			con.createStatement().executeUpdate("INSERT INTO karte(voznjaFK, ime, povratna) VALUES(" + ride + ", '" + txtName.getText() + "', " + cbReturn.isSelected() + ");");
			lblInfo.setStyle("-fx-text-fill: green");
			lblInfo.setText("Kreirana karta za voznju: " + ride);
		} catch (SQLException e) {
			if (e.getErrorCode() == 0) {
				lblInfo.setStyle("-fx-text-fill: red");
				lblInfo.setText("Odabrana voznja ne saobraca na odabrani datum ili datumi za nju jos nisu definisani");
			} else {
				lblInfo.setStyle("-fx-text-fill: red");
				lblInfo.setText(String.valueOf(e.getErrorCode()));
			}
			e.printStackTrace();
		}
	}
	
	@FXML
	private void ticketSearchClicked(ActionEvent event) {
		
		tblTickets.getItems().clear();
		Date date;
		
		String query = "SELECT idkarte, voznjaFK, ime, voznja.datum \r\n"
				+ "FROM karte\r\n"
				+ "INNER JOIN voznja ON voznjaFK = idVoznja "
				+ "WHERE ime LIKE '%" + txtName.getText() + "%'";
		
		if(dtpTicket.getValue() != null) {
			date = Date.valueOf(dtpTicket.getValue().toString());
			query += " AND datum='" + date + "'";
		}
		
		if(!txtTicket.getText().trim().isEmpty()) {
			query += " AND idkarte=" + txtTicket.getText() + "";
		}
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery(query);
			
			while (rs.next()) {
				obt.add(new Ticket(rs.getString("idkarte"), rs.getString("voznjaFK"), rs.getString("ime"), rs.getString("datum")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tblTickets.setItems(obt);
		
	}
	
	@FXML
	private void ticketDelete(ActionEvent event) {
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("DELETE FROM karte WHERE idkarte=" + txtSelectedT.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void clearClicked(ActionEvent event) {
		dtpDate.setValue(null);
	}
	
	@FXML
	private void scheduleTableSelect(MouseEvent event) {
		ScheduleItem line = tblSchedule.getSelectionModel().getSelectedItem();
		txtSelected.setText(line.getIdItem());
		dtpDate2.setValue(dtpDate.getValue());
	}
	
	@FXML
	private void ticketTableSelect(MouseEvent event) {
		Ticket line = tblTickets.getSelectionModel().getSelectedItem();
		txtSelectedT.setText(line.getIdticket());
	}
	
}

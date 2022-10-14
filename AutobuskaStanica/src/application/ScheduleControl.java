package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ScheduleControl implements Initializable{
	
	@FXML
	Spinner<Integer> spnStartHour, spnStartMinute, spnReturnHour, spnReturnMinute;
	
	@FXML
	TextField txtSelectedS, txtSelectedLine;
	
	@FXML
	TableView<BusLines> tblLines;
	
	@FXML
	TableView<ScheduleItem> tblSchedule;
	
	@FXML
	TableColumn<BusLines, String> colLineId, colCityStart, colCityEnd, colThrough;
	
	@FXML
	TableColumn<ScheduleItem, String> colId, colLine, colStart, colReturn, colCompany, colMon, colTue, colWed, colThu, colFri, colSat, colSun, colHol;
	
	@FXML
	CheckBox chkReturn, chkMon, chkTue, chkWed, chkThu, chkFri, chkSat, chkSun, chkHol, chkAll, chkWorkDays;
	
	@FXML
	ComboBox<String> cbCompany;
	
	ObservableList<BusLines> obline = FXCollections.observableArrayList();
	ObservableList<ScheduleItem> obs = FXCollections.observableArrayList();
	
	ObservableList<String> combo = FXCollections.observableArrayList();
	int compId;
	
	List<CheckBox> dayChk = new ArrayList<CheckBox>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		dayChk.add(chkMon);
		dayChk.add(chkTue);
		dayChk.add(chkWed);
		dayChk.add(chkThu);
		dayChk.add(chkFri);
		dayChk.add(chkSat);
		dayChk.add(chkSun);
		dayChk.add(chkHol);
		
		SpinnerValueFactory<Integer> valueFactoryHourS = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23);
		SpinnerValueFactory<Integer> valueFactoryMinS = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
		SpinnerValueFactory<Integer> valueFactoryHourR = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23);
		SpinnerValueFactory<Integer> valueFactoryMinR = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
		
		spnStartHour.setValueFactory(valueFactoryHourS);
		spnStartMinute.setValueFactory(valueFactoryMinS);
		spnReturnHour.setValueFactory(valueFactoryHourR);
		spnReturnMinute.setValueFactory(valueFactoryMinR);
		
		colLineId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCityStart.setCellValueFactory(new PropertyValueFactory<>("start"));
		colCityEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
		colThrough.setCellValueFactory(new PropertyValueFactory<>("through"));
		
		colId.setCellValueFactory(new PropertyValueFactory<>("idItem"));
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
		
		linesLoad();
		scheduleLoad();
		companyLoad();
	
	}
	
	private void linesLoad() {
		
		tblLines.getItems().clear();
		
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
		
		tblLines.setItems(obline);
	}
	
	private void scheduleLoad() {
			
			tblSchedule.getItems().clear();
			
			try {
				Connection con = DBConnector.getConnection();
				
				ResultSet rs = con.createStatement().executeQuery("SELECT idrasporedSedmica, linijaFK, vrPolaska, vrPovratka, prijevoznici.imePrijevoznik, pon, uto, sri, cet, pet, sub, ned, praznik"
						+ " from rasporedsedmica "
						+ "INNER JOIN prijevoznici ON prevoznikFK=prijevoznici.idprijevoznici;");
				
				while (rs.next()) {
					obs.add(new ScheduleItem(rs.getString("idrasporedSedmica"), rs.getString("linijaFK"), rs.getString("linijaFK"), rs.getString("vrPolaska"), rs.getString("vrPovratka"), rs.getString("imePrijevoznik"), rs.getString("pon").charAt(0), rs.getString("uto").charAt(0), rs.getString("sri").charAt(0), rs.getString("cet").charAt(0), rs.getString("pet").charAt(0), rs.getString("sub").charAt(0), rs.getString("ned").charAt(0), rs.getString("praznik").charAt(0)));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tblSchedule.setItems(obs);
	}
	
	private void companyLoad() {
		
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM stanicatuzla.prijevoznici;");
			
			while (rs.next()) {
				combo.add(new String(rs.getString("imePrijevoznik")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cbCompany.setItems(combo);
		
	}
	
	@FXML
	public void insertSchedule(ActionEvent event) {
		char days[] = new char[8];
		for(int i = 0; i < 8; i++) {
			if (dayChk.get(i).isSelected()) {
				days[i] = 'X';
			} else {
				days[i] = '-';
			}
		}
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("insert into "
					+ "stanicatuzla.rasporedsedmica(linijaFK, vrPolaska, vrPovratka, prevoznikFK, pon, uto, sri, cet, pet, sub, ned, praznik) "
					+ "values(" + txtSelectedLine.getText() + ", '" + spnStartHour.getValue().toString() + ":" + spnStartMinute.getValue().toString() 
					+ "', '" + spnReturnHour.getValue().toString() + ":" + spnReturnMinute.getValue().toString() + "', " + compId + ","
					+ " '" + days[0] + "', '" + days[1] + "', '" + days[2] + "', '" + days[3] + "', '" + days[4] + "', '" + days[5] + "'"
					+ ", '" + days[6] + "', '" + days[7] + "')");
			
			scheduleLoad();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void returnCheck(MouseEvent event) {
		if (chkReturn.isSelected()) {
			spnReturnHour.setDisable(false);
			spnReturnMinute.setDisable(false);
		} else {
			spnReturnHour.setDisable(true);
			spnReturnMinute.setDisable(true);
		}
	}
	
	public void getCompanyId() {
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rs = con.createStatement().executeQuery("SELECT idprijevoznici "
					+ "FROM stanicatuzla.prijevoznici "
					+ "WHERE imePrijevoznik = '" + cbCompany.getSelectionModel().getSelectedItem().toString() + "';");
			
			while (rs.next()) {
				compId = Integer.parseInt(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void scheduleDelete(ActionEvent event) {
		
		try {
			Connection con = DBConnector.getConnection();
			
			con.createStatement().executeUpdate("DELETE FROM stanicatuzla.rasporedsedmica WHERE idrasporedSedmica = '" + txtSelectedS.getText() + "'");
			scheduleLoad();
			txtSelectedS.setText(null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void allClicked(MouseEvent event) {
		if(chkAll.isSelected()) {
			for(int i = 0; i < 8; i++) {
				dayChk.get(i).setSelected(true);
				if(i!=7) {
					dayChk.get(i).setDisable(true);
				}
			}
		} else {
			for(int i = 0; i < 8; i++) {
				dayChk.get(i).setSelected(false);
				if(i!=7) {
					dayChk.get(i).setDisable(false);
					}
				}
		}
	}
	
	@FXML
	private void workClicked(MouseEvent event) {
		if(chkWorkDays.isSelected()) {
			for(int i = 0; i < 5; i++) {
				dayChk.get(i).setSelected(true);
				dayChk.get(i).setDisable(true);
			}
		} else {
			for(int i = 0; i < 5; i++) {
				dayChk.get(i).setSelected(false);
				dayChk.get(i).setDisable(false);
			}
		}
	}
	
	@FXML
	private void scheduleTableSelect(MouseEvent event) {
		ScheduleItem line = tblSchedule.getSelectionModel().getSelectedItem();
		txtSelectedS.setText(line.getIdItem());
	}
	
	@FXML
	private void lineTableSelect(MouseEvent event) {
		BusLines line = tblLines.getSelectionModel().getSelectedItem();
		txtSelectedLine.setText(line.getId());
	}
	
	@FXML
	private void scheduleBackButton(ActionEvent event) {
		LoadPanel load = new LoadPanel();
		load.load(event);
	}

}
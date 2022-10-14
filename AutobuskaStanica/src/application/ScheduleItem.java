package application;

public class ScheduleItem {

	String idItem, destination, line, startTime, returnTime, company;
	char mon, tue, wed, thu, fri, sat, sun, hol;
	
	public ScheduleItem(String idItem, String destination, String line, String startTime, String returnTime, String company, char mon,
			char tue, char wed, char thu, char fri, char sat, char sun, char hol) {
		super();
		this.idItem = idItem;
		this.line = line;
		this.startTime = startTime;
		this.returnTime = returnTime;
		this.company = company;
		this.mon = mon;
		this.tue = tue;
		this.wed = wed;
		this.thu = thu;
		this.fri = fri;
		this.sat = sat;
		this.sun = sun;
		this.hol = hol;
		this.destination = destination;
	}
	
	public String getIdItem() {
		return idItem;
	}
	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestionation(String destination) {
		this.destination = destination;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public char getMon() {
		return mon;
	}
	public void setMon(char mon) {
		this.mon = mon;
	}
	public char getTue() {
		return tue;
	}
	public void setTue(char tue) {
		this.tue = tue;
	}
	public char getWed() {
		return wed;
	}
	public void setWed(char wed) {
		this.wed = wed;
	}
	public char getThu() {
		return thu;
	}
	public void setThu(char thu) {
		this.thu = thu;
	}
	public char getFri() {
		return fri;
	}
	public void setFri(char fri) {
		this.fri = fri;
	}
	public char getSat() {
		return sat;
	}
	public void setSat(char sat) {
		this.sat = sat;
	}
	public char getSun() {
		return sun;
	}
	public void setSun(char sun) {
		this.sun = sun;
	}
	public char getHol() {
		return hol;
	}
	public void setHol(char hol) {
		this.hol = hol;
	}
	
}
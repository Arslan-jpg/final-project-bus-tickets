package application;

public class Ticket {
	
	String idticket, ride, name, date;

	public Ticket (String idticket, String ride, String name, String date) {
		this.idticket = idticket;
		this.ride = ride;
		this.name = name;
		this.date = date;
	}
	
	public String getIdticket() {
		return idticket;
	}

	public void setIdticket(String idticket) {
		this.idticket = idticket;
	}

	public String getRide() {
		return ride;
	}

	public void setRide(String ride) {
		this.ride = ride;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}

package application;

public class DateItem {
	
	String idRide, idSch, date;
	
	public DateItem (String idR, String idS, String date) {
		this.idRide = idR;
		this.idSch = idS;
		this.date = date;
	}

	public String getIdRide() {
		return idRide;
	}

	public void setIdRide(String idRide) {
		this.idRide = idRide;
	}

	public String getIdSch() {
		return idSch;
	}

	public void setIdSch(String idSch) {
		this.idSch = idSch;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

package application;

public class Companies {
	
	String id, ime;

	public Companies (String id, String ime) {
		this.id = id;
		this.ime = ime;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}

}

package application;

public class Users {
	
	String id, ime, pass, privilege;

	public Users (String id, String ime, String pass, String privilege) {
		this.id = id;
		this.ime = ime;
		this.pass = pass;
		this.privilege = privilege;
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
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getPrivilege() {
		return privilege;
	}
	
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	

}

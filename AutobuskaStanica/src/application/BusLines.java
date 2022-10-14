package application;

public class BusLines {

	String id, start, end, through;
	
	public BusLines(String id, String start, String end, String through) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.through = through;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStart() {
		return start;
	}
	
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getThrough() {
		return through;
	}
	
	public void setThrough(String through) {
		this.through = through;
	}
	
}

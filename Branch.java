package san.cs631.project;

public class Branch {
	
	private int libID;
	private String lName;
	private String lLocation;
	public int getLibID() {
		return libID;
	}
	public void setLibID(int libID) {
		this.libID = libID;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getlLocation() {
		return lLocation;
	}
	public void setlLocation(String lLocation) {
		this.lLocation = lLocation;
	}
	
	public Branch(){
		
	}
	
	public Branch(int libID, String lName, String lLocation){
		super();
		this.libID = libID;
		this.lLocation = lLocation;
		this.lName = lName;
	}

}

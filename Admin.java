package san.cs631.project;

public class Admin {
	
	private int adminID;
	private String password;
	private String adminName;
	public int getAdminID() {
		return adminID;
	}
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Admin(){
		
	}
	
	public Admin(int adminID, String password, String adminName){
		super();
		this.adminID = adminID;
		this.adminName = adminName;
		this.password = password;
		
	}

}

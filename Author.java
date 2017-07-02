package san.cs631.project;

public class Author {

	private int authorID;
	private String aName;
	public int getAuthorID() {
		return authorID;
	}
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
	public String getaName() {
		return aName;
	}
	public void setaName(String aName) {
		this.aName = aName;
	}
	
	public Author(int authorID, String aName){
		super();
		this.aName = aName;
		this.authorID = authorID;
	}
	public Author(){
		
	}

}

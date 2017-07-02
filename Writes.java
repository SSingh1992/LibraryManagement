package san.cs631.project;

public class Writes {
	
	private int authorID;
	private int docID;
	public int getAuthorID() {
		return authorID;
	}
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	
	public Writes(){
		
	}
	
	public Writes(int authorID, int docID){
		super();
		this.authorID = authorID;
		this.docID = docID;
	}
	
}

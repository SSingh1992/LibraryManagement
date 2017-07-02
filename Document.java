package san.cs631.project;

public class Document {
	
	private int documentID;
	private String title;	
	private String pDate;
	private int publisherID;
	public int getDocumentID() {
		return documentID;
	}
	public void setDocumentID(int documentID) {
		this.documentID = documentID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = pDate;
	}
	public int getPublisherID() {
		return publisherID;
	}
	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}
	
	public Document(){
		
	}
	
	public Document(int documentID, String title, String pDate, int publisherID){
		super();
		this.documentID = documentID;
		this.pDate = pDate;
		this.publisherID = publisherID;
		this.title = title;
	}

}

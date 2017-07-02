package san.cs631.project;

public class Publisher {
	
	private int publisherID;
	private String pubName;
	private String pubAddress;
	public int getPublisherID() {
		return publisherID;
	}
	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}
	public String getPubName() {
		return pubName;
	}
	public void setPubName(String pubName) {
		this.pubName = pubName;
	}
	public String getPubAddress() {
		return pubAddress;
	}
	public void setPubAddress(String pubAddress) {
		this.pubAddress = pubAddress;
	}
	
	public Publisher(){
		
	}
	
	public Publisher(int publisherID, String pubName, String pubAddress){
		super();
		this.pubAddress = pubAddress;
		this.publisherID = publisherID;
		this.pubName = pubName;
		
	}

}

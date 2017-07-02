package san.cs631.project;

public class Reader {
	
	private int readerID;
	private String rType;
	private String rName;
	private String rAddress;
	private int card;
	
	public int getReaderID() {
		return readerID;
	}
	public void setReaderID(int readerID) {
		this.readerID = readerID;
	}
	public String getrType() {
		return rType;
	}
	public void setrType(String rType) {
		this.rType = rType;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getrAddress() {
		return rAddress;
	}
	public void setAddress(String rAddress) {
		this.rAddress = rAddress;
	}
	public int getCard() {
		return card;
	}
	public void setCard(int card) {
		this.card = card;
	}
	
	public Reader(int readerID, String rType, String rName, String rAddress, int card){
		super();
		this.readerID = readerID;
		this.rType = rType;
		this.rName = rName;
		this.rAddress= rAddress;
		this.card = card;
	}
	
	public Reader(){
		 
	 }

}

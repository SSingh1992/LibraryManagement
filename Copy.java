package san.cs631.project;

public class Copy {
	
	private int docID;
	private int libID;
	private int copyNo;
	private String position;
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public int getLibID() {
		return libID;
	}
	public void setLibID(int libID) {
		this.libID = libID;
	}
	public int getCopyNo() {
		return copyNo;
	}
	public void setCopyNo(int copyNo) {
		this.copyNo = copyNo;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public Copy(){
		
	}
	
	public Copy(int docID, int libID, int copyNo, String position){
		super();
		this.copyNo = copyNo;
		this.docID = docID;
		this.libID = libID;
		this.position = position;
		
	}
}

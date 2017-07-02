package san.cs631.project;

public class Proceedings {

	private int docID;
	private String cDate;
	private String cLocation;
	private String cEditor;
	
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public String getcDate() {
		return cDate;
	}
	public void setcDate(String cDate) {
		this.cDate = cDate;
	}
	public String getcLocation() {
		return cLocation;
	}
	public void setcLocation(String cLocation) {
		this.cLocation = cLocation;
	}
	public String getcEditor() {
		return cEditor;
	}
	public void setcEditor(String cEditor) {
		this.cEditor = cEditor;
	}
	
	public Proceedings(){
		
	}

	public Proceedings(int docID, String cDate, String cLocation, String cEditor){
		super();
		this.cDate = cDate;
		this.cEditor = cEditor;
		this.cLocation = cLocation;
		this.docID = docID;
	}
}

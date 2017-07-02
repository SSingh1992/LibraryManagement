package san.cs631.project;

public class InvEditor {
	
	private int docID;
	private String issueNo;
	private String ieName;
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public String getIssueNo() {
		return issueNo;
	}
	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}
	public String getIeName() {
		return ieName;
	}
	public void setIeName(String ieName) {
		this.ieName = ieName;
	}
	
	public InvEditor(){
		
	}
	
	public InvEditor(int docID, String issueNo, String ieName){
		super();
		this.docID = docID;
		this.issueNo = issueNo;
		this.ieName = ieName;
	}

}

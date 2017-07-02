package san.cs631.project;

public class JournalIssue {

	private int docID;
	private String issueNo;
	private String scope;
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
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public JournalIssue(){
		
	}
	
	public JournalIssue(int docID, String issueNo, String scope){
		super();
		this.docID = docID;
		this.issueNo = issueNo;
		this.scope = scope;
		
	}
	
}

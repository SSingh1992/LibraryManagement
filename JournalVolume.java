package san.cs631.project;

public class JournalVolume {
	
	private int docID;
	private String jVolume;
	private int editorID;
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public String getjVolume() {
		return jVolume;
	}
	public void setjVolume(String jVolume) {
		this.jVolume = jVolume;
	}
	public int getEditorID() {
		return editorID;
	}
	public void setEditorID(int editorID) {
		this.editorID = editorID;
	}
	
	public JournalVolume(){
		
	}
	
	public JournalVolume(int docID, String jVolume, int editorID){
		super();
		this.docID = docID;
		this.editorID = editorID;
		this.jVolume = jVolume;
		
	}

}

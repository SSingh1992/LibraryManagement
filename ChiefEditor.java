package san.cs631.project;

public class ChiefEditor {
	
	private int editorID;
	private String eName;
	public int getEditorID() {
		return editorID;
	}
	public void setEditorID(int editorID) {
		this.editorID = editorID;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	
	public ChiefEditor(){
		
	}
	
	public ChiefEditor(int editorID, String eName){
		super();
		this.eName = eName;
		this.editorID = editorID;
	}

}

package san.cs631.project;

public class Book {
	
	private int docID;
	private int ISBN;
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	
	public Book(){
		
	}
	
	public Book(int docID, int ISBN){
		super();
		this.docID = docID;
		this.ISBN = ISBN;
	}
}

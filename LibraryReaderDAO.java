package san.cs631.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryReaderDAO {
	Reader r = new Reader();
	Document d = new Document();
	Admin a = new Admin();
	Branch b = new Branch();
	
	
	public Reader checkCard(int number){
		try {
			String sql = "select * from library.reader where CARD="+number;
			PreparedStatement preStatementCC = LibraryConnection.getConnection().prepareStatement(sql);
			preStatementCC.setMaxRows(1);
			ResultSet result = preStatementCC.executeQuery();
			
			r.setReaderID(0);			
			while(result.next()){
				
				r.setReaderID(result.getInt("READERID"));
				r.setrType(result.getString("RTYPE"));
				r.setrName(result.getString("RNAME"));
				r.setAddress(result.getString("ADDRESS"));
				r.setCard(result.getInt("CARD"));
				//System.out.println(result.getString("RNAME"));
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return r;
	}
		
	public Document docByID(int number){
		try {
			String sqlID = "select * from library.document where DOCID="+number;
			PreparedStatement preStatementID = LibraryConnection.getConnection().prepareStatement(sqlID);
			preStatementID.setMaxRows(1);
			ResultSet result = preStatementID.executeQuery();
			
			d.setDocumentID(0);			
			while(result.next()){
				
				d.setDocumentID(result.getInt("DOCID"));
				d.setTitle(result.getString("TITLE"));
				d.setpDate(result.getString("PDATE"));
				d.setPublisherID(result.getInt("PUBLISHERID"));
				
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return d;
	}

	public boolean docByTitle(String title) {
		// TODO Auto-generated method stub
		Boolean docPresent = false;
		try {
			String sqlTI = "select * from library.document where TITLE like '%"+title+"%'";
			PreparedStatement preStatementTI = LibraryConnection.getConnection().prepareStatement(sqlTI);
			//preStatementTI.setMaxRows(1);
			ResultSet result = preStatementTI.executeQuery();
						
			while(result.next()){				

				docPresent = true;
				System.out.print("Document ID "+result.getInt("DOCID"));System.out.print(" - ");
				System.out.print("Document TITLE "+result.getString("TITLE"));System.out.print(" - ");
				System.out.println("Publisher ID "+result.getInt("PUBLISHERID"));
				
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return docPresent;
	}
	
	public boolean docByPubName(String pubName){
		Boolean pubPresent = false;
		try{
			String sqlPN = "select PUBNAME, DOCID, TITLE, PDATE, publisher.PUBLISHERID, ADDRESS from library.document, library.publisher where document.PUBLISHERID=publisher.PUBLISHERID and publisher.PUBNAME = '"+pubName+"'";
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			//preStatementTI.setMaxRows(1);
			ResultSet result = preStatementPN.executeQuery();			
			while(result.next()){
				
				pubPresent = true;
				
				System.out.print("Publisher NAME "+result.getString("PUBNAME"));System.out.print(" - ");
				System.out.print("Doc ID "+result.getInt("DOCID"));System.out.print(" - ");
				System.out.print("Doc TITLE "+result.getString("TITLE"));System.out.print(" - ");
				System.out.print("Pub DATE "+result.getString("PDATE"));
				System.out.print("Pub ID "+result.getInt("PUBLISHERID"));
				System.out.println("Pub ADDRESS "+result.getString("ADDRESS"));
				
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return pubPresent;
		}
	
	public boolean docBorrowByReader(int readerID, int docID, int libID ){
		boolean docIDCheck = false;
		if(docByID(docID).getDocumentID() == 0){
			docIDCheck = false;
			System.out.println("DOCUMENT can't be borrowed doesn't EXISTS");
		}else {
			docIDCheck = true;
			System.out.println("DOCUMENT EXISTS");
			}
		
		if (docInCopy(docID, libID)){
			System.out.println("Document in the LIBRARY EXISTS");
			docIDCheck = true;
		}else{
			System.out.println("Document in the library doesn't EXISTS.. TRY IN OTHER LIBRARY");
			docIDCheck = false;
		}
		
		
		if (docIDCheck){
			//check in borrows and also return the number of times it is borrowed
			int count = docInBorrows(docID, libID, "*");
			System.out.println("DocCount in BORROWS and number of times borrowed is "+count);
			if(docIDCheck = true & count == 0){
				System.out.println("Document can be borrowed");
				//count = 0 means the document was never borrowed and hence can be borrowed now.
				//INSERT document with reader id in borrows
				//INSERT document with copy no = 1 and readerID, docID BDTIME.
				//docInsertInBorrows(), maxBornumber() and libOfDoc() are the methods which help in achieve the insert
				//maxBornumber + 1 as the query return the last borrow number add one to it
				
				if (docInsertInBorrow(maxBornumber() + 1, readerID, docID, libID, 1 )){
					System.out.println("Doc Inserted copy no 1 -- Check statement");
				}
			}else {
				docIDCheck = false;
			}
			if (count != 0){
				System.out.println("Check of the reader want a copy or check for document is already borrowed");
				int BDTIME_reader = docReaderTIMECountInBorrows(readerID, docID, libID, "BDTIME");
				System.out.println("Number of Times doc borrowed / BDTIME of the reader - "+BDTIME_reader);
				int RDTIME_reader = docReaderTIMECountInBorrows(readerID, docID, libID, "RDTIME");
				System.out.println("Number of Times doc returned / RDTIME of the reader - "+RDTIME_reader);
				if (BDTIME_reader > RDTIME_reader){
					//count of BDTIME is greater than the RDTIME the doc was never returned and hence he needs a copy
					System.out.println(readerID+" has a copy of that document and wants another one");
					docIDCheck = true;
					//insert command will go here for the copy one 
					//BE careful in this copy number need to changed  
					//get the difference of BDTIME - RDTIME copy number will be known
					//
					System.out.println(BDTIME_reader - RDTIME_reader + 1);
					if(docInsertInBorrow(maxBornumber() + 1, readerID, docID, libID, BDTIME_reader - RDTIME_reader)){
						System.out.println("Doc Inserted copy no "+(BDTIME_reader-RDTIME_reader+1)+" -- Check statement");
					}
					
				} else if ((BDTIME_reader == 0) & (RDTIME_reader == 0) || (BDTIME_reader == RDTIME_reader)){
					//(BDTIME_reader == 0) & (RDTIME_reader == 0) -- Document is not borrowed by the reader EVER
					//(BDTIME_reader == RDTIME_reader) -- Document was borrowed by the reader and even returned
					//Maybe borrowed by the other reader or borrowed and then returned. (Remember all the borrowed document will exits in the borrows.)
					//Check in this block if borrowed AT PRESENT.
					int RDTIME_count_Borrows = docInBorrows(docID, libID, "RDTIME");
					int BDTIME_count_Borrows = docInBorrows(docID, libID, "BDTIME");
					if (BDTIME_count_Borrows > RDTIME_count_Borrows){
						System.out.println("DOCUMENT is already being borrowed by somebody");
						docIDCheck = false;
					}else if (BDTIME_reader == RDTIME_reader){
						System.out.println("Recent statement worked");
						docIDCheck = true;
						docInsertInBorrow(maxBornumber() + 1, readerID, docID, libID, 1 );					
						
					}
					/*else{
						System.out.println("DOCUMENT can be borrowed by the reader");
						docIDCheck = true;
						
						//Insert command
						
					}*/
					
				}
				
			}
			
			
		}
		
			
		/*if (docIDCheck){
			System.out.println("true");
		}else{
			System.out.println("false");
		}*/
		return docIDCheck;
		
	}	
	
	public boolean docInCopy(int docID, int libID){
		boolean docInCopy = false;
		try{
			String sqlPN = "select * from library.copy where DOCID = '"+docID+"' and LIBID='"+libID+"'";
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			//preStatementTI.setMaxRows(1);
			ResultSet result = preStatementPN.executeQuery();			
			while(result.next()){
				
				docInCopy = true;
				
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return docInCopy;
	}
	
	public int docInBorrows(int docID, int libID, String TIME){
		int count = -1;
		try{
			String sqlPN = "select count("+TIME+") from library.borrows where DOCID='"+docID+"' and LIBID="+libID;
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			//preStatementTI.setMaxRows(1);
			ResultSet result = preStatementPN.executeQuery();			
			while(result.next()){
				count = result.getInt("COUNT("+TIME+")");
							
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
	}
	
	public int docReaderTIMECountInBorrows(int readerID, int DocID, int libID, String TIME){
		int timeCount = -1; // BTIME(NOT with RTIME) count is -1 then somebody else has borrowed the document
		try{
			String sqlPN = "select count("+TIME+") from library.borrows where DOCID='"+DocID+"' and READERID="+readerID+" and LIBID = "+libID+"";
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			//preStatementTI.setMaxRows(1);
			ResultSet result = preStatementPN.executeQuery();			
			while(result.next()){
				timeCount = result.getInt("COUNT("+TIME+")");
							
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return timeCount;
	}
	
	public boolean docReturnByReader(int readerID, int docID){
		int borNumber = -1;
		Boolean updated = false;
		try{
			String sqlPN = "select * from library.borrows where DOCID='"+docID+"' and READERID = '"+readerID+"' and BORNUMBER != all (select BORNUMBER from library.borrows where RDTIME !='');";
			//select BORNUMBER from library.borrows where RDTIME !=''); - return BORNUMBER where are DOC are returned.
			//Parent query computes BORNUMBER for the docID which is returned by readerID
			
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			preStatementPN.setMaxRows(1);
			ResultSet result = preStatementPN.executeQuery();			
			while(result.next()){
				borNumber = result.getInt("BORNUMBER");
				System.out.println(borNumber);							
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (borNumber != -1){
			System.out.println("UPDATE the BORROWS table with the current date.");
			// Update comes here
			updated = docReturnRTIMEUpdate(borNumber);
			
		}else if (borNumber == -1){
			System.out.println("Something went WRONG in the search of the BORNUMBER");
		}
		
		return updated;
	}
	
	public boolean docReturnRTIMEUpdate(int BORNUMBER){
		Boolean updated = false;
		try{
			String sqlPN = "UPDATE `library`.`borrows` SET `RDTIME`= CURRENT_TIMESTAMP() WHERE `BORNUMBER`='"+BORNUMBER+"';";
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			int count = preStatementPN.executeUpdate(sqlPN);
			if(count == 1){
				//System.out.println(count);
				updated = true;
				System.out.println(dateTimeGenerator());
				}else 
					updated = false;
			}
		//LibraryConnection.getConnection().close();}
		catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return updated;
		
	}
	
	public int docFeeCalculator(int readerID, int docID){
		int numberOfDays = -1;
		try{
			String sqlPN = "SELECT DATEDIFF(CURRENT_TIMESTAMP(), "
					+ "(select BDTIME from library.borrows where DOCID='"+docID+"' and READERID = '"+readerID+"' and BORNUMBER != all "
					+ "(select BORNUMBER from library.borrows where RDTIME !=''))) - 20 as DIFFERENCE";
			//select BORNUMBER from library.borrows where RDTIME !=''); - return BORNUMBER where are DOC are returned.
			//Parent query computes BORNUMBER for the docID which is returned by readerID
			
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			preStatementPN.setMaxRows(1);
			ResultSet result = preStatementPN.executeQuery();			
			while(result.next()){
				numberOfDays = result.getInt("DIFFERENCE");
				System.out.println("Number of DAYS over the 20 days GRACE PERIOD "+numberOfDays);							
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return numberOfDays;
	}
	
	public Boolean docByPubName_DOCID_TITLE(String pubName){
		Boolean pubPresent = false;
		try{
			String sqlPN = "select DOCID, TITLE from library.document, library.publisher where document.PUBLISHERID=publisher.PUBLISHERID and publisher.PUBNAME = '"+pubName+"'";
			PreparedStatement preStatementPN = LibraryConnection.getConnection().prepareStatement(sqlPN);
			//preStatementTI.setMaxRows(1);
			ResultSet result = preStatementPN.executeQuery();			
			while(result.next()){
				
				pubPresent = true;
				
				System.out.print("Doc ID "+result.getInt("DOCID"));System.out.print(" - ");System.out.println("Doc TITLE "+result.getString("TITLE"));
				
			}
			//LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return pubPresent;
		}
	
	public String dateTimeGenerator() {
		// TODO Auto-generated method stub

		Date dt = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("HHMMSS");
		String currentTime = sdf.format(dt);
		System.out.println(currentTime);
		
		return currentTime = sdf.format(dt);
	}

	public Admin adminCheckID(int admin_id) {
		// TODO Auto-generated method stub
		
		try {
			String sql = "select * from library.admin where ADMINID ="+admin_id;
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			preStatementCI.setMaxRows(1);
			ResultSet result = preStatementCI.executeQuery();
			
			a.setAdminID(0);			
			while(result.next()){				
				a.setAdminID(result.getInt("ADMINID"));
				a.setPassword(result.getString("PASSWORD"));
				a.setAdminName(result.getString("ADMIN_NAME"));
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return a;
	}
	
	//INSERT Document in the borrows.
	public boolean docInsertInBorrow(int borNumber, int readerID, int docID, int libID, int copyNo ){
		Boolean insert = false;
		
		try {
			String sql = "INSERT INTO `library`.`borrows` (`BORNUMBER`, `READERID`, `DOCID`, `LIBID`, `COPYNO`, `BDTIME`, `RDTIME`) "
					+ "VALUES ('"+borNumber+"' , '"+readerID+"', '"+docID+"', '"+libID+"', '"+copyNo+"', CURRENT_TIMESTAMP(), null)";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			int count_row = preStatementCI.executeUpdate(sql);
			
			if (count_row == 1){
				insert = true;
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return insert;
	}
	
	public int maxBornumber(){
		int maxBornumber = -1;
		try {
			String sql = "select max(BORNUMBER) as max from borrows";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			ResultSet result = preStatementCI.executeQuery();
						
			while(result.next()){				
				maxBornumber = result.getInt("max");
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		System.out.println("Max bornumber in borrows "+maxBornumber);
		return maxBornumber;
	}
	
	public int libOfDoc(int docID){
		int libID = -1;
		try {
			String sql = "select distinct LIBID from library.copy where DOCID = "+docID;
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			ResultSet result = preStatementCI.executeQuery();						
			while(result.next()){				
				libID = result.getInt("LIBID");
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		System.out.println("Library ID "+libID);
		return libID;
	}
	
	//returns max from the copyno
	public int maxCopyNo(int docID, int libID){
		int max_Copy = 1;
		try {
			String sql = "select max(COPYNO) as max from library.copy where DOCID="+docID+" and LIBID="+libID+"";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			ResultSet result = preStatementCI.executeQuery();
						
			while(result.next()){				
				max_Copy = result.getInt("max");
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		System.out.println("Max copyNo in copy "+max_Copy);
		return max_Copy;
		
	}
	
	// we working here .
	public boolean insertNewDoc(int docID, int libID, String position){
		Boolean insert = false;
		if (docInCopy(docID, libID)){
			try {
				String sql = "INSERT INTO `library`.`copy` (`DOCID`, `LIBID`, `COPYNO`, `POSITION`) "
						+ "VALUES ('"+docID+"', '"+libID+"', '"+(maxCopyNo(docID, libID) + 1)+"', '"+position+"')";
				PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
				int count_row = preStatementCI.executeUpdate(sql);
				
				if (count_row == 1){
					insert = true;
				}
				//LibraryConnection.getConnection().close();
				//Connection close issue -- pooling
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}else{
			System.out.println("Document ENTRY is worng PLEASE check in COPY table");
			insert = false;
		}
		
		
		return insert;
	}
	
	public boolean checkCopyStatus(int docID, int libID, int copyNo){
		boolean check = false;
		Document d =docByID(docID);
		if (d.getDocumentID()!=0){
			System.out.println("DOCUMENT exists");
			try {
				String sql = "select BORNUMBER, reader.READERID, BDTIME, RNAME "
						+ "from library.borrows, library.reader "
						+ "where reader.READERID=borrows.READERID and DOCID="+docID+" and LIBID="+libID+" and COPYNO="+copyNo+" and RDTIME is null";
				PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
				preStatementCI.setMaxRows(1);
				ResultSet result = preStatementCI.executeQuery();			
							
				while(result.next()){	
					check = true;
					System.out.print("Borrow Number "+result.getInt("BORNUMBER"));System.out.print(" - ");
					System.out.print("Reader ID "+result.getInt("READERID"));System.out.print(" - ");
					System.out.print("Borrow date "+result.getString("BDTIME"));System.out.print(" - ");
					System.out.println("Reader Name "+result.getString("RNAME"));
					
				}
				//LibraryConnection.getConnection().close();
				//Connection close issue -- pooling
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			System.out.println("DOCUMENT doesn't exists");
		}
		return check;
		
	}
	
	public boolean checkReaderId(int readerID){
		boolean check = false;
		try {
			String sql = "select * from library.reader where READERID="+readerID+"";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			ResultSet result = preStatementCI.executeQuery();
			
			if (result.next()){
				check = true;
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		
		return check;
	}
	
	public boolean insertReader(Reader rd){
		Boolean insert = false;
		if(checkReaderId(rd.getReaderID()) == false){
			
			try {
				String sql = "INSERT INTO `library`.`reader` (`READERID`, `RTYPE`, `RNAME`, `ADDRESS`, `CARD`) "
						+ "VALUES ('"+rd.getReaderID()+"', '"+rd.getrType()+"', '"+rd.getrName()+"', '"+rd.getrAddress()+"', '"+rd.getCard()+"')";
				PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
				int count_row = preStatementCI.executeUpdate(sql);
				
				if (count_row == 1){
					insert = true;
				}
				//LibraryConnection.getConnection().close();
				//Connection close issue -- pooling
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			System.out.println("WRONG READERID please enter different one");
		}
		
		
		return insert;
	}

	public void libInfo(){
		
		try {
			String sql = "select LIBID, LNAME, LLOCATION from library.branch";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			preStatementCI.setMaxRows(1);
			ResultSet result = preStatementCI.executeQuery();			
						
			while(result.next()){
				System.out.print("LibID "+result.getInt("LIBID"));System.out.print(" - ");
				System.out.print("Library NAME "+result.getString("LNAME"));System.out.print(" - ");
				System.out.println("Library Location "+result.getString("LLOCATION"));
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void freqBorrowers(){
		try {
			String sql = "select count(*) as freq, reader.READERID, RNAME from library.borrows, library.reader where reader.READERID=borrows.READERID group by READERID order by freq desc";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			preStatementCI.setMaxRows(10);
			ResultSet result = preStatementCI.executeQuery();			
						
			while(result.next()){
				System.out.print("Num of times "+result.getInt("freq"));System.out.print(" - ");
				System.out.print("Reader ID "+result.getInt("READERID"));System.out.print(" - ");
				System.out.println("Reader Name "+result.getString("RNAME"));
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void top10Books(int libID){
		try {
			String sql = "select count(*) as most, document.DOCID, TITLE from library.borrows, library.document "
					+ "where document.DOCID=borrows.DOCID and LIBID="+libID+" group by DOCID order by most desc";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			preStatementCI.setMaxRows(10);
			ResultSet result = preStatementCI.executeQuery();			
						
			while(result.next()){
				System.out.print("Num of times "+result.getInt("most"));System.out.print(" - ");
				System.out.print("DOC ID "+result.getInt("DOCID"));System.out.print(" - ");
				System.out.println("Title "+result.getString("TITLE"));
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void top10BooksOfYear(int year){
		try {
			String sql = "select borrows.DOCID, TITLE, count(*) as top from borrows, document "
					+ "where document.DOCID=borrows.DOCID and year(BDTIME)="+year+" group by DOCID order by top desc";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			preStatementCI.setMaxRows(10);
			ResultSet result = preStatementCI.executeQuery();			
						
			while(result.next()){
				System.out.print("Num of times "+result.getInt("top"));System.out.print(" - ");
				System.out.print("DOC ID "+result.getInt("DOCID"));System.out.print(" - ");
				System.out.println("Title "+result.getString("TITLE"));
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void avgFinePerReader(){
		try {
			String sql = "select READERID, avg(fine) from bornumber_fine group by READERID";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			//preStatementCI.setMaxRows(10);
			ResultSet result = preStatementCI.executeQuery();			
						
			while(result.next()){
				System.out.print("ReaderID "+result.getInt("READERID"));System.out.print(" - ");
				System.out.println("Avg FINE "+result.getDouble("avg(fine)")+" Cents");
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int maxResNumber(){
		int maxResNumber = -1;
		try {
			String sql = "select max(RESUMBER) as max_reserves from reserves";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			ResultSet result = preStatementCI.executeQuery();
						
			while(result.next()){				
				maxResNumber = result.getInt("max_reserves");
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		System.out.println("Max resnumber in borrows "+maxResNumber);
		return maxResNumber;
	}
	
	public int countOfDocReserved(int readerID){
		int countRes = -1;
		try {
			String sql = "select count(*) from reserves where Date(DTIME) = curdate() and READERID="+readerID+"";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			ResultSet result = preStatementCI.executeQuery();
						
			while(result.next()){				
				countRes = result.getInt("count(*)");
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		//System.out.println("Max resnumber in borrows "+countRes);
		return countRes;
	}
	
	//INSERT INTO Reserves
	public boolean insertDocIDreserves(int docID, int readerID){
		Boolean insert = false;
		Document d = docByID(docID);
		int count = countOfDocReserved(readerID);
		
		if(d.getDocumentID()!=0){
			//change to 10 to 3 for less doc can be reserved.
			if (count == 3)
			{
				System.out.println("You have already RESERVED more than 10 document in this DAY");
			}else{
				System.out.println("Previously RESERVED doc ON this day is "+count);
				try {
					int libID = libOfDoc(docID);
					int res = maxResNumber()+ 1;
					
					String sql = "INSERT INTO `library`.`reserves` (`RESUMBER`, `READERID`, `DOCID`, `LIBID`, `COPYNO`, `DTIME`) "
							+ "VALUES ('"+res+"', '"+readerID+"', '"+docID+"', '"+libID+"', '1', CURRENT_TIMESTAMP())";
					PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
					int count_row = preStatementCI.executeUpdate(sql);
					
					if (count_row == 1){
						insert = true;
					}
					//LibraryConnection.getConnection().close();
					//Connection close issue -- pooling
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			
		}else{
			System.out.println("DOCUMENT doesn't exists");
		}		
		return insert;
	}
	
	public void reservesStatusActive(int readerID){	
		try {
			String sql = "select DOCID from reserves where Date(DTIME) = curdate() and READERID="+readerID+" and current_time() < '18:00:00'";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			//preStatementCI.setMaxRows(10);
			ResultSet result = preStatementCI.executeQuery();			
						
			while(result.next()){
				System.out.print("ReaderID "+result.getInt("DOCID"));System.out.print(" - ");System.out.println("Active")				;
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void reservesStatusCancelled(int readerID){
		try {
			String sql = "select DOCID from reserves where Date(DTIME) = curdate() and READERID="+readerID+" and current_time() > '18:00:00'";
			PreparedStatement preStatementCI = LibraryConnection.getConnection().prepareStatement(sql);
			//preStatementCI.setMaxRows(10);
			ResultSet result = preStatementCI.executeQuery();			
						
			while(result.next()){
				System.out.print("ReaderID "+result.getInt("DOCID"));System.out.print(" - ");System.out.println("Cancelled")				;
			}
			//LibraryConnection.getConnection().close();
			//Connection close issue -- pooling
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

package san.cs631.project;

import java.util.Scanner;

public class LibraryMenuDriven {
	
	public static void main(String[] args){
		
		
		Reader rd = new Reader();
		int userChoice;
		System.out.println("Welcome library menu");
		Scanner in = new Scanner(System.in);
		//Scanner out = new Scanner(System.in);
		boolean quit = false;
		do{
			System.out.println("1. Reader");
			System.out.println("2. Admin");
			System.out.println("0. To exit");
			//in.skip("\r\n");
			userChoice = in.nextInt();
			LibraryReaderDAO lr = new LibraryReaderDAO();
			switch (userChoice){
			case 1:					
				System.out.println("Please enter you CARD number");
				int number = in.nextInt();
				Reader r = lr.checkCard(number);
				if (r.getReaderID() == 0){
					System.out.println("Wrong CARD number. TRY AGAIN.");
					break;
				}else{
					System.out.println("Welcome "+r.getrName());					
				}
				System.out.println();
				
				/*System.out.println("Mention the Libray ID"); // 28
				in.skip("\r\n");
				int libID = in.nextInt();*/
				
				System.out.println();
				boolean quit_reader = false;
				do{
					System.out.println("1. Search document");
					System.out.println("2. Document CHECKOUT");
					System.out.println("3. Document RETURN");
					System.out.println("4. Document RESERVATION");
					System.out.println("5. COMPUTE FINE for a DOCUMENT");
					System.out.println("6. LIST of DOCUMENTS RESERVED on CURRENT DATE");
					System.out.println("7. LIST the DOCUMENT ID and DOCUMENT TITLES of documents published by a PUBLISHER");
					System.out.println("0. To EXIT");
					int readerChoice = in.nextInt();					
					
					switch (readerChoice){			
					/*READER DOCUMENT SEARCH CHOICES BEGIN HERE*/
					case 1:
						System.out.println("1. Search document by ID");
						System.out.println("2. Search document TITLE");
						System.out.println("3. Search document by PUBLISHER");
						System.out.println("0. To exit");
						boolean quit_doc = false;
						do{
							int docSearchChoice = in.nextInt();							
							switch (docSearchChoice){
							case 1:
								System.out.println("Please enter DOCUMENT ID"); //128745
								int ID = in.nextInt();
								Document dID = lr.docByID(ID);
								if (dID.getDocumentID() == 0){
									System.out.println("Wrong Document number. TRY AGAIN.");
					
									break;
								}else {
									System.out.print("Document ID "+dID.getDocumentID());System.out.print(" - ");
									System.out.print("Document TITLE "+dID.getTitle());System.out.print(" - ");
									System.out.print("Publisher ID "+dID.getPublisherID());
								
								}
								
								System.out.println();								
								break;
								
							case 2:
								System.out.println("Please enter DOCUMENT TITLE"); //HARRY POTTER AND THE SORCERER'S STONE
								in.skip("\r\n");
								String title = in.nextLine(); // to make this work some
								//String title = st.nextLine(); // Doesn't make sence but it works
								Boolean dTitle = lr.docByTitle(title);
								if (dTitle == false){
									System.out.println("Wrong Document number. TRY AGAIN.");
									break;
								}
								
								System.out.println();
								break;
								
							case 3:
								System.out.println("Please enter the PUBLISHER NAME");//BLOOMSBURY PUBLISHING
								in.skip("\r\n");
								String pName = in.nextLine();
								Boolean pubPresent = lr.docByPubName(pName);
								if (pubPresent == false){
									System.out.println("Wrong PUBLISHER NAME. TRY AGAIN.");
									break;
								}
								System.out.println();
								break;
								
							case 0:
				                quit_doc = true;
				                break;
				                
							default:
				                System.out.println("Wrong choice.");
				                break;								
							}						
						}while(!quit_doc);
						
					break;	
						
					
					/*READER DOCUMENT CHECKOUT HERE	*/						
					/*CHECKOUT ACCORDING TO ME MEAN I HAVE TO GET THE BOOK FROM THE LIBRARY.*/	
					case 2:
						System.out.println("Checkout Option");
						
						System.out.println(number +" - CARD NUMBER");
						System.out.println(r.getReaderID()+" - ReaderID");
						System.out.println(r.getrName()+" - Reader Name");
						
						
						System.out.println("PLEASE ENTER THE DOCID which you want to BORROW");
						//select * from library.borrows where DOCID='128722' and RDTIME < '2016-10-26 13:00:00'; // returns row we are good we can give the book to the guy
						//if row is not returned then check for document ever given and reader wants a copy
						//2 if Document exits with a reader who wants a copy
						//3 if Document which was never ever borrowed
						
						in.skip("\r\n");
						int DocID = in.nextInt();
						System.out.println("Enter Libray ID"); // 28
						in.skip("\r\n");
						int libID = in.nextInt();
						Boolean can_be_borrowed = lr.docBorrowByReader(r.getReaderID(), DocID, libID);
						if (can_be_borrowed == false){
							System.out.println("Sorry "+DocID+" is already borrowed");
						}else {
							System.out.println("Document is given to the reader and updated in the database");
						}
						break;
					
						//DOCUMENT RETURN POLICY BEGINS HERE
					case 3:
						System.out.println("Document RETURN option choosen");
						System.out.println("ENTER THE DOCUMENT ID");
						in.skip("\r\n");
						int Doc_ID_return = in.nextInt();
						Boolean updated = lr.docReturnByReader(r.getReaderID(), Doc_ID_return);
						if (updated == true){
							System.out.println("UPDATED in database");							
						}else {
							System.out.println("Wrong REPORT from menu driven");
						}
						break;
						
						//DOCUMENT RESERVES POLICY STARTS HERE
					case 4:		
						System.out.println("MAXIMUM RESERVATION PER DAY IS SET 3 FOR DEMONSTRATION");
						System.out.println("Enter DOCID you want to RESERVES");
						in.skip("\r\n");
						int docID_reserves = in.nextInt();
						
						if(lr.insertDocIDreserves(docID_reserves, r.getReaderID()))
						{
							System.out.println("DOCUMENT ADDED in RESERVES");
						}
						
						break;
						
						//DOCUMENT FINE COMPUTAION GOES HERE
					case 5:
						System.out.println("COMPUTE FINE for a Document");
						System.out.println("ENTER THE DOCUMENT ID for which fee need to be calculated");
						in.skip("\r\n");
						int Doc_ID_fee = in.nextInt();
						int numberOfDays_Over_20 = lr.docFeeCalculator(r.getReaderID(), Doc_ID_fee);
						if (numberOfDays_Over_20 <= 0){
							System.out.println("NO FINE for this DOCUMENT");
						}else{
							System.out.println("FINE for the DOCUMENT is "+numberOfDays_Over_20 * 20+" Cents");
						}
						break;
						
						//RESERVES document LIST
					case 6:
						System.out.println("Reservation by date is also possible");
						System.out.println("LIST of document reserved by "+r.getrName());
						lr.reservesStatusActive(r.getReaderID());
						lr.reservesStatusCancelled(r.getReaderID());		
						break;
						
						//LIST the DOCUMENT ID and DOCUMENT TITLES of documents published by a PUBLISHER
					case 7:
						System.out.println("Please enter the PUBLISHER NAME");//BLOOMSBURY PUBLISHING
						in.skip("\r\n");
						String pName = in.nextLine();
						Boolean pubPresent = lr.docByPubName_DOCID_TITLE(pName);
						if (pubPresent == false){
							System.out.println("Wrong PUBLISHER NAME. TRY AGAIN.");
						}
						System.out.println();
						break;						
						
					case 0:
						System.out.println("EXITING the Reader APPLICATION");
						quit_reader = true;
		                break;
		                
					default:
		                System.out.println("Wrong choice.");
		                break;
					}					
					
				}while(!quit_reader);
					
				
				
				
			break;	
				//************************
			//ADMIN process will start HERE. 		
			case 2:
				System.out.println("Please enter you ADMIN ID number");
				in.skip("\r\n");
				int admin_id = in.nextInt();
				Admin a = lr.adminCheckID(admin_id);
				if (a.getAdminID() == 0){
					System.out.println("Wrong ADMIN ID number. TRY AGAIN.");
					break;
				}
				System.out.println("Please enter you PASSWORD number");
				in.skip("\r\n");
				String pass = in.nextLine();
				if (pass.equals(a.getPassword())){
					System.out.println("Welcome "+a.getAdminName());
				}else{
					System.out.println("WRONG PASSWORD");
					break;
				}
				
				System.out.println();
				
				/*System.out.println("Mention the Libray ID"); // 28
				in.skip("\r\n");
				int libID_admin = in.nextInt();*/
				//do{}while()
				boolean quit_admin = false;
				do{
					System.out.println("1. ADD a DOCUMENT copy.");
					System.out.println("2. SEARCH DOCUMENT copy and check its status.");
					System.out.println("3. ADD a READER");
					System.out.println("4. Print BRANCH INFORMATION (name and location).");
					System.out.println("5. Print top 10 most FREQUENT BORROWERS in a branch and the NUMBER OF BOOKS each has borrowed.");
					System.out.println("6. Print top 10 MOST BORROWED books in a branch.");
					System.out.println("7. Print the 10 most popular books of the year.");
					System.out.println("8. Find the average fine paid per reader.");
					System.out.println("0. To EXIT");
					int adminChoice = in.nextInt();
					switch(adminChoice){
					case 1:
						System.out.println("PLEASE enter DOCID which you want to enter");
						in.skip("\r\n");
						int docID_admin = in.nextInt();						
						
						System.out.println("Please enter the Libray ID"); // 28
						in.skip("\r\n");
						int libID_admin = in.nextInt();						
						
						System.out.println("Please enter the position of the DOCUMENT");
						in.skip("\r\n");
						String position = in.nextLine();							
						if(lr.insertNewDoc(docID_admin, libID_admin, position)){
							System.out.println("Doc Copy Inserted");
							
						}else{
							System.out.println("Doc Copy not Inserted");
						}
						break;
						
					case 2:
						System.out.println("PLEASE enter DOCID which you want to enter");
						in.skip("\r\n");
						int docID_admin_check = in.nextInt();
						System.out.println("Please enter the LIBRARY ID"); // 28
						in.skip("\r\n");
						int libID_admin_check = in.nextInt();						
						System.out.println("Please enter the CopyNO"); // 28
						in.skip("\r\n");
						int copy_admin_check = in.nextInt();						
						lr.checkCopyStatus(docID_admin_check, libID_admin_check, copy_admin_check);
						break;
						
					case 3:
						System.out.println("PLEASE enter READERID which you want to enter");
						in.skip("\r\n");
						rd.setReaderID(in.nextInt());						
						System.out.println("Please enter the READER Type");
						in.skip("\r\n");
						rd.setrType(in.nextLine());
						System.out.println("Please enter the READER Name");
						rd.setrName(in.nextLine());						
						System.out.println("Please enter the READER ADDRESS");
						rd.setAddress(in.nextLine());
						System.out.println("Please enter the READER CARD number");
						rd.setCard(in.nextInt());
						if (lr.insertReader(rd)){
							System.out.println("Reader is ADDED in DATABASE");
						}else {
							System.out.println("UNABLE to ADD READER");
						}						
						break;
						
					case 4:
						lr.libInfo();
						break;
						
					case 5:
						lr.freqBorrowers();
						break;						
						
					case 6:
						System.out.println("Please enter the Library ID for top 10 books");
						in.skip("\r\n");						
						lr.top10Books(in.nextInt());//28
						break;
						
					case 7:
						System.out.println("Please enter the YEAR for top 10 books of that year");
						in.skip("\r\n");
						lr.top10BooksOfYear(in.nextInt());
						break;
						
						
					case 8:
						lr.avgFinePerReader();
						break;
						
					case 0:
		                quit_admin = true;
		                break;
		                
					default:
		                System.out.println("Wrong choice.");
		                break;	
							
					}			
					
				}while(!quit_admin);
				
			break;	

			case 0:
                quit = true;
                break;
                
			default:
                System.out.println("Wrong choice.");
                break;	
			}		
			
			
		}while(!quit);
		
		
	}

}

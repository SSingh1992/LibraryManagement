package san.cs631.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbmsTest {
	
	public static void main(String[] args){
		try {
			String sql = "select * from library.book";
			PreparedStatement preStatement = LibraryConnection.getConnection().prepareStatement(sql);
			ResultSet result = preStatement.executeQuery();
			while(result.next()){
				System.out.print(result.getInt("DOCID"));System.out.print(" - "); System.out.println(result.getInt("ISBN"));
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}

}

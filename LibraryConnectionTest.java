package san.cs631.project;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LibraryConnectionTest {
	public static void main(String[] args) {
		try {
			String sql = "select * from library.reader";
			PreparedStatement preStatement = LibraryConnection.getConnection().prepareStatement(sql);
			//preStatement.setMaxRows(1); 
			ResultSet result = preStatement.executeQuery();
			
			while(result.next()){
				
				System.out.print(result.getInt("READERID"));System.out.print(" - "); System.out.print(result.getString("RTYPE"));System.out.print(" - ");System.out.print(result.getString("RNAME"));System.out.print(" - ");System.out.print(result.getString("ADDRESS"));System.out.print(" - ");System.out.println(result.getInt("CARD"));
			}
			LibraryConnection.getConnection().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}

}

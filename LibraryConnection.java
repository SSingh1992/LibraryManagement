package san.cs631.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class LibraryConnection {
	
	 private static Connection connection;
	 
	 public static Connection getConnection() throws Exception{
		 // create a instance
		 if (connection == null){
			 Properties properties = loadDetails();
			 String driverName = properties.getProperty("driver");
			 Class.forName(driverName);
			 String jdbcUrl = properties.getProperty("jdbcurl");
			 String username = properties.getProperty("username").trim();			 
			 String password = properties.getProperty("password").trim();
			 String enpassword = CryptingUtility.decryptPassword(password);	 
			 
			 connection = DriverManager.getConnection(jdbcUrl,  username , enpassword);
		 }
		 
		 return connection;
	 }
	 public static Properties loadDetails() throws Exception{
		 File f = new File("F://file_operation/database.properties");
		 if(f.exists()){
			 FileInputStream fis = new FileInputStream(f);
			 Properties pro = new Properties();
			 pro.load(fis);
			 return pro;			  
		 }
		 else {
			 throw new RuntimeException("File doesn't exist.");
		 }
	 }
}


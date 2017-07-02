package san.cs631.project;

import java.util.Base64;


public class CryptingUtility {
	
	public static String encryptPassword(String password){
		byte[] bytesToEncode = password.getBytes();
		return  Base64.getEncoder().withoutPadding().encodeToString(bytesToEncode);
	}
	
	public static String decryptPassword(String encryPassword){
		byte[] bytesDecoded = Base64.getDecoder().decode(encryPassword);
		 return (new String(bytesDecoded));
	}

}

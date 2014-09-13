package cz.cuni.mff.java.advanced.gallery.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException;

public class Encryptor {
	public static String makeSHA1Hash(String input) throws cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.reset();
			byte[] buffer = input.getBytes();
			md.update(buffer);
			byte[] digest = md.digest();
			 
			String hexStr = "";
			for (int i = 0; i < digest.length; i++) {
				hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
			}
			return hexStr;
		} catch(NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}
}

package es.cnc.util.app;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class HashUtils {
	
	/**
	 * Calculates MD5 of a String with default charset
	 * @param value
	 * @return
	 */
	public static String calcMD5Base64(String value) {
		return calcMD5Base64(value.getBytes());
	}
	
	/**
	 * Calculates MD5 of a String with charset
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String calcMD5Base64(String value, Charset charset) {
		return calcMD5Base64(value.getBytes(charset));
	}
	
	/**
	 * Calculates MD5 of a byte[] 
	 * @param value
	 * @return
	 */
	public static String calcMD5Base64(byte[] value) {
		MessageDigest md = null;
		String myHash = null;
		byte[] digest = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
			digest = calcHash(value, md);
			myHash = DatatypeConverter.printBase64Binary(digest);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		
		return myHash;
	}
	
	public static byte[] calcHash(byte[] valor, MessageDigest md) {
		byte[] digest = null;
		md.reset();
		md.update(valor);
		digest = md.digest();
		return digest;
	}
	
	public static boolean validateMD5(String md5B64, byte[] original) {
		String aux = null;
		aux = calcMD5Base64(original);
		return aux.equals(md5B64);
	}
}

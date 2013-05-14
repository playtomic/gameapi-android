package com.playtomic.android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;

public class PEncode {

	public static String md5(String value) {
		
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		
		algorithm.reset();
		algorithm.update(value.getBytes());
		byte messageDigest[] = algorithm.digest();
	            
		StringBuffer hexString = new StringBuffer();
		
		for (int i = 0; i < messageDigest.length; i++) {
			hexString.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		return hexString.toString();
	}
	
	/**
     * Encodes the string 'in' using 'flags'.  Asserts that decoding
     * gives the same string.  Returns the encoded string.
     */
    public static String base64(String in) {
        String b64 = Base64.encodeToString(in.getBytes(), Base64.NO_WRAP);
        return b64;
    }
}

package com.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Aesc {
	private static String sKey = "xmyh1234567890ab";
	
	private static String getKey(){
		if (sKey == null) {
			System.out.print("Key is null");
			return null;
		}

		if (sKey.length() != 16) {
			System.out.print("Key不为16位");
			return null;
		}
		return sKey;
	}
	public static String Decrypt(String sSrc) throws Exception {
		try {
			byte[] raw = getKey().getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, skeySpec);
			byte[] encrypted1 = hex2byte(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return null;
	}

	public static String Encrypt(String sSrc) throws Exception {
		byte[] raw = getKey().getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(1, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());
		return byte2hex(encrypted).toLowerCase();
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}

		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	public static void main(String[] args) {
		try {
			System.out.println(Encrypt("sso_jc"));
			System.out.println(Decrypt("fe76f2ec774246be9cc57d5a346bbf8f"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
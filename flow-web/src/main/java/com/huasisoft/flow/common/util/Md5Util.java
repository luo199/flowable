/**
 * Copyright (c) 2006 RiseSoft Co.,Ltd  
 * $Header$
 */
package com.huasisoft.flow.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String generateMd5(String source) throws Exception {
		byte[] strTemp = source.getBytes();
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
			str[k++] = HEX_DIGITS[byte0 & 0xf];
		}

		return new String(str);
	}
	
	//字符串 加密   encodingAlgorithm:MD5
	public static String encode(String encodingAlgorithm, String characterEncoding, final String password) {
		if (password == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(encodingAlgorithm);
			if (characterEncoding!=null && !"".equals(characterEncoding.trim())) {
				messageDigest.update(password.getBytes(characterEncoding));
			} else {
				messageDigest.update(password.getBytes());
			}
			final byte[] digest = messageDigest.digest();
			return getFormattedText(digest);
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);

		for (int j = 0; j < bytes.length; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

}

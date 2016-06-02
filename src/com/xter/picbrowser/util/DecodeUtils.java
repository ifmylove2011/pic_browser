package com.xter.picbrowser.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by XTER on 2016/1/14.
 */
public class DecodeUtils {
	public static String hashKeyFromUrl(String url) {
		String cacheKey;
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(url.getBytes());
			cacheKey = bytesToHexString(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(url.hashCode());
		}
		return cacheKey;
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1)
				sb.append("0");
			sb.append(hex);
		}
		return sb.toString();
	}
}

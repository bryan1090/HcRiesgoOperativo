package com.hc.ro.utils;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Password {

	public Password() {

	}

	/***************************************************************************************************/
	/***
	 * Cifra la contrasenia
	 * 
	 * **/
	public String encriptar(String texto) {
		String claveCifrada = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			claveCifrada = new String(base64Bytes);

		} catch (Exception e) {
			System.out
					.println("******************************************************************");
			System.out.println("Clase: Password");
			System.out.println("Metodo: encriptar(String texto)");
			System.out.println("Error al Cifrar la Clave:"
					+ e.getLocalizedMessage());
			System.out
					.println("******************************************************************");
		}
		return claveCifrada;
	}

	/***************************************************************************************************/
	/**
	 * Descifra la contrasenia
	 * 
	 * **/
	public String desencriptar(String textoEncriptado) {
		String claveDescifrada = "";
		try {
			byte[] message = Base64.decodeBase64(textoEncriptado
					.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainText = decipher.doFinal(message);
			claveDescifrada = new String(plainText, "UTF-8");
		} catch (Exception e) {
			System.out
					.println("******************************************************************");
			System.out.println("Clase: Password");
			System.out.println("Metodo: desencriptar(String textoEncriptado)");
			System.out.println("Error al Descifrar la Clave:"
					+ e.getLocalizedMessage());
			System.out
					.println("******************************************************************");
		}
		return claveDescifrada;
	}

	/***************************************************************************************************/
	private String secretKey = "345tgbh789ok";

	/***************************************************************************************************/

}

package me.mohammad.simpleencryption;

import java.util.HashMap;
import java.util.Map;

public class SimpleEncryption {
	
	private static SimpleEncryption instance;
	
	public static SimpleEncryption openInstance() {
		return instance = instance != null ? instance : new SimpleEncryption();
	}
	
	private Map<Character, String> encryptedMap;
	private Map<String, Character> decryptedMap;
	
	private String characters = " 	^!\"$%&/()=?\\+*~#'-_.:,;{[]}`<>|AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
	
	private SimpleEncryption() {
		instance = this;
		encryptedMap = new HashMap<>();
		decryptedMap = new HashMap<>();
		initializeMaps();
	}
	
	private void initializeMaps() {
		for (final Character character : characters.toCharArray()) {
			final String characterCode = String.valueOf(((int) character) * 8921 / 121 + 11);
			encryptedMap.put(character, characterCode);
			decryptedMap.put(characterCode, character);
		}
	}
	
	private Character decrypt(final String encrypted) {
		return decryptedMap.get(encrypted);
	}
	
	private String encrypt(final Character decrypted) {
		return encryptedMap.get(decrypted);
	}
	
	private String[] chunk(final String string, final int chunkSize) {
		return string.split(String.format("(?<=\\G.{%s})", chunkSize));
	}
	
	public String encryptString(final String decrypted) {
		final StringBuilder builder = new StringBuilder();
		for (final Character toEncrypt : decrypted.toCharArray()) {
			builder.append(encrypt(toEncrypt));
		}
		return builder.toString();
	}
	
	public String decryptString(final String encrypted) {
		final StringBuilder builder = new StringBuilder();
		final String[] chunks = chunk(encrypted, 4);
		for (final String toDecrypt : chunks) {
			builder.append(decrypt(toDecrypt));
		}
		return builder.toString();
	}
	
}

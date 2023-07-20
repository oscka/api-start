package com.osckorea.openmsa.global.util.encrypt;

public interface Encryptor {
	EncryptedField encrypt(String text);

	String decrypt(byte[] cipherText, byte[] nonce);

}

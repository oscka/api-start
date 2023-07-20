package com.osckorea.openmsa.global.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESEncryptor implements Encryptor {

    public static final int AES_KEY_SIZE = 256;
    public static final int GCM_IV_LENGTH = 16;
    public static final int GCM_TAG_LENGTH = 16;

    public static byte[] getKey() {
        String encodedKey = "T51EZgpjAcG4as5h0/Riwi0ig5NvgUAJf0ufQ0TIGRw=";
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedKey = decoder.decode(encodedKey);
        if (decodedKey.length != 32) {
            throw new IllegalArgumentException();
        }
        return decodedKey;
    }

    public static byte[] generateRandomIV() {
        byte[] IV = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);

        return IV;
    }

    @Override
    public EncryptedField encrypt(String text) {
        try{
            Cipher cipher = Cipher.getInstance("AES/GCM/Nopadding");
            SecretKeySpec keySpec = new SecretKeySpec(getKey(), "AES");
            byte[] nonce = generateRandomIV();
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
            byte[] cipherText = cipher.doFinal(text.getBytes());
            return new EncryptedField(nonce, cipherText);
        }catch (Exception e){
            throw new RuntimeException("encrypt fail");
        }
    }

    @Override
    public String decrypt(byte[] cipherText, byte[] nonce) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(getKey(), "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e){
            throw new RuntimeException("decrypt fail");
        }
    }


}

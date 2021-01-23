package com.basiauth.basiauth.config;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OriginalPasswordEncrypter implements PasswordEncoder {
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }

    @Override
    public String encode(CharSequence rawPassword) {
    	String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = rawPassword.toString().getBytes(StandardCharsets.UTF_8);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.encodeBase64(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(decrypt(encodedPassword));
    }

    public OriginalPasswordEncrypter() throws Exception {
        myEncryptionKey = "ThisIsSpartaThisIsSparta";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(StandardCharsets.UTF_8);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }


//    public String encrypt(String unencryptedString) {
//        String encryptedString = null;
//        try {
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            byte[] plainText = unencryptedString.getBytes(StandardCharsets.UTF_8);
//            byte[] encryptedText = cipher.doFinal(plainText);
//            encryptedString = new String(Base64.encodeBase64(encryptedText));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return encryptedString;
//    }


    public String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }



//        TrippleDes td= new TrippleDes();
//
//        String target="imparator";
//        String encrypted=td.encrypt(target);
//        String decrypted=td.decrypt(encrypted);
//
//        System.out.println("String To Encrypt: "+ target);
//        System.out.println("Encrypted String:" + encrypted);
//        System.out.println("Decrypted String:" + decrypted);



}



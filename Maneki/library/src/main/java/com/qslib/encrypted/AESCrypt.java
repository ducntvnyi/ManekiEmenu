package com.qslib.encrypted;

import android.util.Log;

import com.google.common.io.BaseEncoding;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Dang on 4/5/2016.
 */
@SuppressWarnings("ALL")
public class AESCrypt {
    //AESCrypt-ObjC uses CBC and PKCS7Padding
    private static final String AES_MODE = "AES/CBC/PKCS7Padding";
    private static final String CHARSET = "UTF-8";
    //AESCrypt-ObjC uses SHA-256 (and so a 256-bit key)
    private static final String HASH_ALGORITHM = "SHA-256";
    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;

    public AESCrypt(String password) throws Exception {
        // hash password with SHA-256 and crop the output to 128-bit for key
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        // get bytes from password
        byte[] bytes = password.getBytes(CHARSET);
        digest.update(bytes, 0, bytes.length);
        // get key byte from message digest
        byte[] keyBytes = digest.digest();

        Log.e("keyBytes","keyBytes:: " + new String((keyBytes)));

        cipher = Cipher.getInstance(AES_MODE);
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV();
    }

    public static void main() {
        try {
            AESCrypt aesCrypt = new AESCrypt("qsoft");
            String encrypt = aesCrypt.encrypt("Hello world!");
            Log.e("encrypt", "AESCrypt:: encrypt:: " + encrypt);
            String decrypt = aesCrypt.decrypt(encrypt);
            Log.e("decrypt", "AESCrypt:: decrypt:: " + decrypt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AlgorithmParameterSpec getIV() {
        byte[] iv = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return new IvParameterSpec(iv);
    }

    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(CHARSET));
        return BaseEncoding.base64().encode(encrypted);
    }

    public String decrypt(String cryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = BaseEncoding.base64().decode(cryptedText);
        byte[] decrypted = cipher.doFinal(bytes);
        return new String(decrypted, CHARSET);
    }
}

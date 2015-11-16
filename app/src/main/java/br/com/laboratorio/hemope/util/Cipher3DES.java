package br.com.laboratorio.hemope.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by rafaell.tenorio on 25/06/2015.
 */
public class Cipher3DES {
    SecretKeySpec chave;
    IvParameterSpec iv;
    Cipher cifrador;

    public Cipher3DES(String key, String initializationVector) throws Exception {
        cifrador = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        chave = new SecretKeySpec(key.getBytes("UTF8"), "DESede");
        iv = new IvParameterSpec(initializationVector.getBytes());
    }

    public String encryptText(String original) throws Exception {
        byte[] plaintext = original.getBytes("UTF8");
        cifrador.init(Cipher.ENCRYPT_MODE, chave, iv);
        byte[] cipherText = cifrador.doFinal(plaintext);
        return Base64.encodeToString(cipherText, Base64.URL_SAFE | Base64.NO_WRAP);
    }

    public String decryptText(String hidden) throws Exception {
        byte[] hiddentext = Base64.decode(hidden.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        cifrador.init(Cipher.DECRYPT_MODE, chave, iv);
        byte[] originalText = cifrador.doFinal(hiddentext);
        return new String(originalText);
    }
}

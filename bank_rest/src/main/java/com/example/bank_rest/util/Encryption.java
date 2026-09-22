package com.example.bank_rest.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class Encryption {

  private final String key = "322keyencryption";

  private final SecretKey secretKey;

  private final IvParameterSpec ivParameterSpec;

  private Cipher cipher;

  public Encryption() throws NoSuchPaddingException, NoSuchAlgorithmException {
    this.secretKey = new SecretKeySpec(key.getBytes(), "AES");
    this.ivParameterSpec = new IvParameterSpec(new byte[16]);
    this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
  }

  public String encryptCardNumber(String cardNumber)
      throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
    return Base64.getEncoder().encodeToString(cipher.doFinal(cardNumber.getBytes()));
  }

  public String decryptCardNumber(String cardNumber)
      throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
    return new String(cipher.doFinal(Base64.getDecoder().decode(cardNumber.getBytes())));
  }
}

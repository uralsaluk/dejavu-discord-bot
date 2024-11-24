package com.ural.dejavutools.discordbot.service.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

  private static final String ALGORITHM = "AES";
  private static final int KEY_SIZE = 128;

  // Generate a new AES key
  public static SecretKey generateKey() throws Exception {
    KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
    keyGen.init(KEY_SIZE);
    return keyGen.generateKey();
  }

  // Convert a string key to a SecretKey object
  public static SecretKey getKeyFromString(String key) {
    byte[] decodedKey = Base64.getDecoder().decode(key);
    return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
  }

  // Convert a SecretKey object to a string
  public static String getStringFromKey(SecretKey key) {
    return Base64.getEncoder().encodeToString(key.getEncoded());
  }

  // Encrypt a string
  public static String encrypt(String data, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] encryptedBytes = cipher.doFinal(data.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  // Decrypt a string
  public static String decrypt(String encryptedData, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decryptedBytes = Base64.getDecoder().decode(encryptedData);
    byte[] original = cipher.doFinal(decryptedBytes);
    return new String(original);
  }
}

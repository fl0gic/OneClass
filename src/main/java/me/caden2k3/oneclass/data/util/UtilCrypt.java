package me.caden2k3.oneclass.data.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class UtilCrypt {

  /**
   * Encrypts a string using the specified key.
   *
   * @param string The string to be encrypted.
   * @param key The key to be used to encrypt the string.
   * @return The encrypted String.
   */
  public static String encrypt(String string, String key) {
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

      //1 to encrypt.
      cipher.init(1, getKeySpec(key));

      return Base64.encodeBase64String(cipher.doFinal(string.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * Decrypts a string using the specified key.
   *
   * @param string The string to be decrypted.
   * @param key The key to be used to encrypt the string.
   * @return The decrypted String.
   */
  public static String decrypt(String string, String key) {
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

      //2 to decrypt.
      cipher.init(2, getKeySpec(key));
      return new String(cipher.doFinal(Base64.decodeBase64(string)));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  private static SecretKeySpec getKeySpec(String myKey) {
    MessageDigest sha;
    try {
      byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = java.util.Arrays.copyOf(key, 16);
      return new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }

    return null;
  }
}

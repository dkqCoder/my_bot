package com.tty.user.common.utils;

import com.jdd.fm.core.utils.PropertiesUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author
 * des 加解密
 */
public class DesUtils {

    private static final String PASSWORD_CRYPT_KEY =  PropertiesUtil.get("url.des.key");

    /**
     * 解密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, String key) throws Exception {
        byte[] byteSrc = convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(byteSrc);
        return new String(retByte);
    }

    private static byte[] encrypt(String message, String key)
            throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    /**
     * 加密
     * @param value
     * @return
     */
    public static String encrypt(String value) {
        String result = "";
        try {
            value = java.net.URLEncoder.encode(value, "utf-8");
            result = toHexString(encrypt(value, PASSWORD_CRYPT_KEY)).toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return result;
    }

    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    public static void main(String[] args) throws Exception {
        String value = "gadfsvgerh2345a2.xgg69@";
        System.out.println("加密数据:" + value);
        String a = encrypt(value);
        System.out.println("加密后的数据为:" + a);
        String b = decrypt(a, PASSWORD_CRYPT_KEY);
        System.out.println("解密后的字符串=" + b);
    }
}

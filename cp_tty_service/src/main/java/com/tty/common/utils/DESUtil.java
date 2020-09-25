package com.tty.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @desc:
 * @author: wangbinyi
 * @create: 2017-07-25 17:52
 */
public class DESUtil {
    private static final String CHARSET = "utf-8";

    public DESUtil() {
    }

    public static String desEncrypt(String message, String desKey) {
        String encryptStr = null;

        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(1, genSecretKey(desKey));
            byte[] cipherText = cipher.doFinal(message.trim().getBytes("utf-8"));
            byte[] encryptByte = Base64.encodeBase64(cipherText);
            encryptStr = new String(encryptByte, "UTF-8");
            return encryptStr;
        } catch (Throwable var6) {
            throw new RuntimeException("des加密发生异常", var6);
        }
    }

    public static String desDecrypt(String cipherText, String desKey) {
        String decryptStr = null;

        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            byte[] input = Base64.decodeBase64(cipherText.trim().getBytes("UTF-8"));
            cipher.init(2, genSecretKey(desKey));
            byte[] output = cipher.doFinal(input);
            decryptStr = new String(output, "utf-8");
            return decryptStr;
        } catch (Throwable var6) {
            throw new RuntimeException("des解密发生异常", var6);
        }
    }

    private static SecretKey genSecretKey(String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        return secretKey;
    }

    protected static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static void main(String[] args) {
        String body = "<body><ltype>QGSLTO</ltype><periodnum>2010118</periodnum><merchantuserid>11111111</merchantuserid><username>andy</username><idno>130324198804114517</idno><mobile>13021902050</mobile><records><record><orderno>12313106355756701</orderno><playtype>1</playtype><chipintype>2</chipintype><content>02|11|13|14|22|31-09*1</content><orderamount>200</orderamount></record></records></body>";
        String key = "1234567890ABCDEF";
        String encryptStr = desEncrypt(body, key);
        String decryptStr = desDecrypt(encryptStr, key);
        System.out.println(encryptStr);
        System.out.println(decryptStr);
    }
}

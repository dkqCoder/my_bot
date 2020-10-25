package com.tty.user.common.tupu;


import com.tty.user.common.utils.Base64Util;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 鉴权
 *
 * @author soap
 */
public class SignatureAndVerifyUtil {

    public static final String TASKID = "56a8645b0c800bff40990cf1";

    public static final String SECRETID = "58fecf16c31547938d6c4055";

    public static final String TUPUURL = "http://api.open.tuputech.com/v3/recognition/58fecf16c31547938d6c4055";

    private static final String PRIVATEKEYSTRING = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMZ7HzdiRb/zA3LB\n" +
            "AInIpVQCT9dzPHHe0IiG0du0zLB2oyHVTx5623P1dOjXKtbLt3N9P4p2uFfIDZVW\n" +
            "o4NtwnV/0vgWw5l2tR0rj1r173rNfo6vR4FT62XynqiQReBR0U0J3fTbjg0ch2Ab\n" +
            "U30UYLra5t5kCnNQNetbY6b6ZNthAgMBAAECgYEAv77YVHgC7EJH9YeRDoshdTxa\n" +
            "h8jS/ysDiLG+caOED4I9bQUWz1pfQfJChGLCBwp9avv387tnsZ4ZyUkO5HyX25ni\n" +
            "KJLp2WGdFbkbmnXJqR2xG1YqqjKvOqnoB/3hPGR0DRE1gNSKfFF/azA2WQic8AbJ\n" +
            "7TNQIf1MTAUF71BltQECQQDo5WNsGJE7cN8KS7BfOy9YOzsQqvvrx5kEN+tRvkN1\n" +
            "XKnTxxjCfPUWC8GmwRnNe7PCNkAScdSzjcYedlkKpmdRAkEA2iu644AeSlrLodGo\n" +
            "AN0EKdD0bsYeQQ35Cko3rMGLp1f98beSoAObCizynst/+BW0I5hcsob/SK+xdkEo\n" +
            "wpZPEQJBAKyAa3m/YKMkYS/hDAwdbF3v0TnxS5NCTeY//P6Y7KJbNjb0ezRjFa10\n" +
            "m8UFtjTq8SRixmZjBebQlfQuOJmY02ECQFSPKHNuv86/3DcO5JfZEfkHJQYeQbGj\n" +
            "Mcg8NdH+fhtRLnrWVL0ySJAsFalYZHQKLXSyZBNJ4X4Roq2b2al5ypECQCZlEGNS\n" +
            "AVcOv6uzICv6QNnCvQ5OWLRFAwNwo3ShSRP5lXwDOr7q0Ja3kwSV+Vb6Aivoj25X\n" +
            "UQALjNCVPwi9px0=";

    private static final String PUBLICKKEYSTRING = "-----BEGIN PUBLIC KEY-----" + "\n"
            + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDyZneSY2eGnhKrArxaT6zswVH9" + "\n"
            + "/EKz+CLD+38kJigWj5UaRB6dDUK9BR6YIv0M9vVQZED2650tVhS3BeX04vEFhThn" + "\n"
            + "NrJguVPidufFpEh3AgdYDzOQxi06AN+CGzOXPaigTurBxZDIbdU+zmtr6a8bIBBj" + "\n" + "WQ4v2JR/BA6gVHV5TwIDAQAB"
            + "\n" + "-----END PUBLIC KEY-----";

    public static final String TUPUEREQUESTRROR = "图像识别失败";

    public static final String TUPUQRCODE = "你上传的图像包含二维码，请重新提交";

    public static final String TUPUCHARACTER = "请不要上传带有文字信息的图像";

    /**
     * 读取用户私钥
     *
     * @return
     */
    public static PrivateKey readPrivateKey() {


        // 读取你的密钥pkcs8_private_key.pem
//		File private_key_pem = new File(privateKeyPath);
        PrivateKey privateKey = null;
        try {
//			InputStream inPrivate = new FileInputStream(private_key_pem);
            String privateKeyStr;
//			if (StringUtils.isNotEmpty(privateKeyPath)){
            privateKeyStr = PRIVATEKEYSTRING;
//			}else{
//				privateKeyStr = readKey(inPrivate);
//			}

            byte[] buffer = Base64Util.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // 获取密钥对象
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            System.out.println(e);
        }
        return privateKey;
    }

    /**
     * 进行签名
     *
     * @param sign_string
     **/
    public static String Signature(PrivateKey privateKey, String sign_string) {
        try {
            // 用私钥对信息生成数字签名
            Signature signer = Signature.getInstance("SHA256WithRSA");
            signer.initSign(privateKey);
            signer.update(sign_string.getBytes());
            byte[] signed = signer.sign();
            return new String(Base64Util.encode(signed));
        } catch (Exception e) {
            return "err";
        }
    }

    /**
     * 进行验证
     *
     * @param signature 数字签名
     * @param json      真正的有效数据的字符串
     **/
    public static boolean Verify(String signature, String json) {
        try {

            InputStream inPublic = new ByteArrayInputStream(PUBLICKKEYSTRING.getBytes("UTF-8"));
            String publicKeyStr = readKey(inPublic);
            byte[] buffer = Base64Util.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            // 获取公钥匙对象
            PublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            Signature signer = Signature.getInstance("SHA256WithRSA");
            signer.initVerify(pubKey);
            signer.update(json.getBytes("UTF-8"));
            // 验证签名是否正常
            return signer.verify(Base64Util.decode(signature));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }
        return sb.toString();
    }
}
/*
 *@ClassName   SymmUtil
 *@Description create class
 *@Author      kong
 *@Date        2021/5/10 上午11:04
 *@Version     1.0
 */
package com.enc.common.enccommon.utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class SymmUtil extends BaseUtil {

    public static final int ENC = Cipher.ENCRYPT_MODE;
    public static final int DEC = Cipher.DECRYPT_MODE;

    /**
     * 加载 bc 包，获取 Cipher 对象
     * @param algorithm eg:AES/ECB/NoPadding
     */
    public static Cipher getCipher(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        if (algorithm == null || algorithm.length() == 0) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
        return cipher;
    }

    /**
     * 加密
     * @param algorithm  eg:AES/ECB/NoPadding
     * @param cipherMode Cipher.ENCRYPT_MODE / Cipher.DECRYPT_MODE
     */
    public static byte[] crypto(String algorithm, int cipherMode, byte[] keyBytes, byte[] ivBytes, byte[] inBytes) throws Exception {
        if (algorithm == null   || algorithm.length() == 0
            || keyBytes == null || keyBytes.length == 0
            || inBytes == null  || inBytes.length == 0) {
            return null;
        }
        Cipher cipher = getCipher(algorithm);
        Key keySpec   = new SecretKeySpec(keyBytes, "");
        IvParameterSpec ivSpec = null;
        if (ivBytes != null && ivBytes.length > 0) {
            ivSpec = new IvParameterSpec(ivBytes);
        }
        cipher.init(cipherMode, keySpec, ivSpec);
        byte[] value = cipher.doFinal(inBytes);
        return value;
    }
}
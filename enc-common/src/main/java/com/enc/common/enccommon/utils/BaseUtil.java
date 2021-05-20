/*
 *@ClassName   BaseUtil
 *@Description create class
 *@Author      kong
 *@Date        2021/5/17 下午1:31
 *@Version     1.0
 */
package com.enc.common.enccommon.utils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

public class BaseUtil {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /* 点乘方法 */
    public static ECPoint ecPointMul(ECPoint ecPoint, BigInteger bigInteger){
        if (ecPoint == null || bigInteger == null)
            return null;
        ECMultiplier multiplier = new FixedPointCombMultiplier();
        return multiplier.multiply(ecPoint, bigInteger);
    }

    /* 获取公钥 X 值 */
    public static byte[] getPublicKeyX(PublicKey publicKey) {
        if (publicKey == null)
            return null;
        BCECPublicKey pubKey = (BCECPublicKey)publicKey;
        byte[] xBytes = pubKey.getQ().getAffineXCoord().getEncoded();
        return xBytes;
    }

    /* 获取公钥 Y 值 */
    public static byte[] getPublicKeyY(PublicKey publicKey) {
        if (publicKey  == null)
            return null;
        BCECPublicKey pubKey = (BCECPublicKey)publicKey;
        byte[] yBytes = pubKey.getQ().getAffineYCoord().getEncoded();
        return yBytes;
    }

    /* 获取私钥 D 值 */
    public static byte[] getPrivateKeyD(PrivateKey privateKey) {
        if (privateKey == null)
            return null;
        BCECPrivateKey priKey = (BCECPrivateKey)privateKey;
        byte[] dBytes = priKey.getD().toByteArray();
        if (dBytes.length == 33 && dBytes[0] == 0x00) {
            byte[] dBytes1 = new byte[32];
            System.arraycopy(dBytes, 1, dBytes1, 0, 32);
            return dBytes1;
        }
        return dBytes;
    }
}

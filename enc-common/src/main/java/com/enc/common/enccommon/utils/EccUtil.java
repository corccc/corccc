/*
 *@ClassName   EccUtil
 *@Description create class
 *@Author      kong
 *@Date        2021/5/21 上午10:14
 *@Version     1.0
 */
package com.enc.common.enccommon.utils;

import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import sun.awt.geom.Curve;

import javax.crypto.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;

public class EccUtil extends BaseUtil{
    /**
     * 生成密钥生成器
     * secp128r1, secp160k1, secp160r1, secp192k1, secp192r1, secp224k1, secp224r1, secp256k1, secp256r1.
     * @return 密钥生成器
     */
    public static KeyPairGenerator generateKeyPairGenerator(String spec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        ECGenParameterSpec sm2Spec = new ECGenParameterSpec(spec);
        kpg.initialize(sm2Spec);
        kpg.initialize(sm2Spec, new SecureRandom());
        return kpg;
    }

    /**
     * 生成密钥对
     * @return KeyPair 密钥对
     */
    public static KeyPair generateKeyPair(String spec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = generateKeyPairGenerator(spec);
        KeyPair keyPair = kpg.generateKeyPair();
        return keyPair;
    }

    /**
     * x，y 转换为 PublicKey
     * @param x BigInteger x
     * @param y BigInteger y
     * @return PublicKey
     */
    public static PublicKey convertPublicKey(String spec, BigInteger x, BigInteger y) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (x == null || y == null)
            return null;
        KeyPairGenerator kpg = generateKeyPairGenerator(spec);
        KeyPair keyPair = kpg.generateKeyPair();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();
        ECCurve curve = ECCurveUtil.getCurve(spec);
        ECPoint ecPoint = curve.createPoint(x, y);
        ECPublicKeySpec keySpec = new ECPublicKeySpec(ecPoint, publicKey.getParameters());
        return KeyFactory.getInstance("EC").generatePublic(keySpec);
    }

    /**
     * p 转换为 PrivateKey
     * @param p BigInteger p
     * @return PrivateKey
     */
    public static PrivateKey convertPrivateKey(String spec, BigInteger p) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (p == null)
            return null;
        KeyPairGenerator kpg = generateKeyPairGenerator(spec);
        KeyPair keyPair = kpg.generateKeyPair();
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        ECPrivateKeySpec keySpec = new ECPrivateKeySpec(p, privateKey.getParameters());
        return KeyFactory.getInstance("EC").generatePrivate(keySpec);
    }

    /**
     * 根据私钥生成公钥
     * @param privateKey 私钥
     * @return 公钥
     */
    public static PublicKey generatePublicKey(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (privateKey == null)
            return null;
        ECPoint GPoint          = ((BCECPrivateKey) privateKey).getParameters().getG();
        ECPoint pubPoint        = GPoint.multiply(((BCECPrivateKey) privateKey).getD());
        ECPublicKeySpec keySpec = new ECPublicKeySpec (pubPoint, ((BCECPrivateKey) privateKey).getParameters());
        return KeyFactory.getInstance("EC").generatePublic(keySpec);
    }

    /**
     * 加密
     * @param publicKey 公钥
     * @param in        待加密数据
     * @return 密文值
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] in) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (publicKey == null || in == null)
            return null;
        Cipher cipher = Cipher.getInstance("ECIES", new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enc = cipher.doFinal(in);
        return enc;
    }

    /**
     * 解密
     * @param privateKey 私钥
     * @param in         待解密数据
     * @return 明文值
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] in) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (privateKey == null || in == null)
            return null;
        Cipher cipher = Cipher.getInstance("ECIES", new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dec = cipher.doFinal(in);
        return dec;
    }

    /**
     * 签名
     * @param privateKey 私钥
     * @param tbs        签名原文
     * @return 签名结果
     */
    public static byte[] sign(String algorithm, PrivateKey privateKey, byte[] tbs) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(tbs);
        return signature.sign();
    }

    /**
     * 验证签名
     * @param publicKey 公钥
     * @param tbs       签名原文
     * @param sign      签名值
     * @return 验证结果
     */
    public static boolean verify(String algorithm, PublicKey publicKey, byte[] tbs, byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signer = Signature.getInstance(algorithm);
        signer.initVerify(publicKey);
        signer.update(tbs);
        return (signer.verify(sign));
    }


    /**
     * ECDH
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return ecdh 结果
     */
    public static byte[] ecdh(PrivateKey privateKey, PublicKey publicKey) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
        if (privateKey == null || publicKey == null)
            return null;
        BCECPublicKey  pubKey = (BCECPublicKey) publicKey;
        BCECPrivateKey priKey = (BCECPrivateKey) privateKey;

        System.out.println(Hex.toHexString(Sm2Util.getPrivateKeyD(privateKey)));
        System.out.println(publicKey.toString());

        ECPoint pubPoint      = pubKey.getQ();
        ECPoint point         = pubPoint.multiply(priKey.getD());
        return point.getEncoded(false);
    }

    /**
     * 通过 X 计算 Y
     * @param spec 曲线信息
     * @param x    公钥 X
     * @return 公钥 Y
     */
    public static byte[] calY(String spec, byte[] x) {
        byte[] pxBytes = new byte[x.length + 1];
        byte[] tBytes = new byte[1];
        System.arraycopy(x, x.length - 1, tBytes, 0, 1);
        BigInteger pxInteger = new BigInteger(tBytes);
        BigInteger divisor = new BigInteger("2");
        System.out.println(Hex.toHexString(tBytes));
        if (pxInteger.remainder(divisor) == BigInteger.ZERO) {
            byte[] tag = {0x02};
            System.arraycopy(tag, 0, pxBytes, 0, 1);
        } else {
            byte[] tag = {0x03};
            System.arraycopy(tag, 0, pxBytes, 0, 1);
        }
        ECCurve curve = ECCurveUtil.getCurve(spec);
        ECPoint point = curve.decodePoint(pxBytes);
        return point.getAffineYCoord().getEncoded();
    }
}

/*
 *@ClassName   Sm2Util
 *@Description create class
 *@Author      kong
 *@Date        2021/5/14 下午4:24
 *@Version     1.0
 */
package com.enc.common.enccommon.utils;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.provider.digest.SM3;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

public class Sm2Util extends BaseUtil {

    /**
     * 生成密钥生成器
     * @return 密钥生成器
     */
    public static KeyPairGenerator generateKeyPairGenerator() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
        kpg.initialize(sm2Spec);
        kpg.initialize(sm2Spec, new SecureRandom());
        return kpg;
    }

    /**
     * 生成密钥对
     * @return KeyPair 密钥对
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = generateKeyPairGenerator();
        KeyPair keyPair = kpg.generateKeyPair();
        return keyPair;
    }

    /**
     * x，y 转换为 PublicKey
     * @param x BigInteger x
     * @param y BigInteger y
     * @return PublicKey
     */
    public static PublicKey convertPublicKey(BigInteger x, BigInteger y) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (x == null || y == null)
            return null;
        KeyPairGenerator kpg = generateKeyPairGenerator();
        KeyPair keyPair = kpg.generateKeyPair();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();
        ECPoint ecPoint = new SM2P256V1Curve().createPoint(x, y);
        ECPublicKeySpec keySpec = new ECPublicKeySpec(ecPoint, publicKey.getParameters());
        return KeyFactory.getInstance("EC").generatePublic(keySpec);
    }

    /**
     * p 转换为 PrivateKey
     * @param p BigInteger p
     * @return PrivateKey
     */
    public static PrivateKey convertPrivateKey(BigInteger p) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (p == null)
            return null;
        KeyPairGenerator kpg = generateKeyPairGenerator();
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
    public static byte[] encrypt(PublicKey publicKey, byte[] in) throws InvalidCipherTextException, IOException {
        if (publicKey == null || in == null)
            return null;
        SM2Engine sm2Engine = new SM2Engine();
        ECPublicKeyParameters aPub = (ECPublicKeyParameters) PublicKeyFactory.createKey(publicKey.getEncoded());
        sm2Engine.init(true, new ParametersWithRandom(aPub, new SecureRandom()));
        byte[] enc = sm2Engine.processBlock(in, 0, in.length);
        return enc;
    }

    /**
     * 解密
     * @param privateKey 私钥
     * @param in         待解密数据
     * @return 明文值
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] in) throws InvalidCipherTextException, IOException {
        if (privateKey == null || in == null)
            return null;
        SM2Engine sm2Engine = new SM2Engine();
        ECPrivateKeyParameters aPriv = (ECPrivateKeyParameters) PrivateKeyFactory.createKey(privateKey.getEncoded());
        sm2Engine.init(false, aPriv);
        byte[] dec = sm2Engine.processBlock(in, 0, in.length);
        return dec;
    }

    /**
     * 签名
     * @param privateKey 私钥
     * @param tbs        签名原文
     * @param withId     ID
     * @return 签名结果
     */
    public static byte[] sign(PrivateKey privateKey, byte[] tbs, byte[] withId) throws IOException, CryptoException {
        if (privateKey == null || tbs == null || withId == null)
            return null;
        SM2Signer signer = new SM2Signer();
        CipherParameters param = null;
        ECPrivateKeyParameters aPri = (ECPrivateKeyParameters) PrivateKeyFactory.createKey(privateKey.getEncoded());
        ParametersWithRandom pwr = new ParametersWithRandom(aPri, new SecureRandom());
        if (withId != null) {
            param = new ParametersWithID(pwr, withId);
        } else {
            param = pwr;
        }
        signer.init(true, param);
        signer.update(tbs, 0, tbs.length);
        return signer.generateSignature();
    }

    /**
     * 验证签名
     * @param publicKey 公钥
     * @param tbs       签名原文
     * @param sign      签名值
     * @param withId    ID
     * @return 验证结果
     */
    public static boolean verify(PublicKey publicKey, byte[] tbs, byte[] sign, byte[] withId) throws IOException {
        if (publicKey == null || tbs == null || sign == null || withId == null)
            return false;
        SM2Signer signer = new SM2Signer();
        CipherParameters param = null;
        ECPublicKeyParameters aPub = (ECPublicKeyParameters) PublicKeyFactory.createKey(publicKey.getEncoded());
        if (withId != null) {
            param = new ParametersWithID(aPub, withId);
        } else {
            param = aPub;
        }
        signer.init(false, param);
        signer.update(tbs, 0, tbs.length);
        return signer.verifySignature(sign);
    }
}

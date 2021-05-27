package org.zz.gmhelper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.HexEncoder;
import org.zz.gmhelper.skf.SKFUtil;

public class CPKUtil implements ECConstants {

    public static final int SM2_N_BITLENGTH = 256;
    public static final SM2P256V1Curve CURVE = new SM2P256V1Curve();
    public final static BigInteger SM2_ECC_N = CURVE.getOrder();
    public final static BigInteger SM2_ECC_GX = new BigInteger(
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
    public final static BigInteger SM2_ECC_GY = new BigInteger(
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
    public static final ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);


    /**
     * @return CPKMatrix
     * @description: 产生CPK密钥矩阵
     */
    public CPKMatrix generateKeyPairMatrix() {
        CPKMatrix cpkMatrix = null;
        byte[] priKeyMatrix = new byte[256 + 32 * 32 * 32];//256字节头(矩阵名称|生成时间|hash|sig)+32*32矩阵的私钥（32字节）
        byte[] pubKeyMatrix = new byte[256 + 32 * 32 * 65];//256字节头+32*32矩阵的公钥（65字节 04|x|y）
        for (int i = 0; i < 32 * 32; i++) {
            BigInteger d = getBigRand();        //产生私钥d
            ECPoint P = ((new FixedPointCombMultiplier()).multiply(G_POINT, d)).normalize(); //P=d*G
            byte[] priKey = fixTo32Bytes(d.toByteArray());
            byte[] pubKey = org.bouncycastle.util.Arrays.concatenate(new byte[]{0x4}, fixTo32Bytes(P.getAffineXCoord().getEncoded()), fixTo32Bytes(P.getAffineYCoord().getEncoded()));
            System.arraycopy(priKey, 0, priKeyMatrix, 256 + i * 32, 32);
            System.arraycopy(pubKey, 0, pubKeyMatrix, 256 + i * 65, 65);
        }
        cpkMatrix = new CPKMatrix(priKeyMatrix, pubKeyMatrix);
        return cpkMatrix;
    }

    /**
     * @param priKeyMatrix 私钥矩阵
     * @param userID       用户ID
     * @return 用户私钥，32字节
     * @description: <根据用户ID和私钥矩阵计算用户私钥>
     */
    public byte[] computeCPKPriKey(byte[] priKeyMatrix, byte[] userID) {
        if (priKeyMatrix == null || (priKeyMatrix.length != (256 + 33 * 32 * 32))) {
            return null;
        }
        byte[] priKey = null;
        byte[] hash = new byte[32];
        convertIDToHash(userID, hash);

        byte[] key = new byte[32];
        BigInteger bPriKey = BigInteger.valueOf(0);
        for (int i = 0; i < 32; i++) {
            System.arraycopy(priKeyMatrix, 256 + 33 * (32 * hash[i] + i) + 1, key, 0, 32);
            BigInteger bKey = new BigInteger(1, key);
            bPriKey = bPriKey.add(bKey).mod(SM2_ECC_N);
        }
        priKey = fixTo32Bytes(bPriKey.toByteArray());
        return priKey;
    }

    /**
     * @param priKey 用户私钥
     * @return 用户公钥，格式为：04|x|y
     * @description: <根据用户私钥计算用户公钥>
     */
    public byte[] computeCPKPubKey(byte[] priKey) {
        if (priKey == null || (priKey.length != 32)) {
            return null;
        }
        BigInteger d = new BigInteger(1, priKey);
        ECPoint pubKeyPoint = ((new FixedPointCombMultiplier()).multiply(G_POINT, d)).normalize(); //P=d*G
        byte[] pubKey = org.bouncycastle.util.Arrays.concatenate(new byte[]{0x4}, fixTo32Bytes(pubKeyPoint.getAffineXCoord().getEncoded()), fixTo32Bytes(pubKeyPoint.getAffineYCoord().getEncoded()));
        return pubKey;
    }

    /**
     * @param pubKeyMatrix 公钥矩阵
     * @param userID       用户ID
     * @return 用户公钥，格式为：04|x|y
     * @description: <根据用户ID和公钥矩阵计算用户公钥>
     */
    public byte[] computeCPKPubKey(byte[] pubKeyMatrix, byte[] userID) {
        if (pubKeyMatrix == null || (pubKeyMatrix.length != (256 + 32 * 32 * 65))) {
            return null;
        }
        byte[] pubKey = null;
        byte[] hash = new byte[32];
        convertIDToHash(userID, hash);

        byte[] key = new byte[64];
        ECPoint pubKeyPoint = null;
        ECPoint pointP = null;
        for (int i = 0; i < 32; i++) {
            System.arraycopy(pubKeyMatrix, 256 + 65 * (32 * hash[i] + i) + 1, key, 0, 64);
            byte[] Px = org.bouncycastle.util.Arrays.copyOfRange(key, 0, 32);
            byte[] Py = org.bouncycastle.util.Arrays.copyOfRange(key, 32, 64);

            pointP = xyToPoint(Px, Py);
            if (i == 0) {
                pubKeyPoint = pointP;
            } else {
                pubKeyPoint = pubKeyPoint.add(pointP).normalize();
            }
        }
        pubKey = org.bouncycastle.util.Arrays.concatenate(new byte[]{0x4}, fixTo32Bytes(pubKeyPoint.getAffineXCoord().getEncoded()), fixTo32Bytes(pubKeyPoint.getAffineYCoord().getEncoded()));
        return pubKey;
    }

    public void convertIDToHash(byte[] userID, byte[] hash) {
        SHA1Digest digest = new SHA1Digest();
        digest.update(userID, 0, userID.length);
        byte[] userIDHash = new byte[digest.getDigestSize()];
        digest.doFinal(userIDHash, 0);

        for (int i = 0; i < 4; i++) {
            hashGroup(userIDHash, hash, 5 * (4 - i) - 1, 8 * i);
        }
    }

    public void hashGroup(byte[] obuf, byte[] hashbuf, int numO, int numH) {
        byte temp = obuf[numO];
        byte temp1 = obuf[numO - 1];
        hashbuf[numH] = (byte) (temp & 0X1F);
        hashbuf[numH + 1] = (byte) (((temp1 & 0x03) << 3) | ((temp & 0xff) >> 5));
        hashbuf[numH + 2] = (byte) (((temp1 & 0xff) >> 2) & 0x1F);
        temp = obuf[numO - 2];
        hashbuf[numH + 3] = (byte) (((temp & 0x0F) << 1) | ((temp1 & 0xff) >> 7));
        temp1 = obuf[numO - 3];
        hashbuf[numH + 4] = (byte) ((((temp & 0xff) >> 4) & 0x0F) | ((temp1 & 0x01) << 4));
        hashbuf[numH + 5] = (byte) (((temp1 & 0xff) >> 1) & 0x1F);
        temp = obuf[numO - 4];
        hashbuf[numH + 6] = (byte) (((temp & 0x07) << 2) | ((temp1 & 0xff) >> 6));
        hashbuf[numH + 7] = (byte) ((temp & 0xff) >> 3);
    }


    /**
     * @return 域内的随机数
     * @description: <生成随机数，私钥d范围[1,n-2],签名中随机数k范围[1,n-2]，为了方便简化操作，本随机数范围[1,n-2]>
     */
    private BigInteger getBigRand() {
        BigInteger b;
        do {
            b = new BigInteger(SM2_N_BITLENGTH, new SecureRandom());
        }
        while (b.equals(ZERO) || b.compareTo(SM2_ECC_N.subtract(ONE)) >= 0);
        return b;
    }

    /**
     * @param xBytes x坐标
     * @param yBytes y坐标
     * @return Point点
     * @description: <根据X和Y转换成点Point>
     */
    public ECPoint xyToPoint(byte[] xBytes, byte[] yBytes) {
        ECPoint point;
        BigInteger x = new BigInteger(1, xBytes);
        BigInteger y = new BigInteger(1, yBytes);
        point = CURVE.createPoint(x, y);
        return point;
    }

    private byte[] fixTo32Bytes(byte[] src) {
        final int fixLen = 32;
        if (src.length == fixLen) {
            return src;
        }

        byte[] result = new byte[fixLen];
        if (src.length > fixLen) {
            System.arraycopy(src, src.length - result.length, result, 0, result.length);
        } else {
            System.arraycopy(src, 0, result, result.length - src.length, src.length);

        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        CPKUtil cpkUtil = new CPKUtil();
        CPKMatrix cpkMatrix = cpkUtil.generateKeyPairMatrix();
        byte[] priKeyMatrix = cpkMatrix.getPriKeyMatrix();
        byte[] pubKeyMatrix = cpkMatrix.getPubKeyMatrix();
        byte[] userID = "fang".getBytes();
        byte[] prikey = cpkUtil.computeCPKPriKey(priKeyMatrix, userID);
        System.out.println(Hex.toHexString(prikey));
        byte[] pubkey = cpkUtil.computeCPKPubKey(pubKeyMatrix, userID);
        System.out.println(Hex.toHexString(pubkey));
        byte[] pubkey1 = cpkUtil.computeCPKPubKey(prikey);
        System.out.println(Hex.toHexString(pubkey1));

        FileInputStream fis0 = new FileInputStream("F:\\I-IWallProjects\\JavaCPK\\target\\priKeyUniview.vdk");
        FileInputStream fis = new FileInputStream("F:\\I-IWallProjects\\JavaCPK\\target\\pubKeyUniview.vdk");

        ByteArrayOutputStream baos0 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(new byte[256]);

        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = fis.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        fis.close();

        System.arraycopy(baos.toByteArray(), 0, pubKeyMatrix, 0, pubKeyMatrix.length);
        baos.close();

        len = 0;
        byte[] buf0 = new byte[33];
        baos0.write(new byte[256]);
        while ((len = fis0.read(buf0)) != -1) {
            baos0.write(buf0, 1, len - 1);
        }
        fis0.close();

        System.arraycopy(baos0.toByteArray(), 0, priKeyMatrix, 0, baos0.toByteArray().length);
        baos0.close();

        pubkey = cpkUtil.computeCPKPubKey(pubKeyMatrix, "123456".getBytes());
        System.out.println("pub: " + Hex.toHexString(pubkey));

        prikey = cpkUtil.computeCPKPriKey(priKeyMatrix, "123456".getBytes());
        System.out.println("pri: " + Hex.toHexString(prikey));

        String hello = "Hello World";

//        BCECPrivateKey bcecPrivateKey = SM2Util.convertBCECPrivateKey(prikey);
//        BCECPublicKey bcecPublicKey = SM2Util.convertBCECPublicKey(pubkey);
//        System.out.println(Hex.toHexString(bcecPrivateKey.getEncoded()));
//        System.out.println(Hex.toHexString(bcecPublicKey.getEncoded()));
//        byte[] sigDER = SM2Util.sign(bcecPrivateKey, hello.getBytes());
//        byte[] sig = SM2Util.decodeDERSM2Sign(sigDER);
//        System.out.println("Data: " + Hex.toHexString(hello.getBytes()) + ", Sign: " + Hex.toHexString(sig));
//        byte[] sigDER2 = SM2Util.encodeSM2SignToDER(sig);
//        boolean verify = SM2Util.verify(bcecPublicKey, hello.getBytes(), sigDER2);
//        System.out.println("Verify: " + verify);
//        byte[] cipher = SM2Util.encrypt(bcecPublicKey, hello.getBytes());
//        System.out.println("Cipher: " + Hex.toHexString(cipher));//C1C2C3
//        byte[] plain = SM2Util.decrypt(bcecPrivateKey, cipher);
//        System.out.println("Plain: " + Hex.toHexString(plain));

        String skfPubKey = SKFUtil.convertSKFSM2PublicKey(pubkey);
        System.out.println("SKF PublicKey: " + skfPubKey);
        String skfPriKey = SKFUtil.convertSKFSM2PrivateKey(prikey);
        System.out.println("SKF PrivateKey: " + skfPriKey);
//        String skfSignature = SKFUtil.sign(skfPriKey, hello.getBytes());
//        System.out.println("SKF Sign: " + skfSignature);
//        boolean isVerified = SKFUtil.verify(skfPubKey, skfSignature, hello.getBytes());
//        System.out.println("SKF Verify: " + isVerified);
//        String skfCipher = SKFUtil.encrypt(skfPubKey, hello.getBytes());
//        System.out.println("SKF Cipher: " + skfCipher);
//        byte[] skfPlain = SKFUtil.decrypt(skfPriKey, skfCipher);
//        System.out.println("SKF Plain: " + new String(skfPlain));


        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String envelopedKey = SKFUtil.getEnvelopedKey(skfPubKey, pubKeyMatrix, priKeyMatrix, "123456".getBytes());
            System.out.println("SKF EnvelopedKey: " + envelopedKey);
        }
        long end = System.currentTimeMillis();
        System.out.println("" + (end - start) / 100f + " ms");
    }

}
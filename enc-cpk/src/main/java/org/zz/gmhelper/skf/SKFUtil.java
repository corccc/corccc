package org.zz.gmhelper.skf;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.zz.gmhelper.CPKUtil;
import org.zz.gmhelper.SM2Util;
import org.zz.gmhelper.SM4Util;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SKFUtil {
    public static final int SM2_BITS_LEN = 256;
    public static final int ECC_MAX_BITS_LEN = 512;
    private static final byte SM2_SKF_PAD_ZERO32[] = new byte[SM2_BITS_LEN / 8];
    private static final CPKUtil cpkUtil = new CPKUtil();

    public static byte[] int2Bytes(int num) {
        byte[] dest = new byte[4];
        dest[0] = (byte) ((num >> 24) & 0xFF);
        dest[1] = (byte) ((num >> 16) & 0xFF);
        dest[2] = (byte) ((num >> 8) & 0xFF);
        dest[3] = (byte) (num & 0xFF);
        return dest;
    }

    private static byte[] encodeSKFSM2PublicKey(byte[] publicKey65) {
        if (publicKey65 == null || publicKey65.length < 65)
            return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(int2Bytes(SM2_BITS_LEN));//bits len
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(publicKey65, 1, 32);//x
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(publicKey65, 33, 32);//y
            byte[] skfPublicKey = baos.toByteArray();
            baos.close();
            return skfPublicKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decodeSKFSM2PublicKey(byte[] publicKeySKF) {
        if (publicKeySKF == null || publicKeySKF.length < 4 + 128)
            return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(new byte[]{0x04});
            baos.write(publicKeySKF, 4 + 32, 32);//x
            baos.write(publicKeySKF, 4 + 96, 32);//y
            byte[] publicKey65 = baos.toByteArray();
            baos.close();
            return publicKey65;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encodeSKFSM2PrivateKey(byte[] privateKey32) {
        if (privateKey32 == null || privateKey32.length < 32)
            return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(int2Bytes(SM2_BITS_LEN));//bits len
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(privateKey32, 0, 32);//d
            byte[] skfPublicKey = baos.toByteArray();
            baos.close();
            return skfPublicKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decodeSKFSM2PrivateKey(byte[] privateKeySKF) {
        if (privateKeySKF == null || privateKeySKF.length < 4 + 64)
            return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(privateKeySKF, 4 + 32, 32);//d
            byte[] privateKey32 = baos.toByteArray();
            baos.close();
            return privateKey32;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encodeSKFSM2Signature(byte[] signature) {
        if (signature == null || signature.length < 64)
            return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(signature, 0, 32);//r
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(signature, 32, 32);//s
            byte[] skfPublicKey = baos.toByteArray();
            baos.close();
            return skfPublicKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decodeSKFSM2Signature(byte[] signatureSKF) {
        if (signatureSKF == null || signatureSKF.length < 128)
            return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(signatureSKF, 32, 32);//x
            baos.write(signatureSKF, 96, 32);//y
            byte[] signature = baos.toByteArray();
            baos.close();
            return signature;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encodeSKFSM2Cipher(byte[] cipher) {
        if (cipher == null || cipher.length < 1 + 96)
            return null;
        try {
            //cipher[C1||C2||C3] > skf[C1||C3||C2]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(Arrays.copyOfRange(cipher, 1, 33));//C1.X
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(Arrays.copyOfRange(cipher, 33, 65));//C1.Y
            baos.write(Arrays.copyOfRange(cipher, cipher.length - 32, cipher.length));//C3
            baos.write(int2Bytes(cipher.length - 65 - 32));//cipher len
            baos.write(Arrays.copyOfRange(cipher, 65, cipher.length - 32));//C2
            byte[] cipherSKF = baos.toByteArray();
            baos.close();
            return cipherSKF;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decodeSKFSM2Cipher(byte[] cipherSKF) {
        if (cipherSKF == null || cipherSKF.length < 64 + 96 + 4)
            return null;
        try {
            //skf[C1||C3||C2] > cipher[C1||C2||C3]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(new byte[]{0x04});
            baos.write(Arrays.copyOfRange(cipherSKF, 32, 64));//C1.X
            baos.write(Arrays.copyOfRange(cipherSKF, 96, 128));//C1.Y
            baos.write(Arrays.copyOfRange(cipherSKF, 160 + 4, cipherSKF.length));//C2
            baos.write(Arrays.copyOfRange(cipherSKF, 128, 160));//C3
            byte[] cipher = baos.toByteArray();
            baos.close();
            return cipher;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sign(String privateKeySKF, byte[] data) {
        try {
            byte[] decodePrivateKey = Base64.decode(privateKeySKF);
            byte[] privateKey32 = decodeSKFSM2PrivateKey(decodePrivateKey);
            BCECPrivateKey bcecPrivateKey = SM2Util.convertBCECPrivateKey(privateKey32);
            byte[] sigDER = SM2Util.sign(bcecPrivateKey, data);
            byte[] sig = SM2Util.decodeDERSM2Sign(sigDER);
            byte[] signatureSKF = encodeSKFSM2Signature(sig);
            if (signatureSKF != null) {
                return Base64.toBase64String(signatureSKF);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verify(String publicKeySKF, String signatureSKF, byte[] data) {
        try {
            byte[] decodePublicKey = Base64.decode(publicKeySKF);
            byte[] decodeSignature = Base64.decode(signatureSKF);
            byte[] publicKey65 = decodeSKFSM2PublicKey(decodePublicKey);
            BCECPublicKey bcecPublicKey = SM2Util.convertBCECPublicKey(publicKey65);
            byte[] sig = decodeSKFSM2Signature(decodeSignature);
            byte[] sigDER = SM2Util.encodeSM2SignToDER(sig);
            return SM2Util.verify(bcecPublicKey, data, sigDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String encrypt(String publicKeySKF, byte[] data) {
        try {
            byte[] decodePublicKey = Base64.decode(publicKeySKF);
            byte[] publicKey65 = decodeSKFSM2PublicKey(decodePublicKey);
            BCECPublicKey bcecPublicKey = SM2Util.convertBCECPublicKey(publicKey65);
            byte[] cipher = SM2Util.encrypt(bcecPublicKey, data);
            byte[] cipherSKF = encodeSKFSM2Cipher(cipher);
            if (cipherSKF != null) {
                return Base64.toBase64String(cipherSKF);
            }
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(String privateKeySKF, String cipherSKF) {
        try {
            byte[] decodePrivateKey = Base64.decode(privateKeySKF);
            byte[] decodeCipher = Base64.decode(cipherSKF);
            byte[] privateKey32 = decodeSKFSM2PrivateKey(decodePrivateKey);
            BCECPrivateKey bcecPrivateKey = SM2Util.convertBCECPrivateKey(privateKey32);
            byte[] cipher = decodeSKFSM2Cipher(decodeCipher);
            return SM2Util.decrypt(bcecPrivateKey, cipher);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertSKFSM2PublicKey(byte[] publicKey65) {
        byte[] encode = encodeSKFSM2PublicKey(publicKey65);
        if (encode != null) {
            return Base64.toBase64String(encode);
        }
        return null;
    }

    public static String convertSKFSM2PrivateKey(byte[] privateKey32) {
        byte[] encode = encodeSKFSM2PrivateKey(privateKey32);
        if (encode != null) {
            return Base64.toBase64String(encode);
        }
        return null;
    }

    public static String getCPKSM2PublicKey(byte[] pubKeyMatrix, byte[] userId) {
        byte[] publicKey = cpkUtil.computeCPKPubKey(pubKeyMatrix, userId);
        return SKFUtil.convertSKFSM2PublicKey(publicKey);
    }

    public static String getCPKSM2PrivateKey(byte[] priKeyMatrix, byte[] userId) {
        byte[] privateKey = cpkUtil.computeCPKPriKey(priKeyMatrix, userId);
        return SKFUtil.convertSKFSM2PrivateKey(privateKey);
    }

    public static String getEnvelopedKey(String protectPublicKey, byte[] pubKeyMatrix, byte[] priKeyMatrix, byte[] userId) {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] key = new byte[16];
            sr.nextBytes(key);

            byte[] publicKey = cpkUtil.computeCPKPubKey(pubKeyMatrix, userId);
            byte[] privateKey = cpkUtil.computeCPKPriKey(priKeyMatrix, userId);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(int2Bytes(1));//Version
            baos.write(new byte[]{0x00, 0x00, 0x04, 0x01});//SymmAlgId:ECB
            baos.write(int2Bytes(SM2_BITS_LEN));//bits len

            byte[] priKeyCipher = SM4Util.encrypt_Ecb_NoPadding(key, privateKey);
            baos.write(SM2_SKF_PAD_ZERO32);
            baos.write(priKeyCipher);

            byte[] publicKeySKF = encodeSKFSM2PublicKey(publicKey);
            baos.write(publicKeySKF);

            String cipherSKF = encrypt(protectPublicKey, key);
            byte[] decodeCipher = Base64.decode(cipherSKF);
            baos.write(decodeCipher);

            byte[] envelopedKey = baos.toByteArray();
            baos.close();
            return Base64.toBase64String(envelopedKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}

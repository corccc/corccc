/*
 *@ClassName   HashUtil
 *@Description create class
 *@Author      kong
 *@Date        2021/4/27 下午3:45
 *@Version     1.0
 */
package com.enc.common.enccommon.utils;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.*;
import org.bouncycastle.util.encoders.Hex;

public class HashUtil {

    public static Digest getDigest(String algName) {
        Digest digest = null;
        if (algName == null || algName.equals("")) {
            return null;
        }
        switch (algName) {
            case "sha1":
                digest = new SHA1Digest();
                break;
            case "sha224":
                digest = new SHA224Digest();
                break;
            case "sha256":
                digest = new SHA256Digest();
                break;
            case "sha384":
                digest = new SHA384Digest();
                break;
            case "sha512":
                digest = new SHA512Digest();
                break;
            case "md5":
                digest = new MD5Digest();
                break;
            case "sm3":
                digest = new SM3Digest();
                break;
            default:
                break;
        }
        return digest;
    }

    public static String digest(byte[] plainData, Digest digest) {
        if (plainData == null || digest == null){
            return "";
        }
        digest.update(plainData, 0, plainData.length);
        byte[] digestBytes = new byte[digest.getDigestSize()];
        digest.doFinal(digestBytes, 0);
        String digestHex = Hex.toHexString(digestBytes);
        return digestHex;
    }

}

/*
 *@ClassName   AsymmService
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 下午2:31
 *@Version     1.0
 */
package com.enc.asymm.encasymm.service.impl;

import com.enc.asymm.encasymm.entity.AsymmRequestEntity;
import com.enc.asymm.encasymm.entity.AsymmResponseEntity;
import com.enc.asymm.encasymm.service.IAsymmService;
import com.enc.common.enccommon.utils.EccUtil;
import com.enc.common.enccommon.utils.Sm2Util;
import com.enc.common.enccommon.utils.StringUtil;
import org.bouncycastle.crypto.tls.ECCurveType;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class AsymmService implements IAsymmService {

    @Override
    public AsymmResponseEntity genKeyPair(AsymmRequestEntity asymmRequestEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmRequestEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmRequestEntity.getAlgName().toLowerCase();
        if (algName.contains("sm2")) {
            try {
                KeyPair keyPair = Sm2Util.generateKeyPair();
                responseEntity.setPx(Hex.toHexString(Sm2Util.getPublicKeyX(keyPair.getPublic())));
                responseEntity.setPy(Hex.toHexString(Sm2Util.getPublicKeyY(keyPair.getPublic())));
                responseEntity.setPri(Hex.toHexString(Sm2Util.getPrivateKeyD(keyPair.getPrivate())));
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("ecc")) {
            try {
                KeyPair keyPair = EccUtil.generateKeyPair(asymmRequestEntity.getSecp());
                responseEntity.setPx(Hex.toHexString(EccUtil.getPublicKeyX(keyPair.getPublic())));
                responseEntity.setPy(Hex.toHexString(EccUtil.getPublicKeyY(keyPair.getPublic())));
                responseEntity.setPri(Hex.toHexString(EccUtil.getPrivateKeyD(keyPair.getPrivate())));
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("rsa")) {

        }
        return responseEntity;
    }

    @Override
    public AsymmResponseEntity calPubKey(AsymmRequestEntity asymmRequestEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmRequestEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmRequestEntity.getAlgName().toLowerCase();
        if (algName.contains("sm2")) {
            try {
                PrivateKey privateKey = Sm2Util.convertPrivateKey(new BigInteger(asymmRequestEntity.getPri(), 16));
                PublicKey  publicKey  = Sm2Util.generatePublicKey(privateKey);
                responseEntity.setPx(Hex.toHexString(Sm2Util.getPublicKeyX(publicKey)));
                responseEntity.setPy(Hex.toHexString(Sm2Util.getPublicKeyY(publicKey)));
                responseEntity.setPri(asymmRequestEntity.getPri());
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("ecc")) {
            try {
                PrivateKey privateKey = EccUtil.convertPrivateKey(asymmRequestEntity.getSecp(), new BigInteger(asymmRequestEntity.getPri(), 16));
                PublicKey  publicKey  = EccUtil.generatePublicKey(privateKey);
                responseEntity.setPx(Hex.toHexString(Sm2Util.getPublicKeyX(publicKey)));
                responseEntity.setPy(Hex.toHexString(Sm2Util.getPublicKeyY(publicKey)));
                responseEntity.setPri(asymmRequestEntity.getPri());
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("rsa")) {

        } else {
            responseEntity.setShowData("不支持的算法");
        }
        return responseEntity;
    }

    @Override
    public AsymmResponseEntity enc(AsymmRequestEntity asymmRequestEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmRequestEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmRequestEntity.getAlgName().toLowerCase();
        String showStr = "";
        if (algName.contains("sm2")) {
            try {
                PublicKey  publicKey  = Sm2Util.convertPublicKey(
                                            new BigInteger(asymmRequestEntity.getPx(), 16),
                                            new BigInteger(asymmRequestEntity.getPy(), 16));
                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);

                byte[] cipherData = Sm2Util.encrypt(publicKey, dataBytes);
                String cipherStr  = Hex.toHexString(cipherData);
                responseEntity.setPx(asymmRequestEntity.getPx());
                responseEntity.setPy(asymmRequestEntity.getPy());
                showStr = "算法: " + algName + " 加密" + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + formatString + "\n"
                        + "密文值: " + "长度 " + cipherData.length + " 字节" + "\n"
                        + cipherStr;
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("ecc")) {
            try {
                PublicKey  publicKey  = EccUtil.convertPublicKey(asymmRequestEntity.getSecp(),
                        new BigInteger(asymmRequestEntity.getPx(), 16),
                        new BigInteger(asymmRequestEntity.getPy(), 16));
                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);

                byte[] cipherData = EccUtil.encrypt(publicKey, dataBytes);
                String cipherStr  = Hex.toHexString(cipherData);
                responseEntity.setPx(asymmRequestEntity.getPx());
                responseEntity.setPy(asymmRequestEntity.getPy());
                showStr = "算法: " + algName + " 加密" + "\n"
                        + "曲线: " + asymmRequestEntity.getSecp() + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + formatString + "\n"
                        + "密文值: " + "长度 " + cipherData.length + " 字节" + "\n"
                        + cipherStr;
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("rsa")) {

        } else {
            responseEntity.setShowData("不支持的算法");
        }
        return responseEntity;
    }

    @Override
    public AsymmResponseEntity dec(AsymmRequestEntity asymmRequestEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmRequestEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmRequestEntity.getAlgName().toLowerCase();
        String showStr = "";
        if (algName.contains("sm2")) {
            try {
                PrivateKey privateKey = Sm2Util.convertPrivateKey(
                        new BigInteger(asymmRequestEntity.getPri(), 16));
                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);
                byte[] plainData    = Sm2Util.decrypt(privateKey, dataBytes);
                String plainStr     = Hex.toHexString(plainData);
                showStr = "算法: " + algName + " 解密" + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + formatString + "\n"
                        + "明文值: " + "长度 " + plainData.length + " 字节" + "\n"
                        + plainStr;
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("ecc")) {
            try {
                PrivateKey privateKey = EccUtil.convertPrivateKey(asymmRequestEntity.getSecp(),
                        new BigInteger(asymmRequestEntity.getPri(), 16));
                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);
                byte[] plainData    = EccUtil.decrypt(privateKey, dataBytes);
                String plainStr     = Hex.toHexString(plainData);
                showStr = "算法: " + algName + " 解密" + "\n"
                        + "曲线: " + asymmRequestEntity.getSecp() + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + formatString + "\n"
                        + "明文值: " + "长度 " + plainData.length + " 字节" + "\n"
                        + plainStr;
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("rsa")) {

        } else {
            responseEntity.setShowData("不支持的算法");
        }
        return responseEntity;
    }

    @Override
    public AsymmResponseEntity sign(AsymmRequestEntity asymmRequestEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmRequestEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmRequestEntity.getAlgName().toLowerCase();
        String showStr = "";
        if (algName.contains("sm2")) {
            try {
                PrivateKey privateKey = Sm2Util.convertPrivateKey(
                        new BigInteger(asymmRequestEntity.getPri(), 16));
                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);

                String formatIdStr  = StringUtil.formatHexString(asymmRequestEntity.getId());
                byte[] withIdBytes  = StringUtil.hexStringToBytes(formatIdStr);

                byte[] signData     = Sm2Util.sign(privateKey, dataBytes, withIdBytes);
                String signStr      = Hex.toHexString(signData);
                showStr = "算法: " + algName + "签名" + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + "ID: "  + "长度 " + withIdBytes.length + "字节" + "\n"
                        + formatIdStr + "\n"
                        + "签名值: " + "长度 " + signData.length + " 字节" + "\n"
                        + signStr;
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("ecc")) {
            try {
                PrivateKey privateKey = EccUtil.convertPrivateKey(asymmRequestEntity.getSecp(),
                        new BigInteger(asymmRequestEntity.getPri(), 16));
                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);
                byte[] signData     = EccUtil.sign(asymmRequestEntity.getHash(), privateKey, dataBytes);
                String signStr      = Hex.toHexString(signData);
                showStr = "算法: " + algName + "签名" + "\n"
                        + "曲线: " + asymmRequestEntity.getSecp() + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + "签名值: " + "长度 " + signData.length + " 字节" + "\n"
                        + signStr;
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("rsa")) {

        } else {
            responseEntity.setShowData("不支持的算法");
        }
        return responseEntity;
    }

    @Override
    public AsymmResponseEntity vsign(AsymmRequestEntity asymmRequestEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmRequestEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmRequestEntity.getAlgName().toLowerCase();
        String showStr = "";
        if (algName.contains("sm2")) {
            try {
                String formatPxStr  = StringUtil.formatHexString(asymmRequestEntity.getPx());
                String formatPyStr  = StringUtil.formatHexString(asymmRequestEntity.getPy());
                PublicKey publicKey = Sm2Util.convertPublicKey(
                        new BigInteger(formatPxStr, 16),
                        new BigInteger(formatPyStr, 16));

                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);

                String formatSign   = StringUtil.formatHexString(asymmRequestEntity.getSign());
                byte[] signBytes    = StringUtil.hexStringToBytes(formatSign);

                String formatIdStr  = StringUtil.formatHexString(asymmRequestEntity.getId());
                byte[] withIdBytes  = StringUtil.hexStringToBytes(formatIdStr);

                boolean res         = Sm2Util.verify(publicKey, dataBytes, signBytes, withIdBytes);
                showStr = "算法: " + algName + " 验签" + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + formatString + "\n"
                        + "ID: "  + "长度 " + withIdBytes.length + "字节" + "\n"
                        + formatIdStr + "\n"
                        + "签名值: " + "长度 " + signBytes.length + " 字节" + "\n"
                        + formatSign + "\n"
                        + "验签结果: " + (res ? "成功" : "失败");
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("ecc")) {
            try {
                String formatPxStr  = StringUtil.formatHexString(asymmRequestEntity.getPx());
                String formatPyStr  = StringUtil.formatHexString(asymmRequestEntity.getPy());
                PublicKey publicKey = EccUtil.convertPublicKey(asymmRequestEntity.getSecp(),
                        new BigInteger(formatPxStr, 16),
                        new BigInteger(formatPyStr, 16));

                String formatString = StringUtil.formatHexString(asymmRequestEntity.getData());
                byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);

                String formatSign   = StringUtil.formatHexString(asymmRequestEntity.getSign());
                byte[] signBytes    = StringUtil.hexStringToBytes(formatSign);
                boolean res         = EccUtil.verify(asymmRequestEntity.getHash(), publicKey, dataBytes, signBytes);
                showStr = "算法: " + algName + " 验签" + "\n"
                        + "曲线: " + asymmRequestEntity.getSecp() + "\n"
                        + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                        + formatString + "\n"
                        + "签名值: " + "长度 " + signBytes.length + " 字节" + "\n"
                        + formatSign + "\n"
                        + "验签结果: " + (res ? "成功" : "失败");
                responseEntity.setShowData(showStr);
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("rsa")) {

        } else {
            responseEntity.setShowData("不支持的算法");
        }
        return responseEntity;
    }
}

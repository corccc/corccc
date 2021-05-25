/*
 *@ClassName   EcdhService
 *@Description create class
 *@Author      kong
 *@Date        2021/5/24 下午2:12
 *@Version     1.0
 */
package com.enc.ecdh.encecdh.service.impl;

import com.enc.common.enccommon.utils.EccUtil;
import com.enc.common.enccommon.utils.StringUtil;
import com.enc.ecdh.encecdh.entity.EcdhRequestEntity;
import com.enc.ecdh.encecdh.entity.EcdhResponseEntity;
import com.enc.ecdh.encecdh.service.IEcdhService;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class EcdhService implements IEcdhService {


    @Override
    public EcdhResponseEntity genKeyPair(EcdhRequestEntity requestEntity) {
        EcdhResponseEntity responseEntity = new EcdhResponseEntity();
        if (requestEntity == null) {
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        try {
            KeyPair keyPair = EccUtil.generateKeyPair(requestEntity.getSecp());
            responseEntity.setPx(Hex.toHexString(EccUtil.getPublicKeyX(keyPair.getPublic())));
            responseEntity.setPy(Hex.toHexString(EccUtil.getPublicKeyY(keyPair.getPublic())));
            responseEntity.setPri(Hex.toHexString(EccUtil.getPrivateKeyD(keyPair.getPrivate())));
        } catch (Exception e) {
            responseEntity.setShowData(e.getLocalizedMessage());
        }
        return responseEntity;
    }

    @Override
    public EcdhResponseEntity calPubKey(EcdhRequestEntity requestEntity) {
        EcdhResponseEntity responseEntity = new EcdhResponseEntity();
        if (requestEntity == null) {
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        try {
            String fmtPriStr = StringUtil.formatHexString(requestEntity.getPri());
            PrivateKey privateKey = EccUtil.convertPrivateKey(requestEntity.getSecp(), new BigInteger(fmtPriStr, 16));
            PublicKey publicKey   = EccUtil.generatePublicKey(privateKey);
            responseEntity.setPx(Hex.toHexString(EccUtil.getPublicKeyX(publicKey)));
            responseEntity.setPy(Hex.toHexString(EccUtil.getPublicKeyY(publicKey)));
            responseEntity.setPri(requestEntity.getPri());
        } catch (Exception e) {
            responseEntity.setShowData(e.getLocalizedMessage());
        }
        return responseEntity;
    }

    @Override
    public EcdhResponseEntity calSecretKey(EcdhRequestEntity requestEntity) {
        EcdhResponseEntity responseEntity = new EcdhResponseEntity();
        if (requestEntity == null) {
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        try {
            String pri = StringUtil.formatHexString(requestEntity.getPri());
            String px  = StringUtil.formatHexString(requestEntity.getPx());
            String py  = StringUtil.formatHexString(requestEntity.getPy());
            PrivateKey privateKey = EccUtil.convertPrivateKey(requestEntity.getSecp(), new BigInteger(pri, 16));
            PublicKey  publicKey   = EccUtil.convertPublicKey(requestEntity.getSecp(),
                    new BigInteger(px, 16),
                    new BigInteger(py, 16));
            byte[] sx = EccUtil.ecdh(privateKey, publicKey);
            byte[] sy = EccUtil.calY(requestEntity.getSecp(), sx);
            responseEntity.setSx(Hex.toHexString(sx));
            responseEntity.setSy(Hex.toHexString(sy));
        } catch (Exception e) {
            responseEntity.setShowData(e.getLocalizedMessage());
        }
        return responseEntity;
    }
}

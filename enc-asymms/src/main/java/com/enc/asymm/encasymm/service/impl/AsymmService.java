/*
 *@ClassName   AsymmService
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 下午2:31
 *@Version     1.0
 */
package com.enc.asymm.encasymm.service.impl;

import com.enc.asymm.encasymm.entity.AsymmReqeustEntity;
import com.enc.asymm.encasymm.entity.AsymmResponseEntity;
import com.enc.asymm.encasymm.service.IAsymmService;
import com.enc.common.enccommon.utils.Sm2Util;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class AsymmService implements IAsymmService {

    @Override
    public AsymmResponseEntity genKeyPair(AsymmReqeustEntity asymmReqeustEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmReqeustEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmReqeustEntity.getAlgName().toLowerCase();
        if (algName.contains("sm2")) {
            try {
                KeyPair keyPair = Sm2Util.generateKeyPair();
                responseEntity.setPx(Hex.toHexString(Sm2Util.getPublicKeyX(keyPair.getPublic())));
                responseEntity.setPy(Hex.toHexString(Sm2Util.getPublicKeyY(keyPair.getPublic())));
                responseEntity.setPri(Hex.toHexString(Sm2Util.getPrivateKeyD(keyPair.getPrivate())));
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
            return responseEntity;
        } else if (algName.contains("ecc")) {

        } else if (algName.contains("rsa")) {

        }
        return responseEntity;
    }

    @Override
    public AsymmResponseEntity calPubKey(AsymmReqeustEntity asymmReqeustEntity) {
        AsymmResponseEntity responseEntity = new AsymmResponseEntity();
        if (asymmReqeustEntity == null){
            responseEntity.setShowData("数据错误");
            return responseEntity;
        }
        String algName = asymmReqeustEntity.getAlgName().toLowerCase();
        if (algName.contains("sm2")) {
            try {
                PrivateKey privateKey = Sm2Util.convertPrivateKey(new BigInteger(asymmReqeustEntity.getPri(), 16));
                PublicKey  publicKey  = Sm2Util.generatePublicKey(privateKey);
                responseEntity.setPx(Hex.toHexString(Sm2Util.getPublicKeyX(publicKey)));
                responseEntity.setPy(Hex.toHexString(Sm2Util.getPublicKeyY(publicKey)));
                responseEntity.setPri(asymmReqeustEntity.getPri());
            } catch (Exception e) {
                responseEntity.setShowData(e.getLocalizedMessage());
            }
        } else if (algName.contains("ecc")) {

        }
        return responseEntity;
    }
}

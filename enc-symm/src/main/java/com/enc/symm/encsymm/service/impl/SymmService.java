/*
 *@ClassName   SymmService
 *@Description create class
 *@Author      kong
 *@Date        2021/4/26 下午4:18
 *@Version     1.0
 */
package com.enc.symm.encsymm.service.impl;

import com.enc.common.enccommon.entity.ResponseEntity;
import com.enc.common.enccommon.exception.ErrorCode;
import com.enc.common.enccommon.utils.StringUtil;
import com.enc.common.enccommon.utils.SymmUtil;
import com.enc.symm.encsymm.entity.SymmReqeustEntity;
import com.enc.symm.encsymm.service.ISymmService;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

@Service
public class SymmService implements ISymmService {
    @Override
    public ResponseEntity encryptEntity(SymmReqeustEntity encEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        String algName   = encEntity.getAlgName();
        String showStr   = "";
        /* 数据原文 */
        if (encEntity.getData() == null) {
            responseEntity.setShowData(ErrorCode.INPUT_DATA_ERR_MSG);
            return responseEntity;
        }
        String dataStr   = StringUtil.formatHexString(encEntity.getData());
        byte[] dataBytes = StringUtil.hexStringToBytes(dataStr);

        /* 数据密钥 */
        if (encEntity.getKey() == null) {
            responseEntity.setShowData(ErrorCode.INPUT_KEY_ERR_MSG);
            return responseEntity;
        }
        String keyStr    = StringUtil.formatHexString(encEntity.getKey());
        byte[] keyBytes  = StringUtil.hexStringToBytes(keyStr);

        /* 偏移量 */
        String ivStr   = null;
        byte[] ivBytes = null;
        if (encEntity.getIv() != null) {
            ivStr     = StringUtil.formatHexString(encEntity.getIv());
            ivBytes   = StringUtil.hexStringToBytes(ivStr);
        }

        /* 加密 */
        byte[] cipherBytes = null;
        String cipherStr   = null;
        try {
            cipherBytes = SymmUtil.crypto(encEntity.getAlgName(), SymmUtil.ENC, keyBytes, ivBytes, dataBytes);
            cipherStr   = Hex.toHexString(cipherBytes);
            showStr = "算法: " + algName + "\n"
                    + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                    + dataStr + "\n"
                    + "密文值: " + "长度 " + cipherBytes.length + " 字节" + "\n"
                    + cipherStr;
        } catch (Exception e) {
            showStr = e.getMessage();
        }
        responseEntity.setShowData(showStr);
        responseEntity.setData(dataStr);
        responseEntity.setCipherData(cipherStr);
        return responseEntity;
    }

    public ResponseEntity decryptEntity(SymmReqeustEntity encEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        String algName   = encEntity.getAlgName();
        String showStr   = "";
        /* 数据密钥 */
        if (encEntity.getKey() == null) {
            responseEntity.setShowData(ErrorCode.INPUT_KEY_ERR_MSG);
            return responseEntity;
        }
        String keyStr    = StringUtil.formatHexString(encEntity.getKey());
        byte[] keyBytes  = StringUtil.hexStringToBytes(keyStr);

        /* 偏移量 */
        String ivStr   = null;
        byte[] ivBytes = null;
        if (encEntity.getIv() != null) {
            ivStr     = StringUtil.formatHexString(encEntity.getIv());
            ivBytes   = StringUtil.hexStringToBytes(ivStr);
        }

        /* 密文数据 */
        String cipherStr = StringUtil.formatHexString(encEntity.getData());
        byte[] cipherBytes = StringUtil.hexStringToBytes(cipherStr);

        /* 解密 */
        byte[] plainBytes = null;
        String plainStr   = null;
        try {
            plainBytes = SymmUtil.crypto(encEntity.getAlgName(), SymmUtil.DEC, keyBytes, ivBytes, cipherBytes);
            plainStr  = Hex.toHexString(plainBytes);
            showStr = "算法: " + algName + "\n"
                    + "密文: " + "长度 " + cipherBytes.length + " 字节" + "\n"
                    + cipherStr + "\n"
                    + "明文: " + "长度 " + plainBytes.length + " 字节" + "\n"
                    + plainStr;
        } catch (Exception e) {
            showStr = e.getMessage();
        }
        responseEntity.setShowData(showStr);
        responseEntity.setData(plainStr);
        responseEntity.setCipherData(cipherStr);
        return responseEntity;
    }

}

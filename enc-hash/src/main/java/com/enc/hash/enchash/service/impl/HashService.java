/*
 *@ClassName   HashService
 *@Description create class
 *@Author      kong
 *@Date        2021/4/27 下午2:08
 *@Version     1.0
 */
package com.enc.hash.enchash.service.impl;

import com.enc.common.enccommon.entity.RequestEntity;
import com.enc.common.enccommon.entity.ResponseEntity;
import com.enc.common.enccommon.utils.HashUtil;
import com.enc.common.enccommon.utils.StringUtil;
import com.enc.hash.enchash.service.IHashService;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

@Service
public class HashService implements IHashService {

    public ResponseEntity digestEntity(RequestEntity encEntity) {

        ResponseEntity responseEntity = new ResponseEntity();
        String algName      = encEntity.getAlgName().toLowerCase().replaceAll("-","");
        String formatString = StringUtil.formatHexString(encEntity.getData());
        byte[] dataBytes    = StringUtil.hexStringToBytes(formatString);
        Digest digest       = HashUtil.getDigest(algName);
        byte[] digestBytes  = HashUtil.digest(dataBytes, digest);
        String digestStr    = Hex.toHexString(digestBytes);
        String showStr      = "";
        if (digest == null) {
            showStr = "不支持的 hash 算法";
        } else {
            showStr = "算法: " + algName + "\n"
                    + "数据: " + "长度 " + dataBytes.length + " 字节" + "\n"
                    + formatString + "\n"
                    + "HASH值: " + "长度 " + digestBytes.length + " 字节" + "\n"
                    + digestStr;
        }
        responseEntity.setShowData(showStr);
        responseEntity.setData(formatString);
        responseEntity.setCipherData(digestStr);
        return responseEntity;
    }

}

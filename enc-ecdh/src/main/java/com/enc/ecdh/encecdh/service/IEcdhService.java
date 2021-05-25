package com.enc.ecdh.encecdh.service;

import com.enc.ecdh.encecdh.entity.EcdhRequestEntity;
import com.enc.ecdh.encecdh.entity.EcdhResponseEntity;

public interface IEcdhService {
    /**
     * 生成密钥对
     * @param requestEntity 请求实体
     * @return 响应实体
     */
    public EcdhResponseEntity genKeyPair(EcdhRequestEntity requestEntity);

    /**
     * 计算公钥
     * @param requestEntity 请求实体
     * @return 响应实体
     */
    public EcdhResponseEntity calPubKey(EcdhRequestEntity requestEntity);

    /**
     * 计算 ECDH Secret Key
     * @param requestEntity 请求实体
     * @return 响应实体
     */
    public EcdhResponseEntity calSecretKey(EcdhRequestEntity requestEntity);
}

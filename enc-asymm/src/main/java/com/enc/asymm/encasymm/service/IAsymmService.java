/*
 *@ClassName   IAsymmService
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 下午2:31
 *@Version     1.0
 */
package com.enc.asymm.encasymm.service;

import com.enc.asymm.encasymm.entity.AsymmRequestEntity;
import com.enc.asymm.encasymm.entity.AsymmResponseEntity;

public interface IAsymmService {

    /**
     * 生成密钥对
     * @param asymmRequestEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity genKeyPair(AsymmRequestEntity asymmRequestEntity);

    /**
     * 计算公钥
     * @param asymmRequestEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity calPubKey(AsymmRequestEntity asymmRequestEntity);

    /**
     * 加密
     * @param asymmRequestEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity enc(AsymmRequestEntity asymmRequestEntity);


    /**
     * 解密
     * @param asymmRequestEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity dec(AsymmRequestEntity asymmRequestEntity);

    /**
     * 签名
     * @param asymmRequestEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity sign(AsymmRequestEntity asymmRequestEntity);

    /**
     * 验证签名
     * @param asymmRequestEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity vsign(AsymmRequestEntity asymmRequestEntity);

}

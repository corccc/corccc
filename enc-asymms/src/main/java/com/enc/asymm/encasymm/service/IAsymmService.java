/*
 *@ClassName   IAsymmService
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 下午2:31
 *@Version     1.0
 */
package com.enc.asymm.encasymm.service;

import com.enc.asymm.encasymm.entity.AsymmReqeustEntity;
import com.enc.asymm.encasymm.entity.AsymmResponseEntity;

public interface IAsymmService {

    /**
     * 生成密钥对
     * @param asymmReqeustEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity genKeyPair(AsymmReqeustEntity asymmReqeustEntity);

    /**
     * 计算公钥
     * @param asymmReqeustEntity 请求实体
     * @return 响应实体
     */
    public AsymmResponseEntity calPubKey(AsymmReqeustEntity asymmReqeustEntity);

}

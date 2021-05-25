/*
 *@ClassName   AsymmReqeustEntity
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 上午11:13
 *@Version     1.0
 */
package com.enc.asymm.encasymm.entity;

import com.enc.common.enccommon.entity.RequestEntity;
import lombok.Data;

@Data
public class AsymmRequestEntity extends RequestEntity {

    /**
     * public key point x
     */
    private String px;

    /**
     * public key point y
     */
    private String py;

    /**
     * private key value
     */
    private String pri;

    /**
     * id
     */
    private String id;

    /**
     * signature
     */
    private String sign;

    /**
     * spec 曲线
     */
    private String secp;

    /**
     * hash 哈希
     */
    private String hash;

}

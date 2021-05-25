/*
 *@ClassName   AsymmReqeustEntity
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 上午11:13
 *@Version     1.0
 */
package com.enc.ecdh.encecdh.entity;

import com.enc.common.enccommon.entity.RequestEntity;
import lombok.Data;

@Data
public class EcdhRequestEntity extends RequestEntity {

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
     * spec 曲线
     */
    private String secp;
}

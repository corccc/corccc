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
public class AsymmReqeustEntity extends RequestEntity {

    /* =============== ECC begin =============== */
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
    /* =============== ECC end =============== */

    /* =============== RSA begin =============== */

    /* =============== RSA end =============== */


}

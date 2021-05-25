/*
 *@ClassName   AsymmResponseEntity
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 下午2:11
 *@Version     1.0
 */
package com.enc.ecdh.encecdh.entity;

import com.enc.common.enccommon.entity.ResponseEntity;
import lombok.Data;

@Data
public class EcdhResponseEntity extends ResponseEntity {

    private String px;

    private String py;

    private String pri;

    /* secret x */
    private String sx;

    /* secret y */
    private String sy;

}

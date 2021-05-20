/*
 *@ClassName   SymmReqeustEntity
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 上午11:08
 *@Version     1.0
 */
package com.enc.symm.encsymm.entity;

import com.enc.common.enccommon.entity.RequestEntity;
import lombok.Data;

@Data
public class SymmReqeustEntity extends RequestEntity {

    /** 对称及 mac 算法使用 */
    /**
     * alg key
     */
    private String key;

    /**
     * alg iv
     */
    private String iv;
}

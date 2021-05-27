/*
 *@ClassName   CpkRequestEntity
 *@Description create class
 *@Author      kong
 *@Date        2021/5/25 上午10:43
 *@Version     1.0
 */
package com.enc.cpk.enccpk.entity;

import com.enc.common.enccommon.entity.RequestEntity;
import lombok.Data;

@Data
public class CpkRequestEntity extends RequestEntity {
    /**
     * 用户 ID
     */
    private String uid;

    /**
     * hash 算法
     */
    private String hash;

    /**
     * 公钥 矩阵
     */
    private byte[] pubMatrix;

    /**
     * 私钥 矩阵
     */
    private byte[] priMatrix;

}

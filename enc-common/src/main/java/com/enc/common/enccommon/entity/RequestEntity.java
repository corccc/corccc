/*
 *@ClassName   EncEntity
 *@Description create class
 *@Author      kong
 *@Date        2021/4/27 下午3:18
 *@Version     1.0
 */
package com.enc.common.enccommon.entity;

import lombok.Data;

@Data
public class RequestEntity {
    /**
     *  alg name
     */
    private String algName;

    /**
     *  alg data
     */
    private String data;

}

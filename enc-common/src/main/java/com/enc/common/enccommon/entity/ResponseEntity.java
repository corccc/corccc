/*
 *@ClassName   ResponseEntity
 *@Description create class
 *@Author      kong
 *@Date        2021/4/27 下午5:51
 *@Version     1.0
 */
package com.enc.common.enccommon.entity;

import lombok.Data;

@Data
public class ResponseEntity {
    /**
     *  data
     */
    private String data;

    /**
     *  cipher data
     */
    private String cipherData;

    /**
     *
     */
    private String showData = "";

    /**
     * Response code
     */
    private int code = 200;

    /**
     * Response message
     */
    private String message = "success";
}

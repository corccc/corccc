/*
 *@ClassName   ErrorCode
 *@Description create class
 *@Author      kong
 *@Date        2021/5/11 下午3:59
 *@Version     1.0
 */
package com.enc.common.enccommon.exception;

public class ErrorCode {
    /**/
    public static final int     INPUT_DATA_ERR_CODE    = 01000000;
    public static final String  INPUT_DATA_ERR_MSG     = "Input data can`t to be null";

    public static final int     INPUT_KEY_ERR_CODE     = 01000001;
    public static final String  INPUT_KEY_ERR_MSG      = "Input key can`t to be null";

    public static final int     UNSUPPORT_ALG_CODE     = 02000001;
    public static final String  UNSUPPORT_ALG_MSG      = "Unsupported algorithm type";

    public static final int     UNSUPPORT_PADDING_CODE = 02000002;
    public static final String  UNSUPPORT_PADDING_MSG  = "Unsupported padding";

    public static final int     UNSUPPORT_PROVIDER_CODE= 02000003;
    public static final String  UNSUPPORT_PROVIDER_MSG = "Unsupported provider";
}
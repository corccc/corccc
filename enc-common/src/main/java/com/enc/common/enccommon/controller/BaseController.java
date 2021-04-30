/*
 *@ClassName   BaseController
 *@Description create class
 *@Author      kong
 *@Date        2021/4/26 下午4:21
 *@Version     1.0
 */
package com.enc.common.enccommon.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class BaseController {

    protected String getVersion()
    {
       return "1.0.0";
    }

}

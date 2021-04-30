/*
 *@ClassName   SymmController
 *@Description create class
 *@Author      kong
 *@Date        2021/4/26 下午4:18
 *@Version     1.0
 */
package com.enc.symm.encsymm.controller;

import com.enc.common.enccommon.controller.BaseController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/symm")
public class SymmController extends BaseController {

    @GetMapping("/version")
    public String symmVersion(){
        return getVersion();
    }

}

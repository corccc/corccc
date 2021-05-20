/*
 *@ClassName   AsymmController
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 上午10:49
 *@Version     1.0
 */
package com.enc.asymm.encasymm.controller;

import com.enc.asymm.encasymm.entity.AsymmReqeustEntity;
import com.enc.asymm.encasymm.entity.AsymmResponseEntity;
import com.enc.asymm.encasymm.service.impl.AsymmService;
import com.enc.common.enccommon.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.WebEndpoint;

@RestController
@RequestMapping("/asymm")
@CrossOrigin(origins = "*")
public class AsymmController extends BaseController {

    @Autowired
    private AsymmService asymmService;

    @GetMapping("/version")
    public String asymmVersion(){
        return getVersion();
    }

    @PostMapping("/genKeyPair")
    public AsymmResponseEntity genKeyPair(@RequestBody AsymmReqeustEntity reqeustEntity) {
        return asymmService.genKeyPair(reqeustEntity);
    }

    @PostMapping("/calPubKey")
    public AsymmResponseEntity calPubKey(@RequestBody AsymmReqeustEntity reqeustEntity) {
        return asymmService.calPubKey(reqeustEntity);
    }
}

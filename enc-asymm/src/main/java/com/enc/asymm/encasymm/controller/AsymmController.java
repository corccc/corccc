/*
 *@ClassName   AsymmController
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 上午10:49
 *@Version     1.0
 */
package com.enc.asymm.encasymm.controller;

import com.enc.asymm.encasymm.entity.AsymmRequestEntity;
import com.enc.asymm.encasymm.entity.AsymmResponseEntity;
import com.enc.asymm.encasymm.service.impl.AsymmService;
import com.enc.common.enccommon.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public AsymmResponseEntity genKeyPair(@RequestBody AsymmRequestEntity requestEntity) {
        return asymmService.genKeyPair(requestEntity);
    }

    @PostMapping("/calPubKey")
    public AsymmResponseEntity calPubKey(@RequestBody AsymmRequestEntity requestEntity) {
        return asymmService.calPubKey(requestEntity);
    }

    @PostMapping("/enc")
    public AsymmResponseEntity enc(@RequestBody AsymmRequestEntity requestEntity) {
        return asymmService.enc(requestEntity);
    }

    @PostMapping("/dec")
    public AsymmResponseEntity dec(@RequestBody AsymmRequestEntity requestEntity) {
        return asymmService.dec(requestEntity);
    }

    @PostMapping("/sign")
    public AsymmResponseEntity sign(@RequestBody AsymmRequestEntity requestEntity) {
        return asymmService.sign(requestEntity);
    }

    @PostMapping("/vsign")
    public AsymmResponseEntity vsign(@RequestBody AsymmRequestEntity requestEntity) {
        return asymmService.vsign(requestEntity);
    }

}

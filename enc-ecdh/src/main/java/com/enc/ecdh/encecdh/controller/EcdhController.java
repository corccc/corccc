/*
 *@ClassName   AsymmController
 *@Description create class
 *@Author      kong
 *@Date        2021/5/13 上午10:49
 *@Version     1.0
 */
package com.enc.ecdh.encecdh.controller;

import com.enc.common.enccommon.controller.BaseController;
import com.enc.ecdh.encecdh.entity.EcdhRequestEntity;
import com.enc.ecdh.encecdh.entity.EcdhResponseEntity;
import com.enc.ecdh.encecdh.service.IEcdhService;
import com.enc.ecdh.encecdh.service.impl.EcdhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecdh")
@CrossOrigin(origins = "*")
public class EcdhController extends BaseController {

    @Autowired
    private IEcdhService ecdhService;

    @GetMapping("/version")
    public String asymmVersion(){
        return getVersion();
    }

    @PostMapping("/genKeyPair")
    public EcdhResponseEntity genKeyPair(@RequestBody EcdhRequestEntity requestEntity) {
        return ecdhService.genKeyPair(requestEntity);
    }

    @PostMapping("/calPubKey")
    public EcdhResponseEntity calPubKey(@RequestBody EcdhRequestEntity requestEntity) {
        return ecdhService.calPubKey(requestEntity);
    }

    @PostMapping("/calSecretKey")
    public EcdhResponseEntity calSecretKey(@RequestBody EcdhRequestEntity requestEntity) {
        return ecdhService.calSecretKey(requestEntity);
    }

}

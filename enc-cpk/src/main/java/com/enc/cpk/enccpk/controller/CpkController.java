/*
 *@ClassName   CpkController
 *@Description create class
 *@Author      kong
 *@Date        2021/5/25 上午10:37
 *@Version     1.0
 */
package com.enc.cpk.enccpk.controller;

import com.enc.common.enccommon.controller.BaseController;
import com.enc.cpk.enccpk.entity.CpkRequestEntity;
import com.enc.cpk.enccpk.entity.CpkResponseEntity;
import com.enc.cpk.enccpk.service.ICpkService;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cpk")
@CrossOrigin(origins = "*")
public class CpkController extends BaseController {

    @Autowired
    private ICpkService cpkService;


    @GetMapping("/version")
    public String asymmVersion() {
        return getVersion();
    }

    @PostMapping("/pubMatrix")
    public CpkResponseEntity pubMatrix(MultipartFile file, String uid, String hash) {
        return cpkService.pubMatrix(file, uid, hash);
    }

    @PostMapping("/priMatrix")
    public CpkResponseEntity priMatrix(MultipartFile file, String uid, String hash) {
        return cpkService.priMatrix(file, uid, hash);
    }

    @PostMapping("/calPub")
    public CpkResponseEntity calPub(CpkRequestEntity requestEntity) {
        return null;
    }

    @PostMapping("/calPri")
    public CpkResponseEntity calPri(CpkRequestEntity requestEntity) {
        return null;
    }

}

/*
 *@ClassName   HashController
 *@Description create class
 *@Author      kong
 *@Date        2021/4/27 下午2:08
 *@Version     1.0
 */
package com.enc.hash.enchash.controller;

import com.enc.common.enccommon.controller.BaseController;
import com.enc.common.enccommon.entity.RequestEntity;
import com.enc.common.enccommon.entity.ResponseEntity;
import com.enc.hash.enchash.service.IHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hash")
@CrossOrigin(origins = "*")
public class HashController extends BaseController {

    @Autowired
    private IHashService hashService;

    @GetMapping("/version")
    public String hashVersion() {
        return getVersion();
    }

    @PostMapping("/digest")
    public ResponseEntity digest(@RequestBody RequestEntity hashEntity){
        System.out.println("==================");;
        return hashService.digestEntity(hashEntity);
    }
}

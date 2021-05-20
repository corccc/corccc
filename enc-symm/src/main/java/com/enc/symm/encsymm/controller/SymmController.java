/*
 *@ClassName   SymmController
 *@Description create class
 *@Author      kong
 *@Date        2021/4/26 下午4:18
 *@Version     1.0
 */
package com.enc.symm.encsymm.controller;

import com.enc.common.enccommon.controller.BaseController;
import com.enc.common.enccommon.entity.ResponseEntity;
import com.enc.symm.encsymm.entity.SymmReqeustEntity;
import com.enc.symm.encsymm.service.ISymmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/symm")
@CrossOrigin(origins = "*")
public class SymmController extends BaseController {

    @Autowired
    private ISymmService symmService;

    @GetMapping("/version")
    public String symmVersion(){
        return getVersion();
    }

    @PostMapping("/encrypt")
    public ResponseEntity encrypt(@RequestBody SymmReqeustEntity symEntity){
        return symmService.encryptEntity(symEntity);
    }

    @PostMapping("/decrypt")
    public ResponseEntity decrypt(@RequestBody SymmReqeustEntity symEntity){
        return symmService.decryptEntity(symEntity);
    }

}

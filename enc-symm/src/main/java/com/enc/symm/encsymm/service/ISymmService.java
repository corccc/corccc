package com.enc.symm.encsymm.service;

import com.enc.common.enccommon.entity.ResponseEntity;
import com.enc.symm.encsymm.entity.SymmReqeustEntity;

public interface ISymmService {
    /**
     * 加密
     * @param encEntity  加密实体
     * @return 响应实体
     */
    public ResponseEntity encryptEntity(SymmReqeustEntity encEntity);

    /**
     * 解密
     * @param decEntity 解密实体
     * @return 响应实体
     */
    public ResponseEntity decryptEntity(SymmReqeustEntity decEntity);
}

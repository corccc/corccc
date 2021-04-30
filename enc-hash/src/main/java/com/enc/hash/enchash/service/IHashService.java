package com.enc.hash.enchash.service;

import com.enc.common.enccommon.entity.RequestEntity;
import com.enc.common.enccommon.entity.ResponseEntity;

public interface IHashService {
    public ResponseEntity digestEntity(RequestEntity encEntity);
}

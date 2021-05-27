package com.enc.cpk.enccpk.service;

import com.enc.common.enccommon.utils.StringUtil;
import com.enc.cpk.enccpk.entity.CpkRequestEntity;
import com.enc.cpk.enccpk.entity.CpkResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ICpkService {

    public CpkResponseEntity pubMatrix(MultipartFile file, String uid, String hash);

    public CpkResponseEntity priMatrix(MultipartFile file, String uid, String hash);

    public CpkResponseEntity calPub(CpkRequestEntity requestEntity);

    public CpkResponseEntity calPri(CpkRequestEntity requestEntity);
}

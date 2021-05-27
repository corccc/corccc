/*
 *@ClassName   CpkService
 *@Description create class
 *@Author      kong
 *@Date        2021/5/25 下午2:22
 *@Version     1.0
 */
package com.enc.cpk.enccpk.service.impl;

import com.enc.common.enccommon.utils.StringUtil;
import com.enc.cpk.enccpk.entity.CpkRequestEntity;
import com.enc.cpk.enccpk.entity.CpkResponseEntity;
import com.enc.cpk.enccpk.service.ICpkService;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zz.gmhelper.CPKUtil;

@Service
public class CpkService implements ICpkService {

    @Override
    public CpkResponseEntity pubMatrix(MultipartFile file, String uid, String hash) {
        CpkResponseEntity responseEntity = new CpkResponseEntity();
        CPKUtil cpkUtil = new CPKUtil();
        String fmtUid   = StringUtil.formatHexString(uid);
        byte[] usrBytes = StringUtil.hexStringToBytes(fmtUid);
        try {
            byte[] matrixBytes = new byte[file.getBytes().length + 256];
            System.arraycopy(file.getBytes(), 0, matrixBytes, 256, file.getBytes().length);
            byte[] pubBytes = cpkUtil.computeCPKPubKey(matrixBytes, usrBytes);
            String pubStr   = Hex.toHexString(pubBytes);
            String showStr = "";
            showStr = "算法: " + "计算公钥" + "\n"
                    + "映射算法: " + hash + "\n"
                    + "UID: " + "长度 " + usrBytes.length + " 字节" + "\n"
                    + fmtUid + "\n"
                    + "公钥: " + "长度 " + pubStr.length() + " 字节" + "\n"
                    + pubStr;
            responseEntity.setShowData(showStr);
        } catch (Exception e) {
            responseEntity.setShowData("公钥计算失败:" + e.getMessage());
        }
        return responseEntity;
    }

    @Override
    public CpkResponseEntity priMatrix(MultipartFile file, String uid, String hash) {
        CpkResponseEntity responseEntity = new CpkResponseEntity();
        CPKUtil cpkUtil = new CPKUtil();
        String fmtUid   = StringUtil.formatHexString(uid);
        byte[] usrBytes = StringUtil.hexStringToBytes(fmtUid);
        try {
            byte[] matrixBytes = new byte[file.getBytes().length + 256];
            System.arraycopy(file.getBytes(), 0, matrixBytes, 256, file.getBytes().length);
            byte[] priBytes = cpkUtil.computeCPKPriKey(matrixBytes, usrBytes);
            String priStr   = Hex.toHexString(priBytes);
            String showStr = "";
            showStr = "算法: " + "计算私钥" + "\n"
                    + "映射算法: " + hash + "\n"
                    + "UID: " + "长度 " + usrBytes.length + " 字节" + "\n"
                    + fmtUid + "\n"
                    + "私钥: " + "长度 " + priStr.length() + " 字节" + "\n"
                    + priStr;
            responseEntity.setShowData(showStr);
        } catch (Exception e) {
            responseEntity.setShowData(e.getMessage());
        }
        return responseEntity;
    }

    @Override
    public CpkResponseEntity calPub(CpkRequestEntity requestEntity) {
        CpkResponseEntity responseEntity = new CpkResponseEntity();

        CPKUtil cpkUtil = new CPKUtil();
        String fmtUid   = StringUtil.formatHexString(requestEntity.getUid());
        byte[] usrBytes = StringUtil.hexStringToBytes(fmtUid);
        byte[] pubBytes = cpkUtil.computeCPKPubKey(requestEntity.getPubMatrix(), usrBytes);
        String pubStr   = Hex.toHexString(pubBytes);

        String showStr = "";
        showStr = "算法: " + "计算公钥矩阵"
                + "UID: " + "长度 " + usrBytes.length + " 字节" + "\n"
                + fmtUid + "\n"
                + "公钥: " + "长度 " + pubStr.length() + " 字节" + "\n"
                + pubStr;
        responseEntity.setShowData(showStr);
        return responseEntity;
    }

    @Override
    public CpkResponseEntity calPri(CpkRequestEntity requestEntity) {
        CpkResponseEntity responseEntity = new CpkResponseEntity();

        CPKUtil cpkUtil = new CPKUtil();
        String fmtUid   = StringUtil.formatHexString(requestEntity.getUid());
        byte[] usrBytes = StringUtil.hexStringToBytes(fmtUid);
        byte[] priBytes = cpkUtil.computeCPKPriKey(requestEntity.getPriMatrix(), usrBytes);
        String priStr   = Hex.toHexString(priBytes);

        String showStr = "";
        showStr = "算法: " + "计算公钥矩阵"
                + "UID: " + "长度 " + usrBytes.length + " 字节" + "\n"
                + fmtUid + "\n"
                + "公钥: " + "长度 " + priStr.length() + " 字节" + "\n"
                + priStr;
        responseEntity.setShowData(showStr);
        return responseEntity;
    }
}

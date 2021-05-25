/*
 *@ClassName   ECCurveUtil
 *@Description create class
 *@Author      kong
 *@Date        2021/5/21 下午3:08
 *@Version     1.0
 */
package com.enc.common.enccommon.utils;

import com.enc.common.enccommon.enmu.EccSecp;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.math.ec.custom.sec.*;

public class ECCurveUtil {
    public static ECCurve getCurve(String secp){
        ECCurve curve = null;
        switch (secp) {
            case "secp128r1":
                curve = new SecP128R1Curve();
                break;
            case "secp160k1":
                curve = new SecP160K1Curve();
                break;
            case "secp160r1":
                curve = new SecP160R1Curve();
                break;
            case "secp192k1":
                curve = new SecP192K1Curve();
                break;
            case "secp192r1":
                curve = new SecP192R1Curve();
                break;
            case "secp224k1":
                curve = new SecP224K1Curve();
                break;
            case "secp224r1":
                curve = new SecP224R1Curve();
                break;
            case "secp256r1":
                curve = new SecP256R1Curve();
                break;
            case "sm2p256v1":
                curve = new SM2P256V1Curve();
                break;
            default:
                break;
        }
        return curve;
    }
}

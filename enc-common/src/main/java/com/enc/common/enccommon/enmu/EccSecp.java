/*
 *@ClassName   EccSecp
 *@Description create class
 *@Author      kong
 *@Date        2021/5/21 下午2:54
 *@Version     1.0
 */
package com.enc.common.enccommon.enmu;

import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.math.ec.custom.sec.*;

public enum EccSecp {
    SECP128R1("secp128r1",  "secp128r1"),
    SECP160K1("secp160k1",  "secp160k1"),
    SECP160R1("secp160r1",  "secp160r1"),
    SECP192K1("secp192k1",  "secp192k1"),
    SECP192R1("secp192r1",  "secp192r1"),
    SECP224K1("secp224k1",  "secp224k1"),
    SECP224R1("secp224r1",  "secp224r1"),
    SECP256R1("secp256r1",  "secp256r1"),
    ;

    private String name;
    private String value;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    EccSecp(String value){
        this.value = value;
    }

    EccSecp(String name, String value){
        this.name = name;
        this.value = value;
    }

}

package com.enc.common.enccommon;

public enum CertStatus {
    NORMAL("正常", 0),
    FORIMPORT("待导入", 1),
    REVOKED("吊销状态", 2),
    EXPIRED("已过期", 3),

    UPDATE("已更新", 10),
    NOTUPDATE("未更新", 11)
    ;

    private String name;
    private int type;

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    CertStatus(int type) {
        this.type = type;
    }

    CertStatus(String name, int type) {
        this.name = name;
        this.type = type;
    }
}

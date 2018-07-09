package com.jci.vsd.enimt;

/**
 * Created by Yso on 2017/11/8.
 */

public enum ScanTypeEnum {
    REVENUE(0,"收料"),
    STORE(1,"存料"),
    SEND(2,"发料"),
    TRANSFER(3,"主动移库"),
    SCANLOCATION(4,"扫描库位"),
    CHECK(5,"主动盘库"),
    APPLYTRANSFER(6,"申请移库"),
    MATERIALSEARCH(7,"物料查询"),
    ECOSEARCH(8,"ECO查询"),
    BINDCARD(9,"绑定小车"),
    DELIVERSTAUT(10,"发料状态查询");

    private int code;
    private String message;

    ScanTypeEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

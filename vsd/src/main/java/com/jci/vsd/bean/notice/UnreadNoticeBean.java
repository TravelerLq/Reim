package com.jci.vsd.bean.notice;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 17/12/5.
 */

public class UnreadNoticeBean extends BaseBean {

    /**
     * MsgId : f82e3b7d-fa7b-417a-ac82-445084b9c42a
     * Sender : -1
     * Receiver : 3
     * MsgTitle : 消息测试
     * MsgContent : 消息测试
     * CreateDate : 2017-12-04 00:00:00
     * Status 0是未读，1是已读
     *   {
     "MsgId": "c65e0955-57f5-4506-b8e8-bf57af304866",
     "Sender": "-1",
     "Receiver": "2",
     "MsgTitle": "消息测试",
     "MsgContent": "消息测试",
     "CreateDate": "2017-12-04 00:00:00",
     "Status": 1
     }
     */

    private String MsgId;
    private String Sender;
    private String Receiver;
    private String MsgTitle;
    private String MsgContent;
    private String CreateDate;
    private String Status;


    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String MsgId) {
        this.MsgId = MsgId;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getMsgTitle() {
        return MsgTitle;
    }

    public void setMsgTitle(String MsgTitle) {
        this.MsgTitle = MsgTitle;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String MsgContent) {
        this.MsgContent = MsgContent;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

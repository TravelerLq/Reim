package com.jci.vsd.bean.reim;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/3/20.
 */

public class ReimAddItemBean extends BaseBean {

    /**
     * * original : 2
     * start : 1   发生时间
     * end : 1      结束时间
     * bp : 1       发生地点
     * dst : 2     结束地点
     * amount : 1    金额
     * remark : 2       备注
     * number : 1        号码
     * qty : 1         数量
     * details : 2         细节
     * reason : 1       原因
     * cat : 1        二级科目Id
     * item : 2       三级科目Id
     * client : 1 客户
     * cer 证书
     * sign :验签后签名的key
     * categoryName:
     */
    private int id;
    private String start = "";
    private String original = "";
    private String end = "";
    private String bp = "";
    private String dst = "";
    private String amount = "";
    private String remark = "";
    private String number = "";
    private String qty = "";
    private String details = "";
    private String reason = "";
    private String cat = "";
    private String item = "";
    private String client = "";

    private String sign = "";
    private String cer = "";
    private String categoryName = "";

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}

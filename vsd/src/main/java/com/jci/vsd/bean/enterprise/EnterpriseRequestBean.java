package com.jci.vsd.bean.enterprise;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/12.
 */

public class EnterpriseRequestBean extends BaseBean {


    /**
     * name : 南京御安神物联网科技有限公司
     * nature : 有限责任公司
     * vatColl : 一般纳税人
     * incomeTaxColl : 查账征收
     * taxId : 9132010530261362XN
     * bank : 农行华荣支行
     * bankAcct : 10100101040010592
     * address : 南京玄武区玄武大道699-10号5幢
     * phone : 025-83377373
     * invoice : 自开
     * legal : 时辉
     * legalIdNumber : 321028197505222615
     * quota : 1000000.0
     * creator : 2
     */
    private int id;
    private String name;
    private String nature;
    private String vatColl;
    private String incomeTaxColl;
    private String taxId;
    private String bank;
    private String bankAcct;
    private String address;
    private String phone;
    private String invoice;
    private String legal;
    private String legalIdNumber;
    private double quota;
    private int creator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getVatColl() {
        return vatColl;
    }

    public void setVatColl(String vatColl) {
        this.vatColl = vatColl;
    }

    public String getIncomeTaxColl() {
        return incomeTaxColl;
    }

    public void setIncomeTaxColl(String incomeTaxColl) {
        this.incomeTaxColl = incomeTaxColl;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getLegalIdNumber() {
        return legalIdNumber;
    }

    public void setLegalIdNumber(String legalIdNumber) {
        this.legalIdNumber = legalIdNumber;
    }

    public double getQuota() {
        return quota;
    }

    public void setQuota(double quota) {
        this.quota = quota;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}

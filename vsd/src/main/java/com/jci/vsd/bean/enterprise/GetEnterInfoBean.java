package com.jci.vsd.bean.enterprise;

import com.jci.vsd.bean.BaseBean;

import java.util.List;

/**
 * Created by liqing on 18/7/16.
 * 获取公司信息
 */

public class GetEnterInfoBean extends BaseBean {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<CosBean> cos;

    public List<CosBean> getCos() {
        return cos;
    }

    public void setCos(List<CosBean> cos) {
        this.cos = cos;
    }

    public static class CosBean {
        /**
         * id : 118
         * name : 南京御安神物联网科技有限公司
         * nature : 国有企业
         * vatColl : 小规模纳税人
         * incomeTaxColl : 查账征收
         * taxId : 9132010530261362XN
         * bank : 农行华荣支行
         * bankAcct : 10100101040010592
         * address : 南京玄武区玄武大道699-10号5幢
         * phone : 025-83377373
         * invoice : 自开
         * legal : 时辉
         * legalIdNumber : 321028197505222615
         * creator : 26
         * createTime : 1531710072000
         * updater : null
         * updateTime : null
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
        private int creator;
        private long createTime;
        private Object updater;
        private Object updateTime;

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

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdater() {
            return updater;
        }

        public void setUpdater(Object updater) {
            this.updater = updater;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }
    }
}

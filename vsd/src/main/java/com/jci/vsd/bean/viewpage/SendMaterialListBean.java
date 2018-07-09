package com.jci.vsd.bean.viewpage;

import com.jci.vsd.bean.dialog.BaseBean;

/**发料单列表对象
 * Created by Yso on 2017/11/9.
 */

public class SendMaterialListBean extends BaseBean {
    private String orderId;
    private String SPANum;
    private String date;
    private String productLine;
    private String productType;
    private String status;
    private boolean isCheck;

    public SendMaterialListBean(String orderId, String SPANum, String date, String productLine, String productType, String status) {
        this.orderId = orderId;
        this.SPANum = SPANum;
        this.date = date;
        this.productLine = productLine;
        this.productType = productType;
        this.status = status;
    }

    public SendMaterialListBean() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSPANum() {
        return SPANum;
    }

    public void setSPANum(String SPANum) {
        this.SPANum = SPANum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

package com.jci.vsd.bean.viewpage;

import com.jci.vsd.bean.dialog.BaseBean;

/**代发料列表对象
 * Created by Yso on 2017/11/10.
 */

public class WaitSendMaterialListBean extends BaseBean {
    private String materialNo;
    private String areaNo;
    private String num;
    private String descrp;
    private String liftCycle;

    public WaitSendMaterialListBean(String materialNo, String areaNo, String num, String descrp, String liftCycle) {
        this.materialNo = materialNo;
        this.areaNo = areaNo;
        this.num = num;
        this.descrp = descrp;
        this.liftCycle = liftCycle;
    }

    public WaitSendMaterialListBean() {
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }

    public String getLiftCycle() {
        return liftCycle;
    }

    public void setLiftCycle(String liftCycle) {
        this.liftCycle = liftCycle;
    }
}

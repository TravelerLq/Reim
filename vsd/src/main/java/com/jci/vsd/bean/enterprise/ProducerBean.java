package com.jci.vsd.bean.enterprise;


import com.jci.vsd.bean.BaseBean;

import java.util.List;

/**
 * Created by liqing on 18/5/3.
 */

public class ProducerBean extends BaseBean {


    /**
     * fath : 0
     * coverdpts : [{"name":"研发部","dpt":118},{"name":"人事部","dpt":119}]
     * name : 领导审批
     * checkername : 加图索
     * id : 2
     * checker : 29
     * sort : 1
     */

    private int fath;
    private String name;
    private String checkername;
    private int id;
    private int checker;
    private int sort;
    private List<CoverdptsBean> coverdpts;

    public int getFath() {
        return fath;
    }

    public void setFath(int fath) {
        this.fath = fath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckername() {
        return checkername;
    }

    public void setCheckername(String checkername) {
        this.checkername = checkername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChecker() {
        return checker;
    }

    public void setChecker(int checker) {
        this.checker = checker;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<CoverdptsBean> getCoverdpts() {
        return coverdpts;
    }

    public void setCoverdpts(List<CoverdptsBean> coverdpts) {
        this.coverdpts = coverdpts;
    }

    public static class CoverdptsBean extends BaseBean {
        /**
         * name : 研发部
         * dpt : 118
         */

        private String name;
        private int dpt;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDpt() {
            return dpt;
        }

        public void setDpt(int dpt) {
            this.dpt = dpt;
        }
    }
}

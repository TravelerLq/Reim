package com.jci.vsd.bean.enterprise;

import com.jci.vsd.bean.BaseBean;

import java.util.List;

/**
 * Created by liqing on 18/7/24.
 */

public class ProducerSettingInfoBean extends BaseBean {


        /**
         * avlusers : [{"uid":28,"name":"屠正松"}]
         * chkpnts : [{"sort":2,"faths":[{"name":"领导审批","id":2}]}]
         * sort : 3
         * avldpts : [{"name":"财务部","dpt":120}]
         */

        private int sortavl;
        private List<AvlusersBean> avlusers;
        private List<ChkpntsBean> chkpnts;
        private List<AvldptsBean> avldpts;

    public int getSortavl() {
        return sortavl;
    }

    public void setSortavl(int sortavl) {
        this.sortavl = sortavl;
    }

    public List<AvlusersBean> getAvlusers() {
            return avlusers;
        }

        public void setAvlusers(List<AvlusersBean> avlusers) {
            this.avlusers = avlusers;
        }

        public List<ChkpntsBean> getChkpnts() {
            return chkpnts;
        }

        public void setChkpnts(List<ChkpntsBean> chkpnts) {
            this.chkpnts = chkpnts;
        }

        public List<AvldptsBean> getAvldpts() {
            return avldpts;
        }

        public void setAvldpts(List<AvldptsBean> avldpts) {
            this.avldpts = avldpts;
        }

        public static class AvlusersBean {
            /**
             * uid : 28
             * name : 屠正松
             */

            private int uid;
            private String name;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ChkpntsBean {
            /**
             * sort : 2
             * faths : [{"name":"领导审批","id":2}]
             */

            private int sort;
            private List<FathsBean> faths;

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public List<FathsBean> getFaths() {
                return faths;
            }

            public void setFaths(List<FathsBean> faths) {
                this.faths = faths;
            }

            public static class FathsBean {
                /**
                 * name : 领导审批
                 * id : 2
                 */

                private String name;
                private int id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }

        public static class AvldptsBean {
            /**
             * name : 财务部
             * dpt : 120
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



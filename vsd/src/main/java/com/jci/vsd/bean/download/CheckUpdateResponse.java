package com.jci.vsd.bean.download;

import com.jci.vsd.bean.dialog.BaseBean;


/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class CheckUpdateResponse extends BaseBean {


            /**
             * flag : true
             * last : 0.9.9
             * min : 0.9.9
             * url : test
             * des : 测试
             * has : false
             * force : false
             * size : 10
             * md5 : test
             */

            private boolean flag;
            private String last;
            private String min;
            private String url;
            private String des;
            private boolean has;
            private boolean force;
            private int size;
            private String md5;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getLast() {
                return last;
            }

            public void setLast(String last) {
                this.last = last;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public boolean isHas() {
                return has;
            }

            public void setHas(boolean has) {
                this.has = has;
            }

            public boolean isForce() {
                return force;
            }

            public void setForce(boolean force) {
                this.force = force;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }
        }


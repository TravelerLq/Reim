package com.jci.vsd.bean;

import java.util.List;

/**
 * Created by liqing on 18/7/20.
 */

public class CatBean extends BaseBean {


        /**
         * cat : 2
         * cname : 业务招待费
         * items : [{"item":1,"remark":"招待客户**；住宿时间、地点","id":2,"iname":"住宿费"},{"item":2,"remark":"招待客户**；上下车日期、地点","id":3,"iname":"打车票"},{"item":3,"remark":"招待客户**；餐饮时间/地点","id":4,"iname":"餐饮费"},{"item":4,"remark":"招待客户**；招待日期","id":5,"iname":"食品票"},{"item":5,"remark":"发生时间；","id":6,"iname":"系统维护费"},{"item":6,"remark":"发生时间；","id":7,"iname":"软件服务费"},{"item":7,"remark":"发生时间；","id":8,"iname":"技术服务费"},{"item":21,"remark":"采购时间、采购地点、采购明细","id":9,"iname":"办公用品（办公费属下）"},{"item":21,"remark":"采购时间、采购地点、采购明细","id":129,"iname":"办公用品（办公费属下）"},{"item":8,"remark":"出差日期起止、出差地址","id":12,"iname":"火车票"},{"item":9,"remark":"出差日期起止、出差地址","id":13,"iname":"汽车票"},{"item":10,"remark":"出差日期起止、出差地址","id":14,"iname":"飞机票"},{"item":18,"remark":"出差日期起止、出差地址","id":15,"iname":"订票费"},{"item":1,"remark":"住宿时间、地点","id":16,"iname":"住宿费"},{"item":2,"remark":"发生时间、地点","id":17,"iname":"打车票"},{"item":3,"remark":"餐费标准；天数","id":18,"iname":"餐饮费"},{"item":11,"remark":"发生时间、地点","id":19,"iname":"加油票"},{"item":12,"remark":"发生时间、地点","id":20,"iname":"地铁票"},{"item":13,"remark":"发生时间、地点","id":21,"iname":"过路过桥费"},{"item":14,"remark":"发生时间、地点","id":22,"iname":"租车费"},{"item":23,"remark":"出差日期起止、出差地址","id":99,"iname":"轮船（管理部门差旅费属下）"},{"item":24,"remark":"出差日期起止、出差地址","id":100,"iname":"自驾（管理部门差旅费属下）"},{"item":8,"remark":"出差日期起止、出差地址","id":108,"iname":"火车票"},{"item":9,"remark":"出差日期起止、出差地址","id":109,"iname":"汽车票"},{"item":10,"remark":"出差日期起止、出差地址","id":110,"iname":"飞机票"},{"item":18,"remark":"出差日期起止、出差地址","id":111,"iname":"订票费"},{"item":1,"remark":"住宿时间、地点","id":112,"iname":"住宿费"},{"item":2,"remark":"发生时间、地点","id":113,"iname":"打车票"},{"item":3,"remark":"餐费标准；天数","id":114,"iname":"餐饮费"},{"item":11,"remark":"发生时间、地点","id":115,"iname":"加油票"},{"item":12,"remark":"发生时间、地点","id":116,"iname":"地铁票"},{"item":13,"remark":"发生时间、地点","id":117,"iname":"过路过桥费"},{"item":14,"remark":"发生时间、地点","id":118,"iname":"租车费"},{"item":2,"remark":"因**事或**业务外出；发生时间、地点","id":23,"iname":"打车票"},{"item":2,"remark":"因**事或**业务外出；发生时间、地点","id":62,"iname":"打车票"},{"item":12,"remark":"因**事或**业务外出；发生时间、地点","id":63,"iname":"地铁票"},{"item":25,"remark":"因**事或**业务外出；发生时间、地点","id":64,"iname":"公交（管理部门交通费属下）"},{"item":12,"remark":"因**事或**业务外出；发生时间、地点","id":101,"iname":"地铁票"},{"item":25,"remark":"因**事或**业务外出；发生时间、地点","id":102,"iname":"公交（管理部门交通费属下）"},{"item":26,"remark":"采购时间；采购地点；采购明细","id":25,"iname":"劳保用品（劳保费属下）"},{"item":26,"remark":"采购时间；采购地点；采购明细","id":90,"iname":"劳保用品（劳保费属下）"},{"item":26,"remark":"采购时间；采购地点；采购明细","id":107,"iname":"劳保用品（劳保费属下）"},{"item":11,"remark":"本公司车辆；车牌号；行驶公里数；加油时间","id":26,"iname":"加油票"},{"item":15,"remark":"车牌号；停车事由；停车地点、时间","id":27,"iname":"停车费"},{"item":16,"remark":"维修车辆牌号；维修项目；维修原因；预计费用；行驶公里数；维修时间","id":28,"iname":"车辆维修费"},{"item":17,"remark":"保养车辆牌号；保养项目；预计费用；行驶公里数；保养时间","id":29,"iname":"车辆保养费"},{"item":15,"remark":"车牌号；停车事由；停车地点、时间 ","id":119,"iname":"停车费"},{"item":1,"remark":"会议时间、地点；住宿日期、地点","id":36,"iname":"住宿费"},{"item":20,"remark":"会议时间、地点","id":104,"iname":"交通（会议费、董事会费属下）"},{"item":3,"remark":"会议时间、地点；餐饮时间、地点","id":105,"iname":"餐饮费"},{"item":27,"remark":"会议时间、地点","id":106,"iname":"邮电（董事会费属下）"},{"item":20,"remark":"参加**会议；会议时间、地点","id":95,"iname":"交通（会议费、董事会费属下）"},{"item":1,"remark":"参加**会议；会议时间、地点；住宿日期、地点","id":96,"iname":"住宿费"},{"item":3,"remark":"参加**会议；会议时间、地点；餐饮地点、时间","id":97,"iname":"餐饮费"},{"item":19,"remark":"参加**会议；会议时间","id":98,"iname":"文件资料印刷费"}]
         */

        private int cat;
        private String cname;
        private List<ItemsBean> items;

        public int getCat() {
            return cat;
        }

        public void setCat(int cat) {
            this.cat = cat;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean extends BaseBean {
            /**
             * item : 1
             * remark : 招待客户**；住宿时间、地点
             * id : 2
             * iname : 住宿费
             */

            private int item;
            private String remark;
            private int id;
            private String iname;

            public int getItem() {
                return item;
            }

            public void setItem(int item) {
                this.item = item;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIname() {
                return iname;
            }

            public void setIname(String iname) {
                this.iname = iname;
            }
        }
    }



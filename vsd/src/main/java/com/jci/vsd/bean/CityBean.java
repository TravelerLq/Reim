package com.jci.vsd.bean;

import java.util.List;

/**
 * Created by liqing on 18/7/20.
 */

public class CityBean {

    /**
     * cities : [{"counties":[{"areaName":"南明区","areaId":"520102"},{"areaName":"云岩区","areaId":"520103"},{"areaName":"花溪区","areaId":"520111"},{"areaName":"乌当区","areaId":"520112"},{"areaName":"白云区","areaId":"520113"},{"areaName":"观山湖区","areaId":"520115"},{"areaName":"开阳县","areaId":"520121"},{"areaName":"息烽县","areaId":"520122"},{"areaName":"修文县","areaId":"520123"},{"areaName":"清镇市","areaId":"520181"}],"areaName":"贵阳市","areaId":"520100"},{"counties":[{"areaName":"钟山区","areaId":"520201"},{"areaName":"六枝特区","areaId":"520203"},{"areaName":"水城县","areaId":"520221"},{"areaName":"盘县","areaId":"520222"}],"areaName":"六盘水市","areaId":"520200"},{"counties":[{"areaName":"红花岗区","areaId":"520302"},{"areaName":"汇川区","areaId":"520303"},{"areaName":"遵义县","areaId":"520321"},{"areaName":"桐梓县","areaId":"520322"},{"areaName":"绥阳县","areaId":"520323"},{"areaName":"正安县","areaId":"520324"},{"areaName":"道真仡佬族苗族自治县","areaId":"520325"},{"areaName":"务川仡佬族苗族自治县","areaId":"520326"},{"areaName":"凤冈县","areaId":"520327"},{"areaName":"湄潭县","areaId":"520328"},{"areaName":"余庆县","areaId":"520329"},{"areaName":"习水县","areaId":"520330"},{"areaName":"赤水市","areaId":"520381"},{"areaName":"仁怀市","areaId":"520382"}],"areaName":"遵义市","areaId":"520300"},{"counties":[{"areaName":"西秀区","areaId":"520402"},{"areaName":"平坝区","areaId":"520403"},{"areaName":"普定县","areaId":"520422"},{"areaName":"镇宁布依族苗族自治县","areaId":"520423"},{"areaName":"关岭布依族苗族自治县","areaId":"520424"},{"areaName":"紫云苗族布依族自治县","areaId":"520425"}],"areaName":"安顺市","areaId":"520400"},{"counties":[{"areaName":"七星关区","areaId":"520502"},{"areaName":"大方县","areaId":"520521"},{"areaName":"黔西县","areaId":"520522"},{"areaName":"金沙县","areaId":"520523"},{"areaName":"织金县","areaId":"520524"},{"areaName":"纳雍县","areaId":"520525"},{"areaName":"威宁彝族回族苗族自治县","areaId":"520526"},{"areaName":"赫章县","areaId":"520527"}],"areaName":"毕节市","areaId":"520500"},{"counties":[{"areaName":"碧江区","areaId":"520602"},{"areaName":"万山区","areaId":"520603"},{"areaName":"江口县","areaId":"520621"},{"areaName":"玉屏侗族自治县","areaId":"520622"},{"areaName":"石阡县 ","areaId":"520623"},{"areaName":"思南县","areaId":"520624"},{"areaName":"印江土家族苗族自治县","areaId":"520625"},{"areaName":"德江县","areaId":"520626"},{"areaName":"沿河土家族自治县","areaId":"520627"},{"areaName":"松桃苗族自治县","areaId":"520628"}],"areaName":"铜仁市","areaId":"520600"},{"counties":[{"areaName":"兴义市","areaId":"522301"},{"areaName":"兴仁县","areaId":"522322"},{"areaName":"普安县","areaId":"522323"},{"areaName":"晴隆县","areaId":"522324"},{"areaName":"贞丰县","areaId":"522325"},{"areaName":"望谟县","areaId":"522326"},{"areaName":"册亨县","areaId":"522327"},{"areaName":"安龙县","areaId":"522328"}],"areaName":"黔西南布依族苗族自治州","areaId":"522300"},{"counties":[{"areaName":"凯里市","areaId":"522601"},{"areaName":"黄平县","areaId":"522622"},{"areaName":"施秉县","areaId":"522623"},{"areaName":"三穗县","areaId":"522624"},{"areaName":"镇远县","areaId":"522625"},{"areaName":"岑巩县","areaId":"522626"},{"areaName":"天柱县 ","areaId":"522627"},{"areaName":"锦屏县","areaId":"522628"},{"areaName":"剑河县","areaId":"522629"},{"areaName":"台江县","areaId":"522630"},{"areaName":"黎平县","areaId":"522631"},{"areaName":"榕江县","areaId":"522632"},{"areaName":"从江县","areaId":"522633"},{"areaName":"雷山县","areaId":"522634"},{"areaName":"麻江县","areaId":"522635"},{"areaName":"丹寨县","areaId":"522636"}],"areaName":"黔东南苗族侗族自治州","areaId":"522600"},{"counties":[{"areaName":"都匀市","areaId":"522701"},{"areaName":"福泉市","areaId":"522702"},{"areaName":"荔波县","areaId":"522722"},{"areaName":"贵定县","areaId":"522723"},{"areaName":"瓮安县","areaId":"522725"},{"areaName":"独山县","areaId":"522726"},{"areaName":"平塘县","areaId":"522727"},{"areaName":"罗甸县","areaId":"522728"},{"areaName":"长顺县","areaId":"522729"},{"areaName":"龙里县","areaId":"522730"},{"areaName":"惠水县","areaId":"522731"},{"areaName":"三都水族自治县","areaId":"522732"}],"areaName":"黔南布依族苗族自治州","areaId":"522700"},{"counties":[{"areaName":"贵安新区","areaId":"529900"}],"areaName":"贵安新区","areaId":"529900"}]
     * areaName : 贵州省
     * areaId : 520000
     */

    private String areaName;
    private String areaId;
    private List<CitiesBean> cities;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean {
        /**
         * counties : [{"areaName":"南明区","areaId":"520102"},{"areaName":"云岩区","areaId":"520103"},{"areaName":"花溪区","areaId":"520111"},{"areaName":"乌当区","areaId":"520112"},{"areaName":"白云区","areaId":"520113"},{"areaName":"观山湖区","areaId":"520115"},{"areaName":"开阳县","areaId":"520121"},{"areaName":"息烽县","areaId":"520122"},{"areaName":"修文县","areaId":"520123"},{"areaName":"清镇市","areaId":"520181"}]
         * areaName : 贵阳市
         * areaId : 520100
         */

        private String areaName;
        private String areaId;
        private List<CountiesBean> counties;

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public List<CountiesBean> getCounties() {
            return counties;
        }

        public void setCounties(List<CountiesBean> counties) {
            this.counties = counties;
        }

        public static class CountiesBean {
            /**
             * areaName : 南明区
             * areaId : 520102
             */

            private String areaName;
            private String areaId;

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }
        }
    }
}

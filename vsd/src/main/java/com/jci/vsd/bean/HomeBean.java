package com.jci.vsd.bean;

/**
 * Created by liqing on 17/11/6.
 */

public class HomeBean {

    int color;
    String id;
    String title;
    int img;
    String authority;
    int imgFalse;
    private boolean isNeedShowNotice;
    private int unreadCnt;//未读消息条数

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public HomeBean(int img, String id, String title, int color,int imagFalse) {
        this.color = color;
        this.id = id;
        this.title = title;
        this.img = img;
        this.imgFalse=imagFalse;
    }

    public HomeBean(String title) {
        this.title = title;
    }

    public int getImgFalse() {
        return imgFalse;
    }

    public void setImgFalse(int imgFalse) {
        this.imgFalse = imgFalse;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isNeedShowNotice() {
        return isNeedShowNotice;
    }

    public void setNeedShowNotice(boolean needShowNotice) {
        isNeedShowNotice = needShowNotice;
    }

    public int getUnreadCnt() {
        return unreadCnt;
    }

    public void setUnreadCnt(int unreadCnt) {
        this.unreadCnt = unreadCnt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HomeBean user = (HomeBean) obj;
        return title.equals(user.title);
    }
}



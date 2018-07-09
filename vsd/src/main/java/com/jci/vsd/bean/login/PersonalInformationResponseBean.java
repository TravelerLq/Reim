package com.jci.vsd.bean.login;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class PersonalInformationResponseBean extends BaseBean {
    private String card;
    private String Version;
    private String Email;

    public PersonalInformationResponseBean() {
    }

    public PersonalInformationResponseBean(String cardd, String version, String email) {
        this.card = cardd;
        Version = version;
        Email = email;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

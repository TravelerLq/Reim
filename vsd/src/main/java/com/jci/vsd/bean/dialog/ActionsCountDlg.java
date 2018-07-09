package com.jci.vsd.bean.dialog;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.io.Serializable;

/**
 * Created by Victor on 10/31/2017.
 */

public class ActionsCountDlg implements Serializable ,Parcelable {
    private static final long serialVersionUID = 1L;
    public boolean Cancelable = true;
    public String HintText = "";
    public String HintAction = "";
    public transient View.OnClickListener actionListener;
    public int Countnum = 10;
    public ActionsCountDlg(){}

    public static final Creator<ActionsCountDlg> CREATOR = new Creator<ActionsCountDlg>() {
        @Override
        public ActionsCountDlg createFromParcel(Parcel in) {
            return new ActionsCountDlg(in);
        }

        @Override
        public ActionsCountDlg[] newArray(int size) {
            return new ActionsCountDlg[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected ActionsCountDlg(Parcel in) {
        Cancelable = in.readByte() != 0;
        HintText = in.readString();
        HintAction = in.readString();
        Countnum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (Cancelable ? 1 : 0));
        dest.writeString(HintText);
        dest.writeString(HintAction);
        dest.writeInt(Countnum);
    }
}

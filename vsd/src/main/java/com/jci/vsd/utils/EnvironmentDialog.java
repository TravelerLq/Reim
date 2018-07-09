package com.jci.vsd.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jci.vsd.R;
import com.jci.vsd.constant.AppConstant;

/**
 * Author: Edward
 * Date: 2018/1/19
 * Description:
 */


public class EnvironmentDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private EditText ipTxt;
    private Button okBtn;
    private SharedPreferences prefs;


    public EnvironmentDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        this.context = context;
        prefs = context.getSharedPreferences("IP_PREFS", Context.MODE_PRIVATE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_item);
        setSize();
        setCanceledOnTouchOutside(true);
        okBtn = (Button) findViewById(R.id.ok);
        ipTxt = (EditText) findViewById(R.id.ipTxt);

        okBtn.setOnClickListener(this);
        ipTxt.setText(getIP());

    }

    public void setSize() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(ipTxt.getText())) {
            String ip = ipTxt.getText().toString();
            setIP(ip);
            if (!ip.contains("http")) {
                ip = "http://" + ip;
            }

            if (!ip.contains(":")) {
                ip = ip + ":80";
            }

            if (!ip.endsWith("/")) {
                ip = ip + "/";
            }
            Log.i("IP", ip);
            AppConstant.TEMP_URL = ip;
        }

        dismiss();
    }


    private void setIP(String ip) {
        prefs.edit().putString("IP", ip).apply();
    }


    private String getIP() {
        return prefs.getString("IP", "");
    }
}

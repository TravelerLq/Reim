package com.jci.vsd.activity.Reim;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;

import butterknife.BindView;
import cn.unitid.spark.cm.sdk.business.SignatureP1Service;
import cn.unitid.spark.cm.sdk.common.DataProcessType;
import cn.unitid.spark.cm.sdk.data.response.DataProcessResponse;
import cn.unitid.spark.cm.sdk.exception.CmSdkException;
import cn.unitid.spark.cm.sdk.listener.ProcessListener;

/**
 * Created by liqing on 18/6/28.
 * 生成单据、并提交
 */

public class ReimDocSubmitActivtiy extends BaseActivity {

    @BindView(R.id.iv_reim_doc)
    ImageView ivReimDoc;
    @BindView(R.id.btn_reim_submit)
    Button btnReimDocSubmit;
    private String base64Code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_submit);
        initViewEvent();

    }

    @Override
    protected void initViewEvent() {
        ivReimDoc.setOnClickListener(this);
        btnReimDocSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_reim_submit:
                signVerifyP1(base64Code);
                break;
            //提交单据 （先签名添加，成功后－再提交）
            case R.id.iv_reim_doc:
                //查看单据大图
                break;
            default:
                break;


        }
    }

    //签名验证
    private void signVerifyP1(final String base64Code1) {
//        pdu.showpd();

        String plantext = base64Code1;


        ReimDocSubmitActivtiy.this.getIntent().putExtra("data", plantext);
        ReimDocSubmitActivtiy.this.getIntent().putExtra("type", DataProcessType.SIGNATURE_P1.name());
        SignatureP1Service signatureP1Service = new SignatureP1Service(ReimDocSubmitActivtiy.this, "1234", new ProcessListener<DataProcessResponse>() {
            @Override
            public void doFinish(DataProcessResponse dataProcessResponse, String certificate) {
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                if (dataProcessResponse.getRet() == 0) {
                    Log.e("密钥", "= " + dataProcessResponse.getResult());
                    // spu.setCertKey(dataProcessResponse.getResult());
                    //获得就是签名证书
                    Log.e("cert", "= " + certificate);
                    // spu.setCert(certificate);

                    //去提交单据
                    // submitSignJsonstring();
                } else {
//                    if (pdu.getMypDialog().isShowing()) {
//                        pdu.dismisspd();
//                    }
                    Toast.makeText(ReimDocSubmitActivtiy.this, "文件签名失败" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doException(CmSdkException e) {
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                Toast.makeText(ReimDocSubmitActivtiy.this, "文件签名失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

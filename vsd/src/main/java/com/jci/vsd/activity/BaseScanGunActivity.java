package com.jci.vsd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jci.vsd.R;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.ScanGunUtils;
import com.jci.vsd.view.widget.SimpleToast;
import com.zltd.decoder.DecoderManager;

/**快捷扫描
 * Created by Yso on 2017/11/9.
 */

public abstract  class BaseScanGunActivity extends BaseActivity {
    private static final String RECEIVE_SCANDATA_ACTION = "android.intent.action.RECEIVE_SCANDATA_BROADCAST";
    private static final String RECEIVE_SCANDATA_EXTRA = "android.intent.extra.SCAN_BROADCAST_DATA";
    @Override
    protected void initViewEvent() {

    }

    protected DecoderManager mDecoderMgr = null;
    protected ScanGunUtils mUtils;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* mDecoderMgr = DecoderManager.getInstance();
        mUtils = ScanGunUtils.getInstance();
        mUtils.init(BaseScanGunActivity.this);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        register();
      /*  int res = mDecoderMgr.connectDecoderSRV();
        if(res == Constants.RETURN_CAMERA_CONN_ERR){
            new AlertDialog.Builder(BaseScanGunActivity.this)
                    .setTitle(R.string.app_name)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage(R.string.scan_message)
                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }*/
    }

    @Override
    public void onPause() {
        unregister();
        super.onPause();
       /* mUtils.release();
        mUtils.onDestroy();
        mDecoderMgr.stopDecode();
        //lightControlHandler.sendEmptyMessageDelayed(CLOSELIGHT, 1);
        mDecoderMgr.disconnectDecoderSRV();*/
    }


    protected abstract void dealScanMessage(String result);

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String action = intent.getAction();
            if (action.equals(RECEIVE_SCANDATA_ACTION)) {
                String scanDataBytes = (String) bundle
                        .get(RECEIVE_SCANDATA_EXTRA);
                Loger.i("scanDataBytes:"+ (scanDataBytes));
                if(!TextUtils.isEmpty(scanDataBytes)){
                    dealScanMessage(scanDataBytes);
                }else{
                    SimpleToast.ToastMessage(R.string.scan_fail);
                }
            }
        }
    };

    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(RECEIVE_SCANDATA_ACTION);
        registerReceiver(mIntentReceiver, filter);
    }

    private void unregister() {
        try {
            if (mIntentReceiver != null) {
                unregisterReceiver(mIntentReceiver);
            }
        } catch (Exception e) {
        }
    }
}

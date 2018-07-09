/*
 * Copyright (c) 2017 李世界
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jci.vsd.flowable;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import com.jci.vsd.R;

/**
 * 使用该类，要确保ProgressSubscribe的回调定义在主线程中
 * @param <T>
 */
public class ProgressSubscribe<T> implements  Subscriber<T> , ProgressCancelListener {

    private SubscribeOnNextListener mOnNextListener;
    private Context mContext;
    private Subscription arg0;
    private boolean isNeedDialog;
    protected Dialog myDialog = null;
    private ImageView imageView = null;
    private TextView textView = null;
    private String tips;

    public ProgressSubscribe(SubscribeOnNextListener listener, Context context, boolean isNeedDialog){
        this.mOnNextListener = listener;
        mContext = context;
        this.isNeedDialog = isNeedDialog;
    }
    public ProgressSubscribe(SubscribeOnNextListener listener, Context context, boolean isNeedDialog, String tips){
        this.mOnNextListener = listener;
        mContext = context;
        this.isNeedDialog = isNeedDialog;
        this.tips = tips;
    }

    @Override
    public void onError(Throwable e) {
        try {
            dissmissProgressDialog();

        }catch (Exception e1){
            e1.printStackTrace();
        }

    }

    @Override
    public void onNext(Object o) {
        mOnNextListener.onNext(o);//回调MainActivity中的onNext方法
    }

    @Override
    public void onProgressCanceled() {//取消请求
        if(arg0 != null){
        	arg0.cancel();
        }
    }

	@Override
	public void onComplete() {
        try {
            dissmissProgressDialog();
        }catch (Exception e){
            e.printStackTrace();
        }
		mOnNextListener.onCompleted();//回调MainActivity中的onNext方法
	}

	@Override
	public void onSubscribe(Subscription arg0) {
        this.arg0 = arg0;
        mOnNextListener.onSubscribe(arg0);
        if (isNeedDialog) {
            if (!TextUtils.isEmpty(tips)) {
                showProgressDialog(tips);
            } else {
                showProgressDialog("");
            }
        }
		arg0.request(1);//设置访问次数为1
	}


    public void showProgressDialog(String msg) {
        try{
            dissmissProgressDialog();
            myDialog = new Dialog(mContext, R.style.dialog);
            myDialog.show();
            View view = LayoutInflater.from(mContext).inflate(
                    R.layout.loading_image, null);
            Animation operatingAnim = AnimationUtils.loadAnimation(mContext,
                    R.anim.rotate);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            imageView = (ImageView) view.findViewById(R.id.animationImage);
            textView = (TextView)view.findViewById(R.id.connectText);
            textView.setText(msg);
            textView.setVisibility(View.VISIBLE);
            imageView.startAnimation(operatingAnim);
            myDialog.setContentView(view);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateProgressShowMessage(String titleMsg){
        if(isNeedDialog && myDialog != null && myDialog.isShowing()){
            textView.setText(titleMsg);
        }else{
            showProgressDialog(titleMsg);
        }
    }


    public void dissmissProgressDialog() {
        try{
            if (myDialog != null) {
                if (myDialog.isShowing()) {
                    if (imageView != null) {
                        unbindDrawables(imageView);
                    }
                    myDialog.dismiss();
                }
                myDialog = null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Title: unbindDrawables
     * @Description: onRelease bitmap resources
     * @param @param view
     * @return void
     * @throws
     */
    private void unbindDrawables(View view) {
        Drawable back = view.getBackground();
        if (back != null) {
            back.setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
	
}


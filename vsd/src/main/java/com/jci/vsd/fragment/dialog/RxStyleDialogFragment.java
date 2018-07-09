package com.jci.vsd.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.dialog.ActionsCountDlg;
import com.jci.vsd.utils.Loger;

import io.reactivex.observers.DefaultObserver;


/**
 * RxJava 的进度框
 * Created by Victor on 10/31/2017.
 */

public class RxStyleDialogFragment extends DialogFragment {
    private Context mContext;
    protected Dialog myDialog = null;
    private ImageView imageView = null;
    private TextView textView = null;
    private String tips;
    public interface CancelRxJava{
        public void canceRxJava();
    }
    private CancelRxJava cancelRxJava;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getContext();
        myDialog = new Dialog(mContext, R.style.dialog);
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.loading_image, null);
        Animation operatingAnim = AnimationUtils.loadAnimation(mContext,
                R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView = (ImageView) view.findViewById(R.id.animationImage);
        textView = (TextView)view.findViewById(R.id.connectText);
        imageView.startAnimation(operatingAnim);
        myDialog.setContentView(view);
        return myDialog;
    }

    private void renderView(final ActionsCountDlg action) {
        setCancelable(action.Cancelable);
        if (action.Countnum > 0) {
            //resetDialog(action.HintText, action.Countnum);
        }
        Loger.i("renderView == "+action.HintText);
        if (!TextUtils.isEmpty(action.HintText)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(action.HintText);
            textView.setOnClickListener(action.actionListener);
        } else {
            textView.setVisibility(View.GONE);
        }
        textView.setText(action.HintText);
    }

    /**
     * 更新进度框字体
     * @param updateParam
     */

    public void updateProgress(ActionsCountDlg updateParam) {
        renderView(updateParam);
    }

    public void setCancelRxJava(CancelRxJava cancelRxJava) {
        this.cancelRxJava = cancelRxJava;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if(cancelRxJava != null){
            cancelRxJava.canceRxJava();
        }
    }
}

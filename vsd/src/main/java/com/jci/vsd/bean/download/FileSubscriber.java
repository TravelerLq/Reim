package com.jci.vsd.bean.download;

import android.app.Application;

import rx.Subscriber;

/**
 * Created by miya95 on 2016/12/5.
 */
public class FileSubscriber<T> extends Subscriber<T> {
    private Application application;
    private FileCallBack fileCallBack;

    public FileSubscriber(Application application, FileCallBack fileCallBack) {
        this.application = application;
        this.fileCallBack = fileCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fileCallBack != null)
            fileCallBack.onStart();
    }

    @Override
    public void onCompleted() {
        if (fileCallBack != null) {
            fileCallBack.onCompleted();
            fileCallBack.setDownloadError(false);
            fileCallBack.unsubscribe();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (fileCallBack != null) {
            fileCallBack.onError(e);
            fileCallBack.unsubscribe();
            fileCallBack.setDownloadError(false);
        }
    }

    @Override
    public void onNext(T t) {
        if (fileCallBack != null && !fileCallBack.isDownloadError())
            fileCallBack.onSuccess(t);
        fileCallBack.setDownloadError(false);
        fileCallBack.unsubscribe();
    }

}

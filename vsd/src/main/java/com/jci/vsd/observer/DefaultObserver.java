/*
 * Copyright (c) 2017 李虎
 * Copyright (c) 2017 李世界
 * Copyright (c) 2017 朱璟
 * Copyright (c) 2017 heisenberg.gong
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

package com.jci.vsd.observer;

import com.jci.vsd.R;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 保护不让外部包的类构造使用ProgressObserver
 */

public abstract class DefaultObserver<T> implements Observer<T>{
    protected abstract void showProgress();

    protected abstract void dismissProgress();

    private final boolean TAG = false;
    private Disposable disposable;
    private ObserverHolder mHolder;

    @Override
    public void onSubscribe(Disposable d) {
        if (TAG){
            Loger.d("---");
        }
        disposable = d;
        onBeginInternal();
    }

    @Override
    public void onNext(T t) {
        if (TAG){
            Loger.d("---");
        }
    }

    @Override
    public void onError(Throwable t) {
        if (TAG){
            Loger.d("---");
        }
        t.printStackTrace();
        onReleaseInternal();
        doException(t);
    }

    @Override
    public void onComplete() {
        if (TAG){
            Loger.d("---");
        }
        onReleaseInternal();
    }

    final void setObserverHolder(ObserverHolder observerHolder) {
        mHolder = observerHolder;
    }

    private void onBeginInternal() {
        onBegin();
        showProgress();
    }

    private void onReleaseInternal() {
        onRelease();
        dismissProgress();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (mHolder == null) {
            return;
        }
        if (disposable != null) {
            mHolder.removeDisposable(disposable);
        }
    }

    /**
     * 开始执行任务，可以在此执行想要的操作
     * onSubscribe
     */

    protected void onBegin() {
        if (TAG){
            Loger.d("---");
        }
    }

    /**
     * 结束执行任务，可以在结束显示
     * onError onComplete
     */
    protected void onRelease() {
        if (TAG){
            Loger.d("---");
        }
    }

    protected void doException(Throwable t) {
        Loger.i("doException = "+t.getClass().getSimpleName());
        if (t instanceof IApiException) {
            SimpleToast.ToastMessage(((IApiException) t).getErrorMsg());
            return;
        }

        if (t instanceof SocketException) {
            SimpleToast.ToastMessage(R.string.unknown_host_exception);
            return;
        }

        if (t instanceof SocketTimeoutException) {
            SimpleToast.ToastMessage(R.string.socket_timeout_exception);
            return;
        }

        if (t instanceof UnknownHostException) {
            SimpleToast.ToastMessage(R.string.unknown_host_exception);
            return;
        }
        SimpleToast.ToastMessage(t.getMessage());
    }
}

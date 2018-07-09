package com.jci.vsd.observer;

import android.view.View;
import android.widget.ListView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.utils.Loger;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换工具类
 * Created by heisenberg on 2017/6/23.
 */

public final class RxHelper {

    //bind Observable io
    private static <T> Observable<T> bindSameUI(@android.support.annotation.NonNull Observable<T> observable) {
        return observable.throttleFirst(500, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach();
    }

    //bind Observable io
    private static <T> Observable<T> bindNewThread(@android.support.annotation.NonNull Observable<T> observable) {
        return observable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .onTerminateDetach();
    }

    //bind Flowable io
    private static <T> Flowable<T> bindNewThread(@android.support.annotation.NonNull Flowable<T> flowable) {
        return flowable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .onTerminateDetach();
    }
    //bind Observable ui
    private static <T> Observable<T> bindUI(@android.support.annotation.NonNull Observable<T> observable) {
        return observable.throttleFirst(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach();
    }

    //bind Flowable ui
    private static <T> Flowable<T> bindUI(@android.support.annotation.NonNull Flowable<T> flowable) {
        return flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach();
    }


    /**
     * 调用此方法：
     * 将被观察者给观察者于UI线程观察
     * 防止遗忘onTerminateDetach
     * 并简化代码
     *
     * @param observable the observable
     * @param observer   the observer
     * @param <T>        the input type
     */
    public static <T> void bindOnUI(@android.support.annotation.NonNull Observable<T> observable, @android.support.annotation.NonNull Observer<T> observer) {
        bindUI(observable).subscribe(observer);
    }
    /**
     * 调用此方法：
     * 将被观察者给观察者于UI线程观察
     * 防止遗忘onTerminateDetach
     * 并简化代码
     *
     * @param observable the observable
     * @param observer   the observer
     * @param <T>        the input type
     */
    public static <T> void bindSameUI(@android.support.annotation.NonNull Observable<T> observable, @android.support.annotation.NonNull Observer<T> observer) {
        bindSameUI(observable).subscribe(observer);
    }

    /**
     * 调用此方法
     * 将被观察者给观察者于UI线程观察
     * 防止遗忘onTerminateDetach
     * 并简化代码
     *
     * @param flowable   the flowable
     * @param subscriber the subscriber
     * @param <T>        the input type
     */
    public static <T> void bindOnUI(@android.support.annotation.NonNull Flowable<T> flowable, @android.support.annotation.NonNull Subscriber<T> subscriber) {
        bindUI(flowable).subscribe(subscriber);
    }
    /**
     * 调用此方法
     * 按钮点击1秒内只能执行一次，防止连续点击
     *
     * @param view       view on click
     * @param observable the observable
     * @param observer   the observer
     * @param <T>        the input type
     */
    public static <T> void onClickOne(View view, final Observable<T> observable, final Observer<T> observer) {
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                bindOnUI(observable, observer);
            }
        });
    }
    /**
     * 调用此方法
     * 按钮点击1秒内只能执行一次，防止连续点击
     *
     * @param view       view on click
     * @param onClickListener   the onClickListener
     */
    public static void onClickOne(final View view, final View.OnClickListener onClickListener) {
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                onClickListener.onClick(view);
            }
        });
    }

    /**
     * 调用此方法
     * 按钮点击1秒内只能执行一次，防止连续点击
     *
     * @param view       view on click
     * @param flowable   the flowable
     * @param subscriber the subscriber
     * @param <T>        the input type
     */
    public static <T> void onClickOne(View view, final Flowable<T> flowable, final Subscriber<T> subscriber) {
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                bindOnUI(flowable, subscriber);
            }
        });
    }

    /**
     * 倒计时
     * @param time
     * @return
     */
    public static Observable<Integer> countdown(int time) {
        Loger.i("countdown "+time);
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {//通过map()将0、1、2、3...的计数变为...3、2、1、0倒计时
                    @Override
                    public Integer apply(@NonNull Long increaseTime) throws Exception {
                        Loger.i("countdown increaseTime "+increaseTime);
                        return countTime - increaseTime.intValue();
                    }
                }).take(countTime + 1);//通过take()取>=0的Observable
    }

    private static <T> Observable<T> bindSameUINotSchedule(@android.support.annotation.NonNull Observable<T> observable) {
        return observable.onTerminateDetach();
    }

    /**
     * 调用此方法：
     * 将被观察者给观察者于UI线程观察,不切换线程进度，使用默认线程
     * 防止遗忘onTerminateDetach
     * 并简化代码
     *
     * @param observable the observable
     * @param observer   the observer
     * @param <T>        the input type
     */
    public static <T> void bindSameUINotSchedule(@android.support.annotation.NonNull Observable<T> observable, @android.support.annotation.NonNull Observer<T> observer) {
        bindSameUINotSchedule(observable).subscribe(observer);
    }



    /**
     * 调用此方法：
     * 将被观察者给观察者于UI线程观察
     * 防止遗忘onTerminateDetach
     * 并简化代码
     *
     * @param observable the observable
     * @param observer   the observer
     * @param <T>        the input type
     */
    public static <T> void bindOnUIActivityLifeCycle(@android.support.annotation.NonNull Observable<T> observable, @android.support.annotation.NonNull Observer<T> observer, BaseActivity baseActivity) {
        bindUI(observable,baseActivity).subscribe(observer);
    }

    /**
     * Fragment 调用RXJAVA的生命周期
     * @param observable
     * @param observer
     * @param baseActivity
     * @param <T>
     */

    public static <T> void bindOnUIFragmentLifeCycle(@android.support.annotation.NonNull Observable<T> observable, @android.support.annotation.NonNull Observer<T> observer, BaseFragment baseActivity) {
        bindUI(observable,baseActivity).subscribe(observer);
    }

    /**
     * Activity生命周期对应
     * @param observable
     * @param baseActivity
     * @param <T>
     * @return
     */
    private static <T> Observable<T> bindUI(@android.support.annotation.NonNull Observable<T> observable,BaseActivity baseActivity) {
        return (Observable<T>) observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach().compose(baseActivity.bindToLifecycle());
    }

    /**
     * Activity生命周期对应
     * @param observable
     * @param baseActivity
     * @param <T>
     * @return
     */
    private static <T> Observable<T> bindUI(@android.support.annotation.NonNull Observable<T> observable,BaseFragment baseActivity) {
        return (Observable<T>) observable.throttleFirst(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach().compose(baseActivity.bindToLifecycle());
    }

    /**
     * listView 仿抖动
     * @param mList
     * @param consumer
     * @param <T>
     * @return
     */
    public static void getListViewQuick(ListView mList, Consumer<AdapterViewItemClickEvent> consumer){
        RxAdapterView.itemClickEvents(mList).throttleFirst(AppConstant.QUCIK_CLICK, TimeUnit.MILLISECONDS).subscribe(consumer);
    }

    public static void getViewQuick(View view, Consumer<Object> consumer){
        RxView.clicks(view).throttleFirst(AppConstant.QUCIK_CLICK, TimeUnit.MILLISECONDS).subscribe(consumer);
    }
}

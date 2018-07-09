package com.jci.vsd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jci.vsd.R;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.observer.DialogObserverHolder;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/25.
 */

public class HelpFragment extends BaseFragment  implements View.OnClickListener, DialogObserverHolder {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void addSubscription(Subscription subscription) {

    }

    @Override
    public void removeDisposable(Disposable disposable) {

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return null;
    }
}

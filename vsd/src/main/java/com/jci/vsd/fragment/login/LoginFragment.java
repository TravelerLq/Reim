package com.jci.vsd.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jci.vsd.R;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.observer.DialogObserverHolder;
import com.jci.vsd.utils.Utils;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/25.
 * login
 */

public class LoginFragment  extends BaseFragment implements View.OnClickListener, DialogObserverHolder {


    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container,false);
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
        return getActivity().getSupportFragmentManager();
    }


    @Override
    public void onDestroyView() {
        Utils.closeSoftKeyBoardNo(baseActivity);
        super.onDestroyView();
    }
}

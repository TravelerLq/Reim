package com.jci.vsd.fragment.reim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jci.vsd.R;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.utils.Loger;

/**
 * Created by liqing on 18/8/3.
 * 招待费fragment
 */

public class ReimTreatFragment extends BaseFragment {

    private String itemId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reim_treat, container, false);
        itemId = this.getArguments().getString("itemId");
        Loger.e("--fragment.getArgumentId" + itemId);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

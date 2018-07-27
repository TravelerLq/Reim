package com.jci.vsd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.jci.vsd.fragment.login.LoginFragment;
import com.jci.vsd.fragment.login.RegisterFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liqing on 18/6/25.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mData;
    private HashMap<Integer, Fragment> mFragmentHashMap = new HashMap<>();

    public PageAdapter(FragmentManager fm,List<Fragment> mData) {
        super(fm);
        this.mData = mData;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    private Fragment createFragment(int pos) {
        Fragment fragment = mFragmentHashMap.get(pos);

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new LoginFragment();
                    Log.i("fragment", "fragmentlogin");
                    break;
                case 1:
                    fragment = new RegisterFragment();
                    Log.i("fragment", "fragmentregister");
                    break;

            }
            mFragmentHashMap.put(pos, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}

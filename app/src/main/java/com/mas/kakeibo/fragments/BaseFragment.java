package com.mas.kakeibo.fragments;

import android.support.v4.app.Fragment;

import com.mas.kakeibo.activities.BaseActivity;

/**
 * Created by sow.m on 2018/11/06.
 */
public class BaseFragment extends Fragment {

    public BaseActivity obtainBaseActivity() {
        return (BaseActivity) getActivity();
    }
}

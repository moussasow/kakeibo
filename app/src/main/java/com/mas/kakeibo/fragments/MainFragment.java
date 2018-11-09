package com.mas.kakeibo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mas.kakeibo.R;
import com.mas.kakeibo.activities.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sow.m on 2018/11/06.
 */
public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.fragment_main_btn_shopping_list)
    void btnShoppingClick() {
        // ShoppingListFragmentを表示
        navigateToFragment(ShoppingListFragment.newInstance());
    }

    @OnClick(R.id.fragment_main_btn_add)
    void onAddClick() {
        // 入力フラグメントへ遷移
        navigateToFragment(InputFragment.newInstance());
    }
}

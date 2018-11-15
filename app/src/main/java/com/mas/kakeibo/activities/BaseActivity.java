package com.mas.kakeibo.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mas.kakeibo.R;
import com.mas.kakeibo.fragments.MainFragment;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        // アプリ起動後、メインフラグメントを表示
        String date = "10月23日";
        addFragment(MainFragment.newInstance(date));
    }

    /**
     * フラグメントを設定
     *
     * @param fragment フラグメント
     */
    public void addFragment(Fragment fragment) {
        final String fullName = fragment.getClass().getName();
        final String name = fullName.substring(fullName.lastIndexOf(".") + 1);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.base_activity_fragment_container, fragment, name)
                .disallowAddToBackStack()
                .commit();
    }

    /**
     * フラグメントを変更する
     *
     * @param fragment フラグメント
     */
    public void replaceFragment(Fragment fragment) {
        final String fullName = fragment.getClass().getName();
        final String name = fullName.substring(fullName.lastIndexOf(".") + 1);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_activity_fragment_container, fragment, name)
                .addToBackStack(name)
                .commit();
    }
}

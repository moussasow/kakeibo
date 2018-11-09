package com.mas.kakeibo.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mas.kakeibo.R;
import com.mas.kakeibo.database.DatabaseManager;
import com.mas.kakeibo.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sow.m on 2018/11/09.
 */
public class InputFragment extends BaseFragment {
    private static final String TAG = InputFragment.class.getSimpleName();

    public static InputFragment newInstance() {
        return new InputFragment();
    }

    @BindView(R.id.input)
    TextView mInput;

    private DatabaseManager mDatabaseManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.debug(TAG, "onActivityCreated");
        mDatabaseManager = DatabaseManager.getInstance(getActivity());
        addDummyData();
    }

    private void addDummyData() {

        mDatabaseManager.addEntry("ミルク",
                "食べ物",
                200,
                "セブンイレブン",
                "新宿３丁目",
                "11月9日",
                "img.png");
    }

    @OnClick(R.id.show)
    void onShowClick() {
        Cursor cursor = mDatabaseManager.retrieveAllEntries();
        StringBuilder text = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                text.append(cursor.getString(1) + " ");
                text.append(cursor.getString(2) + " ");
                text.append(cursor.getInt(3) + "\n");
                text.append(cursor.getString(4) + " ");
                text.append(cursor.getString(5) + " ");
                text.append(cursor.getString(6) + "\n");
            } while (cursor.moveToNext());
        }

        mInput.setText(text);
    }
}

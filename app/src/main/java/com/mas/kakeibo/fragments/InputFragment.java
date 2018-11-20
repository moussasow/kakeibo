package com.mas.kakeibo.fragments;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.mas.kakeibo.R;
import com.mas.kakeibo.constants.Common;
import com.mas.kakeibo.database.DatabaseManager;
import com.mas.kakeibo.dialogs.ProductDatePicker;
import com.mas.kakeibo.events.DatePickerEvent;
import com.mas.kakeibo.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    @BindView(R.id.fragment_input_product_name)
    EditText mEditProductName;
    @BindView(R.id.fragment_input_product_category)
    EditText mEditProductCategory;
    @BindView(R.id.fragment_input_product_price)
    EditText mEditProductPrice;
    @BindView(R.id.fragment_input_shop_name)
    EditText mEditShopName;
    @BindView(R.id.fragment_input_shop_address)
    EditText mEditProductShopAddress;
    @BindView(R.id.fragment_input_date)
    TextView mTextPurchaseDate;

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
    }


    @OnClick(R.id.fragment_input_register)
    void onRegisterClick() {
        addToDatabase();
    }

    private void addToDatabase() {
        final String productName = mEditProductName.getText().toString();
        final String productCategory = mEditProductCategory.getText().toString();
        final String productPrice = mEditProductPrice.getText().toString();
        final String shopName = mEditShopName.getText().toString();
        final String shopAddress = mEditProductShopAddress.getText().toString();
        final String date = mTextPurchaseDate.getText().toString();

        if (!isValidInput(productName, productCategory, productPrice, shopName, shopAddress, date)) {
            return;
        }

        mDatabaseManager.addEntry(productName,
                productCategory,
                Integer.parseInt(productPrice),
                shopName,
                shopAddress,
                date,
                "NA");

    }

    private boolean isValidInput(String name, String category, String price, String shop, String address, String date) {
        if (TextUtils.isEmpty(name)) {
            showError("商品名");
            return false;
        }

        if (TextUtils.isEmpty(category)) {
            showError("商品種類");
            return false;
        }

        if (TextUtils.isEmpty(price)) {
            showError("値段");
            return false;
        }

        if (!price.matches(Common.REGEX_NUMBER)) {
            showError("数字");
            return false;
        }

        if (TextUtils.isEmpty(shop)) {
            showError("店舗名");
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            showError("店舗住所");
            return false;
        }

        if (TextUtils.isEmpty(date)) {
            showError("日付");
            return false;
        }

        return true;
    }

    private void showError(String type) {
        final View view = getView();
        if (view == null || TextUtils.isEmpty(type)) {
            return;
        }

        final String message = String.format(getString(R.string.fragment_input_error) , type);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }


    @OnClick(R.id.fragment_input_date)
    void onDateTextClick() {
        showDatePicker(getFragmentManager());
    }

    private void showDatePicker(FragmentManager manager) {
        if (manager == null) {
            return;
        }

        AppCompatDialogFragment fragment = new ProductDatePicker();
        ((ProductDatePicker) fragment).createDateEvent(new DatePickerEvent() {
            @Override
            public void onDatePicked(String date) {
                mTextPurchaseDate.setText(date);
            }
        });

        fragment.show(manager, "datePicker");
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

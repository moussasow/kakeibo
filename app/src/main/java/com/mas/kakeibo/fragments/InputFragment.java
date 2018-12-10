package com.mas.kakeibo.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

import com.mas.kakeibo.R;
import com.mas.kakeibo.constants.Common;
import com.mas.kakeibo.database.DatabaseManager;
import com.mas.kakeibo.dialogs.ProductDatePicker;
import com.mas.kakeibo.events.DatePickerEvent;
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
    private String mImageLocation;

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

        showInputDialog(productName,
                productCategory,
                productPrice,
                shopName,
                shopAddress,
                date,
                mImageLocation);

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

        final String message = String.format(getString(R.string.fragment_input_error), type);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }


    private void showInputDialog(final String name,
                                 final String category,
                                 final String price,
                                 final String shop,
                                 final String address,
                                 final String date,
                                 final String path) {

        new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.fragment_input_dialog_title))
                .setMessage(getString(R.string.fragment_input_dialog_message))
                .setNegativeButton(getString(R.string.fragment_input_dialog_btn_cancel), null)
                .setPositiveButton(getString(R.string.fragment_input_dialog_btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // データベースに登録
                        mDatabaseManager.addEntry(name,
                                category,
                                Integer.parseInt(price),
                                shop,
                                address,
                                date,
                                path);

                        clearInputFields();
                    }
                })
                .create()
                .show();
    }

    private void clearInputFields() {
        mEditProductName.setText("");
        mEditProductCategory.setText("");
        mEditProductPrice.setText("");
        mEditShopName.setText("");
        mEditProductShopAddress.setText("");
        mTextPurchaseDate.setText("");
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
        final BaseFragment cameraFragment = CameraFragment.newInstance();
        cameraFragment.setTargetFragment(this, Common.CAMERA_REQUEST_CODE);

        obtainBaseActivity().replaceFragment(cameraFragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Common.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageLocation = data.getStringExtra(Common.BUNDLE_IMAGE_PATH);
        }
    }
}

package com.mas.kakeibo.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mas.kakeibo.R;
import com.mas.kakeibo.adapters.ShopListAdapter;
import com.mas.kakeibo.adapters.models.ShoppingModel;
import com.mas.kakeibo.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sow.m on 2018/11/06.
 */
public class ShoppingListFragment extends BaseFragment {

    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @BindView(R.id.fragment_shopping_list_recycler_view)
    RecyclerView mShoppingRecyclerView;

    private ShopListAdapter mShopListAdapter;
    private DatabaseManager mDatabaseManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabaseManager = DatabaseManager.getInstance(getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mShoppingRecyclerView.setLayoutManager(layoutManager);

        showList();
    }

    private void showList() {
        mShopListAdapter = new ShopListAdapter(retrieveData());
        mShoppingRecyclerView.setAdapter(mShopListAdapter);
    }

    private List<ShoppingModel> retrieveData() {

        List<ShoppingModel> list = new ArrayList<>();

        Cursor cursor = mDatabaseManager.retrieveAllEntries();
        if (cursor.moveToFirst()) {
            do {
                ShoppingModel model = new ShoppingModel();

                model.setListId(cursor.getInt(0));
                model.setProductName(cursor.getString(1));
                model.setProductCategory(cursor.getString(2));
                model.setProductPrice(cursor.getInt(3));
                model.setShopName(cursor.getString(4));
                model.setShopAddress(cursor.getString(5));
                model.setDate(cursor.getString(6));
                model.setImageUrl(cursor.getString(7));


                list.add(model);
            } while (cursor.moveToNext());
        }

        return list;
    }
}

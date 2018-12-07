package com.mas.kakeibo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mas.kakeibo.R;
import com.mas.kakeibo.adapters.models.ShoppingModel;

import java.util.List;

/**
 * Created by sow.m on 2018/12/07.
 */
public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private List<ShoppingModel> mShoppingList;

    public ShopListAdapter(List<ShoppingModel> shoppingList) {
        mShoppingList = shoppingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        final View view = inflater.inflate(R.layout.adapter_shop_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ShoppingModel model = mShoppingList.get(position);

        holder.mTextProductName.setText(model.getProductName());
    }

    @Override
    public int getItemCount() {
        return mShoppingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextProductName;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextProductName = itemView.findViewById(R.id.adapter_shop_list_product_name);
        }
    }
}

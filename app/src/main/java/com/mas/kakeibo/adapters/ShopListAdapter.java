package com.mas.kakeibo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mas.kakeibo.R;
import com.mas.kakeibo.adapters.models.ShoppingModel;
import com.mas.kakeibo.utils.BitmapUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        holder.mTextProductCategory.setText(model.getProductCategory());
        holder.mTextProductName.setText(model.getProductName());
        holder.mTextPurchaseDate.setText(model.getDate());
        holder.mTextShopName.setText(model.getShopName());
        holder.mTextShopAddress.setText(model.getShopAddress());

        String price = String.format("¥%d", model.getProductPrice());
        holder.mTextPurchasePrice.setText(price);

        String imageUrl = model.getImageUrl();
        if (TextUtils.isEmpty(imageUrl)) {
            holder.mImageView.setImageResource(android.R.drawable.ic_menu_gallery);
        } else {
            BitmapUtil.loadBitmap(imageUrl, holder.mImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mShoppingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_shop_list_product_category)
        TextView mTextProductCategory;
        @BindView(R.id.adapter_shop_list_product_name)
        TextView mTextProductName;
        @BindView(R.id.adapter_shop_list_text_price)
        TextView mTextPurchasePrice;
        @BindView(R.id.adapter_shop_list_text_date)
        TextView mTextPurchaseDate;
        @BindView(R.id.adapter_shop_list_text_shop_name)
        TextView mTextShopName;
        @BindView(R.id.adapter_shop_list_text_shop_address)
        TextView mTextShopAddress;
        @BindView(R.id.adapter_shop_list_image)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

package com.medico.app.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ProductListResponse.Data> list;
    public List<ProductListResponse.Data> cartList = null;
    String type;
    private PaginationAdapterCallback mCallback;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;

    public ProductDetailAdapter(Context context, PaginationAdapterCallback mCallback, String type) {
        this.context = context;
        this.mCallback = mCallback;
        this.list = new ArrayList<>();
        this.type = type;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View v1 = null;
                if (type.equals("Detail")) {
                    v1 = inflater.inflate(R.layout.together, parent, false);
                }
                viewHolder = new myViewHolder(v1);
                break;

            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hld, int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                try {
                    final myViewHolder holder = (myViewHolder) hld;
                    if (type.equals("Detail")) {
                        final ProductListResponse.Data listNew = list.get(position);
                        holder.tv_product_name.setText(listNew.getDrugName());
                        holder.tv_product_type.setText(listNew.getDrugType());
                        holder.tv_discount_percent.setText(listNew.getDiscount() + "% OFF");
                        holder.tv_product_price.setPaintFlags(holder.tv_product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        float actualPrice = Float.parseFloat(listNew.getUnitPrice());
                        float totalDiscount = (actualPrice * Float.parseFloat(listNew.getDiscount())) / 100;
                        holder.tv_product_price.setText(String.valueOf("MRP " + listNew.getUnitPrice()));
                        float priceAfterDiscount = actualPrice - totalDiscount;
                        holder.tv_discount_price.setText("₹ " + String.valueOf(String.format("%.2f", priceAfterDiscount)));
                        //Glide.with(context).load(listNew.medicine_image).into(holder.iv_medicine);

                    }
                    break;

                } catch (Exception e) {
                }
            case LOADING:
                if (retryPageLoad) {
                } else {
                }
                break;

        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_product_name, tv_product_type, tv_discount_price, tv_product_price, tv_discount_percent;
        public ImageView iv_product;

        public myViewHolder(View itemView) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_type = itemView.findViewById(R.id.tv_product_type);
            tv_discount_price = itemView.findViewById(R.id.tv_discount_price);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tv_discount_percent = itemView.findViewById(R.id.tv_discount_percent);

        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageButton mRetryBtn;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);
            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }

    }

           /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(ProductListResponse.Data results) {
        list.add(results);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<ProductListResponse.Data> moveResults) {
        for (ProductListResponse.Data result : moveResults) {
            add(result);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ProductListResponse.Data());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = list.size() - 1;
        ProductListResponse.Data result = getItem(position);

        if (result != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(list.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public ProductListResponse.Data getItem(int position) {
        return list.get(position);
    }
}
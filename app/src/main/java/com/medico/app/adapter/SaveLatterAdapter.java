package com.medico.app.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.medico.app.R;
import com.medico.app.interfaceClass.CartItemCount;
import com.medico.app.response.Cart.CartImage;
import com.medico.app.response.Cart.SaveLaterList;
import com.medico.app.response.CategoryList;
import com.medico.app.response.OrderResponse.DrugData;
import com.medico.app.utils.MedicoLoading;
import com.medico.app.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class SaveLatterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SaveLaterList> list;
    private String type;
    private PaginationAdapterCallback mCallback;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    CartItemCount cartItemCount;

    public SaveLatterAdapter(Context context, CartItemCount cartItemCount, PaginationAdapterCallback mCallback, String type) {
        this.context = context;
        this.cartItemCount = cartItemCount;
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
                if (type.equals("saveLater")) {
                    v1 = inflater.inflate(R.layout.row_save_for_later, parent, false);
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
                    if (type.equals("saveLater")) {
                        final SaveLaterList listNew = list.get(position);
                        holder.tv_name.setText(listNew.getProductId().getDrugName());
                        holder.tv_price_comment.setText(listNew.getProductId().getDiscount() + "% OFF");
                        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        float actualPrice = Float.parseFloat(listNew.getProductId().getUnitPrice());
                        float totalDiscount = (actualPrice * Float.parseFloat(listNew.getProductId().getDiscount())) / 100;
                        holder.tv_price.setText(String.valueOf("MRP " + listNew.getProductId().getUnitPrice()));
                        float priceAfterDiscount = actualPrice - totalDiscount;
                        holder.tv_discounted_price.setText("₹ " + String.valueOf(String.format("%.2f", priceAfterDiscount)));
                        holder.rbItem.setRating((float) 3.5);

                        List<CartImage> saveLaterImage = listNew.getProductId().getImages();
                        if (saveLaterImage != null && !saveLaterImage.isEmpty()) {
                            Glide.with(context).load(saveLaterImage.get(0).getImageUrl())
                                    .apply(new RequestOptions().placeholder(R.drawable.ic_order_mediciens).error
                                            (R.drawable.ic_order_mediciens)).into(holder.iv_product);
                        }
                        holder.iv_delete.setOnClickListener(view -> {
                            list.remove(listNew);
                            notifyDataSetChanged();
                        });
                        holder.tv_moveCart.setOnClickListener(view -> {
                            list.remove(listNew);
                            notifyDataSetChanged();
                            cartItemCount.getCartItem(true, "moveToCart", listNew.getProductId().getDrugId(),
                                    String.valueOf("1"));
                        });

                    }

                    break;

                } catch (Exception e) {
                    e.printStackTrace();
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
        public TextView tv_name, tv_product_type, tv_discounted_price, tv_price, tv_price_comment, tv_moveCart;
        public ImageView iv_product, iv_delete;
        public RatingBar rbItem;

        public myViewHolder(View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.iv_product);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_price_comment = itemView.findViewById(R.id.tv_price_comment);
            rbItem = itemView.findViewById(R.id.rbItem);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            tv_moveCart = itemView.findViewById(R.id.tv_moveCart);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MedicoLoading progress;

        public LoadingVH(View itemView) {
            super(itemView);
            progress = itemView.findViewById(R.id.progress);
            progress.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.progress:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }

    }

    public void add(SaveLaterList results) {
        list.add(results);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<SaveLaterList> moveResults) {
        for (SaveLaterList result : moveResults) {
            add(result);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new SaveLaterList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = list.size() - 1;
        SaveLaterList result = getItem(position);
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

    public SaveLaterList getItem(int position) {
        return list.get(position);
    }
}
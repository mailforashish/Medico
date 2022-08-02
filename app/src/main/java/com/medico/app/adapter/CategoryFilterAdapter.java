package com.medico.app.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medico.app.R;
import com.medico.app.response.CategoryList;
import com.medico.app.utils.PaginationAdapterCallback;

import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<CategoryList> list;
    public List<CategoryList> cartList = null;
    String type;
    private PaginationAdapterCallback mCallback;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;
    public boolean isImageChanged = false;

    public CategoryFilterAdapter(Context context, PaginationAdapterCallback mCallback, List<CategoryList> list, String type) {
        this.context = context;
        this.mCallback = mCallback;
        this.list = list;
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
                if (type.equals("Filter")) {
                    v1 = inflater.inflate(R.layout.layout_category_sort, parent, false);
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
                    if (type.equals("Filter")) {
                        final CategoryList listNew = list.get(position);
                        holder.tv_name.setText(listNew.getCategory());
                        //holder.tv_product_type.setText(listNew.getDrugType());
                        holder.tv_price_comment.setText(listNew.getDiscount() + "% OFF");
                        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        float actualPrice = Float.parseFloat(listNew.getPrice());
                        float totalDiscount = (actualPrice * Float.parseFloat(listNew.getDiscount())) / 100;
                        holder.tv_price.setText(String.valueOf("MRP " + listNew.getPrice()));
                        float priceAfterDiscount = actualPrice - totalDiscount;
                        holder.tv_discounted_price.setText("₹ " + String.valueOf(String.format("%.2f", priceAfterDiscount)));
                        holder.iv_product.setImageResource(list.get(position).getCategory_image());
                        holder.rbItem.setRating((float) 3.5);
                    }

                    holder.iv_rx_heart.setOnClickListener(view -> {
                        if (isImageChanged) {
                            holder.iv_rx_heart.setImageResource(R.drawable.ic_heart);
                            isImageChanged = false;
                        } else {
                            holder.iv_rx_heart.setImageResource(R.drawable.ic_save_heart);
                            isImageChanged = true;
                        }

                    });
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
        public TextView tv_name, tv_product_type, tv_discounted_price, tv_price, tv_price_comment;
        public ImageView iv_product, iv_rx_heart;
        public RatingBar rbItem;

        public myViewHolder(View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.iv_product);
            iv_rx_heart = itemView.findViewById(R.id.iv_rx_heart);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_product_type = itemView.findViewById(R.id.tv_product_type);
            tv_discounted_price = itemView.findViewById(R.id.tv_discounted_price);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_price_comment = itemView.findViewById(R.id.tv_price_comment);
            rbItem = itemView.findViewById(R.id.rbItem);

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

    public void add(CategoryList results) {
        list.add(results);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<CategoryList> moveResults) {
        for (CategoryList result : moveResults) {
            add(result);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new CategoryList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = list.size() - 1;
        CategoryList result = getItem(position);

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

    public CategoryList getItem(int position) {
        return list.get(position);
    }
}
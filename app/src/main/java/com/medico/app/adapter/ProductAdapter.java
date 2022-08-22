package com.medico.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.medico.app.R;
import com.medico.app.activity.ProductDetailsActivity;
import com.medico.app.interfaceClass.CartItemCount;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.utils.MaxLimit;
import com.medico.app.utils.MedicoLoading;
import com.medico.app.utils.PaginationAdapterCallback;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    CartItemCount cartItemCount;
    MaxLimit maxLimit;

    public ProductAdapter(Context context, PaginationAdapterCallback mCallback, String type, CartItemCount cartItemCount) {
        this.context = context;
        this.mCallback = mCallback;
        this.list = new ArrayList<>();
        this.type = type;
        this.cartItemCount = cartItemCount;
        maxLimit = new MaxLimit(0);
    }

    public ProductAdapter(Context context, PaginationAdapterCallback mCallback, String type) {
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
                if (type.equals("HealthCare")) {
                    v1 = inflater.inflate(R.layout.product_list_layout, parent, false);
                } else if (type.equals("search")) {
                    v1 = inflater.inflate(R.layout.adapter_search, parent, false);
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

                final myViewHolder holder = (myViewHolder) hld;
                if (type.equals("HealthCare")) {
                    final ProductListResponse.Data listNew = list.get(position);
                    holder.tv_drug_name.setText(list.get(position).getDrugName());
                    holder.tv_drug_type.setText(list.get(position).getDrugType());
                    holder.tv_manufacturer.setText(list.get(position).getManufactur());
                    holder.tv_discPercent.setText(list.get(position).getDiscount() + "% OFF");
                    holder.tv_real_price.setPaintFlags(holder.tv_real_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    float actualPrice = Float.parseFloat(listNew.getUnitPrice());
                    float totalDiscount = (actualPrice * Float.parseFloat(list.get(position).getDiscount())) / 100;
                    holder.tv_real_price.setText(String.valueOf("MRP " + list.get(position).getUnitPrice()));
                    float priceAfterDiscount = actualPrice - totalDiscount;
                    holder.tv_off_price.setText("₹ " + String.valueOf(String.format("%.2f", priceAfterDiscount)));
                    Glide.with(context).load(list.get(position).getImages().get(0).getImageUrl())
                            .apply(new RequestOptions().placeholder(R.drawable.ic_order_mediciens).error
                                    (R.drawable.ic_order_mediciens)).into(holder.iv_medicine);

                    holder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.tv_add_cart.setVisibility(View.GONE);
                            holder.llAddPlusMinus.setVisibility(View.VISIBLE);
                            cartList = new ArrayList<ProductListResponse.Data>(Arrays.asList(listNew));
                            cartList.remove(listNew);
                            listNew.quantity = 1;
                            cartList.add(listNew);
                            ((Activity) context).invalidateOptionsMenu();
                            cartItemCount.getCartItem(true, "addCart", listNew.getDrugId(),
                                    String.valueOf(listNew.quantity));
                        }
                    });

                    if (cartList != null) {
                        for (int i = 0; i < cartList.size(); i++) {
                            if (cartList.get(i).getDrugId().equals(list.get(position).getDrugId())) {
                                holder.tv_add_cart.setVisibility(View.GONE);
                                holder.llAddPlusMinus.setVisibility(View.VISIBLE);
                            }else {
                                holder.tv_add_cart.setVisibility(View.VISIBLE);
                                holder.llAddPlusMinus.setVisibility(View.GONE);
                            }
                        }
                    }

                    holder.iv_plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Integer.parseInt(holder.tv_quantity.getText().toString()) >= 1) {
                                holder.iv_minus.setVisibility(View.VISIBLE);
                                int a = Integer.parseInt(holder.tv_quantity.getText().toString());
                                a++;
                                holder.tv_quantity.setText(Integer.toString(maxLimit.limit(a)));
                                if (cartList != null) {
                                    if (cartList.contains(listNew)) {
                                        cartList.remove(listNew);
                                        listNew.quantity = a;
                                        cartList.add(listNew);
                                        ((Activity) context).invalidateOptionsMenu();
                                        if (a <= 10) {
                                            cartItemCount.getCartItem(true, "UpdateQuantity", listNew.getDrugId(),
                                                    String.valueOf(listNew.quantity));
                                        } else {
                                            Toast.makeText(context, "limit exceeded", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            }

                        }
                    });
                    holder.iv_minus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Integer.parseInt(holder.tv_quantity.getText().toString()) <= 1) {
                                holder.llAddPlusMinus.setVisibility(View.GONE);
                                holder.tv_add_cart.setVisibility(View.VISIBLE);
                                cartList.remove(listNew);
                                ((Activity) context).invalidateOptionsMenu();
                                cartItemCount.getCartItem(true, "remove", listNew.getDrugId(),
                                        String.valueOf(listNew.quantity));
                            } else {
                                int a = Integer.parseInt(holder.tv_quantity.getText().toString());
                                a--;
                                holder.tv_quantity.setText(Integer.toString(a));
                                cartList.remove(listNew);
                                listNew.quantity = a;
                                cartList.add(listNew);
                                cartItemCount.getCartItem(true, "UpdateQuantity", listNew.getDrugId(),
                                        String.valueOf(listNew.quantity));
                                ((Activity) context).invalidateOptionsMenu();
                            }
                        }
                    });
                } else {
                    holder.tv_product_name.setText(list.get(position).getDrugName());
                    holder.tv_manufacturer.setText(list.get(position).getManufactur());
                }

                holder.main_container.setOnClickListener(view -> {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("drug_id", list.get(position).getDrugId());
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                });
                break;
           /* try {
                } catch (Exception e) {
                }*/
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
        public TextView tv_drug_name, tv_drug_type,
                tv_add_cart, tv_off_price, tv_real_price, tv_quantity, tv_discPercent;
        public ImageView iv_minus, iv_plus, iv_medicine;
        public LinearLayout llAddPlusMinus;
        public ConstraintLayout main_container;
        //search utem Define here
        public TextView tv_product_name, tv_manufacturer;

        public myViewHolder(View itemView) {
            super(itemView);

            tv_drug_name = itemView.findViewById(R.id.tv_drug_name);
            tv_drug_type = itemView.findViewById(R.id.tv_drug_type);
            tv_manufacturer = itemView.findViewById(R.id.tv_manufacturer);
            tv_off_price = itemView.findViewById(R.id.tv_off_price);
            tv_add_cart = itemView.findViewById(R.id.tv_add_cart);
            tv_real_price = itemView.findViewById(R.id.tv_real_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            iv_minus = itemView.findViewById(R.id.iv_minus);
            iv_plus = itemView.findViewById(R.id.iv_plus);
            llAddPlusMinus = itemView.findViewById(R.id.llAddPlusMinus);
            iv_medicine = itemView.findViewById(R.id.iv_medicine);
            tv_discPercent = itemView.findViewById(R.id.tv_discPercent);
            main_container = itemView.findViewById(R.id.main_container);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MedicoLoading progress;

        public LoadingVH(View itemView) {
            super(itemView);
            progress = itemView.findViewById(R.id.progress);
            progress.setOnClickListener(this);
            if (isLoadingAdded) {
                progress.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.progress:
                    progress.setVisibility(View.INVISIBLE);
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }

    }

    public void add(ProductListResponse.Data results) {
        list.add(results);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<ProductListResponse.Data> moveResults) {
        for (ProductListResponse.Data result : moveResults) {
            add(result);
        }
    }

    public List<ProductListResponse.Data> getList() {
        return list;
    }

    public void remove(ProductListResponse.Result r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeAll() {
        if (list != null && list.size() > 0) {
            list.clear();
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

    // method for filtering our recyclerview items.
    public void filterList(List<ProductListResponse.Data> filterllist) {
        list = filterllist;
        notifyDataSetChanged();
    }
}
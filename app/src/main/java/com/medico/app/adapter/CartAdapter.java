package com.medico.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.medico.app.R;
import com.medico.app.interfaceClass.CartItemCount;
import com.medico.app.response.Cart.CartImage;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.Cart.CartResult;
import com.medico.app.response.Cart.ProductId;
import com.medico.app.response.OrderResponse.AddressList;
import com.medico.app.response.OrderResponse.DrugData;
import com.medico.app.utils.MaxLimit;
import com.medico.app.utils.SessionManager;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    public List<CartList> cartList;
    SessionManager sessionManager;
    TextView tv_amount_input;
    Button btn_pay;
    CartItemCount cartItemCount;
    MaxLimit maxLimit;

    public CartAdapter(Context context, CartItemCount cartItemCount, List<CartList> cartList, TextView tv_amount_input, Button btn_pay) {
        this.context = context;
        this.cartItemCount = cartItemCount;
        this.cartList = cartList;
        this.tv_amount_input = tv_amount_input;
        this.btn_pay = btn_pay;
        sessionManager = new SessionManager(context);
        maxLimit = new MaxLimit(0);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CartList listNew = cartList.get(position);
        try {
            holder.tv_drug_name.setText(listNew.getProductId().getDrugName());
            holder.tv_manufacturer.setText(listNew.getProductId().getManufactur());
            holder.tv_discPercent.setText(listNew.getProductId().getDiscount() + "%OFF");
            holder.tv_real_price.setPaintFlags(holder.tv_real_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            float actualPrice = Float.parseFloat(listNew.getProductId().getUnitPrice());
            float totalDiscount = (actualPrice * Float.parseFloat(listNew.getProductId().getDiscount())) / 100;
            holder.tv_real_price.setText(String.valueOf("MRP " + listNew.getProductId().getUnitPrice()));
            float priceAfterDiscount = actualPrice - totalDiscount;
            holder.tv_off_price.setText("₹ " + String.valueOf(String.format("%.2f", priceAfterDiscount)));
            List<CartImage> drug_image = cartList.get(position).getProductId().getImages();
            if (drug_image != null && !drug_image.isEmpty()) {
                Glide.with(context).load(drug_image.get(0).getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_order_mediciens).error
                                (R.drawable.ic_order_mediciens)).into(holder.iv_medicine);
            }
            updateprice();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cartList != null) {
            for (CartList d : cartList) {
                if (listNew.getId() == d.getId()) {
                    listNew.setQuantity(d.getQuantity());
                }
            }
        }
        if (listNew.getQuantity() != 0) {
            holder.llAddPlusMinus.setVisibility(View.VISIBLE);
            holder.tv_quantity.setText(Integer.toString(listNew.getQuantity()));
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
                            listNew.setQuantity(a);
                            cartList.add(listNew);
                            sessionManager.saveListInLocal("cart", cartList);
                            updateprice();
                            ((Activity) context).invalidateOptionsMenu();
                            Log.e("CartFunction", "Cart Adapter " + listNew.getProductId().getDrugId() +
                                    String.valueOf(listNew.getQuantity()));
                            if (a <= 10) {
                                cartItemCount.getCartItem(true, "UpdateQuantity",
                                        listNew.getProductId().getDrugId(), String.valueOf(listNew.getQuantity()));
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
                    cartList.remove(listNew);
                    sessionManager.saveListInLocal("cart", cartList);
                    notifyDataSetChanged();
                    updateprice();
                    ((Activity) context).invalidateOptionsMenu();
                    cartItemCount.getCartItem(true, "remove",
                            listNew.getProductId().getDrugId(), String.valueOf(listNew.getQuantity()));
                } else {
                    int a = Integer.parseInt(holder.tv_quantity.getText().toString());
                    a--;
                    holder.tv_quantity.setText(Integer.toString(a));
                    cartList.remove(listNew);
                    listNew.setQuantity(a);
                    cartList.add(listNew);
                    sessionManager.saveListInLocal("cart", cartList);
                    updateprice();
                    //notifyDataSetChanged();
                    cartItemCount.getCartItem(true, "UpdateQuantity",
                            listNew.getProductId().getDrugId(), String.valueOf(listNew.getQuantity()));
                    ((Activity) context).invalidateOptionsMenu();
                }
            }
        });
        holder.iv_cart_item_delete.setOnClickListener(v -> {
            notifyDataSetChanged();
            ((Activity) context).invalidateOptionsMenu();
            cartItemCount.getCartItem(true, "remove",
                    cartList.get(position).getProductId().getDrugId(), String.valueOf(cartList.get(position).getQuantity()));
            cartList.remove(cartList.get(position));
            sessionManager.saveListInLocal("cart", cartList);
            updateprice();
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_drug_name, tv_manufacturer,
                tv_add_cart, tv_off_price, tv_real_price, tv_quantity, tv_discPercent;
        public ImageView iv_medicine, iv_minus, iv_plus, iv_cart_item_delete;
        public LinearLayout llAddPlusMinus;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_cart_item_delete = itemView.findViewById(R.id.iv_cart_item_delete);
            tv_drug_name = itemView.findViewById(R.id.tv_drug_name);
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

        }
    }

    public void updateprice() {
        Float sum = 0.f;
        for (int i = 0; i < cartList.size(); i++) {
            float actualPrice = Float.parseFloat(cartList.get(i).getProductId().getUnitPrice());
            float totalDiscount = (actualPrice * Float.parseFloat(cartList.get(i).getProductId().getDiscount())) / 100;
            float priceAfterDiscount = actualPrice - totalDiscount;
            sum = sum + (priceAfterDiscount * cartList.get(i).getQuantity());

        }
        tv_amount_input.setText(String.valueOf(String.format("%.2f", sum)));
        btn_pay.setText("Proceed to Pay ₹ " + String.valueOf(String.format("%.2f", sum)));
        Log.e("Total Amount : INR", " finalamountadapter " + sum);
    }

}

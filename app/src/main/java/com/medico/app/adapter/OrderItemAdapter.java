package com.medico.app.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.response.Cartlist.CartResult;
import com.medico.app.response.OrderRequest.OrderItem;
import com.medico.app.response.OrderResponse.OrderListResult;
import com.medico.app.response.ProductList.ProductListResponse;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    public List<OrderItem> orderList = null;

    public OrderItemAdapter(Context context, List<OrderItem> orderList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.orderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.order_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderItem listNew = orderList.get(position);
        holder.tv_medicine_name.setText(listNew.getProductName());
        holder.tv_medicine_cm_name.setText(listNew.getManufactureName());
        holder.tv_real_price.setPaintFlags(holder.tv_real_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_real_price.setText("₹ " + listNew.getProductPrice());
        holder.tv_off_price.setText(String.valueOf(listNew.getDiscountAfterPrice()));
        holder.tv_quantity.setText("Qty " + String.valueOf(listNew.getQuantity()));
        //Glide.with(context).load(listNew.medicine_image).into(holder.iv_medicine);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_medicine_name, tv_medicine_cm_name,
                tv_off_price, tv_real_price, tv_quantity;
        public ImageView iv_medicine;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_medicine_name = itemView.findViewById(R.id.tv_medicine_name);
            tv_medicine_cm_name = itemView.findViewById(R.id.tv_medicine_cm_name);
            tv_off_price = itemView.findViewById(R.id.tv_off_price);
            tv_real_price = itemView.findViewById(R.id.tv_real_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            iv_medicine = itemView.findViewById(R.id.iv_medicine_cart);


        }
    }


}

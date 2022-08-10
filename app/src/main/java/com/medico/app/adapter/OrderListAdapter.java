package com.medico.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.activity.OrderDetailActivity;
import com.medico.app.activity.ProductDetailsActivity;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.response.Cartlist.CartResult;
import com.medico.app.response.OrderRequest.OrderItem;
import com.medico.app.response.OrderResponse.OrderListResult;

import java.io.Serializable;
import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {
    List<OrderListResult> orderList;
    Context context;

    public OrderListAdapter(Context context, List<OrderListResult> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (orderList.get(position).getOrderStatus() == 1) {
            holder.tv_order_status.setText("Processing");
        } else if (orderList.get(position).getOrderStatus() == 2) {
            holder.tv_order_status.setText("Packing");
        } else if (orderList.get(position).getOrderStatus() == 3) {
            holder.tv_order_status.setText("Shipped");
        } else if (orderList.get(position).getOrderStatus() == 4) {
            holder.tv_order_status.setText("Delivered");
        } else if (orderList.get(position).getOrderStatus() == 5) {
            holder.tv_order_status.setText("Failed");
        }
       /* holder.tv_patients_name.setText(orderList.get(position).getOrderDetailsJson().getShippingAddress().getName());
        holder.tv_place_on.setText("Place On " + String.valueOf(orderList.get(position).getDeliveredDate()));
        int size = Math.min(2, orderList.get(position).getOrderDetailsJson().getOrderItem().size());
        Log.e("orderdapter", "onBindViewHolder: " + size);
        for (int i = 0; i < size - 1; i++) {
            if (size == 1) {
                holder.tv_all_order_list.setText(orderList.get(position).getOrderDetailsJson().getOrderItem().get(i).getProductName());
                holder.tv_more.setVisibility(View.GONE);
            } else {
                holder.tv_all_order_list.setText(orderList.get(position).getOrderDetailsJson().getOrderItem().get(i).getProductName() + "\n"
                        + orderList.get(position).getOrderDetailsJson().getOrderItem().get(i + 1).getProductName());
                holder.tv_more.setVisibility(View.VISIBLE);
                // break;
            }

        }*/

        holder.linear_place_on.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            OrderListResult listNew = orderList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("LIST", listNew);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        try {

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_order_status, tv_patients_name, tv_all_order_list, tv_place_on, tv_view_detail, tv_more;
        public LinearLayout linear_place_on;

        public MyViewHolder(View view) {
            super(view);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);
            tv_patients_name = itemView.findViewById(R.id.tv_patients_name);
            tv_all_order_list = itemView.findViewById(R.id.tv_all_order_list);
            tv_place_on = itemView.findViewById(R.id.tv_place_on);
            tv_view_detail = itemView.findViewById(R.id.tv_view_detail);
            tv_more = itemView.findViewById(R.id.tv_more);
            linear_place_on = itemView.findViewById(R.id.linear_place_on);

        }
    }
}

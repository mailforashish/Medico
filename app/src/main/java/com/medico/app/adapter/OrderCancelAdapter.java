package com.medico.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.interfaceClass.OrderCancel;
import com.medico.app.response.CancellationList;

import java.util.List;

public class OrderCancelAdapter extends RecyclerView.Adapter<OrderCancelAdapter.MyViewHolder> {
    List<CancellationList> list;
    Context context;
    private int SelectedPosition = -1;
    OrderCancel orderCancel;

    public OrderCancelAdapter(Context context, List<CancellationList> list, OrderCancel orderCancel) {
        this.context = context;
        this.list = list;
        this.orderCancel = orderCancel;
    }

    @NonNull
    @Override
    public OrderCancelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cancel_layout, parent, false);
        return new OrderCancelAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCancelAdapter.MyViewHolder holder, int position) {
        try {
            holder.tv_cancel_reason.setText(list.get(position).getCancel_reason());
            holder.rb_cancel_reason.setOnClickListener(view -> {
                SelectedPosition = holder.getAdapterPosition();
                orderCancel.getOrderCancelReason(true,list.get(position).getCancel_reason());
                notifyDataSetChanged();
            });
            if (SelectedPosition == position) {
                holder.rb_cancel_reason.setChecked(true);
            } else {
                holder.rb_cancel_reason.setChecked(false);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox rb_cancel_reason;
        public TextView tv_cancel_reason;

        public LinearLayout linear_Upi_img;

        public MyViewHolder(View view) {
            super(view);
            tv_cancel_reason = itemView.findViewById(R.id.tv_cancel_reason);
            rb_cancel_reason = itemView.findViewById(R.id.rb_cancel_reason);

        }
    }


}

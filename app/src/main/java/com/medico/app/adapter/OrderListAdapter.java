package com.medico.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.activity.OrderDetailActivity;
import com.medico.app.response.OrderResponse.AddressList;
import com.medico.app.response.OrderResponse.DrugData;
import com.medico.app.response.OrderResponse.DrugList;
import com.medico.app.response.OrderResponse.OrderDataList;
import com.medico.app.utils.MedicoDateFormater;

import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {
    List<OrderDataList> orderList;
    Context context;

    public OrderListAdapter(Context context, List<OrderDataList> orderList) {
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
        try {
            if (orderList.get(position).getStatus().equals("1")) {
                holder.tv_order_status.setText("Processing");
            } else if (orderList.get(position).getStatus().equals("2")) {
                holder.tv_order_status.setText("Packing");
            } else if (orderList.get(position).getStatus().equals("3")) {
                holder.tv_order_status.setText("Shipped");
            } else if (orderList.get(position).getStatus().equals("4")) {
                holder.tv_order_status.setText("Delivered");
            } else if (orderList.get(position).getStatus().equals("5")) {
                holder.tv_order_status.setText("Failed");
            }

            List<AddressList> address = orderList.get(position).getAddress();
            if (address != null && !address.isEmpty())
                holder.tv_patients_name.setText(address.get(0).getName());
            holder.tv_place_on.setText("Place On " +
                    String.valueOf(MedicoDateFormater.formatDateFromString(
                            "dd-MM-yyyy hh:mm a", "dd-MM-yyyy", orderList.get(position).getCreatedAt())));
            List<DrugData> drugs = orderList.get(position).getDrugs();
            if (drugs != null && !drugs.isEmpty()) {
                List<DrugList> drug = drugs.get(0).getDrug();
                if (drug != null && !drug.isEmpty()) {
                    if (drugs.size() == 1) {
                        holder.tv_all_order_list.setText(drug.get(0).getDrugName());
                        holder.tv_more.setVisibility(View.GONE);
                    } else if (drugs.size() >= 2) {
                        holder.tv_all_order_list.setText(drug.get(0).getDrugName() + "\n"
                                + drug.get(1).getDrugName());
                        holder.tv_more.setVisibility(View.VISIBLE);
                    }
                }
                holder.linear_place_on.setOnClickListener(view -> {
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    OrderDataList listNew = orderList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("LIST", listNew);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
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

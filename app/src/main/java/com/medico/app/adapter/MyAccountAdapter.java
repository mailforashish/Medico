package com.medico.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.activity.ManageAddressActivity;
import com.medico.app.activity.OrderListActivity;
import com.medico.app.activity.PrescriptionActivity;
import com.medico.app.activity.SaveActivity;
import com.medico.app.activity.WalletActivity;
import com.medico.app.response.MyAccountList;

import java.util.List;

public class MyAccountAdapter extends RecyclerView.Adapter<MyAccountAdapter.MyViewHolder> {
    List<MyAccountList> list;
    Context context;

    public MyAccountAdapter(Context context, List<MyAccountList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_account_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.iv_option.setImageResource(list.get(position).getOptions_image());
            holder.tv_option.setText(list.get(position).getOptions());
            holder.rl_my_account.setOnClickListener(v -> {
                if (position == 0) {
                    context.startActivity(new Intent(context, OrderListActivity.class));
                } else if (position == 1) {
                    context.startActivity(new Intent(context, PrescriptionActivity.class)
                            .putExtra("Event", "Account"));
                } else if (position == 2) {
                    context.startActivity(new Intent(context, SaveActivity.class));
                } else if (position == 3) {
                    context.startActivity(new Intent(context, WalletActivity.class));
                } else if (position == 5) {
                    context.startActivity(new Intent(context, ManageAddressActivity.class));
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.size();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_option;
        public TextView tv_option;
        public RelativeLayout rl_my_account;

        public MyViewHolder(View view) {
            super(view);
            iv_option = itemView.findViewById(R.id.iv_option);
            tv_option = itemView.findViewById(R.id.tv_option);
            rl_my_account = itemView.findViewById(R.id.rl_my_account);
        }
    }
}

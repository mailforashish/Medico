package com.medico.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.response.DescriptionList;

import java.util.List;


public class ProductDescritionAdapter extends RecyclerView.Adapter<ProductDescritionAdapter.MyViewHolder> {
    List<DescriptionList> list;
    Context context;

    public ProductDescritionAdapter(Context context, List<DescriptionList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.description_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.iv_tab_select.setImageResource(list.get(position).getImg_tab());
            holder.tv_tab_title.setText(list.get(position).getMd_title());
            holder.tv_tab_sub_title.setText(list.get(position).getMd_sub_title());
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
        public ImageView iv_tab_select;
        public TextView tv_tab_title, tv_tab_sub_title;

        public MyViewHolder(View view) {
            super(view);
            iv_tab_select = itemView.findViewById(R.id.iv_tab_select);
            tv_tab_title = itemView.findViewById(R.id.tv_tab_title);
            tv_tab_sub_title = itemView.findViewById(R.id.tv_tab_sub_title);


        }
    }
}

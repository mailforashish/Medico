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
import com.medico.app.response.OffersList;
import com.medico.app.response.QuantityList;

import java.util.List;

public class SelectQuantityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<QuantityList> list;
    String type;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public SelectQuantityAdapter(Context context, List<QuantityList> list,String type) {
        this.context = context;
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
                if (type.equals("Select")) {
                    v1 = inflater.inflate(R.layout.select_quantity_row, parent, false);
                }
                viewHolder = new myViewHolder(v1);
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
                    if (type.equals("Select")) {
                        holder.tv_qty.setText(list.get(position).getQty());
                    }

                } catch (Exception e) {
                }

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
        ImageView iv_check;
        public TextView tv_qty;

        public myViewHolder(View itemView) {
            super(itemView);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            iv_check = itemView.findViewById(R.id.iv_check);
        }
    }

}
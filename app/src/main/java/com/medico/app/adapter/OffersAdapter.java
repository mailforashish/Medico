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

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<OffersList> list;
    String type;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public OffersAdapter(Context context, List<OffersList> list, String type) {
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
                if (type.equals("LabTest")) {
                    v1 = inflater.inflate(R.layout.layout_offer_lab_test, parent, false);
                } else if (type.equals("PRODUCT")) {
                    v1 = inflater.inflate(R.layout.layout_pdp_offers, parent, false);
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
                    if (type.equals("LabTest")) {
                        holder.tv_offer_info.setText(list.get(position).getOffers());
                        holder.tv_code_text.setText("Code: " + list.get(position).getOffers_code());
                    } else if (type.equals("PRODUCT")){

                        // Glide.with(context).load(R.drawable.female_placeholder).apply(new RequestOptions()).into(holder.user_image);
                    }
                    holder.tv_offer_info.setText(list.get(position).getOffers());
                    holder.tv_code_text.setText(list.get(position).getOffers_code());


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
        ImageView iv_offer_icon;
        public TextView tv_offer_info, tv_code_text;

        public myViewHolder(View itemView) {
            super(itemView);
            tv_offer_info = itemView.findViewById(R.id.tv_offer_info);
            tv_code_text = itemView.findViewById(R.id.tv_code_text);
        }
    }

}
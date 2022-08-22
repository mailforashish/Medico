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
import com.medico.app.response.OrderResponse.DrugList;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    public List<DrugList> orderList = null;

    public OrderItemAdapter(Context context, List<DrugList> orderList) {
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
        final DrugList listNew = orderList.get(position);
        holder.tv_drug_name.setText(listNew.getDrugName());
        //holder.tv_manufacturer.setText(listNew.get);
        holder.tv_strike_price.setPaintFlags(holder.tv_strike_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_strike_price.setText("MRP " + listNew.getMrp());
        holder.tv_discount_percent.setText(String.valueOf(listNew.getDiscount()) + "%OFF");
        float actualPrice = Float.parseFloat(listNew.getMrp());
        float totalDiscount = (actualPrice * Float.parseFloat(listNew.getDiscount())) / 100;
        float priceAfterDiscount = actualPrice - totalDiscount;
        holder.tv_price.setText("₹ " + String.valueOf(String.format("%.2f", priceAfterDiscount)));
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
        public TextView tv_drug_name, tv_manufacturer,
                tv_price, tv_strike_price, tv_discount_percent, tv_quantity;
        public ImageView iv_medicine;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_drug_name = itemView.findViewById(R.id.tv_drug_name);
            tv_manufacturer = itemView.findViewById(R.id.tv_manufacturer);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_strike_price = itemView.findViewById(R.id.tv_strike_price);
            tv_discount_percent = itemView.findViewById(R.id.tv_discount_percent);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);


        }
    }


}

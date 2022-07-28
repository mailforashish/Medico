package com.medico.app.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.interfaceClass.PlaceOrder;
import com.medico.app.response.PaymentOfferList;

import java.util.List;


public class PaymentOffersAdapter extends RecyclerView.Adapter<PaymentOffersAdapter.MyViewHolder> {
    List<PaymentOfferList> list;
    Context context;
    private int SelectedPosition = -1;
    private PlaceOrder placeOrder;
    private String transactionType;

    public PaymentOffersAdapter(Context context, List<PaymentOfferList> list, PlaceOrder placeOrder) {
        this.context = context;
        this.list = list;
        this.placeOrder = placeOrder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_offer_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            holder.iv_payment_icon.setImageResource(list.get(position).getImage_payment_icon());
            holder.payment_modeTitle.setText(list.get(position).getPayment_modeTitle());
            holder.payment_modeDescription.setText(list.get(position).getPayment_modeDescription());

            HideView(holder.linear_Upi_img, holder.image, holder.tv_more, holder.rb_payment_select, position);
            if (position == 4) {
                holder.view_2.setVisibility(View.VISIBLE);
                holder.tv_other_option.setVisibility(View.VISIBLE);
            } else {
                holder.view_2.setVisibility(View.GONE);
                holder.tv_other_option.setVisibility(View.GONE);
            }

            holder.btn_place_order.setOnClickListener(v -> {
                placeOrder.getPlaceOrder(true, transactionType, list.get(position).getPayment_modeTitle());

            });

            holder.rb_payment_select.setOnClickListener(view -> {
                SelectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();

            });
            if (SelectedPosition == position) {
                holder.rb_payment_select.setChecked(true);
                holder.btn_place_order.setVisibility(View.VISIBLE);
                if (holder.payment_modeTitle.getText().toString().equals("Pay on Delivery")) {
                    transactionType = "1";
                } else {
                    transactionType = "2";
                }
            } else {
                holder.rb_payment_select.setChecked(false);
                holder.btn_place_order.setVisibility(View.GONE);
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
        public RadioButton rb_payment_select;
        public ImageView iv_payment_icon, image;
        public TextView payment_modeTitle, payment_modeDescription, tv_more, tv_other_option;
        public Button btn_place_order;
        public View view_2;
        public LinearLayout linear_Upi_img;

        public MyViewHolder(View view) {
            super(view);
            rb_payment_select = itemView.findViewById(R.id.rb_payment_select);
            iv_payment_icon = itemView.findViewById(R.id.iv_payment_icon);
            payment_modeTitle = itemView.findViewById(R.id.payment_modeTitle);
            payment_modeDescription = itemView.findViewById(R.id.payment_modeDescription);
            btn_place_order = itemView.findViewById(R.id.btn_place_order);
            linear_Upi_img = itemView.findViewById(R.id.linear_Upi_img);
            image = itemView.findViewById(R.id.image);
            tv_more = itemView.findViewById(R.id.tv_more);
            tv_other_option = itemView.findViewById(R.id.tv_other_option);
            view_2 = itemView.findViewById(R.id.view_2);

        }
    }

    public void HideView(LinearLayout upi, ImageView imageView,
                         TextView tv_more, RadioButton radioButton, int pos) {
        if (pos == 0) {
            upi.setVisibility(View.GONE);
            radioButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
        }
        if (pos == 1) {
            upi.setVisibility(View.GONE);
            radioButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
        }
        if (pos == 2) {
            upi.setVisibility(View.GONE);
            radioButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
        }
        if (pos == 3) {
            upi.setVisibility(View.GONE);
            radioButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
        } else if (pos == 4) {
            upi.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.iv_upi);
            radioButton.setButtonDrawable(R.drawable.ic_payment_arrow);

        } else if (pos == 5) {
            upi.setVisibility(View.VISIBLE);
            tv_more.setVisibility(View.INVISIBLE);
            radioButton.setButtonDrawable(R.drawable.ic_payment_arrow);

        } else if (pos == 6) {
            upi.setVisibility(View.VISIBLE);
            tv_more.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.iv_card);
            radioButton.setButtonDrawable(R.drawable.ic_payment_arrow);

        } else if (pos == 7) {
            upi.setVisibility(View.GONE);
            radioButton.setButtonDrawable(R.drawable.ic_payment_arrow);
        } else if (pos == 8) {
            upi.setVisibility(View.GONE);
            radioButton.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.black)));
        }
    }


}

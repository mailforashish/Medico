package com.medico.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.medico.app.R;
import com.medico.app.response.Prescription.PrescriptionResult;
import com.medico.app.utils.MedicoDateFormater;

import java.util.List;


public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.MyViewHolder> {
    List<PrescriptionResult> prescriptionLists;
    Context context;

    public PrescriptionAdapter(Context context, List<PrescriptionResult> prescriptionLists) {
        this.context = context;
        this.prescriptionLists = prescriptionLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prescription, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.tv_date.setText(String.valueOf(MedicoDateFormater.formatDateFromString(
                            "dd-MM-yyyy hh:mm a" ,"dd-MM-yyyy", prescriptionLists.get(position).getCreatedAt())));
            Glide.with(context).load(prescriptionLists.get(position).getPerceptionUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_order_mediciens).error
                            (R.drawable.ic_order_mediciens)).into(holder.iv_prescription);
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
        return prescriptionLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        public ImageView iv_prescription;

        public MyViewHolder(View view) {
            super(view);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_prescription = itemView.findViewById(R.id.iv_prescription);
        }
    }
}

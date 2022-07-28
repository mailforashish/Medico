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
import com.medico.app.response.TodayUpdateList;

import java.util.List;


public class AllUpdateAdapter extends RecyclerView.Adapter<AllUpdateAdapter.MyViewHolder> {
    List<TodayUpdateList> todayUpdateLists;
    Context context;

    public AllUpdateAdapter(Context context, List<TodayUpdateList> todayUpdateLists) {
        this.context = context;
        this.todayUpdateLists = todayUpdateLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_update_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            if (todayUpdateLists.get(position).getToday_off_images() == 0){
               holder.iv_today_update.setVisibility(View.GONE);
            }
            holder.tv_no_find.setText(todayUpdateLists.get(position).getNo_find());
            holder.tv_today_date.setText(todayUpdateLists.get(position).getToday_date());
            holder.tv_today_content.setText(todayUpdateLists.get(position).getToday_content());
            holder.iv_today_update.setImageResource(todayUpdateLists.get(position).getToday_off_images());
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return todayUpdateLists.size();

    }

    @Override
    public int getItemCount() {
        return todayUpdateLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iv_today_update;
        public TextView tv_no_find, tv_today_date, tv_today_content;

        public MyViewHolder(View view) {
            super(view);
            iv_today_update = itemView.findViewById(R.id.iv_today_update);
            tv_no_find = itemView.findViewById(R.id.tv_no_find);
            tv_today_date = itemView.findViewById(R.id.tv_today_date);
            tv_today_content = itemView.findViewById(R.id.tv_today_content);


        }
    }
}

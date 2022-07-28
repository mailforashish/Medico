package com.medico.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.Swipe.OnSwipeTouchListener;
import com.medico.app.Swipe.TouchListener;
import com.medico.app.activity.CategoryActivity;
import com.medico.app.response.CategoryList;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<CategoryList> list;
    Context context;
    Activity activity;
    String specialist;

    public CategoryAdapter(Context context, List<CategoryList> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shop_by_category, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rl_image_container.setOnTouchListener(new OnSwipeTouchListener(context, new TouchListener() {
            @Override
            public void onSingleTap() {
                Log.e("TAG", ">> Single tap");
                try {
                    Log.e("TAG", "position>> 0 ");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("type", list.get(position).getCategory());
                    context.startActivity(new Intent(context, CategoryActivity.class).putExtras(bundle));
                    activity.overridePendingTransition(R.anim.enter, R.anim.exit);

                } catch (Exception e) {
                }
            }

            @Override
            public void onDoubleTap() {
                Log.e("TAG", ">> Double tap");
            }

            @Override
            public void onLongPress() {
                Log.e("TAG", ">> Long press");
            }

            @Override
            public void onSwipeLeft() {
                Log.e("TAG", ">> Swipe left");
            }

            @Override
            public void onSwipeRight() {
                Log.e("TAG", ">> Swipe right");

            }
        }));


        try {
            holder.iv_top_category.setImageResource(list.get(position).getCategory_image());
            holder.tv_category_label.setText(list.get(position).getCategory());
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
        public ImageView iv_top_category;
        public TextView tv_category_label;
        public ConstraintLayout rl_image_container;

        public MyViewHolder(View view) {
            super(view);
            iv_top_category = itemView.findViewById(R.id.iv_top_category);
            tv_category_label = itemView.findViewById(R.id.tv_category_label);
            rl_image_container = itemView.findViewById(R.id.rl_image_container);


        }
    }
}

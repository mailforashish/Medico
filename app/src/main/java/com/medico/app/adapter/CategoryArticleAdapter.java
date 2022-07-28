package com.medico.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.response.CategoryList;

import java.util.List;


public class CategoryArticleAdapter extends RecyclerView.Adapter<CategoryArticleAdapter.MyViewHolder> {
    List<CategoryList> list;
    Context context;
    Activity activity;
    public String[] mColors = {"#cdfbfe","#fdeef3","#fffbe8","#fdeef1","#fffbe3","#cdfbee"};

    public CategoryArticleAdapter(Context context, List<CategoryList> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_article_listrow, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.category_img.setImageResource(list.get(position).getCategory_image());
            holder.category_txt.setText(list.get(position).getCategory());
            holder.rl_article.setBackgroundColor(Color.parseColor(mColors[position % 6])); // 3 can be replaced by mColors.length
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.size();

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView category_img;
        public TextView category_txt;
        public CardView card_article;
        public RelativeLayout rl_article;

        public MyViewHolder(View view) {
            super(view);
            category_img = itemView.findViewById(R.id.category_img);
            category_txt = itemView.findViewById(R.id.category_txt);
            card_article = itemView.findViewById(R.id.card_article);
            rl_article = itemView.findViewById(R.id.rl_article);


        }
    }
}

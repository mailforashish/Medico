package com.medico.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.medico.app.R;
import com.medico.app.response.HealthArticleList;

import java.util.List;


public class HealthArticleAdapter extends RecyclerView.Adapter<HealthArticleAdapter.MyViewHolder> {
    List<HealthArticleList> list;
    Context context;

    public HealthArticleAdapter(Context context, List<HealthArticleList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.health_layout, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            holder.tv_articles.setText(list.get(position).getArticle());
            holder.tv_articles_date.setText(list.get(position).getArticleDate());
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
        public TextView tv_articles,tv_articles_date;

        public MyViewHolder(View view) {
            super(view);
            tv_articles = itemView.findViewById(R.id.tv_articles);
            tv_articles_date = itemView.findViewById(R.id.tv_articles_date);

            /*img_articles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (canOpen) {
                        new PictureViewOnViewProfileAlbum(context, list, getAdapterPosition());
                    }
                }
            });*/
        }
    }
}

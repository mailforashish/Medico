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
import com.medico.app.response.CategoryList;

import java.util.List;

public class AllSubChildCategoryAdapter extends RecyclerView.Adapter<AllSubChildCategoryAdapter.ViewHolder> {
    private List<CategoryList> arrayList;
    Context mContext;

    public AllSubChildCategoryAdapter(List<CategoryList> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_category_child, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_subCategoryChild.setText(arrayList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subCategoryChild;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_subCategoryChild = itemView.findViewById(R.id.tv_subCategoryChild);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

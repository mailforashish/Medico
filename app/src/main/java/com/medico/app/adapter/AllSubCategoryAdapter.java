package com.medico.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.response.CategoryList;

import java.util.List;

public class AllSubCategoryAdapter extends RecyclerView.Adapter<AllSubCategoryAdapter.ViewHolder> {
    private List<CategoryList> subCategoryList;
    Context mContext;
    private List<CategoryList> childLists;
    private AllSubChildCategoryAdapter childCategoryAdapter;
    public int[] mColors = {R.drawable.cg_category4, R.drawable.cg_category5, R.drawable.cg_category, R.drawable.cg_category1, R.drawable.cg_category2, R.drawable.cg_category3};
    boolean isExpandable;

    public AllSubCategoryAdapter(List<CategoryList> subCategoryList, List<CategoryList> childLists, Context mContext) {
        this.subCategoryList = subCategoryList;
        this.childLists = childLists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_subCategoryTitle.setText(subCategoryList.get(position).getCategory());
        holder.rvSubCategoryChild.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if (isExpandable) {
            holder.iv_arrow_sub.setImageResource(R.drawable.ic_child_up);
        } else {
            holder.iv_arrow_sub.setImageResource(R.drawable.ic_child_down);
        }
        holder.iv_arrow_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandable) {
                    isExpandable = false;
                } else {
                    isExpandable = true;
                }
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.rvSubCategoryChild.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        childCategoryAdapter = new AllSubChildCategoryAdapter(childLists, mContext);
        holder.rvSubCategoryChild.setAdapter(childCategoryAdapter);
        childCategoryAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subCategoryTitle;
        ImageView iv_arrow_sub;
        RecyclerView rvSubCategoryChild;
        ConstraintLayout container_subCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_subCategoryTitle = itemView.findViewById(R.id.tv_subCategoryTitle);
            iv_arrow_sub = itemView.findViewById(R.id.iv_arrow_sub);
            rvSubCategoryChild = itemView.findViewById(R.id.rvSubCategoryChild);
            container_subCategory = itemView.findViewById(R.id.container_subCategory);
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

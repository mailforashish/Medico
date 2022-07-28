package com.medico.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medico.app.R;
import com.medico.app.response.CategoryList;

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.ViewHolder> {
    private static final String TAG = "PostAdapter";
    private AllSubCategoryAdapter allSubCategoryAdapter;
    private List<CategoryList> categoryLists;
    private Context mContext;
    private List<CategoryList> SubcategoryLists;
    private List<CategoryList> childLists;
    public int[] mColors = {R.drawable.cg_category, R.drawable.cg_category1, R.drawable.cg_category2, R.drawable.cg_category3, R.drawable.cg_category4, R.drawable.cg_category5};

    public AllCategoryAdapter(Context mContext, List<CategoryList> categoryLists, List<CategoryList> SubcategoryLists, List<CategoryList> childLists) {
        this.categoryLists = categoryLists;
        this.SubcategoryLists = SubcategoryLists;
        this.childLists = childLists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_categories_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.category_txt.setText(categoryLists.get(position).getCategory());
        holder.category_img.setImageResource(categoryLists.get(position).getCategory_image());
        holder.viewBackground.setBackgroundResource(mColors[position % 6]);

        CategoryList model = categoryLists.get(position);
        boolean isExpandable = model.isExpandable();
        holder.rvSubCategory.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if (isExpandable) {
            holder.img_drop.setImageResource(R.drawable.ic_up_button);
        } else {
            holder.img_drop.setImageResource(R.drawable.ic_down_button);
        }
        holder.img_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setExpandable(!model.isExpandable());
                //list = model.getNestedList();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.rvSubCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        allSubCategoryAdapter = new AllSubCategoryAdapter(SubcategoryLists, childLists, mContext);
        holder.rvSubCategory.setAdapter(allSubCategoryAdapter);
        allSubCategoryAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvSubCategory;
        TextView category_txt, category_desc;
        ImageView category_img, img_drop;
        LinearLayout viewBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_txt = itemView.findViewById(R.id.category_txt);
            category_desc = itemView.findViewById(R.id.category_desc);
            rvSubCategory = itemView.findViewById(R.id.rvSubCategory);
            category_img = itemView.findViewById(R.id.category_img);
            viewBackground = itemView.findViewById(R.id.viewBackground);
            img_drop = itemView.findViewById(R.id.img_drop);
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

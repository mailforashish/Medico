package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.medico.app.R;
import com.medico.app.adapter.AllCategoryAdapter;
import com.medico.app.adapter.CartAdapter;
import com.medico.app.databinding.ActivityAllCategoryBinding;
import com.medico.app.fragment.HomeFragment;
import com.medico.app.response.CategoryList;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryActivity extends AppCompatActivity {
    ActivityAllCategoryBinding binding;
    HideStatus hideStatus;
    public static List<CategoryList> categoryArticleLists = new ArrayList<>();
    AllCategoryAdapter allCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_category);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        categoryArticleLists = HomeFragment.categoryLists;

        binding.rvMainCategory.setLayoutManager(new LinearLayoutManager(AllCategoryActivity.this, LinearLayoutManager.VERTICAL, false));
        allCategoryAdapter = new AllCategoryAdapter(AllCategoryActivity.this, categoryArticleLists, categoryArticleLists,categoryArticleLists);
        binding.rvMainCategory.setAdapter(allCategoryAdapter);
        allCategoryAdapter.notifyDataSetChanged();

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPage() {
            onBackPressed();
        }


    }

}
package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.medico.app.R;
import com.medico.app.adapter.BannerAdapter;
import com.medico.app.adapter.CategoryArticleAdapter;
import com.medico.app.adapter.CategoryFilterAdapter;
import com.medico.app.databinding.ActivityCategoryBinding;
import com.medico.app.fragment.HomeFragment;
import com.medico.app.response.Banner.BannerResult;
import com.medico.app.response.CategoryList;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.PaginationAdapterCallback;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class CategoryActivity extends AppCompatActivity implements PaginationAdapterCallback {
    ActivityCategoryBinding binding;
    private String category;
    SessionManager sessionManager;
    private Timer timer;
    private ImageView[] dots;
    private List<BannerResult> bannerList = new ArrayList<>();
    private BannerAdapter bannerAdapter;
    private CategoryArticleAdapter categoryArticleAdapter;
    public static List<CategoryList> categoryArticleLists = new ArrayList<>();
    private CategoryFilterAdapter categoryFilterAdapter;
    HideStatus hideStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        hideStatus = new HideStatus(getWindow(), true);

        sessionManager = new SessionManager(this);
        binding.setClickListener(new EventHandler(this));
        category = String.valueOf(getIntent().getSerializableExtra("type"));
        categoryArticleLists = HomeFragment.categoryLists;

        binding.tvCategory.setText(category);
        bannerList = sessionManager.getBannerFromLocal("banner");
        if (bannerList != null) {
            setBannerData();
        }

        binding.rvArticles.setLayoutManager(new GridLayoutManager(this, 3));
        categoryArticleAdapter = new CategoryArticleAdapter(this, categoryArticleLists, this);
        binding.rvArticles.setAdapter(categoryArticleAdapter);
        binding.rvCategoryList.setLayoutManager(new GridLayoutManager(this, 2));
        categoryFilterAdapter = new CategoryFilterAdapter(this, CategoryActivity.this, categoryArticleLists, "Filter");
        binding.rvCategoryList.setAdapter(categoryFilterAdapter);

    }

    @Override
    public void retryPageLoad() {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void setBannerData() {
        bannerAdapter = new BannerAdapter(bannerList, this);
        binding.bannerPager.setAdapter(bannerAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                binding.bannerPager.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.bannerPager.setCurrentItem((binding.bannerPager.getCurrentItem() + 1) % bannerList.size());
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 5000, 5000);
        //override createDots methods here
        createDots(0);
        binding.bannerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void createDots(int current_position) {
        try {
            if (binding.dotsCategory != null)
                binding.dotsCategory.removeAllViews();
            dots = new ImageView[bannerList.size()];

            for (int i = 0; i < bannerList.size(); i++) {
                dots[i] = new ImageView(this);
                if (i == current_position) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_lab_dots));

                } else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_lab_dots));
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(6, 0, 8, 0);
                binding.dotsCategory.addView(dots[i], layoutParams);
            }

        } catch (Exception e) {

        }

    }



}
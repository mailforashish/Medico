package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.medico.app.R;
import com.medico.app.adapter.CategoryAdapter;
import com.medico.app.databinding.ActivitySearchProductBinding;
import com.medico.app.fragment.HomeFragment;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.adapter.ProductAdapter;
import com.medico.app.response.CategoryList;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.PaginationAdapterCallback;
import com.medico.app.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity implements ApiResponseInterface, PaginationAdapterCallback {
    ActivitySearchProductBinding binding;
    private List<ProductListResponse.Data> list = new ArrayList<>();
    private ApiManager apiManager;
    private GridLayoutManager gridLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    public static List<CategoryList> categoryLists = new ArrayList<>();
    private HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_product);
        hideStatus = new HideStatus(getWindow(), true);
        categoryLists = HomeFragment.categoryLists;
        binding.setClickListener(new EventHandler(this));

        binding.rvShopBy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this, categoryLists, this);
        binding.rvShopBy.setAdapter(categoryAdapter);

        gridLayoutManager = new GridLayoutManager(this, 1);
        binding.searchList.setLayoutManager(gridLayoutManager);
        apiManager = new ApiManager(this, this);

        binding.searchList.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call

            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    binding.linearShopBy.setVisibility(View.VISIBLE);
                    binding.searchList.setVisibility(View.GONE);
                } else {
                    binding.searchList.setVisibility(View.VISIBLE);
                    binding.linearShopBy.setVisibility(View.GONE);
                }
                apiManager.searchProduct(newText, String.valueOf(currentPage));
                return true;
            }
        });

        binding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPages();
            }
        });


    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPage() {
            onBackPressed();
        }
        public  void viewAll(){
            startActivity(new Intent(mContext,AllCategoryActivity.class));
        }


    }

    @Override
    public void isError(String errorCode) {
        Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.SEARCH_PRODUCT) {
            ProductListResponse rsp = (ProductListResponse) response;
            if (rsp != null) {
                list.clear();
                list = rsp.getResult().getData();
                //TOTAL_PAGES = rsp.getResult().getTo();
                if (list.size() > 0) {
                    productAdapter = new ProductAdapter(this, SearchProductActivity.this, "search");
                    binding.searchList.setItemAnimator(new DefaultItemAnimator());
                    binding.searchList.setAdapter(productAdapter);
                    // Set data in adapter
                    productAdapter.addAll(list);
                    if (currentPage < TOTAL_PAGES) {
                        productAdapter.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }
                }
            } else {
                clearSearch();
            }
        }
    }

    void clearSearch() {
        list.clear();
        if (productAdapter != null) {
            productAdapter.removeAll();
            productAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void retryPageLoad() {
        //apiManager.getUserListNextPage(String.valueOf(currentPage), binding.searchEd.getText().toString());
    }

    void resetPages() {
        // Reset Current page when refresh data
        this.currentPage = 1;
        this.isLastPage = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

}
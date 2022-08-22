package com.medico.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medico.app.R;
import com.medico.app.activity.MainActivity;
import com.medico.app.activity.CartActivity;
import com.medico.app.adapter.ProductAdapter;
import com.medico.app.databinding.FragmentHealthCareBinding;
import com.medico.app.interfaceClass.CartItemCount;
import com.medico.app.response.Addcart.AddCartResponse;
import com.medico.app.response.Addcart.RemoveCartResponse;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.Cart.CartResult;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.PaginationAdapterCallback;
import com.medico.app.utils.PaginationScrollListener;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class HealthCareFragment extends Fragment implements CartItemCount, ApiResponseInterface, PaginationAdapterCallback {
    FragmentHealthCareBinding binding;
    SessionManager sessionManager;
    ProductAdapter productAdapter;
    List<ProductListResponse.Data> list = new ArrayList<>();
    List<CartList> ItemList = new ArrayList<>();
    ApiManager apiManager;
    GridLayoutManager gridLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private int TotalCartItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_health_care, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setClickListener(new EventHandler(getContext()));
        sessionManager = new SessionManager(getContext());
        apiManager = new ApiManager(getContext(), this);

        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.rvMedicine.setLayoutManager(gridLayoutManager);
        apiManager.getProductList(String.valueOf(currentPage));
        binding.rvMedicine.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
                new Handler().postDelayed(() -> apiManager.getProductListNextPage(String.valueOf(currentPage)), 500);
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

        ItemList = sessionManager.getListFromLocal("cart");
        if (ItemList != null) {
            binding.tvCartItem.setText(String.valueOf(ItemList.size()));
        }

        binding.clCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

    }

    @Override
    public void getCartItem(boolean add, String action, String product_id, String quantity) {
        if (add) {
            if (action.equals("addCart")) {
                apiManager.addCart(product_id, quantity);
            } else if (action.equals("remove")) {
                apiManager.deleteCart(product_id);
            } else if (action.equals("UpdateQuantity")) {
                apiManager.changeQuantity(product_id, quantity);
            }
        }

    }

    @Override
    public void isError(String errorCode) {
    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.PRODUCT_LIST) {
            ProductListResponse rsp = (ProductListResponse) response;
            list = rsp.getResult().getData();
            try {
                TOTAL_PAGES = rsp.getResult().getTo();
                if (list.size() > 0) {
                    productAdapter = new ProductAdapter(getActivity(), HealthCareFragment.this, "HealthCare", this);
                    binding.rvMedicine.setAdapter(productAdapter);
                    // Set data in adapter
                    productAdapter.addAll(list);
                    productAdapter.notifyDataSetChanged();
                    Log.e("listSize", String.valueOf(list.size()));
                    if (currentPage < TOTAL_PAGES) {
                        productAdapter.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }
                }
            } catch (Exception e) {
            }

        }
        if (ServiceCode == Constant.PRODUCT_LIST_NEXT_PAGE) {
            ProductListResponse rsp = (ProductListResponse) response;
            productAdapter.removeLoadingFooter();
            isLoading = false;
            List<ProductListResponse.Data> results = rsp.getResult().getData();
            list.addAll(results);
            productAdapter.addAll(results);
            if (currentPage != TOTAL_PAGES) productAdapter.addLoadingFooter();
            else isLastPage = true;
        }
        if (ServiceCode == Constant.ADD_CART) {
            AddCartResponse rsp = (AddCartResponse) response;
            if (rsp != null) {
                TotalCartItem = rsp.getData();
                binding.tvCartItem.setText(String.valueOf(TotalCartItem));
                Log.e("CartFunction", "Total Item After Add " + TotalCartItem);
            }
        }
        if (ServiceCode == Constant.CHANGE_QUANTITY) {
            Object rsp = (Object) response;
        }
        if (ServiceCode == Constant.REMOVE_CART) {
            RemoveCartResponse rsp = (RemoveCartResponse) response;
            TotalCartItem = rsp.getData();
            binding.tvCartItem.setText(String.valueOf(TotalCartItem));
            Log.e("CartFunction", "Total Item After remove " + TotalCartItem);
        }

    }


    @Override
    public void retryPageLoad() {
    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backProductPage() {
            String pos = "1";
            ((MainActivity) getActivity()).rePlaceFragment(pos);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ItemList = sessionManager.getListFromLocal("cart");
        if (ItemList != null) {
            binding.tvCartItem.setText(String.valueOf(ItemList.size()));
        }

       /* if (productAdapter != null) {
            productAdapter.refreshrecyclerview();
            productAdapter.notifyDataSetChanged();
        }*/

    }

    private void filter(String text) {
        List<ProductListResponse.Data> filteredlist = new ArrayList<>();
        for (ProductListResponse.Data item : list) {
            if (item.getDrugName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
                Log.e("TotalItem", "search1 " + text + new Gson().toJson(filteredlist));
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("TotalItem", "search2 " + text + new Gson().toJson(filteredlist));
            productAdapter.filterList(filteredlist);
        }

    }


}
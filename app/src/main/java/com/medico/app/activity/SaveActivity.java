package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.medico.app.R;
import com.medico.app.adapter.CartAdapter;
import com.medico.app.adapter.SaveLatterAdapter;
import com.medico.app.databinding.ActivitySaveBinding;
import com.medico.app.interfaceClass.CartItemCount;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.Cart.CartResponse;
import com.medico.app.response.Cart.SaveLaterList;
import com.medico.app.response.MoveToCart.MoveToCartResponse;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class SaveActivity extends AppCompatActivity implements ApiResponseInterface, PaginationAdapterCallback, CartItemCount {
    ActivitySaveBinding binding;
    HideStatus hideStatus;
    private List<SaveLaterList> saveLater = new ArrayList<>();
    private ApiManager apiManager;
    private SaveLatterAdapter saveLatterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        apiManager = new ApiManager(this, this);
        apiManager.getCartList();
        binding.rvSaveLater.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPage() {
            onBackPressed();
            finish();
        }
    }

    @Override
    public void getCartItem(boolean add, String action, String drug_id, String quantity) {
        if (add) {
            if (action.equals("moveToCart")) {
                apiManager.moveToCart(drug_id, quantity);
                saveLatterAdapter.notifyDataSetChanged();
            } else if (action.equals("remove")) {

            }
        }
    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.CART_LIST) {
            CartResponse rsp = (CartResponse) response;
            if (rsp != null) {
                binding.clNoItem.setVisibility(View.GONE);
                saveLater = rsp.getData().getSaveLater();
                saveLatterAdapter = new SaveLatterAdapter(SaveActivity.this, this, this, "saveLater");
                binding.rvSaveLater.setAdapter(saveLatterAdapter);
                saveLatterAdapter.addAll(saveLater);
                saveLatterAdapter.notifyDataSetChanged();
            }
        }
        if (ServiceCode == Constant.MOVE_TO_CART) {
            MoveToCartResponse rsp = (MoveToCartResponse) response;
            if (rsp != null) {

            }
        }
    }

    @Override
    public void retryPageLoad() {

    }
}
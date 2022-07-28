package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;


import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.medico.app.R;
import com.medico.app.adapter.OrderItemAdapter;
import com.medico.app.databinding.ActivityOrderDetailBinding;
import com.medico.app.dialog.OrderCancelDialog;
import com.medico.app.response.Addcart.AddCartResponse;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.response.Cartlist.CartResult;
import com.medico.app.response.OrderRequest.OrderItem;
import com.medico.app.response.OrderResponse.OrderListResult;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OrderDetailActivity extends AppCompatActivity implements ApiResponseInterface {
    ActivityOrderDetailBinding binding;
    private String totalItems;
    private OrderListResult orderListResults;
    List<OrderItem> orderItem = new ArrayList<>();
    SessionManager sessionManager;
    OrderItemAdapter orderItemAdapter;
    private String valid_reason = null;
    ApiManager apiManager;
    HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);

        orderListResults = (OrderListResult) getIntent().getSerializableExtra("LIST");
        Log.e("getData", "fomrAdapter " + new Gson().toJson(orderListResults));
        totalItems = String.valueOf(orderListResults.getOrderDetailsJson().getOrderItem().size());
        if (orderListResults != null) {
            binding.tvPatientsNameInput.setText(orderListResults.getOrderDetailsJson().getShippingAddress().getName());
            binding.tvOrderNoInput.setText(orderListResults.getOrderId());
            binding.tvAddressView.setText(Html.fromHtml("<b>" + orderListResults.getOrderDetailsJson().getShippingAddress().getTypeName() + "</b>" + "<br />" +
                    "<b>" + orderListResults.getOrderDetailsJson().getShippingAddress().getName() + "</b>" + "<br />" +
                    "<small>" + orderListResults.getOrderDetailsJson().getShippingAddress().getAddress1() + "</small>" + "<br />" +
                    "<small>" + orderListResults.getOrderDetailsJson().getShippingAddress().getPincode() + "</small>" + "<br />" +
                    "<small>" + orderListResults.getOrderDetailsJson().getShippingAddress().getMobile() + "</small>"));
        }

        binding.tvOrderItemCount.setText(totalItems + " Items");
        binding.tvOrderAmount.setText(String.valueOf(orderListResults.getAmount()));
        binding.tvDeliverDate.setText(String.valueOf("Delivery Date " + orderListResults.getDeliveredDate()));
        for (int i = 0; i < orderListResults.getOrderDetailsJson().getOrderItem().size(); i++) {
            orderItem.add(orderListResults.getOrderDetailsJson().getOrderItem().get(i));
        }
        Log.e("orderItem", "sizefix " + orderItem.size());
        binding.rvOrderItem.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        orderItemAdapter = new OrderItemAdapter(OrderDetailActivity.this, orderItem);
        binding.rvOrderItem.setAdapter(orderItemAdapter);
        binding.tabOrder.addTab(binding.tabOrder.newTab().setText("Order Summary"));
        binding.tabOrder.addTab(binding.tabOrder.newTab().setText("Items"));
        binding.tabOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.view.getTab().getText().equals("Order Summary")) {
                    binding.rootOrder.setVisibility(View.VISIBLE);
                    binding.rootItems.setVisibility(View.GONE);
                } else {
                    binding.rootItems.setVisibility(View.VISIBLE);
                    binding.rootOrder.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        registerReceiver(receiver, new IntentFilter("REASON_CHANGED_ACTION"));
    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backOrderDetail() {
            onBackPressed();
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }

        public void OrderCancel() {
            if (binding.tvCancel.getText().toString().equals("Cancel")) {
                new OrderCancelDialog(mContext, binding.tvOrderNoInput.getText().toString());
            } else {
               /* String product_id = "";
                String quantity = "";
                boolean is_reOderVerify = false;
                for (int i = 0; i < orderItem.size(); i++) {
                    product_id = orderItem.get(i).getProductId();
                    quantity = orderItem.get(i).getQuantity();
                    apiManager.addCart(product_id, quantity);
                    is_reOderVerify = true;
                }
                if (is_reOderVerify) {
                    startActivity(new Intent(mContext, CartActivity.class));
                }*/
                print(orderItem, 0);

            }
        }
    }

    void print(List<OrderItem> a, int index) {
        //using a recursive method:
        if ((a != null) && (index < a.size())) {
            Log.e("REORDER", "getProductId" + a.get(index).getProductId());
            Log.e("REORDER", "getQuantity" + a.get(index).getQuantity());
            print(a, ++index);
        }
    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.ADD_CART) {
            AddCartResponse rsp = (AddCartResponse) response;
            if (rsp != null) {
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                valid_reason = intent.getStringExtra("reason");
                if (valid_reason != null && !valid_reason.isEmpty() && !valid_reason.equals("null")) {
                    binding.clStatus.setVisibility(View.VISIBLE);
                    binding.viewStatus.setVisibility(View.VISIBLE);
                    binding.tvReason.setText(valid_reason);
                    binding.tvCancel.setText("Re Order");
                    binding.tvCancel.setTextColor(Color.WHITE);
                    binding.tvCancel.setBackgroundResource(R.drawable.button_re_order);
                } else {
                    binding.clStatus.setVisibility(View.GONE);
                    binding.viewStatus.setVisibility(View.GONE);

                }

            }
        }
    };
}
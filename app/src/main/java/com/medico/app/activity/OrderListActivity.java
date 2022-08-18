package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.medico.app.R;
import com.medico.app.adapter.CartAdapter;
import com.medico.app.adapter.OrderListAdapter;
import com.medico.app.databinding.ActivityOrderListBinding;
import com.medico.app.response.Cartlist.CartResponse;
import com.medico.app.response.Cartlist.CartResult;
import com.medico.app.response.OrderResponse.OrderDataList;
import com.medico.app.response.OrderResponse.OrderListResponse;
import com.medico.app.response.OrderResponse.OrderListResult;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity implements ApiResponseInterface {
    ActivityOrderListBinding binding;
    private OrderListAdapter orderListAdapter;
    private List<OrderDataList> orderList = new ArrayList<>();
    HideStatus hideStatus;
    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));

        apiManager = new ApiManager(this, this);
        apiManager.getOrderList();
        binding.rvOrderList.setLayoutManager(new LinearLayoutManager(OrderListActivity.this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        orderList.clear();
        if (ServiceCode == Constant.ORDER_LIST) {
            OrderListResponse rsp = (OrderListResponse) response;
            if (rsp != null) {
                orderList = rsp.getResult().getData();
                orderListAdapter = new OrderListAdapter(this, orderList);
                binding.rvOrderList.setAdapter(orderListAdapter);
                orderListAdapter.notifyDataSetChanged();
            }

        }
    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backOrderList() {
            onBackPressed();
            finish();
        }
    }
}
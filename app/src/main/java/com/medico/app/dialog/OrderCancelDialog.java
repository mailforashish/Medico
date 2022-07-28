package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.medico.app.R;
import com.medico.app.adapter.OrderCancelAdapter;
import com.medico.app.databinding.DialogOrderCancelBinding;
import com.medico.app.interfaceClass.OrderCancel;
import com.medico.app.response.CancellationList;

import java.util.ArrayList;
import java.util.List;


public class OrderCancelDialog extends Dialog implements OrderCancel {
    DialogOrderCancelBinding binding;
    Context context;
    String orderNumber;
    private List<CancellationList> list = new ArrayList<>();
    private OrderCancelAdapter orderCancelAdapter;
    private String valid_reason;

    public OrderCancelDialog(@NonNull Context context, String orderNumber) {
        super(context);
        this.context = context;
        this.orderNumber = orderNumber;
        init();
    }

    void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_order_cancel, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.tvOrderNumber.setText(orderNumber);
        binding.rvCancelReasons.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        orderCancelAdapter = new OrderCancelAdapter(context, list, this);
        binding.rvCancelReasons.setAdapter(orderCancelAdapter);
        setData();
        show();

        binding.setClickListener(new EventHandler(getContext()));

    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void closeDialog() {
            dismiss();
        }

        public void cancelOrder() {
            Intent intent = new Intent("REASON_CHANGED_ACTION");
            intent.putExtra("reason", valid_reason);
            getContext().sendBroadcast(intent);
            dismiss();
        }

    }

    @Override
    public void getOrderCancelReason(boolean select, String reason) {
        if (select) {
            valid_reason = reason;
        }
    }


    private void setData() {
        CancellationList list1 = new CancellationList("I want to modify items in my order");
        list.add(list1);
        list1 = new CancellationList("Incorrect Address selected");
        list.add(list1);
        list1 = new CancellationList("Forgot to apply coupon");
        list.add(list1);
        list1 = new CancellationList("Facing payment related issues");
        list.add(list1);
        list1 = new CancellationList("Bought item from out side");
        list.add(list1);
        list1 = new CancellationList("Item not stock");
        list.add(list1);
        list1 = new CancellationList("Others");
        list.add(list1);
    }


}
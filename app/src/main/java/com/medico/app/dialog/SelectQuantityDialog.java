package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.medico.app.R;
import com.medico.app.adapter.OrderCancelAdapter;
import com.medico.app.adapter.SelectQuantityAdapter;
import com.medico.app.databinding.SelectQuantityBinding;
import com.medico.app.response.Addcart.AddCartResponse;
import com.medico.app.response.Addcart.RemoveCartResponse;
import com.medico.app.response.QuantityList;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SelectQuantityDialog extends Dialog implements ApiResponseInterface {
    TextView select;
    OnMyDialogResult myDialogResult;
    private Context context;
    private String Product_Id;
    List<String> quantityList;
    SelectQuantityBinding binding;
    SelectQuantityAdapter selectQuantityAdapter;
    List<QuantityList> mValues = new ArrayList<>();

    public SelectQuantityDialog(@NonNull Context context, String Product_Id) {
        super(context);
        this.context = context;
        this.Product_Id = Product_Id;
        init();
    }

    void init() {
        try {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.select_quantity, null, false);
            setContentView(binding.getRoot());
            getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            this.getWindow().setGravity(Gravity.CENTER);
            this.setCancelable(true);

            binding.setClickListener(new EventHandler(getContext()));
            quantityList = new ArrayList<String>(Arrays.asList(context.getResources().getStringArray(R.array.quantity)));
            for (int i = 0; i < quantityList.size(); i++) {
                String data = quantityList.get(i);
                mValues.add(new QuantityList(data));
            }
            binding.rvQuantity.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            selectQuantityAdapter = new SelectQuantityAdapter(context, mValues, "Select");
            binding.rvQuantity.setAdapter(selectQuantityAdapter);

            show();

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myDialogResult != null) {
                        //myDialogResult.finish(CityAdapter.city);
                        dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backDialog() {
            dismiss();
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
        if (ServiceCode == Constant.CHANGE_QUANTITY) {
            Object rsp = (Object) response;

        }
        if (ServiceCode == Constant.REMOVE_CART) {
            RemoveCartResponse rsp = (RemoveCartResponse) response;
        }

    }


    public interface OnMyDialogResult {
        void finish(String result);
    }
}
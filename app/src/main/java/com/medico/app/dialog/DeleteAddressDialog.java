package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.medico.app.R;
import com.medico.app.databinding.DialogAddressDeleteBinding;
import com.medico.app.interfaceClass.AddressAction;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;


public class DeleteAddressDialog extends Dialog implements ApiResponseInterface {
    DialogAddressDeleteBinding binding;
    Context context;
    ApiManager apiManager;
    String address_id;
    AddressResult addressResult;
    AddressAction addressAction;

    public DeleteAddressDialog(@NonNull Context context, AddressResult addressResult, AddressAction addressAction) {
        super(context);
        this.context = context;
        this.addressResult = addressResult;
        this.addressAction = addressAction;
        address_id = String.valueOf(addressResult.getId());
        apiManager = new ApiManager(getContext(), this);
        init();
    }

    void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_address_delete, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        show();

        binding.setClickListener(new EventHandler(getContext()));

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void exitButton() {
            dismiss();
        }

        public void oKButton() {
            apiManager.deleteAddress(address_id);
            addressAction.addressChanges("delete", addressResult,-1);
            dismiss();
        }

    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.DELETE_ADDRESS) {
            Object rsp = (Object) response;
            if (rsp != null) {
            }
        }
    }

}
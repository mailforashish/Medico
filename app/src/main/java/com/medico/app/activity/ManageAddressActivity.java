package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.medico.app.R;
import com.medico.app.adapter.MainAddressAdapter;

import com.medico.app.databinding.ActivityManageAddressBinding;
import com.medico.app.dialog.AddressDialog;
import com.medico.app.dialog.DeleteAddressDialog;

import com.medico.app.interfaceClass.AddressAction;
import com.medico.app.interfaceClass.DeleteAddress;
import com.medico.app.response.Address.AddressResponse;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;

import java.util.ArrayList;
import java.util.List;

public class ManageAddressActivity extends AppCompatActivity implements ApiResponseInterface, DeleteAddress, AddressAction {
    ActivityManageAddressBinding binding;
    private MainAddressAdapter addressAdapter;
    private List<AddressResult> addressLists = new ArrayList<>();
    ApiManager apiManager;
    HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_address);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        apiManager = new ApiManager(this, this);
        apiManager.getAddressList();
        binding.rvManageAddress.setLayoutManager(new LinearLayoutManager(ManageAddressActivity.this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void isError(String errorCode) {
    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.ADDRESS_LIST) {
            AddressResponse rsp = (AddressResponse) response;
            try {
                if (rsp != null) {
                    addressLists = rsp.getData();
                    addressAdapter = new MainAddressAdapter(ManageAddressActivity.this, addressLists, "manage", this);
                    binding.rvManageAddress.setAdapter(addressAdapter);
                    addressAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void editDeleteData(boolean select, String type, AddressResult addressResult, int position) {
        if (select) {
            if (type.equals("delete")) {
                new DeleteAddressDialog(ManageAddressActivity.this, addressResult, this);
            } else {
                new AddressDialog(ManageAddressActivity.this, type, addressResult, this, position);
            }
        }
    }

    @Override
    public void addressChanges(String action, AddressResult addressResult, int pos) {
        if (action.equals("delete")) {
            addressLists.remove(addressResult);
            addressAdapter.notifyDataSetChanged();
        } else if (action.equals("add")) {
            addressLists.add(addressResult);
            addressAdapter.notifyDataSetChanged();
        } else if (action.equals("edit")) {
            addressLists.set(pos, addressResult);
            addressAdapter.notifyDataSetChanged();
        }

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void addAddressPage() {
            new AddressDialog(mContext, "add", ManageAddressActivity.this);
        }

        public void backManagePage() {
            onBackPressed();
            finish();
        }
    }
}
package com.medico.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medico.app.R;
import com.medico.app.activity.EditProfileActivity;
import com.medico.app.adapter.MyAccountAdapter;
import com.medico.app.databinding.FragmentMyAccountBinding;
import com.medico.app.dialog.ExitDialog;
import com.medico.app.response.MyAccountList;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class MyAccountFragment extends Fragment {
    FragmentMyAccountBinding binding;
    private MyAccountAdapter myAccountAdapter;
    List<MyAccountList> myAccountLists = new ArrayList<>();
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setClickListener(new EventHandler(getContext()));
        sessionManager = new SessionManager(getContext());
        binding.rvMyAccount.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myAccountAdapter = new MyAccountAdapter(getContext(), myAccountLists);
        binding.rvMyAccount.setAdapter(myAccountAdapter);
        setData();
    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void editProfile() {
            startActivity(new Intent(getContext(), EditProfileActivity.class));
        }

        public void userLogOut() {
            new ExitDialog(MyAccountFragment.this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.tvName.setText(sessionManager.getUserName());
        binding.tvMobile.setText(sessionManager.getMobile());
        binding.tvEmail.setText(sessionManager.getUserEmail());
    }

    private void setData() {
        MyAccountList list1 = new MyAccountList("Order", R.drawable.ic_orders_my_bag);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Prescriptions", R.drawable.ic_prescriptions_my);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Save for Later", R.drawable.ic_save_for_my);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Wallet", R.drawable.ic_wallet_my);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Need Help", R.drawable.ic_need_my);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Manage Address", R.drawable.ic_address_my);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Set Refunds Preferences", R.drawable.ic_set_refund_my);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Refer and Earn", R.drawable.ic_refer_my);
        myAccountLists.add(list1);
        list1 = new MyAccountList("Legal", R.drawable.ic_legal_my);
        myAccountLists.add(list1);
    }


}
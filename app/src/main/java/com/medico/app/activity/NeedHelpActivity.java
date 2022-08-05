package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.medico.app.R;
import com.medico.app.adapter.NeedHelpAdapter;
import com.medico.app.databinding.ActivityLegalBinding;
import com.medico.app.databinding.ActivityNeedHelpBinding;
import com.medico.app.response.MyAccountList;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class NeedHelpActivity extends AppCompatActivity implements PaginationAdapterCallback {
    ActivityNeedHelpBinding binding;
    HideStatus hideStatus;
    private NeedHelpAdapter needHelpAdapter;
    private List<MyAccountList> needLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_need_help);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));

        binding.rvHelp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        needHelpAdapter = new NeedHelpAdapter(this, this, "Need");
        binding.rvHelp.setAdapter(needHelpAdapter);
        setData();
    }

    @Override
    public void retryPageLoad() {

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


    private void setData() {
        MyAccountList list1 = new MyAccountList("Medicine & Healthcare Orders", null);
        needLists.add(list1);
        list1 = new MyAccountList("Delivery", null);
        needLists.add(list1);
        list1 = new MyAccountList("Returns", null);
        needLists.add(list1);
        list1 = new MyAccountList("Payment", null);
        needLists.add(list1);
        list1 = new MyAccountList("Diagnostics", null);
        needLists.add(list1);
        list1 = new MyAccountList("Promotions", null);
        needLists.add(list1);
        list1 = new MyAccountList("General Enquiries", null);
        needLists.add(list1);
        list1 = new MyAccountList("Go Medicos", null);
        needLists.add(list1);
        list1 = new MyAccountList("Go Medicos Wallet", null);
        needLists.add(list1);
        list1 = new MyAccountList("Doctor Consultation", null);
        needLists.add(list1);
        list1 = new MyAccountList("Referrals", null);
        needLists.add(list1);
        list1 = new MyAccountList("COVID-19 Help Guide", null);
        needLists.add(list1);
        needHelpAdapter.addAll(needLists);
    }
}
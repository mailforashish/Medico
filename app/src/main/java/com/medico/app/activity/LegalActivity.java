package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.medico.app.R;
import com.medico.app.adapter.NeedHelpAdapter;
import com.medico.app.databinding.ActivityLegalBinding;
import com.medico.app.response.MyAccountList;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class LegalActivity extends AppCompatActivity implements PaginationAdapterCallback {
    ActivityLegalBinding binding;
    HideStatus hideStatus;
    private NeedHelpAdapter needHelpAdapter;
    private List<MyAccountList> needLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_legal);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));

        binding.rvLegal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        needHelpAdapter = new NeedHelpAdapter(this, this, "Legal");
        binding.rvLegal.setAdapter(needHelpAdapter);
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
        MyAccountList list1 = new MyAccountList("Term & Conditions", R.drawable.ic_upload_prescription);
        needLists.add(list1);
        list1 = new MyAccountList("Privacy Policy", R.drawable.ic_pasword);
        needLists.add(list1);
        list1 = new MyAccountList("Customer Support Policy", R.drawable.ic_need_my);
        needLists.add(list1);
        needHelpAdapter.addAll(needLists);
    }
}
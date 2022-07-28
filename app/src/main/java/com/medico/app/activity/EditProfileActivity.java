package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.medico.app.R;
import com.medico.app.databinding.ActivityEditProfileBinding;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    SessionManager sessionManager;
    HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        sessionManager = new SessionManager(this);
        binding.edtMobile.setText(sessionManager.getMobile());
        binding.edtName.setText(sessionManager.getUserName());

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPage() {
            onBackPressed();
        }

        public void btnSaveDetails() {
            if (binding.edtName.getText().toString().isEmpty()) {
                Toast.makeText(mContext, "Name can't be Blank", Toast.LENGTH_SHORT).show();
            } else {
                sessionManager.setUserName(binding.edtName.getText().toString());
                finish();
                onBackPressed();
            }


        }
    }
}
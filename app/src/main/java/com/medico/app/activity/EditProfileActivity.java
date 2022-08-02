package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.medico.app.R;
import com.medico.app.databinding.ActivityEditProfileBinding;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    SessionManager sessionManager;
    HideStatus hideStatus;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        sessionManager = new SessionManager(this);
        binding.edtMobile.setText(sessionManager.getMobile());
        binding.edtName.setText(sessionManager.getUserName());
        binding.edtemailId.setText(sessionManager.getUserEmail());
        binding.edtemailId.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (binding.edtemailId.getText().toString().trim().matches(emailPattern) && s.length() > 0) {
                    binding.tvVerified.setText("valid email");
                } else {
                    binding.tvVerified.setText("invalid email");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


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
                sessionManager.setUserEmail(binding.edtemailId.getText().toString());
                finish();
                onBackPressed();
            }


        }
    }
}
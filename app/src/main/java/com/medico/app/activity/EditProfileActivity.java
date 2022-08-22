package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medico.app.R;
import com.medico.app.databinding.ActivityEditProfileBinding;
import com.medico.app.response.EditProfile.EditProfileResponse;
import com.medico.app.response.OTp.OTPResponse;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;

public class EditProfileActivity extends AppCompatActivity implements ApiResponseInterface {
    ActivityEditProfileBinding binding;
    SessionManager sessionManager;
    HideStatus hideStatus;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        binding.edtMobile.setText(sessionManager.getMobile());
        binding.edtName.setText(sessionManager.getUserName());
        binding.edtemailId.setText(sessionManager.getUserEmail());

        binding.edtName.addTextChangedListener(new CustomTextWatcher(binding.edtName));
        binding.edtMobile.addTextChangedListener(new CustomTextWatcher(binding.edtMobile));
        binding.edtemailId.addTextChangedListener(new CustomTextWatcher(binding.edtemailId));

    }

    private class CustomTextWatcher implements TextWatcher {
        private View view;

        private CustomTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.edtName:
                    if (editable.length() <= 3) {
                        binding.tvNameError.setVisibility(View.VISIBLE);
                        binding.tvNameError.setText("Enter Valid Name");
                    } else {
                        binding.tvNameError.setVisibility(View.INVISIBLE);

                    }
                    break;
                case R.id.edtMobile:

                    break;
                case R.id.edtemailId:
                    if (binding.edtemailId.getText().toString().trim().matches(emailPattern) && editable.length() > 0) {
                        binding.tvVerified.setText("valid email");
                    } else {
                        binding.tvVerified.setText("invalid email");
                    }
                    break;
            }
        }
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
            if (isMandatoryFields()) {
                apiManager.editProfile(binding.edtName.getText().toString(), binding.edtMobile.getText().toString(),
                        binding.edtemailId.getText().toString());
            } else {
                Toast.makeText(mContext, "Please fill the required fields", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.EDIT_PROFILE) {
            EditProfileResponse rsp = (EditProfileResponse) response;
            if (rsp != null) {
                sessionManager.setUserName(rsp.getData().getFirstName());
                sessionManager.saveMobile(rsp.getData().getMobile());
                sessionManager.setUserEmail(rsp.getData().getEmail());
                finish();
            }
        }
    }

    private boolean isMandatoryFields() {
        if (binding.edtName.getText().toString().isEmpty()) {
            return false;
        } else if (binding.edtMobile.getText().toString().isEmpty()) {
            return false;
        } else if (binding.edtemailId.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

}
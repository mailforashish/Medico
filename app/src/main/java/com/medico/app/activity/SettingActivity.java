package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.medico.app.R;
import com.medico.app.databinding.ActivitySettingBinding;
import com.medico.app.utils.HideStatus;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPage() {
            onBackPressed();
        }

        public void notificationSettings() {
        }

        public void changePassword() {
        }

        public void shareOurApp() {
        }

        public void rateOurApp() {
        }

        public void termsConditions() {
        }

        public void privacyPolicy() {
        }

        public void logout() {

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
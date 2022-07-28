package com.medico.app.dialog;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.medico.app.R;
import com.medico.app.activity.MainActivity;
import com.medico.app.utils.SessionManager;

public class PermissionDialog extends Dialog {
    MainActivity context;
    public static final int RequestPermissionCode = 7;

    public PermissionDialog(MainActivity context) {
        super(context);
        this.context = context;
        init();
    }

    void init() {
        try {
            this.setContentView(R.layout.permission_dialog);
            this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //this.setCancelable(true);
            show();
            TextView tv_allow = findViewById(R.id.tv_allow);
            tv_allow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Adding if condition inside button.
                    ActivityCompat.requestPermissions(
                            context,
                            new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            },
                            RequestPermissionCode

                    );
                    new SessionManager(context).setUserAskpermission();
                    dismiss();
                }
            });


        } catch (Exception e) {
        }
    }



}
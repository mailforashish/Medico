package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.medico.app.R;
import com.medico.app.activity.StartActivity;
import com.medico.app.fragment.MyAccountFragment;
import com.medico.app.utils.SessionManager;


public class ExitDialog extends Dialog {
    MyAccountFragment context;
    public ExitDialog(@NonNull MyAccountFragment context) {
        super(context.getContext());
        this.context = context;

        init();
    }

    void init() {
        this.setContentView(R.layout.dialog_exit);
        this.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(false);
        show();

        TextView closeDialog = findViewById(R.id.close_dialog);
        TextView logout = findViewById(R.id.logout);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SessionManager(getContext()).logoutUser();
                context.startActivity(new Intent(context.getActivity(), StartActivity.class));
                context.getActivity().finish();
                dismiss();
            }
        });
    }


}
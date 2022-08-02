package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.medico.app.R;
import com.medico.app.databinding.DialogNoInternetBinding;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.NetworkCheck;

import java.util.Random;

public class NoInternetDialog extends Dialog {
    DialogNoInternetBinding binding;
    Context context;
    private int[] images = {R.drawable.ic_internet_1, R.drawable.ic_internet_2, R.drawable.ic_internet_3};
    static int idx = 0;
    HideStatus hideStatus;
    private NetworkCheck networkCheck;

    public NoInternetDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        init();
    }

    void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_no_internet, null, false);
        setContentView(binding.getRoot());
        hideStatus = new HideStatus(getWindow(), true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(false);
        binding.setClickListener(new EventHandler(getContext()));
        networkCheck = new NetworkCheck();
        show();

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void reFreshPage() {
            idx = new Random().nextInt(images.length);
            binding.ivNoInternet.setImageResource(images[idx]);
            if (networkCheck.isNetworkAvailable(mContext)) {
                dismiss();
            }
        }
    }
}
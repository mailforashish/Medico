package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.medico.app.R;
import com.medico.app.databinding.LegalDialogBinding;
import com.medico.app.utils.HideStatus;

public class LegalDialog extends Dialog {
    LegalDialogBinding binding;
    Context context;
    HideStatus hideStatus;
    int position;
    String url = "";
    public LegalDialog(@NonNull Context context, int position) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        this.position = position;
        init();
    }

    void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.legal_dialog, null, false);
        setContentView(binding.getRoot());
        hideStatus = new HideStatus(getWindow(), true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(false);
        if (position == 0) {
            url = "https://sites.google.com/view/meetlive/home";
        } else if (position == 1) {
            //url = "https://sites.google.com/view/meetlive/home";
        } else if (position == 2) {
            //url = "https://sites.google.com/view/meetlive/home";
        }
        startWebView(url);
        show();

    }

    private void startWebView(String url) {
        binding.progress.setVisibility(View.VISIBLE);
        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                binding.progress.setVisibility(View.GONE);
            }
        });
        binding.webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}




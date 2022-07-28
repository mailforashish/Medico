package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.medico.app.R;
import com.medico.app.activity.OrderListActivity;
import com.medico.app.activity.PaymentActivity;


public class OrderCompletedDialog extends Dialog {
    String amount;
    String transaction_id;
    PaymentActivity context;

    public OrderCompletedDialog(PaymentActivity context, String transactionId, String amount) {
        super(context);
        this.context = context;
        this.transaction_id = transactionId;
        this.amount = amount;
        init();
    }

    void init() {
        this.setContentView(R.layout.order_successfull);
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(false);
        TextView transactionId = findViewById(R.id.transaction_id);
        transactionId.setText(transaction_id);
        Button btn_done = findViewById(R.id.done_btn);
        btn_done.setOnClickListener(view -> {
            context.startActivity(new Intent(context, OrderListActivity.class));
            dismiss();
            context.finish();
        });
        show();
    }
}
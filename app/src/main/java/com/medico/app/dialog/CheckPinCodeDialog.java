package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medico.app.R;
import com.medico.app.databinding.DialogPinVerifyBinding;
import com.medico.app.fragment.HomeFragment;
import com.medico.app.response.PinCode.PinCodeResponse;
import com.medico.app.retrofit.ApiInterface;
import com.medico.app.utils.HideStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.medico.app.retrofit.ApiClient.getRequestHeader;

public class CheckPinCodeDialog extends Dialog {
    DialogPinVerifyBinding binding;
    private Context context;
    private HideStatus hideStatus;
    private boolean is_valid;
    private OnMyDialogPinCode myDialogResult;

    public CheckPinCodeDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        init();
    }

    void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_pin_verify, null, false);
        setContentView(binding.getRoot());
        hideStatus = new HideStatus(getWindow(), true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.setClickListener(new EventHandler(getContext()));
        binding.edtPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.edtPincode.getText().toString().length() >= 6) {
                    binding.tvInvalidPincode.setVisibility(View.INVISIBLE);
                    is_valid = true;
                } else {
                    is_valid = false;
                    binding.tvInvalidPincode.setVisibility(View.VISIBLE);
                }
            }
        });
        show();

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void closePage() {
            dismiss();
        }

        public void checkPin() {
            if (is_valid) {
                getDataFromPinCode(binding.edtPincode.getText().toString());
            }
        }
    }

    private void getDataFromPinCode(String pinCode) {
        String url = "http://www.postalpincode.in/api/pincode/";
        Retrofit retrofit = null;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<PinCodeResponse> call = apiInterface.getPinCode(pinCode);
        call.enqueue(new Callback<PinCodeResponse>() {
            @Override
            public void onResponse(Call<PinCodeResponse> call, Response<PinCodeResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        List<PinCodeResponse.PostOffice> postOfficeArray = response.body().getPostOffice();
                        String name = postOfficeArray.get(0).getName();
                        String district = postOfficeArray.get(0).getDistrict();
                        String state = postOfficeArray.get(0).getState();
                        if (myDialogResult != null) {
                            myDialogResult.finish(pinCode, name);
                            dismiss();
                        }
                        Log.e("Details_pin_code:", "" + "\n" + "District is : " + district + "\n" + "State : " + state + "\n" + "City : " + name);
                    } catch (Exception e) {
                        binding.tvInvalidPincode.setVisibility(View.VISIBLE);
                        binding.tvInvalidPincode.setText("Pin code is not valid");
                        e.printStackTrace();
                        Log.e("Details_pin_error:", "" + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<PinCodeResponse> call, Throwable t) {
                Log.e("Details_pin_code:", "Details_Errpr " + t.getMessage());
                Toast.makeText(context, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDialogResult(OnMyDialogPinCode dialogResult) {
        myDialogResult = dialogResult;
    }

    public interface OnMyDialogPinCode {
        void finish(String pinCode, String city);
    }

}
package com.medico.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.medico.app.R;
import com.medico.app.databinding.ActivityMobileNumberBinding;
import com.medico.app.response.LoginResponse;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.NetworkCheck;
import com.medico.app.utils.SessionManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MobileNumberActivity extends AppCompatActivity implements ApiResponseInterface {
    ActivityMobileNumberBinding binding;
    private static String mobile = "";
    ApiManager apiManager;
    private boolean required;

    private String blockCharacterSet = "~#^|$%&!\\/!@#$%^&*(){}_[]|\\?/<>,.:-'';§£¥.+\\ ";
    public InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    private NetworkCheck networkCheck;
    private String SmsApi = "https://2factor.in/API/V1/2084a5d9-c0a0-11eb-8089-0200cd936042/SMS/";
    SessionManager sessionManager;
    private static int otpNumber = 111111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_number);
        binding.setClickListener(new EventHandler(this));

        networkCheck = new NetworkCheck();
        apiManager = new ApiManager(this, this);
        sessionManager = new SessionManager(this);
        InputFilter filter_phone = new InputFilter.LengthFilter(10);
        binding.etMobileNumber.setFilters(new InputFilter[]{filter, filter_phone});

        binding.etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
                if (binding.etMobileNumber.getText().toString().length() >= 10 && isValidPhone(s.toString())) {
                    binding.tvPhoneError.setVisibility(View.INVISIBLE);
                    required = true;
                } else {
                    required = false;
                    binding.tvPhoneError.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void nextPage() {
            if (TextUtils.isEmpty(binding.etMobileNumber.getText().toString()) || binding.etMobileNumber.getText().toString().length() <= 9) {
                Toast.makeText(mContext, "Enter a valid phone number.", Toast.LENGTH_SHORT).show();
            } else {
                if (networkCheck.isNetworkAvailable(getApplicationContext())) {
                    if (required) {
                        apiManager.login(binding.etMobileNumber.getText().toString());
                    }

                } else {
                    Toast.makeText(mContext, "Check your connection.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.LOGIN) {
            LoginResponse rsp = (LoginResponse) response;
            if (rsp != null) {
                Toast.makeText(MobileNumberActivity.this, rsp.getMessage(), Toast.LENGTH_SHORT).show();
                sessionManager.saveMobile(binding.etMobileNumber.getText().toString());
                startActivity(new Intent(MobileNumberActivity.this, OTPVerifyActivity.class)
                        .putExtra("number", binding.etMobileNumber.getText().toString())
                        .putExtra("otp", String.valueOf(otpNumber))
                );

                finishAffinity();
            }


        }


    }


    /* private void sendOtp2Factor(String phone) {
         Random rand = new Random();
         otpNumber = rand.nextInt(999999);
         SmsApi = SmsApi + "/" + phone + "/" + String.format("%06d", otpNumber);

         new RequestTask().execute(SmsApi);
     }

     class RequestTask extends AsyncTask<String, String, String> {
         @Override
         protected String doInBackground(String... uri) {
             String responseString = null;
             try {
                 URL url = new URL(uri[0]);
                 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                 if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                     // Do normal input or output stream reading
                     startActivity(new Intent(MobileNumberActivity.this, OTPVerifyActivity.class)
                             .putExtra("number", binding.etMobileNumber.getText().toString())
                             .putExtra("otp", String.valueOf(otpNumber))
                     );
                     Log.e("SendOTP", "Send_otp " + otpNumber);
                     sessionManager.saveMobile(binding.etMobileNumber.getText().toString());
                     finishAffinity();

                 } else {
                     //response = "FAILED"; // See documentation for more info on response handling
                 }
             } catch (IOException e) {
                 //TODO Handle problems..
             }
             return responseString;
         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);
             //Do anything with response..
         }
     }
 */
    public static boolean isValidPhone(String s) {
        Pattern p = Pattern.compile("(0|91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

}
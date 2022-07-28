package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.medico.app.R;
import com.medico.app.databinding.ActivityOtpverifyBinding;
import com.medico.app.response.OTp.OTPResponse;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.NetworkCheck;
import com.medico.app.utils.SessionManager;

public class OTPVerifyActivity extends AppCompatActivity implements ApiResponseInterface {
    ActivityOtpverifyBinding binding;
    private AppCompatEditText[] editTexts;
    private String receive_otp = "";
    private static String mobile = "";
    private String inputValue;

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
    ApiManager apiManager;
    SessionManager session;
    private static String android_id = "";
    private static int otpNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverify);
        binding.setClickListener(new EventHandler(this));

        mobile = getIntent().getStringExtra("number");
        receive_otp = String.valueOf(getIntent().getStringExtra("otp"));
        binding.tvMobileNumber.setText(String.format("+91-%s ", mobile));
        Log.e("sendOTP", "receive_otp " + receive_otp);
        networkCheck = new NetworkCheck();
        apiManager = new ApiManager(this, this);
        session = new SessionManager(this);

        editTexts = new AppCompatEditText[]{binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4, binding.etOtp5, binding.etOtp6};
        binding.etOtp1.addTextChangedListener(new PinTextWatcher(0));
        binding.etOtp2.addTextChangedListener(new PinTextWatcher(1));
        binding.etOtp3.addTextChangedListener(new PinTextWatcher(2));
        binding.etOtp4.addTextChangedListener(new PinTextWatcher(3));
        binding.etOtp5.addTextChangedListener(new PinTextWatcher(4));
        binding.etOtp6.addTextChangedListener(new PinTextWatcher(5));

        binding.etOtp1.setOnKeyListener(new PinOnKeyListener(0));
        binding.etOtp2.setOnKeyListener(new PinOnKeyListener(1));
        binding.etOtp3.setOnKeyListener(new PinOnKeyListener(2));
        binding.etOtp4.setOnKeyListener(new PinOnKeyListener(3));
        binding.etOtp5.setOnKeyListener(new PinOnKeyListener(4));
        binding.etOtp6.setOnKeyListener(new PinOnKeyListener(5));

        InputFilter filter_phone = new InputFilter.LengthFilter(1);
        binding.etOtp1.setFilters(new InputFilter[]{filter, filter_phone});
        binding.etOtp2.setFilters(new InputFilter[]{filter, filter_phone});
        binding.etOtp3.setFilters(new InputFilter[]{filter, filter_phone});
        binding.etOtp4.setFilters(new InputFilter[]{filter, filter_phone});
        binding.etOtp5.setFilters(new InputFilter[]{filter, filter_phone});
        binding.etOtp6.setFilters(new InputFilter[]{filter, filter_phone});

    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void changeNumber() {
        }

        public void resendCode() {
            if (networkCheck.isNetworkAvailable(getApplicationContext())) {
                otpNumber = 0;
                SmsApi = "https://2factor.in/API/V1/2084a5d9-c0a0-11eb-8089-0200cd936042/SMS/";

                String new_mobile = "+91" + mobile;
                //sendAgainOTP(new_mobile);
            } else {
                Toast.makeText(getApplicationContext(), "Check your connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }


  /*  private void sendAgainOTP(String phone) {
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
                    receive_otp = String.valueOf(otpNumber);
                    Log.e("SendOTP", "ReSend_otp " + otpNumber);
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
    }*/


    public class PinTextWatcher implements TextWatcher {
        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;
            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = newTypedString;
            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (AppCompatEditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;

            inputValue = binding.etOtp1.getText().toString() + binding.etOtp2.getText().toString() +
                    binding.etOtp3.getText().toString() + binding.etOtp4.getText().toString() +
                    binding.etOtp5.getText().toString() + binding.etOtp6.getText().toString();

            Log.e("OTPVAlues", "OTPValue " + inputValue);
            Log.e("OTPVAlues", "OTPReceiveValue " + receive_otp);
            if (receive_otp.equals(inputValue)) {
                apiManager.loginOTP(mobile, inputValue);
                //Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_SHORT).show();
                binding.tvInvalidOtp.setVisibility(View.INVISIBLE);
            } else {
                binding.tvInvalidOtp.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Enter Valid OTP.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }


    public class PinOnKeyListener implements View.OnKeyListener {
        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }


    /*private String mHash = "";

    private void hash() {
        PackageInfo info;
        try {

            info = getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_SIGNATURES);

            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //Log.e("Klive_sha_key", md.toString());
                String something = new String(Base64.encode(md.digest(), 0));
                mHash = something;
                //Log.e("Klive_Hash_key", something);
                System.out.println("Hash key" + something);
            }

        } catch (PackageManager.NameNotFoundException e1) {
            //     Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            //     Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            //     Log.e("exception", e.toString());
        }
    }*/


    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.OTP_LOGIN) {
            OTPResponse rsp = (OTPResponse) response;
            if (rsp != null) {
                session.createLoginSession(rsp);
                startActivity(new Intent(OTPVerifyActivity.this, MainActivity.class));
                finishAffinity();
            }


        }
    }
}
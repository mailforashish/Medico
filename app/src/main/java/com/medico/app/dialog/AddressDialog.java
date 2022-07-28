package com.medico.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;

import com.medico.app.R;

import com.medico.app.databinding.DialogAddressBinding;
import com.medico.app.interfaceClass.AddressAction;
import com.medico.app.response.AddEdit.AddEditAddressResponse;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.response.Address.District;
import com.medico.app.response.Address.State;
import com.medico.app.response.Address.Type;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddressDialog extends Dialog implements ApiResponseInterface {
    DialogAddressBinding binding;
    Context context;
    private AppCompatEditText[] editTexts;
    private String addressType = "";
    ApiManager apiManager;
    private String Id;
    private String editType;
    private AddressResult addressResult;
    private HideStatus hideStatus;
    private AddressAction addressAction;
    private int position;

    private String blockCharacterSet = "~#^|$%&*!\\/*!@#$%^&*(){}_[]|\\?/<>,.:-'';§£¥.+\\ ";
    public InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public AddressDialog(@NonNull Context context, String editType, AddressAction addressAction) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        this.editType = editType;
        this.addressAction = addressAction;
        init();
    }

    public AddressDialog(@NonNull Context context, String editType, AddressResult addressResult, AddressAction addressAction, int position) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        this.editType = editType;
        this.addressResult = addressResult;
        this.addressAction = addressAction;
        this.position = position;
        addressType = String.valueOf(addressResult.getType().getId());
        Id = String.valueOf(addressResult.getId());
        init();
    }

    void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_address, null, false);
        setContentView(binding.getRoot());
        hideStatus = new HideStatus(getWindow(), true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        apiManager = new ApiManager(getContext(), this);
        binding.setClickListener(new EventHandler(getContext()));

        if (addressResult != null) {
            binding.tvName.setText(addressResult.getName());
            binding.tvPhone.setText(addressResult.getMobile());
            binding.edtpincode.setText(String.valueOf(addressResult.getPincode()));
            binding.tvFlatNumber.setText(addressResult.getAddress2());
            binding.tvStreetName.setText(addressResult.getAddress1());
            binding.tvLandmarkName.setText(addressResult.getLandmark());
        }
        show();
        editTexts = new AppCompatEditText[]{binding.tvName, binding.tvPhone, binding.edtpincode, binding.tvFlatNumber, binding.tvStreetName, binding.tvLandmarkName};
        binding.tvName.addTextChangedListener(new FieldTextWatcher(0));
        binding.tvPhone.addTextChangedListener(new FieldTextWatcher(1));
        binding.edtpincode.addTextChangedListener(new FieldTextWatcher(2));
        binding.tvFlatNumber.addTextChangedListener(new FieldTextWatcher(3));
        binding.tvStreetName.addTextChangedListener(new FieldTextWatcher(4));
        binding.tvLandmarkName.addTextChangedListener(new FieldTextWatcher(5));
        InputFilter phone = new InputFilter.LengthFilter(10);
        binding.tvPhone.setFilters(new InputFilter[]{filter, phone});
        InputFilter pin = new InputFilter.LengthFilter(6);
        binding.edtpincode.setFilters(new InputFilter[]{filter, pin});

        binding.tvHomeAddress.setBackgroundResource(R.drawable.selected_addresstype_bg);
        binding.tvHomeAddress.setTextColor(context.getResources().getColor(R.color.white));
        addressType = "1";
    }

    public class FieldTextWatcher implements TextWatcher {
        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        FieldTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;
            if (currentIndex == 0) {
                this.isFirst = true;
            } else if (currentIndex == editTexts.length - 1) {
                this.isLast = true;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {
            isAllEditTextsFilled(currentIndex);
        }

        private boolean isAllEditTextsFilled(int currentIndex) {
            //Log.e("currentIndex", "valueIndex" + currentIndex);
            if (currentIndex == 0) {
                if (editTexts[currentIndex].length() > 1) {
                    binding.tvNameError.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvNameError.setVisibility(View.VISIBLE);
                }

            } else if (currentIndex == 1) {
                if (editTexts[currentIndex].length() >= 10 && isValidPhone(editTexts[currentIndex].getText().toString())) {
                    binding.tvPhoneError.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvPhoneError.setVisibility(View.VISIBLE);
                }

            } else if (currentIndex == 2) {
                if (editTexts[currentIndex].length() >= 6) {
                    binding.tvPincodeError.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvPincodeError.setVisibility(View.VISIBLE);
                }

            } else if (currentIndex == 3) {
                if (editTexts[currentIndex].length() > 4) {
                    binding.tvFlatError.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvFlatError.setVisibility(View.VISIBLE);
                }

            } else if (currentIndex == 4) {
                if (editTexts[currentIndex].length() > 4) {
                    binding.tvStreetError.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvStreetError.setVisibility(View.VISIBLE);
                }

            } else if (currentIndex == 5) {
                if (editTexts[currentIndex].length() > 1) {
                    binding.tvLandmarkError.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvLandmarkError.setVisibility(View.VISIBLE);
                }
            }
            return true;
        }


    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backDialog() {
            dismiss();
        }

        public void homeSelect() {
            binding.tvHomeAddress.setBackgroundResource(R.drawable.selected_addresstype_bg);
            binding.tvOffice.setBackgroundResource(R.drawable.unselected_addresstype_bg);
            binding.tvOthers.setBackgroundResource(R.drawable.unselected_addresstype_bg);
            binding.tvHomeAddress.setTextColor(context.getResources().getColor(R.color.white));
            binding.tvOffice.setTextColor(context.getResources().getColor(R.color.black_new));
            binding.tvOthers.setTextColor(context.getResources().getColor(R.color.black_new));
            addressType = "1";
        }

        public void officeSelect() {
            binding.tvOffice.setBackgroundResource(R.drawable.selected_addresstype_bg);
            binding.tvHomeAddress.setBackgroundResource(R.drawable.unselected_addresstype_bg);
            binding.tvOthers.setBackgroundResource(R.drawable.unselected_addresstype_bg);
            binding.tvOffice.setTextColor(context.getResources().getColor(R.color.white));
            binding.tvHomeAddress.setTextColor(context.getResources().getColor(R.color.black_new));
            binding.tvOthers.setTextColor(context.getResources().getColor(R.color.black_new));
            addressType = "2";
        }

        public void otherSelect() {
            binding.tvOthers.setBackgroundResource(R.drawable.selected_addresstype_bg);
            binding.tvHomeAddress.setBackgroundResource(R.drawable.unselected_addresstype_bg);
            binding.tvOffice.setBackgroundResource(R.drawable.unselected_addresstype_bg);
            binding.tvOthers.setTextColor(context.getResources().getColor(R.color.white));
            binding.tvOffice.setTextColor(context.getResources().getColor(R.color.black_new));
            binding.tvHomeAddress.setTextColor(context.getResources().getColor(R.color.black_new));
            addressType = "3";

        }

        public void saveContinue() {
            AddressResult list = new AddressResult();
            State state = new State();
            District district = new District();
            Type type = new Type();
            list.setName(binding.tvName.getText().toString());
            list.setMobile(binding.tvPhone.getText().toString());
            list.setAddress1(binding.tvFlatNumber.getText().toString());
            list.setAddress2(binding.tvStreetName.getText().toString());
            state.setName("New Delhi");
            list.setState(state);
            district.setName("1");
            list.setDistrict(district);
            list.setPincode(Integer.parseInt(binding.edtpincode.getText().toString()));
            list.setLandmark(binding.tvLandmarkName.getText().toString());
            type.setId(Integer.parseInt(addressType));
            if (addressType.equals("1")) {
                type.setValue("Home");
            } else if (addressType.equals("2")) {
                type.setValue("Office");
            } else if (addressType.equals("3")) {
                type.setValue("Other");
            }
            list.setType(type);

            if (isMandatoryFields() && editType.equals("add")) {
                apiManager.addAddress(binding.tvName.getText().toString(), binding.tvPhone.getText().toString(), binding.tvFlatNumber.getText().toString(),
                        binding.tvStreetName.getText().toString(), "1", "1", binding.edtpincode.getText().toString(),
                        binding.tvLandmarkName.getText().toString(), addressType);
                addressAction.addressChanges("add", list, -1);
                dismiss();
            } else if (isMandatoryFields() && editType.equals("edit")) {
                apiManager.editAddress(Id, binding.tvName.getText().toString(), binding.tvPhone.getText().toString(), binding.tvFlatNumber.getText().toString(),
                        binding.tvStreetName.getText().toString(), "1", "1", binding.edtpincode.getText().toString(),
                        binding.tvLandmarkName.getText().toString(), addressType);
                addressAction.addressChanges("edit", list, position);
                dismiss();
            } else {
                Toast.makeText(mContext, "some field empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isValidPhone(String s) {
        Pattern p = Pattern.compile("(0|91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    private boolean isMandatoryFields() {
        if (binding.tvName.getText().toString().isEmpty()) {
            return false;
        } else if (binding.tvPhone.getText().toString().isEmpty()) {
            return false;
        } else if (binding.edtpincode.getText().toString().isEmpty()) {
            return false;
        } else if (binding.tvFlatNumber.getText().toString().isEmpty()) {
            return false;
        } else if (binding.tvStreetName.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.ADD_ADDRESS) {
            AddEditAddressResponse rsp = (AddEditAddressResponse) response;
            try {
                if (rsp != null) {
                    //Toast.makeText(context, rsp.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

            }

        }
        if (ServiceCode == Constant.EDIT_ADDRESS) {
            AddEditAddressResponse rsp = (AddEditAddressResponse) response;
            try {
                if (rsp != null) {
                    //Toast.makeText(context, rsp.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

            }

        }

    }

}
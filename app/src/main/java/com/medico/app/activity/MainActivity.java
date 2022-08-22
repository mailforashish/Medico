package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.Cart.CartResult;
import com.medico.app.R;
import com.medico.app.databinding.ActivityMainBinding;
import com.medico.app.fragment.HealthCareFragment;
import com.medico.app.fragment.HomeFragment;
import com.medico.app.fragment.MyAccountFragment;
import com.medico.app.fragment.NotificationFragment;
import com.medico.app.interfaceClass.TextSizeIncrease;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.NetworkCheck;
import com.medico.app.utils.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextSizeIncrease {
    ActivityMainBinding binding;
    Fragment fragment = null;
    TextSizeIncrease textSizeIncrease;
    Fragment homeFragment = new HomeFragment();
    Fragment healthCareFragment = new HealthCareFragment();
    Fragment notificationFragment = new NotificationFragment();
    Fragment myAccountFragment = new MyAccountFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active;
    SessionManager sessionManager;
    boolean doubleBackToExitPressedOnce = false;

    public static DrawerLayout drawer;
    private TextView tv_user_name, tv_user_mobile_no;

    private TextView tv;
    private double latitude;
    private double longitude;
    private String city_name = "";
    private String pin_code = "";
    private int REQUEST_CODE_CHECK_SETTINGS = 2022;
    private List<CartList> purchaseLists = new ArrayList<>();
    HideStatus hideStatus;
    private NetworkCheck networkCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        hideStatus = new HideStatus(getWindow(), true);
        networkCheck = new NetworkCheck();
        binding.setClickListener(new EventHandler(this));
        drawer = findViewById(R.id.drawer_layout);
        fm.beginTransaction().add(R.id.fragment_view, healthCareFragment, "2").hide(healthCareFragment).commit();
        fm.beginTransaction().add(R.id.fragment_view, notificationFragment, "3").hide(notificationFragment).commit();
        fm.beginTransaction().add(R.id.fragment_view, myAccountFragment, "4").hide(myAccountFragment).commit();
        addFragment(homeFragment, "1");

        textSizeIncrease = (TextSizeIncrease) this;
        textSizeIncrease.setTextSize(binding.tvHome.getId());
        binding.viewBarHome.setVisibility(View.VISIBLE);

        sessionManager = new SessionManager(this);
        purchaseLists = sessionManager.getListFromLocal("cart");

        View header = binding.navigationView.getHeaderView(0);
        tv_user_name = (TextView) header.findViewById(R.id.tv_user_name);
        tv_user_name.setText(sessionManager.getUserName());
        tv_user_mobile_no = (TextView) header.findViewById(R.id.tv_user_mobile_no);
        tv_user_mobile_no.setText("+91-" + sessionManager.getMobile());

       /* if (!sessionManager.getUserAskpermission().equals("no")) {
            new PermissionDialog(MainActivity.this);
        }*/
        getPermission();

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void appointment() {
            drawer.closeDrawer(Gravity.LEFT);
        }

        public void testBooking() {
            Log.e("NavigationClick", "I am here");
        }

        public void eConsultation() {
            Log.e("NavigationClick", "I am here");
        }

        public void buySubscription() {
            Log.e("NavigationClick", "I am here");
        }

        public void reminder() {
            Log.e("NavigationClick", "I am here");
        }

        public void wallet() {
            Log.e("NavigationClick", "I am here");
        }

        public void myOrders() {
            if (purchaseLists != null && !purchaseLists.isEmpty()) {
                startActivity(new Intent(mContext, OrderListActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                drawer.closeDrawer(Gravity.LEFT);
            } else {
                Toast.makeText(mContext, "Please Some Item", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(Gravity.LEFT);
            }
        }

        public void setting() {
            Log.e("NavigationClick", "I am here");
            startActivity(new Intent(mContext, SettingActivity.class));
            overridePendingTransition(R.anim.enter, R.anim.exit);
            drawer.closeDrawer(Gravity.LEFT);
        }

        public void helps() {
            Log.e("NavigationClick", "I am here");
        }

        public void aboutUs() {
            Log.e("NavigationClick", "I am here");
        }

        public void tabHome() {
            unselectAllMenu();
            binding.ivHomeTab.setImageResource(R.drawable.ic_home_selected);
            showFragment(homeFragment);
            textSizeIncrease.setTextSize(binding.tvHome.getId());
            binding.viewBarHome.setVisibility(View.VISIBLE);
        }

        public void tabHealth() {
            unselectAllMenu();
            binding.ivHealthTab.setImageResource(R.drawable.ic_healthcare_selected);
            healthCareFragment = new HealthCareFragment();
            fm.beginTransaction().add(R.id.fragment_view, healthCareFragment, "2").hide(healthCareFragment).commit();
            showFragment(healthCareFragment);
            textSizeIncrease.setTextSize(binding.tvHealthCare.getId());
            binding.viewBarHealth.setVisibility(View.VISIBLE);
        }

        public void tabNotification() {
            unselectAllMenu();
            binding.ivNotificationTab.setImageResource(R.drawable.ic_noti_selected);
            notificationFragment = new NotificationFragment();
            fm.beginTransaction().add(R.id.fragment_view, notificationFragment, "3").hide(notificationFragment).commit();
            showFragment(notificationFragment);
            textSizeIncrease.setTextSize(binding.tvNotification.getId());
            binding.viewBarNotification.setVisibility(View.VISIBLE);
        }

        public void tabAccount() {
            unselectAllMenu();
            binding.ivAccountTab.setImageResource(R.drawable.ic_account_selected);
            myAccountFragment = new MyAccountFragment();
            fm.beginTransaction().add(R.id.fragment_view, myAccountFragment, "4").hide(myAccountFragment).commit();
            showFragment(myAccountFragment);
            textSizeIncrease.setTextSize(binding.tvAccount.getId());
            binding.viewAccount.setVisibility(View.VISIBLE);
        }

    }

    private void unselectAllMenu() {
        binding.ivHomeTab.setImageResource(R.drawable.ic_home_unselected);
        binding.ivHealthTab.setImageResource(R.drawable.ic_healthcare_unselected);
        binding.ivNotificationTab.setImageResource(R.drawable.ic_noti_unselected);
        binding.ivAccountTab.setImageResource(R.drawable.ic_account_unselected);
        binding.viewBarHome.setVisibility(View.INVISIBLE);
        binding.viewBarHealth.setVisibility(View.INVISIBLE);
        binding.viewBarNotification.setVisibility(View.INVISIBLE);
        binding.viewAccount.setVisibility(View.INVISIBLE);
        tv.setTextSize(10);
    }

    @Override
    public void setTextSize(Integer id) {
        tv = (TextView) findViewById(id);
        tv.setTextSize(12);
    }

    public void rePlaceFragment(String pos) {
        if (pos.equals("1")) {
            unselectAllMenu();
            binding.ivHomeTab.setImageResource(R.drawable.ic_home_selected);
            showFragment(homeFragment);
            textSizeIncrease.setTextSize(binding.tvHome.getId());
            binding.viewBarHome.setVisibility(View.VISIBLE);
        } else if (pos.equals("2")) {
            unselectAllMenu();
            binding.ivNotificationTab.setImageResource(R.drawable.ic_noti_selected);
            showFragment(notificationFragment);
            textSizeIncrease.setTextSize(binding.tvNotification.getId());
            binding.viewBarNotification.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        unselectAllMenu();
        binding.ivHomeTab.setImageResource(R.drawable.ic_home_selected);
        binding.viewBarHome.setVisibility(View.VISIBLE);
        textSizeIncrease.setTextSize(binding.tvHome.getId());
        if (active instanceof HomeFragment || active instanceof HomeFragment) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "click BACK again to go Exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            showFragment(homeFragment);
        }
        drawer.closeDrawer(Gravity.LEFT);
    }

    private void addFragment(Fragment fragment, String tag) {
        fm.beginTransaction().add(R.id.fragment_view, fragment, tag).commit();
        active = fragment;
    }

    private void showFragment(Fragment fragment) {
        fm.beginTransaction().hide(active).show(fragment).commit();
        active = fragment;
    }

    private void getPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        try {
                            if (report.areAllPermissionsGranted()) {
                                autoCountrySelect();
                            }

                            if (report.isAnyPermissionPermanentlyDenied()) {

                            }

                            if (report.getGrantedPermissionResponses().get(0).getPermissionName().equals("android.permission.ACCESS_FINE_LOCATION")) {
                                autoCountrySelect();
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void autoCountrySelect() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        for (String provider : lm.getAllProviders()) {
            @SuppressWarnings("ResourceType")
            Location location = lm.getLastKnownLocation(provider);
            if (location != null) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0) {
                        city_name = addresses.get(0).getLocality();
                        pin_code = addresses.get(0).getPostalCode();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String country = addresses.get(0).getCountryName();
                        String state = addresses.get(0).getAdminArea();
                        String city = addresses.get(0).getLocality();
                        String postalCode = addresses.get(0).getPostalCode();
                        String address = addresses.get(0).getAddressLine(0);
                        String latitude = String.valueOf(location.getLatitude());
                        String longitude = String.valueOf(location.getLongitude());
                        sessionManager.saveLocation(city, postalCode, address, latitude, longitude);
                        Log.e("LocationMain", "City " + city_name);
                        Log.e("LocationMain", "Pin Code " + pin_code);
                        Log.e("LocationMain", "Address  " + address);
                        Log.e("LocationMain", "latitude " + String.valueOf(latitude));
                        Log.e("LocationMain", "longitude " + String.valueOf(longitude));
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // enableLocationSettings();
            }
        }

    }

}
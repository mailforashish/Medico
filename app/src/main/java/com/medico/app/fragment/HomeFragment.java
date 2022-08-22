package com.medico.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.medico.app.R;
import com.medico.app.activity.AllCategoryActivity;
import com.medico.app.activity.MainActivity;
import com.medico.app.adapter.HealthArticleAdapter;
import com.medico.app.adapter.BannerAdapter;
import com.medico.app.adapter.StateAdapter;
import com.medico.app.databinding.FragmentHomeBinding;
import com.medico.app.adapter.CategoryAdapter;
import com.medico.app.dialog.CheckPinCodeDialog;
import com.medico.app.interfaceClass.ShowFragment;
import com.medico.app.activity.SearchProductActivity;
import com.medico.app.response.Banner.BannerResponse;
import com.medico.app.response.Banner.BannerResult;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.Cart.CartResponse;
import com.medico.app.response.Cart.CartResult;
import com.medico.app.response.CategoryList;
import com.medico.app.response.HealthArticleList;
import com.medico.app.response.stateList.StateResponse;
import com.medico.app.response.stateList.StateResult;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.medico.app.utils.SessionManager.CITY;
import static com.medico.app.utils.SessionManager.PIN_CODE;


public class HomeFragment extends Fragment implements ShowFragment, ApiResponseInterface {
    FragmentHomeBinding binding;
    CategoryAdapter categoryAdapter;
    public static List<CategoryList> categoryLists = new ArrayList<>();
    HealthArticleAdapter healthArticleAdapter;
    LinearLayoutManager articleLayoutManager;
    List<HealthArticleList> healthArticleLists = new ArrayList<>();
    private List<String> statesListNew = new ArrayList<>();
    private List<StateResult> statesList = new ArrayList<>();
    private List<CartList> cartList;
    private StateAdapter stateAdapter;
    ApiManager apiManager;

    private List<BannerResult> bannerList = new ArrayList<>();
    SessionManager sessionManager;

    private Timer timer;
    private ImageView[] dots;
    private BannerAdapter bannerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setClickListener(new EventHandler(getContext()));

        sessionManager = new SessionManager(getContext());
        apiManager = new ApiManager(getContext(), this);
        //apiManager.getStateList();
        apiManager.getBannerList();
        apiManager.getCartList();

        binding.recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(), 4));
        categoryAdapter = new CategoryAdapter(getContext(), categoryLists, getActivity());
        binding.recyclerViewMain.setAdapter(categoryAdapter);

        articleLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvHealthArticles.setLayoutManager(articleLayoutManager);
        healthArticleAdapter = new HealthArticleAdapter(getActivity(), healthArticleLists);
        binding.rvHealthArticles.setAdapter(healthArticleAdapter);
        binding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (bannerList != null) {
            setBannerData();
        }
        setData();
    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void openDrawer() {
            MainActivity.drawer.openDrawer(Gravity.LEFT);
        }

        public void nextActivity() {
            startActivity(new Intent(mContext, SearchProductActivity.class));
        }

        public void openPinDialog() {
            CheckPinCodeDialog checkPinCodeDialog = new CheckPinCodeDialog(mContext, getActivity());
            checkPinCodeDialog.show();
            checkPinCodeDialog.setDialogResult(new CheckPinCodeDialog.OnMyDialogPinCode() {
                @Override
                public void finish(String pinCode, String city) {
                    binding.tvCurrentLocation.setText(pinCode + " " + city);
                }
            });

        }

        public void viewCategory() {
            startActivity(new Intent(mContext, AllCategoryActivity.class));
        }

        public void openNotification() {
            ((MainActivity) getActivity()).rePlaceFragment("2");
        }

    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.STATE_LIST) {
            StateResponse rsp = (StateResponse) response;
            /*try {
                statesList = rsp.getData();
                Log.e("StateData", "StateList " + statesList);
                for (int i = 0; i < statesList.size(); i++) {
                    statesListNew.add(statesList.get(i).getName());
                }
                stateAdapter = new StateAdapter(getContext(), R.layout.spinner_item_states, statesListNew);
                binding.spinner.setAdapter(stateAdapter);

            } catch (Exception e) {
            }*/
        }
        if (ServiceCode == Constant.BANNER_LIST) {
            BannerResponse rsp = (BannerResponse) response;
            try {
                bannerList = rsp.getData();
                bannerAdapter = new BannerAdapter(bannerList, getContext());
                binding.viewpagerLab.setAdapter(bannerAdapter);
                sessionManager.saveBannerLocal("banner", bannerList);
            } catch (Exception e) {

            }

        }
        if (ServiceCode == Constant.CART_LIST) {
            CartResponse rsp = (CartResponse) response;
            if (rsp != null) {
                cartList = rsp.getData().getCart();
                sessionManager.saveListInLocal("cart", cartList);
            }

        }
    }

    private void setBannerData() {
        try {
            bannerAdapter = new BannerAdapter(bannerList, getContext());
            binding.viewpagerLab.setAdapter(bannerAdapter);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    binding.viewpagerLab.post(new Runnable() {
                        @Override
                        public void run() {
                            if (bannerList != null && bannerList.size() > 1) {
                                binding.viewpagerLab.setCurrentItem((binding.viewpagerLab.getCurrentItem() + 1) % bannerList.size());
                            }

                        }
                    });
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 5000, 5000);
            //override createDots methods here
            createDots(0);
            binding.viewpagerLab.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    createDots(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

        } catch (Exception e) {
        }
    }

    private void createDots(int current_position) {
        if (binding.dotsLayoutLab != null)
            binding.dotsLayoutLab.removeAllViews();
        dots = new ImageView[bannerList.size()];

        for (int i = 0; i < bannerList.size(); i++) {
            dots[i] = new ImageView(getContext());
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_lab_dots));

            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.inactive_lab_dots));
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(6, 0, 8, 0);
            binding.dotsLayoutLab.addView(dots[i], layoutParams);
        }

    }

    public void setData() {
        CategoryList list1 = new CategoryList(R.drawable.covid, "Covid Essential", "3", "340");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.personal, "Personal Care", "3", "240");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.health_food, "Health food and Drinks", "3", "390");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.skin_care, "Skin Care", "2", "140");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.home_care, "Home Care", "3", "360");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.ayurvedic, "Ayurvedic Care", "6", "540");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.sexual_wellness, "Sexual Wellness", "3", "740");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.fitness, "Fitness & Supplements", "8", "840");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.mother_care, "Mother And Baby Care", "3", "540");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.health_device, "Health Care Device", "9", "244");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.heath_condition, "Health Condition", "3", "348");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.diabetic, "Diabetic Care ", "3", "213");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.elderly_care, "Elderly Care", "4", "321");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.accessories, "Accessories And Wearables", "2", "987");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.beauty, "Beauty", "3", "654");
        categoryLists.add(list1);
        list1 = new CategoryList(R.drawable.surgicals, "Surgical and Dressings", "7", "1365");
        categoryLists.add(list1);


        HealthArticleList list3 = new HealthArticleList("Simple steps to maintain a healthy heart for all ages", "12 Jun 2020");
        healthArticleLists.add(list3);
        list3 = new HealthArticleList("Superfoods you must incorporate in your family's daily diet", "11 Jun 2020");
        healthArticleLists.add(list3);
        list3 = new HealthArticleList("Simple steps to maintain a healthy heart for all ages", "12 Jun 2020");
        healthArticleLists.add(list3);
        list3 = new HealthArticleList("Superfoods you must incorporate in your family's daily diet", "11 Jun 2020");
        healthArticleLists.add(list3);


    }

    @Override
    public void onResume() {
        super.onResume();
        HashMap<String, String> location = sessionManager.getSaveLocation();
        if (location.isEmpty()) {
        } else {
            binding.tvCurrentLocation.setText(location.get(PIN_CODE) + " " + location.get(CITY));
        }
    }

    @Override
    public void getSelectedPosition(String position) {
        ((MainActivity) getActivity()).rePlaceFragment(position);
    }

}
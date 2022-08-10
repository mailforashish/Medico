package com.medico.app.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.medico.app.R;
import com.medico.app.adapter.BannerAdapter;
import com.medico.app.databinding.ActivityProductDetailsBinding;
import com.medico.app.adapter.ProductDescritionAdapter;
import com.medico.app.response.Addcart.AddCartResponse;
import com.medico.app.response.Addcart.RemoveCartResponse;
import com.medico.app.response.Banner.BannerResult;
import com.medico.app.response.Cartlist.CartResponse;
import com.medico.app.response.Cartlist.CartResult;
import com.medico.app.response.DescriptionList;
import com.medico.app.response.OffersList;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.MaxLimit;
import com.medico.app.utils.PaginationAdapterCallback;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.medico.app.utils.SessionManager.CITY;
import static com.medico.app.utils.SessionManager.PIN_CODE;

public class ProductDetailsActivity extends AppCompatActivity implements ApiResponseInterface, PaginationAdapterCallback {
    ActivityProductDetailsBinding binding;
    private Timer timer;
    private ImageView[] dots;
    SessionManager sessionManager;
    List<CartResult> cartList = new ArrayList<>();
    private List<BannerResult> bannerList = new ArrayList<>();
    private BannerAdapter bannerAdapter;
    //private ProductDetailAdapter productDetailAdapter;
    //List<ProductListResponse.Data> list = new ArrayList<>();
    //private OffersAdapter offersAdapter;
    //LinearLayoutManager linearOffer;
    ApiManager apiManager;
    private static final int PAGE_START = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private ProductDescritionAdapter productDescritionAdapter;
    List<DescriptionList> descriptionLists = new ArrayList<>();

    private boolean isUserScrolling = false;
    private boolean isListGoingUp = true;
    LinearLayoutManager linearLayoutManager;
    private List<OffersList> offersLists = new ArrayList<>();
    private String Drug_Id, Product_Name, Product_Type, Manufacture, Price, Discount;
    float priceAfterDiscount;
    String quantity = "";
    private int TotalCartItem;
    HideStatus hideStatus;
    MaxLimit maxLimit;
    List<String> description = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        maxLimit = new MaxLimit(0);

        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        apiManager.getProductList(String.valueOf(currentPage));
        apiManager.getCartList();
        //get Intent Data of Product details
        Drug_Id = getIntent().getStringExtra("drug_id");
        Product_Name = getIntent().getStringExtra("pd_Name");
        Product_Type = getIntent().getStringExtra("pd_Type");
        Manufacture = getIntent().getStringExtra("manufacture");
        Price = getIntent().getStringExtra("price");
        Discount = getIntent().getStringExtra("discount");
        float actualPrice = Float.parseFloat(Price);
        float totalDiscount = (actualPrice * Float.parseFloat(Discount)) / 100;
        priceAfterDiscount = actualPrice - totalDiscount;
        binding.tvDrugName.setText(Product_Name);
        binding.tvQuantityPack.setText(Product_Type);
        binding.tvManufacturer.setText(Manufacture);
        binding.tvPrice.setText(String.valueOf(String.format("%.2f", priceAfterDiscount)));
        binding.tvStrikePrice.setText("MRP " + Price);
        binding.tvStrikePrice.setPaintFlags(binding.tvStrikePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvDiscountPercent.setText(Discount + "% OFF");
        binding.rbItem.setRating((float) 3.5);
        binding.tvRatingsCount.setText("4.3");
        //collapse mode variable define here
        binding.textCollapseModeName.setText(Product_Name);
        binding.tvPriceTop.setText(String.valueOf(String.format("%.2f", priceAfterDiscount)));
        binding.tvStrikePriceTop.setText("MRP " + Price);
        binding.tvStrikePriceTop.setPaintFlags(binding.tvStrikePriceTop.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvDiscountPercentTop.setText(Discount + "% OFF");

        bannerList = sessionManager.getBannerFromLocal("banner");
        if (bannerList != null) {
            setBannerData();
        }

      /*binding.rvTogether.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        linearOffer = new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvOfferListVertical.setLayoutManager(linearOffer);
        offersAdapter = new OffersAdapter(this, offersLists, "PRODUCT");
        binding.rvOfferListVertical.setAdapter(offersAdapter);*/

        linearLayoutManager = new LinearLayoutManager(this);
        binding.rvSideEffect.setLayoutManager(linearLayoutManager);
        productDescritionAdapter = new ProductDescritionAdapter(ProductDetailsActivity.this, descriptionLists);
        binding.rvSideEffect.setAdapter(productDescritionAdapter);
        binding.rvSideEffect.setHasFixedSize(true);
        setData();

        binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Uses"));
        binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Contraindication"));
        binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Side effects"));
        binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Precautions and Warnings"));


        binding.tabDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isUserScrolling = false;
                int position = tab.getPosition();
                if (position == 0) {
                    binding.rvSideEffect.smoothScrollToPosition(0);
                } else if (position == 1) {
                    binding.rvSideEffect.smoothScrollToPosition(8);
                } else if (position == 2) {
                    binding.rvSideEffect.smoothScrollToPosition(6);
                } else if (position == 3) {
                    binding.rvSideEffect.smoothScrollToPosition(10);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.rvSideEffect.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isUserScrolling = true;
                    if (isListGoingUp) {
                        //my recycler view is actually inverted so I have to write this condition instead
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 == descriptionLists.size()) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isListGoingUp) {
                                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 == descriptionLists.size()) {
                                            // Toast.makeText(ProductDetailsActivity.this, "exeute something", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }, 50);
                            //waiting for 50ms because when scrolling down from top, the variable isListGoingUp is still true until the onScrolled method is executed
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (isUserScrolling) {
                    if (itemPosition == 0) { //  item position of uses
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(0);
                        tab.select();
                    } else if (itemPosition == 4) {//  item position of side effects
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(1);
                        tab.select();
                    } else if (itemPosition == 6) {//  item position of how it works
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(2);
                        tab.select();
                    } else if (itemPosition == 10) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(3);
                        tab.select();
                    }
                }
            }
        });

        binding.nsvBelowTabs.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                       // Log.e("VisibleTop", "appBar " + (binding.nsvBelowTabs.getBottom() - (200)));
                       // Log.e("VisibleTop", "nsvBelowTabs " + (binding.nsvBelowTabs.getHeight() + binding.nsvBelowTabs.getScrollY()));
                        if ((binding.nsvBelowTabs.getBottom() - (200)) <= (binding.nsvBelowTabs.getHeight() + binding.nsvBelowTabs.getScrollY())) {
                            //scroll view is at bottom
                            binding.collapse.setVisibility(View.VISIBLE);
                            binding.collapse.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                        } else {
                            binding.collapse.setVisibility(View.INVISIBLE);
                            binding.collapse.animate().translationY(-binding.collapse.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                            //scroll view is not at bottom
                        }
                    }
                });



        Animation animMove = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        animMove.setDuration(300);
        animMove.setRepeatMode(Animation.REVERSE);
        animMove.setRepeatCount(Animation.INFINITE);
        binding.ivOfferIconPercent.startAnimation(animMove);

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backMDDetail() {
            onBackPressed();
        }

        public void moveCartPage() {
            if (Integer.parseInt(binding.tvQuantity.getText().toString()) >= 1) {
                startActivity(new Intent(mContext, CartActivity.class));
            }
        }

        public void addCart() {
            binding.btMdAddCart.setVisibility(View.INVISIBLE);
            binding.llAddPlusMinus.setVisibility(View.VISIBLE);
            binding.clViewCart.setVisibility(View.VISIBLE);
            binding.btMdAddCartTop.setVisibility(View.INVISIBLE);
            binding.llAddPlusMinusTop.setVisibility(View.VISIBLE);
            binding.clViewCart.setVisibility(View.VISIBLE);
            quantity = "1";
            //add product in cart
            apiManager.addCart(Drug_Id, quantity);
        }

        public void itemPlus() {
            if (Integer.parseInt(binding.tvQuantity.getText().toString()) >= 1) {
                binding.ivMinus.setVisibility(View.VISIBLE);
                int a = Integer.parseInt(binding.tvQuantity.getText().toString());
                a++;
                binding.tvQuantity.setText(Integer.toString(maxLimit.limit(a)));
                binding.tvQuantityTop.setText(Integer.toString(maxLimit.limit(a)));
                //update product quantity
                if (a <= 10) {
                    apiManager.changeQuantity(Drug_Id, binding.tvQuantity.getText().toString());
                } else {
                    Toast.makeText(mContext, "limit exceeded", Toast.LENGTH_SHORT).show();

                }
            }

        }

        public void itemMinus() {
            if (Integer.parseInt(binding.tvQuantity.getText().toString()) <= 1) {
                binding.llAddPlusMinus.setVisibility(View.GONE);
                binding.llAddPlusMinusTop.setVisibility(View.GONE);
                binding.btMdAddCart.setVisibility(View.VISIBLE);
                binding.btMdAddCartTop.setVisibility(View.VISIBLE);
                //remove product with id
                apiManager.deleteCart(Drug_Id);
            } else {
                int a = Integer.parseInt(binding.tvQuantity.getText().toString());
                a--;
                binding.tvQuantity.setText(Integer.toString(a));
                binding.tvQuantityTop.setText(Integer.toString(a));
                apiManager.changeQuantity(Drug_Id, binding.tvQuantity.getText().toString());
            }
        }
    }

    @Override
    public void isError(String errorCode) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void isSuccess(Object response, int ServiceCode) {
        /*if (ServiceCode == Constant.PRODUCT_LIST) {
            ProductListResponse rsp = (ProductListResponse) response;
            list = rsp.getResult().getData();
            TOTAL_PAGES = rsp.getResult().getTo();
            if (list.size() > 0) {
                productDetailAdapter = new ProductDetailAdapter(this, ProductDetailsActivity.this, "Detail");
                binding.rvTogether.setAdapter(productDetailAdapter);
                // Set data in adapter
                productDetailAdapter.addAll(list);
                Log.e("productlistSize", String.valueOf(list.size()));
               *//* String data = list.get(0).getDescription();
                List<String> lines = Arrays.stream(data.split("\\##?\\*\\'"))
                        .map(x -> x.trim())
                        .filter(x -> x.length() > 0)
                        .collect(Collectors.toList());
                StringBuilder sb = new StringBuilder();
                String regex = "[-+^#<!@>$%&({*}?/.=~),'_:]*";
                for (String line : lines) {
                    Log.e("Sentence", " " + line);
                    line = line.replaceAll(regex, "")
                            .replaceAll("\\[", "")
                            .replaceAll("\\]", "");
                    sb.append("\n" + line);
                }
                binding.tvDiscription.setText(sb);*//*

                if (currentPage < TOTAL_PAGES) {
                    productDetailAdapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            }
        }*/
       /* if (ServiceCode == Constant.PRODUCT_LIST_NEXT_PAGE) {
            ProductListResponse rsp = (ProductListResponse) response;
            productDetailAdapter.removeLoadingFooter();
            isLoading = false;
            List<ProductListResponse.Data> results = rsp.getResult().getData();
            list.addAll(results);
            productDetailAdapter.addAll(results);
            if (currentPage != TOTAL_PAGES) productDetailAdapter.addLoadingFooter();
            else isLastPage = true;
        }*/
        if (ServiceCode == Constant.ADD_CART) {
            AddCartResponse rsp = (AddCartResponse) response;
            if (rsp != null) {
                TotalCartItem = rsp.getData();
                binding.tvCartNum.setText(String.valueOf(TotalCartItem));
                Log.e("CartFunction", "Total Item After Add " + TotalCartItem);
                binding.tvCartNum.setText(String.valueOf(TotalCartItem));
                binding.tvCartItem.setText(String.valueOf(TotalCartItem) + "Item" + "\n" + "in Cart");
            }
        }
        if (ServiceCode == Constant.CHANGE_QUANTITY) {
            Object rsp = (Object) response;
            Log.e("CartFunction", "Total Item After remove " + "");
        }
        if (ServiceCode == Constant.REMOVE_CART) {
            RemoveCartResponse rsp = (RemoveCartResponse) response;
            TotalCartItem = rsp.getData();
            binding.tvCartNum.setText(String.valueOf(TotalCartItem));
            binding.tvCartItem.setText(String.valueOf(TotalCartItem) + "Item" + "\n" + "in Cart");
            Log.e("CartFunction", "Total Item After remove " + TotalCartItem);
        }
        if (ServiceCode == Constant.CART_LIST) {
            CartResponse rsp = (CartResponse) response;
            if (rsp != null) {
                cartList = rsp.getData();
                if (cartList != null) {
                    binding.tvCartNum.setText(String.valueOf(cartList.size()));
                    if (cartList.size() >= 1) {
                        binding.clViewCart.setVisibility(View.VISIBLE);
                        binding.tvCartItem.setText(String.valueOf(cartList.size() + " Item" + "\n" + "in Cart"));
                    } else {
                        binding.clViewCart.setVisibility(View.GONE);
                    }
                    Log.e("cartData", "" + new Gson().toJson(cartList));
                    for (int i = 0; i < cartList.size(); i++) {
                        if (cartList.get(i).getProductId().getDrugId().equals(Drug_Id)) {
                            binding.btMdAddCart.setVisibility(View.GONE);
                            binding.llAddPlusMinus.setVisibility(View.VISIBLE);
                            binding.tvQuantity.setText(Integer.toString(cartList.get(i).getQuantity()));
                            binding.btMdAddCartTop.setVisibility(View.GONE);
                            binding.llAddPlusMinusTop.setVisibility(View.VISIBLE);
                            binding.tvQuantityTop.setText(Integer.toString(cartList.get(i).getQuantity()));
                        }
                    }
                }
            }

        }
    }

    @Override
    public void retryPageLoad() {
    }

    private void setBannerData() {
        bannerAdapter = new BannerAdapter(bannerList, this);
        binding.bannerPager.setAdapter(bannerAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                binding.bannerPager.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.bannerPager.setCurrentItem((binding.bannerPager.getCurrentItem() + 1) % bannerList.size());
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 5000, 5000);
        //override createDots methods here
        createDots(0);
        binding.bannerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, String> location = sessionManager.getSaveLocation();
        if (location.isEmpty()) {
        } else {
            binding.rowLocation.tvCity.setText(location.get(PIN_CODE) + " " + location.get(CITY));
        }
    }

    private void createDots(int current_position) {
        try {
            if (binding.dotsLayoutDetails != null)
                binding.dotsLayoutDetails.removeAllViews();
            dots = new ImageView[bannerList.size()];

            for (int i = 0; i < bannerList.size(); i++) {
                dots[i] = new ImageView(this);
                if (i == current_position) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_lab_dots));

                } else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_lab_dots));
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(6, 0, 8, 0);
                binding.dotsLayoutDetails.addView(dots[i], layoutParams);
            }

        } catch (Exception e) {

        }

    }

    private void setData() {

        DescriptionList list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Uses", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Contraindications", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Side effects", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);

        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);

        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);

        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Precautiona and Warning", "For prevention of heart attack, clot-related stroke heart condition like stable");
        descriptionLists.add(list2);


       /* OffersList list3 = new OffersList("Flat 30% off+up to Rs.5000 cashback", "Code:MPAT500");
        offersLists.add(list3);
        list3 = new OffersList("Flat 25% off", "Code:YOURLAB12");
        offersLists.add(list3);
        list3 = new OffersList("Get Flat rs.200 cashback on Medico app", "Code:LAB7898");
        offersLists.add(list3);*/


    }

}


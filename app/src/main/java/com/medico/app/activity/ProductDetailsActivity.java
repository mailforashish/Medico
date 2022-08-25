package com.medico.app.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.medico.app.R;
import com.medico.app.adapter.BannerAdapter;
import com.medico.app.adapter.ImageAdapter;
import com.medico.app.databinding.ActivityProductDetailsBinding;
import com.medico.app.adapter.ProductDescritionAdapter;
import com.medico.app.response.Addcart.AddCartResponse;
import com.medico.app.response.Addcart.RemoveCartResponse;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.Cart.CartResponse;
import com.medico.app.response.DescriptionList;
import com.medico.app.response.ProductDetail.ProductDetailResponse;
import com.medico.app.response.ProductDetail.ProductImages;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.MaxLimit;
import com.medico.app.utils.ReadMoreTextView;
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
    List<CartList> cartList = new ArrayList<>();
    private List<ProductImages> imageList = new ArrayList<>();
    private ImageAdapter bannerAdapter;
    ApiManager apiManager;
    private ProductDescritionAdapter productDescritionAdapter;
    List<DescriptionList> descriptionLists = new ArrayList<>();
    private boolean isUserScrolling = false;
    private boolean isListGoingUp = true;
    LinearLayoutManager linearLayoutManager;
    private String Drug_Id, Product_Name, Product_Type, Manufacture, Price, Discount;
    float priceAfterDiscount;
    String quantity = "";
    private int TotalCartItem;
    HideStatus hideStatus;
    MaxLimit maxLimit;
    public boolean isImageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        maxLimit = new MaxLimit(0);
        //get Intent Data of Product details
        Drug_Id = getIntent().getStringExtra("drug_id");
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        apiManager.getProduct(Drug_Id);
        apiManager.getCartList();

        linearLayoutManager = new LinearLayoutManager(this);
        binding.rvSideEffect.setLayoutManager(linearLayoutManager);
        productDescritionAdapter = new ProductDescritionAdapter(ProductDetailsActivity.this, descriptionLists);
        binding.rvSideEffect.setAdapter(productDescritionAdapter);
        binding.rvSideEffect.setHasFixedSize(true);

        binding.tabDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isUserScrolling = false;
                int position = tab.getPosition();
                if (position == 0) {
                    binding.rvSideEffect.smoothScrollToPosition(0);
                } else if (position == 1) {
                    binding.rvSideEffect.smoothScrollToPosition(1);
                } else if (position == 2) {
                    binding.rvSideEffect.smoothScrollToPosition(2);
                } else if (position == 3) {
                    binding.rvSideEffect.smoothScrollToPosition(3);
                } else if (position == 4) {
                    binding.rvSideEffect.smoothScrollToPosition(4);
                } else if (position == 5) {
                    binding.rvSideEffect.smoothScrollToPosition(5);
                } else if (position == 6) {
                    binding.rvSideEffect.smoothScrollToPosition(6);
                } else if (position == 7) {
                    binding.rvSideEffect.smoothScrollToPosition(7);
                } else if (position == 8) {
                    binding.rvSideEffect.smoothScrollToPosition(8);
                } else if (position == 9) {
                    binding.rvSideEffect.smoothScrollToPosition(9);
                } else if (position == 10) {
                    binding.rvSideEffect.smoothScrollToPosition(10);
                } else if (position == 11) {
                    binding.rvSideEffect.smoothScrollToPosition(11);
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
                            }, 40);
                            //waiting for 40ms because when scrolling down from top, the variable isListGoingUp is still true until the onScrolled method is executed
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
                    } else if (itemPosition == 1) {//  item position of side effects
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(1);
                        tab.select();
                    } else if (itemPosition == 2) {//  item position of how it works
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(2);
                        tab.select();
                    } else if (itemPosition == 3) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(3);
                        tab.select();
                    } else if (itemPosition == 4) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(4);
                        tab.select();
                    } else if (itemPosition == 5) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(5);
                        tab.select();
                    } else if (itemPosition == 6) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(6);
                        tab.select();
                    } else if (itemPosition == 7) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(7);
                        tab.select();
                    } else if (itemPosition == 8) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(8);
                        tab.select();
                    } else if (itemPosition == 9) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(9);
                        tab.select();
                    } else if (itemPosition == 10) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(10);
                        tab.select();
                    } else if (itemPosition == 11) {//  item position of precaution
                        TabLayout.Tab tab = binding.tabDetail.getTabAt(11);
                        tab.select();
                    }
                }
            }
        });
        Animation animMove = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        animMove.setDuration(300);
        animMove.setRepeatMode(Animation.REVERSE);
        animMove.setRepeatCount(Animation.INFINITE);
        binding.ivOfferIconPercent.startAnimation(animMove);
        //for show collapsing layout code here
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    binding.collapse.setVisibility(View.VISIBLE);
                    binding.collapse.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                } else if (verticalOffset == 0) {
                    binding.collapse.setVisibility(View.INVISIBLE);
                    binding.collapse.animate().translationY(-binding.collapse.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                } else {
                    binding.collapse.setVisibility(View.INVISIBLE);
                    binding.collapse.animate().translationY(-binding.collapse.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                }
            }
        });


    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backMDDetail() {
            onBackPressed();
            finish();
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

        public void saveLater() {
            if (isImageChanged) {
                binding.ivRxHeart.setImageResource(R.drawable.ic_heart);
                isImageChanged = false;
            } else {
                binding.ivRxHeart.setImageResource(R.drawable.ic_save_heart);
                isImageChanged = true;
                apiManager.saveForLater(Drug_Id);
            }

        }

    }

    @Override
    public void isError(String errorCode) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.PRODUCT_DETAIL) {
            ProductDetailResponse rsp = (ProductDetailResponse) response;
            if (rsp != null) {
                //show image slider for Product detail page
                imageList = rsp.getData().getImages();
                if (imageList != null) {
                    setBannerData();
                }
                Product_Name = rsp.getData().getDrugName();
                Product_Type = rsp.getData().getPackingType();
                Manufacture = rsp.getData().getManufactur();
                Price = String.valueOf(rsp.getData().getUnitPrice());
                Discount = String.valueOf(rsp.getData().getDiscount());
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
                //show more and view less
                binding.tvMdDescInput.setText(rsp.getData().getDescription());
                new ReadMoreTextView(binding.tvMdDescInput, 3, "View More", true);

                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Uses"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Side effect"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Driving"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Kidney"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Liver"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Pregnancy"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Breast"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Alcohol"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("Benefits"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("HowToUse"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("HowWorks"));
                binding.tabDetail.addTab(binding.tabDetail.newTab().setText("QuickTips"));
                setData(rsp);
            }
        }
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
            TotalCartItem = rsp.getData().getCart().size();
            binding.tvCartNum.setText(String.valueOf(TotalCartItem));
            binding.tvCartItem.setText(String.valueOf(TotalCartItem) + "Item" + "\n" + "in Cart");
            Log.e("CartFunction", "Total Item After remove " + TotalCartItem);
        }
        if (ServiceCode == Constant.CART_LIST) {
            CartResponse rsp = (CartResponse) response;
            if (rsp != null) {
                cartList = rsp.getData().getCart();
                if (cartList != null) {
                    binding.tvCartNum.setText(String.valueOf(cartList.size()));
                    if (cartList.size() >= 1) {
                        binding.clViewCart.setVisibility(View.VISIBLE);
                        binding.tvCartItem.setText(String.valueOf(cartList.size() + " Item" + "\n" + "in Cart"));
                    } else {
                        binding.clViewCart.setVisibility(View.GONE);
                    }
                    //Log.e("cartData", "" + new Gson().toJson(cartList));
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
        if (ServiceCode == Constant.SAVE_LATER) {
            CartResponse rsp = (CartResponse) response;
            if (rsp != null) {

            }
        }
    }

    @Override
    public void retryPageLoad() {
    }

    private void setBannerData() {
        bannerAdapter = new ImageAdapter(imageList, this);
        binding.bannerPager.setAdapter(bannerAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                binding.bannerPager.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.bannerPager.setCurrentItem((binding.bannerPager.getCurrentItem() + 1) % imageList.size());
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
            dots = new ImageView[imageList.size()];

            for (int i = 0; i < imageList.size(); i++) {
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

    private void setData(ProductDetailResponse rsp) {
        DescriptionList list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Uses", rsp.getData().getUses().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Side effect", rsp.getData().getSideEffect().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Driving", rsp.getData().getDriving().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Kidney", rsp.getData().getKidney().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Liver", rsp.getData().getLiver().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Pregnancy", rsp.getData().getPregnancy().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Breast", rsp.getData().getBreastFeeding().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Alcohol", rsp.getData().getAlcohol().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Benefits", rsp.getData().getBenefits().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "How to Use", rsp.getData().getHowToUse().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "How to Work", rsp.getData().getHowWorks().trim());
        descriptionLists.add(list2);
        list2 = new DescriptionList(R.drawable.ic_order_mediciens, "Quick Tips", rsp.getData().getQuickTips().trim());
        descriptionLists.add(list2);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


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

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.medico.app.R;
import com.medico.app.adapter.BannerAdapter;
import com.medico.app.databinding.ActivityProductDetailsBinding;
import com.medico.app.adapter.ProductDescritionAdapter;
import com.medico.app.response.Addcart.AddCartResponse;
import com.medico.app.response.Addcart.RemoveCartResponse;
import com.medico.app.response.Banner.BannerResult;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.Cart.CartResponse;
import com.medico.app.response.DescriptionList;
import com.medico.app.response.ProductDetail.ProductDetailResponse;
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
    private List<BannerResult> bannerList = new ArrayList<>();
    private BannerAdapter bannerAdapter;
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
        bannerList = sessionManager.getBannerFromLocal("banner");
        if (bannerList != null) {
            setBannerData();
        }

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
                //
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


    /*public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);

                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(addClickablePartTextViewResizable(tv.getText().toString(), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });
    }



    private static SpannableStringBuilder addClickablePartTextViewResizable(final String str , final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        if (str.contains(spanableText)) {
            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        makeTextViewResizable(tv, 3, "View More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }*/

}


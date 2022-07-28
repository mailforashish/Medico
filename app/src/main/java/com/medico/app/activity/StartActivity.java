package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.medico.app.R;
import com.medico.app.adapter.StartPageAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {
    private ViewPager myPager;
    private int[] layouts = {R.layout.your_specialist, R.layout.search_pharmacy, R.layout.get_connected};
    private StartPageAdapter startPageAdapter;
    private Timer timer;
    private LinearLayout dots_layout;
    private ImageView[] dots;
    private Button btn_started;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        myPager = findViewById(R.id.viewpager);
        startPageAdapter = new StartPageAdapter(layouts, this);
        dots_layout = findViewById(R.id.dots_layout);

        myPager.setAdapter(startPageAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                myPager.post(new Runnable() {

                    @Override
                    public void run() {
                        myPager.setCurrentItem((myPager.getCurrentItem() + 1) % layouts.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 5000, 5000);



        //override createDots methods here
        createDots(0);
        btn_started = findViewById(R.id.btn_started);
        btn_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MobileNumberActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });


        myPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void createDots(int current_position) {

        if (dots_layout != null)
            dots_layout.removeAllViews();
        dots = new ImageView[layouts.length];

        for (int i = 0; i < layouts.length; i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));

            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dots));
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(6, 0, 8, 0);
            dots_layout.addView(dots[i], layoutParams);
        }


    }




}

package com.medico.app.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.medico.app.R;
import com.medico.app.adapter.NotificationMenuAdapter;

public class NotificationFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager tabViewpager;
    private ImageView iv_nt_offer;
    private NotificationMenuAdapter notificationMenuAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        tabViewpager = view.findViewById(R.id.tab_Viewpager);
        iv_nt_offer = view.findViewById(R.id.iv_nt_offer);
        notificationMenuAdapter = new NotificationMenuAdapter(getChildFragmentManager(), getContext());
        tabViewpager.setAdapter(notificationMenuAdapter);
        tabLayout.setupWithViewPager(tabViewpager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(notificationMenuAdapter.getTabView(i));

        }
        notificationMenuAdapter.setOnSelectView(tabLayout, 0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                notificationMenuAdapter.setOnSelectView(tabLayout, position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                notificationMenuAdapter.setUnSelectView(tabLayout, position);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Animation animMove = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        animMove.setDuration(300);
        animMove.setRepeatMode(Animation.REVERSE);
        animMove.setRepeatCount(Animation.INFINITE);
        iv_nt_offer.startAnimation(animMove);

        return view;
    }






}



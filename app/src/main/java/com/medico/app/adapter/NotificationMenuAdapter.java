package com.medico.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.medico.app.R;
import com.medico.app.fragment.AllUpdateFragment;
import com.medico.app.fragment.OrderUpdateFragment;

import java.util.HashMap;
import java.util.Map;


public class NotificationMenuAdapter extends FragmentStatePagerAdapter {
    private final Map<Integer, Fragment> fragmentMap;
    private final String[] tabTitles = new String[]{"All Updates", "Order Updates"};
    private final int[] imageResId = {R.drawable.under_un_selected, R.drawable.under_un_selected};
    private final int[] selectedImageResId = {R.drawable.under_selected, R.drawable.under_selected};
    Context context;

    //Constructor to the class
    public NotificationMenuAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        fragmentMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                AllUpdateFragment fragment1 = new AllUpdateFragment();
                fragmentMap.put(0, fragment1);
                return fragment1;

            case 1:
                OrderUpdateFragment fragment2 = new OrderUpdateFragment();
                fragmentMap.put(1, fragment2);
                return fragment2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    public Fragment getFragment(int pos) {
        if (fragmentMap == null) return null;
        return fragmentMap.get(pos);
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = v.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        ImageView img = v.findViewById(R.id.imgView);
        img.setImageResource(imageResId[position]);
        return v;
    }

    // I add two func here.
    public void setOnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View view = tab.getCustomView();
        TextView textView = view.findViewById(R.id.textView);
        ImageView img = view.findViewById(R.id.imgView);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        textView.setTextSize(18f);
        img.setImageResource(selectedImageResId[position]);
    }

    public void setUnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View view = tab.getCustomView();
        TextView textView = view.findViewById(R.id.textView);
        ImageView img = view.findViewById(R.id.imgView);
        textView.setTextColor(context.getResources().getColor(R.color.grey500));
        textView.setTextSize(14f);
        img.setImageResource(imageResId[position]);
    }
}

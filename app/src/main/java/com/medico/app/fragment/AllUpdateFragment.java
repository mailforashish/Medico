package com.medico.app.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.medico.app.R;
import com.medico.app.adapter.AllUpdateAdapter;
import com.medico.app.databinding.FragmentAllUpdateBinding;
import com.medico.app.response.TodayUpdateList;

import java.util.ArrayList;
import java.util.List;

public class AllUpdateFragment extends Fragment {
    FragmentAllUpdateBinding binding;
    private AllUpdateAdapter allUpdateAdapter;
    List<TodayUpdateList> todayUpdateLists = new ArrayList<>();

    public AllUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_update, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setClickListener(new EventHandler(getContext()));


        binding.rvTodayUpdate.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        allUpdateAdapter = new AllUpdateAdapter(getContext(), todayUpdateLists);
        binding.rvTodayUpdate.setAdapter(allUpdateAdapter);
        setData();

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }
    }

    public void setData() {
        TodayUpdateList list0 = new TodayUpdateList("Couldn't find what are you \n were looking for", "12:05PM", "Let us help you! Call us now to complete your" +
                "booking or choose from 500+ lab tests & health" +
                "packages via PharmEasy App & stay on top of your" +
                "health.", 0);
        todayUpdateLists.add(list0);
        list0 = new TodayUpdateList("Couldn't find what are you \n were looking for", "10:05AM", "Let us help you! Call us now to complete your" +
                "booking or choose from 500+ lab tests & health" +
                "packages via PharmEasy App & stay on top of your" +
                "health.", 0);
        todayUpdateLists.add(list0);
        list0 = new TodayUpdateList("Couldn't find what are you \n were looking for", "8:51AM", "Discount milega, dhakke nahi", R.drawable.banner_4);
        todayUpdateLists.add(list0);

        list0 = new TodayUpdateList("Couldn't find what are you \n were looking for", "10:05AM", "Let us help you! Call us now to complete your" +
                "booking or choose from 500+ lab tests & health" +
                "packages via PharmEasy App & stay on top of your" +
                "health.", 0);
        todayUpdateLists.add(list0);

        list0 = new TodayUpdateList("Couldn't find what are you \n were looking for", "10:05AM", "Let us help you! Call us now to complete your" +
                "booking or choose from 500+ lab tests & health" +
                "packages via PharmEasy App & stay on top of your" +
                "health.", 0);
        todayUpdateLists.add(list0);
    }


}
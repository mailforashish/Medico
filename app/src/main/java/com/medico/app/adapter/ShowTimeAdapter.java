package com.medico.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.medico.app.R;
import com.medico.app.response.TimeModel;

import java.util.HashMap;
import java.util.List;

public class ShowTimeAdapter extends BaseAdapter {

    private List<TimeModel> listData;
    private LayoutInflater layoutInflater;
    private Context mContext;
    public HashMap<Integer, Boolean> hashMapSelected;

    public ShowTimeAdapter(Context mContext, List<TimeModel> listData) {
        this.mContext = mContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(mContext);

        hashMapSelected = new HashMap<>();
        for (int i = 0; i < listData.size(); i++) {
            hashMapSelected.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void makeAllUnselect(int position) {
        hashMapSelected.put(position, true);
        for (int i = 0; i < hashMapSelected.size(); i++) {
            if (i != position)
                hashMapSelected.put(i, false);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.time_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        TimeModel country = this.listData.get(position);
        holder.bt_time.setText(country.getTime());
        holder.bt_time.setBackground(mContext.getDrawable(R.drawable.ic_unselected_bg));
        holder.bt_time.setTextColor(ContextCompat.getColor(mContext, R.color.black_new));

        if (hashMapSelected.get(position) == true) {
            holder.bt_time.setBackground(mContext.getDrawable(R.drawable.ic_selected_bg));
            holder.bt_time.setTextColor(ContextCompat.getColor(mContext, R.color.white));

        } else {
            holder.bt_time.setBackground(mContext.getDrawable(R.drawable.ic_unselected_bg));
            holder.bt_time.setTextColor(ContextCompat.getColor(mContext, R.color.black_new));
        }

        return convertView;
    }


    private class ViewHolder {
        TextView bt_time;

        public ViewHolder(View view) {
            bt_time = view.findViewById(R.id.bt_time);
        }
    }

}
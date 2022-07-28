package com.medico.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.medico.app.R;
import com.medico.app.response.Banner.BannerResponse;
import com.medico.app.response.Banner.BannerResult;

import java.util.List;
import java.util.Objects;


public class BannerAdapter extends PagerAdapter {
    private List<BannerResult> imageList;
    private LayoutInflater layoutInflater;
    private Context context;

    public BannerAdapter(List<BannerResult> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.item_lab, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);
        Glide.with(context)
                .load(imageList.get(position).getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.banner_1).
                        error(R.drawable.banner_1)).into(imageView);
        Objects.requireNonNull(container).addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View)object;
        container.removeView(view);
    }
}

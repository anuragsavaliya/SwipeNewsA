package com.example.newsapppublic1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.ItemNewsadapterBinding;

public class NewsAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 5;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ItemNewsadapterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.item_newsadapter, container, false);

        container.addView(binding.getRoot());
        return binding.getRoot();

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}

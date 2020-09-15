package com.example.newsapppublic1.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.newsapppublic1.Model.PictureFacer;
import com.example.newsapppublic1.Model.Videofacer;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.ItemMediaadapterBinding;

import java.util.ArrayList;

public class MediaPagerAdapter extends PagerAdapter {


    private int tabCount;
    private ArrayList<PictureFacer> listImages;
    private ArrayList<Videofacer> listVideos;

    public MediaPagerAdapter(int tabCount, ArrayList<PictureFacer> listImages, ArrayList<Videofacer> listVideos) {
        this.tabCount = tabCount;
        this.listImages = listImages;
        this.listVideos = listVideos;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Log.d("tt", "listvideosize " + listVideos.size());
        Log.d("tt", "listvideosizeimg " + listImages.size());

        ItemMediaadapterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.item_mediaadapter, container, false);
        Log.d("yyyyy", "instantiateItem: " + position);


        ImagesAdapter imagesAdapter = new ImagesAdapter(listImages);
        binding.rvImages.setAdapter(imagesAdapter);
      /*
        if (position == 0) {
            VideoAdapter imagesAdapter1 = new VideoAdapter(listVideos);
            binding.rvImages.setAdapter(imagesAdapter1);
            Log.d("yyyyy", "instantiateItem: video" + position);
        } else {

            ImagesAdapter imagesAdapter = new ImagesAdapter(listImages);
            binding.rvImages.setAdapter(imagesAdapter);
            Log.d("yyyyy", "instantiateItem: images" + position);
        }*/

        container.addView(binding.getRoot());
        return binding.getRoot();

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

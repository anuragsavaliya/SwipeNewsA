package com.example.newsapppublic1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newsapppublic1.Activites.SettingsActivity;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    FragmentProfileBinding binding;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        binding.imgPost.setOnClickListener(this);
        binding.imgSave.setOnClickListener(this);
        binding.imgTag.setOnClickListener(this);
        binding.imgVideo.setOnClickListener(this);

        binding.imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SettingsActivity.class));
            }
        });
        return binding.getRoot();
    }

    private void seticontint(ImageView img, Boolean b) {
        if (b)
            img.setColorFilter(ContextCompat.getColor(context, R.color.colorpink), android.graphics.PorterDuff.Mode.SRC_IN);

        else {
            binding.imgPost.setColorFilter(ContextCompat.getColor(context, R.color.blacklight), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.imgSave.setColorFilter(ContextCompat.getColor(context, R.color.blacklight), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.imgVideo.setColorFilter(ContextCompat.getColor(context, R.color.blacklight), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.imgTag.setColorFilter(ContextCompat.getColor(context, R.color.blacklight), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onClick(View view) {
        seticontint(null, false);
        switch (view.getId()) {
            case R.id.img_post:
                seticontint(binding.imgPost, true);
                break;
            case R.id.img_save:
                seticontint(binding.imgSave, true);
                break;
            case R.id.img_tag:
                seticontint(binding.imgTag, true);
                break;
            case R.id.img_video:
                seticontint(binding.imgVideo, true);
                break;


        }
    }
}
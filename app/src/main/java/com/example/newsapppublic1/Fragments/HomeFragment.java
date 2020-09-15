package com.example.newsapppublic1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newsapppublic1.Adapters.NewsAdapter;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        NewsAdapter newsAdapter = new NewsAdapter();
        binding.viewPager.setAdapter(newsAdapter);


        return binding.getRoot();
    }
}
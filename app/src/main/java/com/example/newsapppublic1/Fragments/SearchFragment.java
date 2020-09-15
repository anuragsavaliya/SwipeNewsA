package com.example.newsapppublic1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newsapppublic1.Activites.SearchActivity;
import com.example.newsapppublic1.Adapters.HeshtagsAdapter;
import com.example.newsapppublic1.Adapters.TrandingPostAdapter;
import com.example.newsapppublic1.Adapters.UsersAdapter;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        HeshtagsAdapter heshtagsAdapter = new HeshtagsAdapter();
        binding.rvHeshtags.setAdapter(heshtagsAdapter);
        UsersAdapter usersAdapter = new UsersAdapter();
        binding.rvUsers.setAdapter(usersAdapter);
        TrandingPostAdapter trandingPostAdapter = new TrandingPostAdapter();
        binding.rvTrandingpost.setAdapter(trandingPostAdapter);

        binding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(container.getContext(), SearchActivity.class));
            }
        });
        return binding.getRoot();
    }
}
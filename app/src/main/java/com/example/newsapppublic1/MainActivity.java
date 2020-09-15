package com.example.newsapppublic1;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newsapppublic1.Fragments.CamaraFragment;
import com.example.newsapppublic1.Fragments.HomeFragment;
import com.example.newsapppublic1.Fragments.NotificationFragment;
import com.example.newsapppublic1.Fragments.ProfileFragment;
import com.example.newsapppublic1.Fragments.SearchFragment;
import com.example.newsapppublic1.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        init();
        navItemeControl();
        loadFragment(new HomeFragment());

    }

    private void navItemeControl() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        binding.line1.setVisibility(View.VISIBLE);
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.action_add:
                        fragment = new CamaraFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.action_notfication:
                        fragment = new NotificationFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        break;
                }

                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
    }
}
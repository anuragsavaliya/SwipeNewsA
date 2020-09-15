package com.example.newsapppublic1.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.newsapppublic1.FragmentsSearches.AllSearchFragment;
import com.example.newsapppublic1.FragmentsSearches.HeshtagSearchFragment;
import com.example.newsapppublic1.FragmentsSearches.PostSearchFragment;
import com.example.newsapppublic1.FragmentsSearches.UserSearchFragment;

public class SearchViewPagerAdapter extends FragmentStatePagerAdapter {


    public SearchViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllSearchFragment tab1 = new AllSearchFragment();
                return tab1;

            case 1:
                PostSearchFragment tab3 = new PostSearchFragment();
                return tab3;


            case 2:
                UserSearchFragment tab2 = new UserSearchFragment();
                return tab2;


            case 3:
                HeshtagSearchFragment tab4 = new HeshtagSearchFragment();
                return tab4;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

package com.app.bet.HomeScreen.Fixtures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new AllFragment();
        switch (position){
            case 0:fragment = new AllFragment();break;
            case 1:fragment = new InternationalFragment();break;
            case 2:fragment = new ODIFragment();break;
            case 3:fragment = new T20Fragment();break;
            case 4:fragment = new LeagueFragment();break;
            case 5:fragment = new WomenFragment();break;
            case 6:fragment = new TestFragment();break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "Tabs";
        switch (position){
            case 0:title = "All";break;
            case 1:title = "International";break;
            case 2:title = "ODI";break;
            case 3:title = "T20";break;
            case 4:title = "League";break;
            case 5:title = "Women";break;
            case 6:title = "Test";break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 7;
    }
}

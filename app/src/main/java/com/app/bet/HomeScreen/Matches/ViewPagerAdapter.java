package com.app.bet.HomeScreen.Matches;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.bet.HomeScreen.Matches.LiveMatch.LiveFragment;
import com.app.bet.HomeScreen.Matches.ResultMatch.ResultFragment;
import com.app.bet.HomeScreen.Matches.UpcomingMatch.UpcomingFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MatchData> listInPlay;
    private ArrayList<MatchData> listUpcoming;
    private ArrayList<Integer> pos;
    private ArrayList<Integer> posu;
    private ArrayList<String> matchtype;

    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<MatchData> listInPlay ,ArrayList<Integer> pos , ArrayList<String> matchtype , ArrayList<MatchData> listUpcoming , ArrayList<Integer> posu) {
        super(fm);
        this.listInPlay = listInPlay;
        this.pos = pos;
        this.matchtype = matchtype;
        this.listUpcoming = listUpcoming;
        this.posu = posu;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new LiveFragment(listInPlay,pos,matchtype);
        switch (position){
            case 0:fragment = new LiveFragment(listInPlay,pos,matchtype);break;
            case 1:fragment = new UpcomingFragment(listUpcoming,posu);break;
            case 2:fragment = new ResultFragment();break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "Tabs";
        switch (position){
            case 0:title = "Live";break;
            case 1:title = "Upcoming";break;
            case 2:title = "Result";break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

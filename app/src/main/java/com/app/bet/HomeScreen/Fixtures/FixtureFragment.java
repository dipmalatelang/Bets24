package com.app.bet.HomeScreen.Fixtures;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bet.R;
import com.google.android.material.tabs.TabLayout;

public class FixtureFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fixture, container, false);

        TabLayout TLFixtures = view.findViewById(R.id.TLFixtures);
        ViewPager VPFixtures = view.findViewById(R.id.VPFixtures);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        VPFixtures.setAdapter(viewPagerAdapter);
        TLFixtures.setupWithViewPager(VPFixtures);

        return view;
    }

}

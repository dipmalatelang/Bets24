package com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.KhaiLagaiMatches.KhaiLagaiFragment;
import com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.SessionMatches.PlaySessionFragment;
import com.app.bet.R;
import com.google.android.material.tabs.TabLayout;

public class SaveMatches extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_matches);

        tablayout= findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new KhaiLagaiFragment(), "Khai Lagai");
        adapter.addFragment(new PlaySessionFragment(),"Play session");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

}

package com.app.bet.HomeScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.app.bet.HomeScreen.Favourites.FavouritesFragment;
import com.app.bet.HomeScreen.Fixtures.FixtureFragment;
import com.app.bet.HomeScreen.Home.HomeFragment;
import com.app.bet.HomeScreen.Matches.MatchesFragment;
import com.app.bet.HomeScreen.More.MoreFragment;
import com.app.bet.HomeScreen.News.NewsFragment;
import com.app.bet.HomeScreen.Settings.SettingFragment;
import com.app.bet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeScreenActivity extends AppCompatActivity {
    DrawerLayout drawer_layout;
    NavigationView navigation_view;
    BottomNavigationView BN_navigation;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BN_navigation = findViewById(R.id.BN_navigation);
        navigation_view = findViewById(R.id.navigation_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BN_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framLayout, new HomeFragment());
        fragmentTransaction.commit();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        setupDrawerContent(navigation_view);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    fragmentTransaction.replace(R.id.framLayout, new HomeFragment());
                    toolbar.setTitle("Home");
                    break;

                case R.id.menu_matches:
                    fragmentTransaction.replace(R.id.framLayout, new MatchesFragment());
                    toolbar.setTitle("Matches");
                    break;

                case R.id.menu_more:
                    fragmentTransaction.replace(R.id.framLayout, new MoreFragment());
                    toolbar.setTitle("More");
                    break;
            }
            fragmentTransaction.commit();
            return true;
        }
    };

    private void setupDrawerContent(NavigationView navigation_view) {
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.nav_fixture:
                        fragmentTransaction.replace(R.id.framLayout, new FixtureFragment());
                        break;
                    case R.id.nav_favourites:
                        fragmentTransaction.replace(R.id.framLayout, new FavouritesFragment());
                        break;
                    case R.id.nav_news:
                        fragmentTransaction.replace(R.id.framLayout, new NewsFragment());
                        break;
                    case R.id.nav_setting:
                        fragmentTransaction.replace(R.id.framLayout, new SettingFragment());
                        break;
                }
                fragmentTransaction.commit();
                toolbar.setTitle(menuItem.getTitle());
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

}

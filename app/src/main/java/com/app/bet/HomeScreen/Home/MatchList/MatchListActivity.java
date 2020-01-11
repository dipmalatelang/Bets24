package com.app.bet.HomeScreen.Home.MatchList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.HomeScreen.Matches.MatchData;
import com.app.bet.HomeScreen.Matches.ViewPagerAdapter;
import com.app.bet.R;
import com.app.bet.Util.APICall;
import com.app.bet.Util.ApiInterface;
import com.app.bet.Util.MatchInfoData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchListActivity extends AppCompatActivity {

    String sport = "All";
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar Tb_App;
    private ArrayList<MatchData> listInPlay = new ArrayList<>();
    private ArrayList<MatchData> listUpcoming = new ArrayList<>();
    private ArrayList<String> matchtype = new ArrayList<>();
    private boolean stat = false;
    private ArrayList<Integer> pos = new ArrayList<>();
    private boolean statu = false;
    private ArrayList<Integer> posu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        Tb_App = findViewById(R.id.Tb_App);
        setSupportActionBar(Tb_App);

        if (getIntent() != null){
            sport = getIntent().getStringExtra("sport");
            Tb_App.setTitle(sport);
        }

        if (sport.equalsIgnoreCase("All")){
            tabLayout = findViewById(R.id.tabLayout);
            viewPager = findViewById(R.id.viewPager);
            getMatches();

        }else {
            tabLayout = findViewById(R.id.tabLayout);
            viewPager = findViewById(R.id.viewPager);
            getIndividualMatches(sport);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMatches(){
        listInPlay.clear();
        ApiInterface apiInterface = APICall.getAPI().create(ApiInterface.class);
        Call<MatchInfoData> call = apiInterface.getMatchList();
        call.enqueue(new Callback<MatchInfoData>() {
            @Override
            public void onResponse(Call<MatchInfoData> call, Response<MatchInfoData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        MatchInfoData infoData = response.body();
                        for (int i = 0 ; i < infoData.sportsData.size(); i++){
                            if (infoData.sportsData.get(i).name.equalsIgnoreCase("Cricket")){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("1")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(listInPlay.size());
                                                    matchtype.add("Cricket");
                                                    listInPlay.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    matchtype.add("Cricket");
                                                    listInPlay.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        } else {
                                            for (int l = 0 ; l < 1; l++){
                                                if (!statu){
                                                    statu = true;
                                                    posu.add(listUpcoming.size());
                                                    listUpcoming.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listUpcoming.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        }
                                    }
                                }

                                stat = false;
                                statu = false;

                            } else if (infoData.sportsData.get(i).name.equalsIgnoreCase("Soccer")){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("1")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(listInPlay.size());
                                                    matchtype.add("Soccer");
                                                    listInPlay.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    matchtype.add("Soccer");
                                                    listInPlay.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        } else {
                                            for (int l = 0 ; l < 1; l++){
                                                if (!statu){
                                                    statu = true;
                                                    posu.add(listUpcoming.size());
                                                    listUpcoming.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listUpcoming.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        }
                                    }
                                }

                                stat = false;
                                statu = false;

                            } else if (infoData.sportsData.get(i).name.equalsIgnoreCase("Tennis")){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("1")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(listInPlay.size());
                                                    matchtype.add("Tennis");
                                                    listInPlay.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    matchtype.add("Tennis");
                                                    listInPlay.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        } else {
                                            for (int l = 0 ; l < 1; l++){
                                                if (!statu){
                                                    statu = true;
                                                    posu.add(listUpcoming.size());
                                                    listUpcoming.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listUpcoming.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        }
                                    }
                                }
                                stat = false;
                                statu = false;
                            }
                        }

                        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager() , listInPlay , pos , matchtype , listUpcoming, posu);
                        viewPager.setAdapter(viewPagerAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchInfoData> call, Throwable t) {
                Log.d("DATATA",""+t);
            }
        });
    }

    private void getIndividualMatches(final String sportname){
        listInPlay.clear();
        ApiInterface apiInterface = APICall.getAPI().create(ApiInterface.class);
        Call<MatchInfoData> call = apiInterface.getMatchList();
        call.enqueue(new Callback<MatchInfoData>() {
            @Override
            public void onResponse(Call<MatchInfoData> call, Response<MatchInfoData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        MatchInfoData infoData = response.body();
                        for (int i = 0 ; i < infoData.sportsData.size(); i++){
                            if (infoData.sportsData.get(i).name.equalsIgnoreCase(sportname)){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("1")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(listInPlay.size());
                                                    matchtype.add(sportname);
                                                    listInPlay.add(new MatchData(sportname,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    matchtype.add(sportname);
                                                    listInPlay.add(new MatchData(sportname,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        } else {
                                            for (int l = 0 ; l < 1; l++){
                                                if (!statu){
                                                    statu = true;
                                                    posu.add(listUpcoming.size());
                                                    listUpcoming.add(new MatchData(sportname,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listUpcoming.add(new MatchData(sportname,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        }
                                    }
                                }

                                stat = false;
                                statu = false;

                            }
                        }

                        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager() , listInPlay , pos , matchtype , listUpcoming, posu);
                        viewPager.setAdapter(viewPagerAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchInfoData> call, Throwable t) {
                Log.d("DATATA",""+t);
            }
        });
    }
}

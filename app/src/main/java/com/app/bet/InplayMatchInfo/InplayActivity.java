package com.app.bet.InplayMatchInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bet.HomeScreen.Matches.LiveMatch.CricketScoreData;
import com.app.bet.HomeScreen.Matches.LiveMatch.SoccerScoreData;
import com.app.bet.HomeScreen.Matches.LiveMatch.TennisScoreData;
import com.app.bet.MatchData.FancyAdapter;
import com.app.bet.MatchData.FancyData;
import com.app.bet.MatchData.MatchOddsData;
import com.app.bet.R;
import com.app.bet.UpcomingMatchInfo.MatchOddsAdapter;
import com.app.bet.Util.APICall;
import com.app.bet.Util.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InplayActivity extends AppCompatActivity {

    ImageView Iv_TV;
    Button Btn_Score, Btn_MatchOdd, Btn_Fancy;
    WebView WebView;
    RelativeLayout RL_Cricket_Score, RL_Tennis_Score, RL_Soccer_Score;
    LinearLayout LL_MatchOdds, LL_Session;
    TextView Tv_Team1, Tv_Team1Score, Tv_Team1Wicket, Tv_Team2, Tv_Team2Score, Tv_Team2Wicket, Tv_Player1, Tv_Player2,
            Tv_OverValue, Tv_Ball_no_Value, Tv_Player2RunsValue,
            Tv_Tennis_Team1, Tv_Tennis_Team1Score, Tv_SetValue, Tv_Tennis_Team2, Tv_Tennis_Team2Score, Tv_Tennis_Player1,
            Tv_Tennis_Player2, Tv_Team1_Set1, Tv_Team2_Set1, Tv_Team1_Set2, Tv_Team2_Set2, Tv_Team1_Set3, Tv_Team2_Set3,
            Tv_Soccer_Team1, Tv_Soccer_Team1Score, Tv_Soccer_Team2, Tv_Soccer_Team2Score;
    CricketScoreData matchDetailsData;
    TennisScoreData tennisScoreData;
    SoccerScoreData soccerScoreData;
    MatchOddsData matchOddsData;
    FancyData fancyData;
    ArrayList<FancyData.data_info> fancydatalist;
    ArrayList<MatchOddsData.data_info.runnerData_info> datalist;
    MatchOddsAdapter matchOddsAdapter;
    FancyAdapter fancyAdapter;
    RecyclerView Rv_MatchOddView, Rv_SessionView;
    String matchId = "0", marketId = "0", fancyId = "0";
    String sportname = "Cricket";

    final Handler handler = new Handler();
    final int delay = 1000; //milliseconds
    boolean status = false;

    final Handler shandler = new Handler();
    final int sdelay = 10000; //milliseconds
    boolean sstatus = false;

    String Tv_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inplay);
        Iv_TV = findViewById(R.id.Iv_TV);
        Btn_Score = findViewById(R.id.Btn_Score);
        Btn_MatchOdd = findViewById(R.id.Btn_MatchOdd);
        Btn_Fancy = findViewById(R.id.Btn_Fancy);
        WebView = findViewById(R.id.WebView);
        RL_Cricket_Score = findViewById(R.id.RL_Cricket_Score);
        RL_Tennis_Score = findViewById(R.id.RL_Tennis_Score);
        RL_Soccer_Score = findViewById(R.id.RL_Soccer_Score);
        LL_MatchOdds = findViewById(R.id.LL_MatchOdds);
        LL_Session = findViewById(R.id.LL_Session);
        Tv_Team1 = findViewById(R.id.Tv_Team1);
        Tv_Team1Score = findViewById(R.id.Tv_Team1Score);
        Tv_Team1Wicket = findViewById(R.id.Tv_Team1Wicket);
        Tv_Team2 = findViewById(R.id.Tv_Team2);
        Tv_Team2Score = findViewById(R.id.Tv_Team2Score);
        Tv_Team2Wicket = findViewById(R.id.Tv_Team2Wicket);
        Tv_Player1 = findViewById(R.id.Tv_Player1);
        Tv_Player2 = findViewById(R.id.Tv_Player2);
        Tv_OverValue = findViewById(R.id.Tv_OverValue);
        Tv_Tennis_Team1 = findViewById(R.id.Tv_Tennis_Team1);
        Tv_Tennis_Team1Score = findViewById(R.id.Tv_Tennis_Team1Score);
        Tv_SetValue = findViewById(R.id.Tv_SetValue);
        Tv_Tennis_Team2 = findViewById(R.id.Tv_Tennis_Team2);
        Tv_Tennis_Team2Score = findViewById(R.id.Tv_Tennis_Team2Score);
        Tv_Tennis_Player1 = findViewById(R.id.Tv_Tennis_Player1);
        Tv_Tennis_Player2 = findViewById(R.id.Tv_Tennis_Player2);
        Tv_Team1_Set1 = findViewById(R.id.Tv_Team1_Set1);
        Tv_Team2_Set1 = findViewById(R.id.Tv_Team2_Set1);
        Tv_Team1_Set2 = findViewById(R.id.Tv_Team1_Set2);
        Tv_Team2_Set2 = findViewById(R.id.Tv_Team2_Set2);
        Tv_Team1_Set3 = findViewById(R.id.Tv_Team1_Set3);
        Tv_Team2_Set3 = findViewById(R.id.Tv_Team2_Set3);
        Tv_Soccer_Team1 = findViewById(R.id.Tv_Soccer_Team1);
        Tv_Soccer_Team1Score = findViewById(R.id.Tv_Soccer_Team1Score);
        Tv_Soccer_Team2 = findViewById(R.id.Tv_Soccer_Team2);
        Tv_Soccer_Team2Score = findViewById(R.id.Tv_Soccer_Team2Score);
        Tv_Ball_no_Value = findViewById(R.id.Tv_Ball_no_Value);
        Tv_Player2RunsValue = findViewById(R.id.Tv_Player2RunsValue);
        Rv_MatchOddView = findViewById(R.id.Rv_MatchOddView);
        Rv_SessionView = findViewById(R.id.Rv_SessionView);

        if (getIntent() != null){
            sportname = getIntent().getStringExtra("SportName");
            matchId = getIntent().getStringExtra("MatchId");
            marketId = getIntent().getStringExtra("MarketId");
            fancyId = getIntent().getStringExtra("FancyId");
        }

        Log.d("dadaddada",""+matchId+" "+marketId+" "+fancyId);

        if (sportname.equalsIgnoreCase("Cricket")){
            getFancy(fancyId);
            getCricketMatchesDetails(matchId);
            RL_Cricket_Score.setVisibility(View.VISIBLE);
        } else if (sportname.equalsIgnoreCase("Tennis")){
            getTennnisMatchesDetails(matchId);
            LL_Session.setVisibility(View.GONE);
            Btn_Fancy.setVisibility(View.GONE);
            RL_Tennis_Score.setVisibility(View.VISIBLE);
        } else if(sportname.equalsIgnoreCase("Soccer")){
            getSoccerMatchesDetails(matchId);
            LL_Session.setVisibility(View.GONE);
            Btn_Fancy.setVisibility(View.GONE);
            RL_Soccer_Score.setVisibility(View.VISIBLE);
        }

        getMatchOdds(marketId);

        WebView.getSettings().setJavaScriptEnabled(true);
        Tv_URL = "https://shivexch.com/tv_api/live_tv/index.html?token=678540d1-9359-4232-a9dd-144c6d8e2c0f&mtid= "+matchId;
        WebView.loadUrl(Tv_URL);
        if (Tv_URL.equalsIgnoreCase("Tv Not Comming")){
            WebView.setVisibility(View.GONE);
        }

        Iv_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WebView.getVisibility() == View.VISIBLE){
                    WebView.setVisibility(View.GONE);
                    Iv_TV.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_white_bg));
                } else {
                    WebView.setVisibility(View.VISIBLE);
                    Iv_TV.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                }
                Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_MatchOdd.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_Fancy.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
            }
        });

        Btn_Score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sportname.equalsIgnoreCase("Cricket")){
                    if (RL_Cricket_Score.getVisibility() == View.VISIBLE){
                        RL_Cricket_Score.setVisibility(View.GONE);
                        Btn_Score.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorBackground));
                        Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_white_bg));
                    } else {
                        RL_Cricket_Score.setVisibility(View.VISIBLE);
                        Btn_Score.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorWhite));
                        Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                    }
                } else if (sportname.equalsIgnoreCase("Tennis")){
                    if (RL_Tennis_Score.getVisibility() == View.VISIBLE){
                        RL_Tennis_Score.setVisibility(View.GONE);
                        Btn_Score.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorBackground));
                        Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_white_bg));
                    } else {
                        RL_Tennis_Score.setVisibility(View.VISIBLE);
                        Btn_Score.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorWhite));
                        Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                    }
                } else if(sportname.equalsIgnoreCase("Soccer")){
                    if (RL_Soccer_Score.getVisibility() == View.VISIBLE){
                        RL_Soccer_Score.setVisibility(View.GONE);
                        Btn_Score.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorBackground));
                        Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_white_bg));
                    } else {
                        RL_Soccer_Score.setVisibility(View.VISIBLE);
                        Btn_Score.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorWhite));
                        Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                    }
                }

                Iv_TV.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_MatchOdd.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_Fancy.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
            }
        });

        Btn_MatchOdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LL_MatchOdds.getVisibility() == View.VISIBLE){
                    LL_MatchOdds.setVisibility(View.GONE);
                    Btn_MatchOdd.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorBackground));
                    Btn_MatchOdd.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_white_bg));
                }else {
                    LL_MatchOdds.setVisibility(View.VISIBLE);
                    Btn_MatchOdd.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorWhite));
                    Btn_MatchOdd.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                }

                Iv_TV.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_Fancy.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
            }
        });

        Btn_Fancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LL_Session.getVisibility() == View.VISIBLE){
                    LL_Session.setVisibility(View.GONE);
                    Btn_Fancy.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorBackground));
                    Btn_Fancy.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_white_bg));
                }else {
                    LL_Session.setVisibility(View.VISIBLE);
                    Btn_Fancy.setTextColor(InplayActivity.this.getResources().getColor(R.color.colorWhite));
                    Btn_Fancy.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                }

                Iv_TV.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_MatchOdd.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
                Btn_Score.setBackgroundDrawable(InplayActivity.this.getResources().getDrawable(R.drawable.btn_green_bg));
            }
        });
    }

    public void getCricketMatchesDetails(final String id){
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<CricketScoreData> call = apiInterface.getCricketScore(id);
        call.enqueue(new Callback<CricketScoreData>() {
            @Override
            public void onResponse(Call<CricketScoreData> call, Response<CricketScoreData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        matchDetailsData = response.body();
                        for (int i = 0; i<matchDetailsData.data.size(); i++){

                            if (matchDetailsData.data == null || matchDetailsData.data.size() == 0){
                                Tv_Team1Score.setText("0");
                                Tv_Team1Wicket.setText("0");
                                Tv_Team2Score.setText("0");
                                Tv_Team2Wicket.setText("0");
                            } else {
                                if (matchDetailsData.data.get(i).score.home.inning2 != null || matchDetailsData.data.get(i).score.away.inning2 != null){
                                    if (matchDetailsData.data.get(i).score.home.inning2 == null){
                                        Tv_Team1Score.setText("0");
                                        Tv_Team1Wicket.setText("0");
                                    } else {
                                        Tv_Team1Score.setText(matchDetailsData.data.get(i).score.home.inning2.runs);
                                        Tv_Team1Wicket.setText(" / " +matchDetailsData.data.get(i).score.home.inning2.wickets);
                                    }

                                    if (matchDetailsData.data.get(i).score.away.inning2 == null){
                                        Tv_Team2Score.setText("0");
                                        Tv_Team2Wicket.setText("0");
                                    } else {
                                        Tv_Team2Score.setText(matchDetailsData.data.get(i).score.away.inning2.runs);
                                        Tv_Team2Wicket.setText(" / " +matchDetailsData.data.get(i).score.away.inning2.wickets);
                                    }
                                } else {
                                    if (matchDetailsData.data.get(i).score.home.inning1 == null){
                                        Tv_Team1Score.setText("0");
                                        Tv_Team1Wicket.setText("0");
                                    } else {
                                        Tv_Team1Score.setText(matchDetailsData.data.get(i).score.home.inning1.runs);
                                        Tv_Team1Wicket.setText(" / " +matchDetailsData.data.get(i).score.home.inning1.wickets);
                                    }

                                    if (matchDetailsData.data.get(i).score.away.inning1 == null){
                                        Tv_Team2Score.setText("0");
                                        Tv_Team2Wicket.setText("0");
                                    } else {
                                        Tv_Team2Score.setText(matchDetailsData.data.get(i).score.away.inning1.runs);
                                        Tv_Team2Wicket.setText(" / " +matchDetailsData.data.get(i).score.away.inning1.wickets);
                                    }
                                }
                            }

                            Tv_Team1.setText(matchDetailsData.data.get(i).score.home.name);
                            Tv_Team2.setText(matchDetailsData.data.get(i).score.away.name);
                            Tv_Player1.setText(matchDetailsData.data.get(i).stateOfBall.bowlerName);
                            Tv_Player2.setText(matchDetailsData.data.get(i).stateOfBall.batsmanName);
                            Tv_OverValue.setText(matchDetailsData.data.get(i).stateOfBall.overNumber);
                            Tv_Ball_no_Value.setText(matchDetailsData.data.get(i).stateOfBall.overBallNumber);
                            Tv_Player2RunsValue.setText(matchDetailsData.data.get(i).stateOfBall.batsmanRuns);
                        }

                        sstatus = true;
                        shandler.postDelayed(new Runnable() {
                            public void run() {
                                //do something
                                refreshCricket(id);
                                if (sstatus) {
                                    shandler.postDelayed(this, sdelay);
                                }

                            }
                        }, sdelay);
                    }
                }
            }

            @Override
            public void onFailure(Call<CricketScoreData> call, Throwable t) {
                Log.d("DATATATAT_Cricket", ""+t);
            }
        });
    }

    public void getTennnisMatchesDetails(final String id){
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<TennisScoreData> call = apiInterface.getTennisScore(id);
        call.enqueue(new Callback<TennisScoreData>() {
            @Override
            public void onResponse(Call<TennisScoreData> call, Response<TennisScoreData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        tennisScoreData = response.body();
                        for (int j=0; j<tennisScoreData.data.size(); j++){
                            Tv_Tennis_Team1.setText(tennisScoreData.data.get(j).score.home.name);
                            Tv_Tennis_Team2.setText(tennisScoreData.data.get(j).score.away.name);
                            Tv_Tennis_Player1.setText(tennisScoreData.data.get(j).score.home.name);
                            Tv_Tennis_Player2.setText(tennisScoreData.data.get(j).score.away.name);
                            Tv_SetValue.setText(tennisScoreData.data.get(j).currentSet);
                            Tv_Tennis_Team1Score.setText(tennisScoreData.data.get(j).score.home.score);
                            Tv_Tennis_Team2Score.setText(tennisScoreData.data.get(j).score.away.score);
                            if (tennisScoreData.data.get(j).score.home.gameSequence == null || tennisScoreData.data.get(j).score.away.gameSequence == null){
                                Tv_Team1_Set1.setText("0");
                                Tv_Team2_Set1.setText("0");
                            } else {
                                for (int a=0; a<tennisScoreData.data.get(j).score.home.gameSequence.size(); a++){
                                    if (a == 0){
                                        Tv_Team1_Set1.setText(tennisScoreData.data.get(j).score.home.gameSequence.get(a));
                                    } else if (a == 1){
                                        Tv_Team1_Set2.setText(tennisScoreData.data.get(j).score.home.gameSequence.get(a));
                                    } else if (a == 2){
                                        Tv_Team1_Set3.setText(tennisScoreData.data.get(j).score.home.gameSequence.get(a));
                                    }
                                }
                                for (int b = 0; b<tennisScoreData.data.get(j).score.away.gameSequence.size(); b++){
                                    if (b == 0){
                                        Tv_Team2_Set1.setText(tennisScoreData.data.get(j).score.away.gameSequence.get(b));
                                    } else if (b == 1){
                                        Tv_Team2_Set2.setText(tennisScoreData.data.get(j).score.away.gameSequence.get(b));
                                    } else if (b == 2){
                                        Tv_Team2_Set3.setText(tennisScoreData.data.get(j).score.away.gameSequence.get(b));
                                    }
                                }
                            }
                        }

                        sstatus = true;
                        shandler.postDelayed(new Runnable() {
                            public void run() {
                                //do something
                                refreshTennis(id);
                                if (sstatus) {
                                    shandler.postDelayed(this, sdelay);
                                }

                            }
                        }, sdelay);
                    }
                }
            }

            @Override
            public void onFailure(Call<TennisScoreData> call, Throwable t) {
                Log.d("DATATATAT_Tennis", ""+t);
            }
        });
    }

    public void getSoccerMatchesDetails(final String id){
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<SoccerScoreData> call = apiInterface.getSoccerScore(id);
        call.enqueue(new Callback<SoccerScoreData>() {
            @Override
            public void onResponse(Call<SoccerScoreData> call, Response<SoccerScoreData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        soccerScoreData = response.body();
                        for (int i = 0; i<soccerScoreData.data.size(); i++){
                            Tv_Soccer_Team1.setText(soccerScoreData.data.get(i).score.home.name);
                            Tv_Soccer_Team2.setText(soccerScoreData.data.get(i).score.away.name);
                            Tv_Soccer_Team1Score.setText(soccerScoreData.data.get(i).score.home.score);
                            Tv_Soccer_Team2Score.setText(soccerScoreData.data.get(i).score.away.score);
                        }

                        sstatus = true;
                        shandler.postDelayed(new Runnable() {
                            public void run() {
                                //do something
                                refreshSoccer(id);
                                if (sstatus) {
                                    shandler.postDelayed(this, sdelay);
                                }

                            }
                        }, sdelay);
                    }
                }
            }

            @Override
            public void onFailure(Call<SoccerScoreData> call, Throwable t) {

            }
        });
    }

    public void getMatchOdds(final String bfid){
        ApiInterface apiInterface = APICall.getAPII().create(ApiInterface.class);
        Call<MatchOddsData> call = apiInterface.getMatchOddsData(bfid);
        call.enqueue(new Callback<MatchOddsData>() {
            @Override
            public void onResponse(Call<MatchOddsData> call, Response<MatchOddsData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        matchOddsData = response.body();
                        datalist = matchOddsData.getData().getRunnerData();
                        if (datalist.size() != 0){
                            Rv_MatchOddView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            matchOddsAdapter = new MatchOddsAdapter(getApplicationContext(), datalist);
                            Rv_MatchOddView.setAdapter(matchOddsAdapter);

                            status = true;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    //do something
                                    refreshOdds(bfid);
                                    if (status) {
                                        handler.postDelayed(this, delay);
                                    }

                                }
                            }, delay);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchOddsData> call, Throwable t) {
                Log.d("DATATATATAA", ""+t);
            }
        });
    }

    public void getFancy(String mtid){
        ApiInterface apiInterface = APICall.getAPI().create(ApiInterface.class);
        Call<FancyData> call = apiInterface.getFancyData(mtid);
        call.enqueue(new Callback<FancyData>() {
            @Override
            public void onResponse(Call<FancyData> call, Response<FancyData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        fancyData = response.body();
                        fancydatalist = fancyData.getData();
                        if (fancydatalist.size() != 0){
                            Rv_SessionView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            fancyAdapter = new FancyAdapter(getApplicationContext(), fancydatalist);
                            Rv_SessionView.setAdapter(fancyAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FancyData> call, Throwable t) {
                Log.d("DATAFS", ""+t);
            }
        });
    }

    public void refreshOdds(String bfid){
        ApiInterface apiInterface = APICall.getAPII().create(ApiInterface.class);
        Call<MatchOddsData> call = apiInterface.getMatchOddsData(bfid);
        call.enqueue(new Callback<MatchOddsData>() {
            @Override
            public void onResponse(Call<MatchOddsData> call, Response<MatchOddsData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        matchOddsData = response.body();
                        datalist = matchOddsData.getData().getRunnerData();
                        if (datalist.size() != 0){
                            matchOddsAdapter.refreshOdds(datalist);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchOddsData> call, Throwable t) {
                Log.d("DATATATATAA", ""+t);
            }
        });
    }

    public void refreshCricket(String Id){
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<CricketScoreData> call = apiInterface.getCricketScore(Id);
        call.enqueue(new Callback<CricketScoreData>() {
            @Override
            public void onResponse(Call<CricketScoreData> call, Response<CricketScoreData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        matchDetailsData = response.body();
                        for (int i = 0; i<matchDetailsData.data.size(); i++){

                            if (matchDetailsData.data == null || matchDetailsData.data.size() == 0){
                                Tv_Team1Score.setText("0");
                                Tv_Team1Wicket.setText("0");
                                Tv_Team2Score.setText("0");
                                Tv_Team2Wicket.setText("0");
                            } else {
                                if (matchDetailsData.data.get(i).score.home.inning2 != null || matchDetailsData.data.get(i).score.away.inning2 != null){
                                    if (matchDetailsData.data.get(i).score.home.inning2 == null){
                                        Tv_Team1Score.setText("0");
                                        Tv_Team1Wicket.setText("0");
                                    } else {
                                        Tv_Team1Score.setText(matchDetailsData.data.get(i).score.home.inning2.runs);
                                        Tv_Team1Wicket.setText(" / " +matchDetailsData.data.get(i).score.home.inning2.wickets);
                                    }

                                    if (matchDetailsData.data.get(i).score.away.inning2 == null){
                                        Tv_Team2Score.setText("0");
                                        Tv_Team2Wicket.setText("0");
                                    } else {
                                        Tv_Team2Score.setText(matchDetailsData.data.get(i).score.away.inning2.runs);
                                        Tv_Team2Wicket.setText(" / " +matchDetailsData.data.get(i).score.away.inning2.wickets);
                                    }
                                } else {
                                    if (matchDetailsData.data.get(i).score.home.inning1 == null){
                                        Tv_Team1Score.setText("0");
                                        Tv_Team1Wicket.setText("0");
                                    } else {
                                        Tv_Team1Score.setText(matchDetailsData.data.get(i).score.home.inning1.runs);
                                        Tv_Team1Wicket.setText(" / " +matchDetailsData.data.get(i).score.home.inning1.wickets);
                                    }

                                    if (matchDetailsData.data.get(i).score.away.inning1 == null){
                                        Tv_Team2Score.setText("0");
                                        Tv_Team2Wicket.setText("0");
                                    } else {
                                        Tv_Team2Score.setText(matchDetailsData.data.get(i).score.away.inning1.runs);
                                        Tv_Team2Wicket.setText(" / " +matchDetailsData.data.get(i).score.away.inning1.wickets);
                                    }
                                }
                            }

                            Tv_Team1.setText(matchDetailsData.data.get(i).score.home.name);
                            Tv_Team2.setText(matchDetailsData.data.get(i).score.away.name);
                            Tv_Player1.setText(matchDetailsData.data.get(i).stateOfBall.bowlerName);
                            Tv_Player2.setText(matchDetailsData.data.get(i).stateOfBall.batsmanName);
                            Tv_OverValue.setText(matchDetailsData.data.get(i).stateOfBall.overNumber);
                            Tv_Ball_no_Value.setText(matchDetailsData.data.get(i).stateOfBall.overBallNumber);
                            Tv_Player2RunsValue.setText(matchDetailsData.data.get(i).stateOfBall.batsmanRuns);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CricketScoreData> call, Throwable t) {
                Log.d("DATATATAT_Cricket", ""+t);
            }
        });

    }

    public void refreshTennis(String Id){
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<TennisScoreData> call = apiInterface.getTennisScore(Id);
        call.enqueue(new Callback<TennisScoreData>() {
            @Override
            public void onResponse(Call<TennisScoreData> call, Response<TennisScoreData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        tennisScoreData = response.body();
                        for (int j=0; j<tennisScoreData.data.size(); j++){
                            Tv_Tennis_Team1.setText(tennisScoreData.data.get(j).score.home.name);
                            Tv_Tennis_Team2.setText(tennisScoreData.data.get(j).score.away.name);
                            Tv_Tennis_Player1.setText(tennisScoreData.data.get(j).score.home.name);
                            Tv_Tennis_Player2.setText(tennisScoreData.data.get(j).score.away.name);
                            Tv_SetValue.setText(tennisScoreData.data.get(j).currentSet);
                            Tv_Tennis_Team1Score.setText(tennisScoreData.data.get(j).score.home.score);
                            Tv_Tennis_Team2Score.setText(tennisScoreData.data.get(j).score.away.score);
                            if (tennisScoreData.data.get(j).score.home.gameSequence == null || tennisScoreData.data.get(j).score.away.gameSequence == null){
                                Tv_Team1_Set1.setText("0");
                                Tv_Team2_Set1.setText("0");
                            } else {
                                for (int a=0; a<tennisScoreData.data.get(j).score.home.gameSequence.size(); a++){
                                    if (a == 0){
                                        Tv_Team1_Set1.setText(tennisScoreData.data.get(j).score.home.gameSequence.get(a));
                                    } else if (a == 1){
                                        Tv_Team1_Set2.setText(tennisScoreData.data.get(j).score.home.gameSequence.get(a));
                                    } else if (a == 2){
                                        Tv_Team1_Set3.setText(tennisScoreData.data.get(j).score.home.gameSequence.get(a));
                                    }
                                }
                                for (int b = 0; b<tennisScoreData.data.get(j).score.away.gameSequence.size(); b++){
                                    if (b == 0){
                                        Tv_Team2_Set1.setText(tennisScoreData.data.get(j).score.away.gameSequence.get(b));
                                    } else if (b == 1){
                                        Tv_Team2_Set2.setText(tennisScoreData.data.get(j).score.away.gameSequence.get(b));
                                    } else if (b == 2){
                                        Tv_Team2_Set3.setText(tennisScoreData.data.get(j).score.away.gameSequence.get(b));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TennisScoreData> call, Throwable t) {
                Log.d("DATATATAT_Tennis", ""+t);
            }
        });
    }

    public void refreshSoccer(String Id){
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<SoccerScoreData> call = apiInterface.getSoccerScore(Id);
        call.enqueue(new Callback<SoccerScoreData>() {
            @Override
            public void onResponse(Call<SoccerScoreData> call, Response<SoccerScoreData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        soccerScoreData = response.body();
                        for (int i = 0; i<soccerScoreData.data.size(); i++){
                            Tv_Soccer_Team1.setText(soccerScoreData.data.get(i).score.home.name);
                            Tv_Soccer_Team2.setText(soccerScoreData.data.get(i).score.away.name);
                            Tv_Soccer_Team1Score.setText(soccerScoreData.data.get(i).score.home.score);
                            Tv_Soccer_Team2Score.setText(soccerScoreData.data.get(i).score.away.score);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SoccerScoreData> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        status = false;
        sstatus = false;
    }
}

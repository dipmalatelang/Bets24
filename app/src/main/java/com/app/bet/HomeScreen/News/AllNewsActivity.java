package com.app.bet.HomeScreen.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.bet.HomeScreen.News.CricketNews.NewsCricketAdapter;
import com.app.bet.HomeScreen.News.CricketNews.RssFeedCricket;
import com.app.bet.HomeScreen.News.SoccerNews.NewsSoccerAdapter;
import com.app.bet.HomeScreen.News.SoccerNews.RssFeedSoccer;
import com.app.bet.HomeScreen.News.TennisNews.NewsTennisAdapter;
import com.app.bet.HomeScreen.News.TennisNews.RssFeedTennis;
import com.app.bet.R;
import com.app.bet.Util.APICall;
import com.app.bet.Util.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNewsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView RvNews;
    TextView TvCricket, TvSoccer, TvTennis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        RvNews = findViewById(R.id.RvNews);

        TvCricket = findViewById(R.id.TvCricket);
        TvSoccer = findViewById(R.id.TvSoccer);
        TvTennis = findViewById(R.id.TvTennis);

        String title = "cricket";
        if (getIntent() != null) {
            title = getIntent().getStringExtra("sports");
        }
        if (title.equalsIgnoreCase("cricket")) {
            TvCricket.setTextColor(getResources().getColor(R.color.colorAccent));
            TvSoccer.setTextColor(getResources().getColor(R.color.colorWhite));
            TvTennis.setTextColor(getResources().getColor(R.color.colorWhite));
            getCricketNews();
        } else if (title.equalsIgnoreCase("soccer")) {
            TvSoccer.setTextColor(getResources().getColor(R.color.colorAccent));
            TvTennis.setTextColor(getResources().getColor(R.color.colorWhite));
            TvCricket.setTextColor(getResources().getColor(R.color.colorWhite));
            getSoccerNews();
        } else {
            TvTennis.setTextColor(getResources().getColor(R.color.colorAccent));
            TvSoccer.setTextColor(getResources().getColor(R.color.colorWhite));
            TvCricket.setTextColor(getResources().getColor(R.color.colorWhite));
            getTennisNews();
        }

        TvCricket.setOnClickListener(this);
        TvSoccer.setOnClickListener(this);
        TvTennis.setOnClickListener(this);
    }

    private void getTennisNews() {
        ApiInterface apiInterface = APICall.getRssTennis().create(ApiInterface.class);
        Call<RssFeedTennis> call = apiInterface.getTennisFeeds();
        call.enqueue(new Callback<RssFeedTennis>() {
            @Override
            public void onResponse(Call<RssFeedTennis> call, Response<RssFeedTennis> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        RssFeedTennis rssFeedTennis = response.body();
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(AllNewsActivity.this, 1);
                        RvNews.setLayoutManager(linearLayoutManager);
                        NewsTennisAdapter newsTennisAdapter = new NewsTennisAdapter(AllNewsActivity.this, rssFeedTennis, rssFeedTennis.getItemTennis().size());
                        RvNews.setAdapter(newsTennisAdapter);


                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeedTennis> call, Throwable t) {
                Log.d("DATATAA1", "" + t);
            }
        });
    }

    private void getSoccerNews() {
        ApiInterface apiInterface = APICall.getRssSoccer().create(ApiInterface.class);
        Call<RssFeedSoccer> call = apiInterface.getSoccerFeeds();
        call.enqueue(new Callback<RssFeedSoccer>() {
            @Override
            public void onResponse(Call<RssFeedSoccer> call, Response<RssFeedSoccer> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        RssFeedSoccer rssFeedSoccer = response.body();
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(AllNewsActivity.this, 1);
                        RvNews.setLayoutManager(linearLayoutManager);
                        NewsSoccerAdapter newsSoccerAdapter = new NewsSoccerAdapter(AllNewsActivity.this, rssFeedSoccer, rssFeedSoccer.getItemSoccer().size());
                        RvNews.setAdapter(newsSoccerAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeedSoccer> call, Throwable t) {
                Log.d("DATATAA1", "" + t);
            }
        });
    }

    private void getCricketNews() {
        ApiInterface apiInterface = APICall.getRssCricket().create(ApiInterface.class);
        Call<RssFeedCricket> call = apiInterface.getCricketFeeds();
        call.enqueue(new Callback<RssFeedCricket>() {
            @Override
            public void onResponse(Call<RssFeedCricket> call, Response<RssFeedCricket> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        RssFeedCricket rssFeedCricket = response.body();
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(AllNewsActivity.this, 1);
                        RvNews.setLayoutManager(linearLayoutManager);
                        NewsCricketAdapter newsCricketAdapter = new NewsCricketAdapter(AllNewsActivity.this, rssFeedCricket, rssFeedCricket.getItemCrickets().size());
                        RvNews.setAdapter(newsCricketAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeedCricket> call, Throwable t) {
                Log.d("DATATAA1", "" + t);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TvCricket:
                TvCricket.setTextColor(getResources().getColor(R.color.colorAccent));
                TvSoccer.setTextColor(getResources().getColor(R.color.colorWhite));
                TvTennis.setTextColor(getResources().getColor(R.color.colorWhite));
                getCricketNews();
                break;
            case R.id.TvSoccer:
                TvSoccer.setTextColor(getResources().getColor(R.color.colorAccent));
                TvTennis.setTextColor(getResources().getColor(R.color.colorWhite));
                TvCricket.setTextColor(getResources().getColor(R.color.colorWhite));
                getSoccerNews();
                break;
            case R.id.TvTennis:
                TvTennis.setTextColor(getResources().getColor(R.color.colorAccent));
                TvSoccer.setTextColor(getResources().getColor(R.color.colorWhite));
                TvCricket.setTextColor(getResources().getColor(R.color.colorWhite));
                getTennisNews();
                break;
        }
    }
}

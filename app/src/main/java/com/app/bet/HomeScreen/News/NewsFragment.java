package com.app.bet.HomeScreen.News;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class NewsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView RvNews_Cricket,RvNews_Soccer,RvNews_Tennis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        RvNews_Cricket = view.findViewById(R.id.RvNews_Cricket);
        RvNews_Soccer = view.findViewById(R.id.RvNews_Soccer);
        RvNews_Tennis = view.findViewById(R.id.RvNews_Tennis);
        TextView tvCricketView = view.findViewById(R.id.TvCricketView);
        TextView tvSoccerView = view.findViewById(R.id.TvSoccerView);
        TextView tvTennisView = view.findViewById(R.id.TvTennisView);

        tvCricketView.setOnClickListener(this);
        tvSoccerView.setOnClickListener(this);
        tvTennisView.setOnClickListener(this);


        getCricketNews();
        getSoccerNews();
        getTennisNews();

        return view;
    }

    private void getTennisNews(){
        ApiInterface apiInterface = APICall.getRssTennis().create(ApiInterface.class);
        Call<RssFeedTennis> call = apiInterface.getTennisFeeds();
        call.enqueue(new Callback<RssFeedTennis>() {
            @Override
            public void onResponse(Call<RssFeedTennis> call, Response<RssFeedTennis> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        RssFeedTennis rssFeedTennis = response.body();
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),1);
                        RvNews_Tennis.setLayoutManager(linearLayoutManager);
                        NewsTennisAdapter newsTennisAdapter = new NewsTennisAdapter(getContext(), rssFeedTennis,3);
                        RvNews_Tennis.setAdapter(newsTennisAdapter);


                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeedTennis> call, Throwable t) {
                Log.d("DATATAA1",""+t);
            }
        });
    }

    private void getSoccerNews(){
        ApiInterface apiInterface = APICall.getRssSoccer().create(ApiInterface.class);
        Call<RssFeedSoccer> call = apiInterface.getSoccerFeeds();
        call.enqueue(new Callback<RssFeedSoccer>() {
            @Override
            public void onResponse(Call<RssFeedSoccer> call, Response<RssFeedSoccer> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        RssFeedSoccer rssFeedSoccer = response.body();
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),1);
                        RvNews_Soccer.setLayoutManager(linearLayoutManager);
                        NewsSoccerAdapter newsSoccerAdapter = new NewsSoccerAdapter(getContext(), rssFeedSoccer,3);
                        RvNews_Soccer.setAdapter(newsSoccerAdapter);


                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeedSoccer> call, Throwable t) {
                Log.d("DATATAA1",""+t);
            }
        });
    }

    private void getCricketNews(){
        ApiInterface apiInterface = APICall.getRssCricket().create(ApiInterface.class);
        Call<RssFeedCricket> call = apiInterface.getCricketFeeds();
        call.enqueue(new Callback<RssFeedCricket>() {
            @Override
            public void onResponse(Call<RssFeedCricket> call, Response<RssFeedCricket> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        RssFeedCricket rssFeedCricket = response.body();
                        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),1);
                        RvNews_Cricket.setLayoutManager(linearLayoutManager);
                        NewsCricketAdapter newsCricketAdapter = new NewsCricketAdapter(getContext(), rssFeedCricket,3);
                        RvNews_Cricket.setAdapter(newsCricketAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<RssFeedCricket> call, Throwable t) {
                Log.d("DATATAA1",""+t);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(),AllNewsActivity.class);
        switch (v.getId()){
            case R.id.TvCricketView:
                intent.putExtra("sports","cricket");
                if (getContext() != null){
                    getContext().startActivity(intent);
                }
                break;
            case R.id.TvSoccerView:
                intent.putExtra("sports","soccer");
                if (getContext() != null){
                    getContext().startActivity(intent);
                }
                break;
            case R.id.TvTennisView:
                intent.putExtra("sports","tennis");
                if (getContext() != null){
                    getContext().startActivity(intent);
                }
                break;
        }
    }
}

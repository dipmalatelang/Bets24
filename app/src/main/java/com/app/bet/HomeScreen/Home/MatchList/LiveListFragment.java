package com.app.bet.HomeScreen.Home.MatchList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bet.HomeScreen.Matches.LiveMatch.LiveMatchAdapter;
import com.app.bet.HomeScreen.Matches.MatchData;
import com.app.bet.R;
import com.app.bet.Util.APICall;
import com.app.bet.Util.ApiInterface;
import com.app.bet.Util.MatchInfoData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveListFragment extends Fragment {
    private RecyclerView Rv_Liveinplay;
    private TextView TvNoInplay;
    private LiveMatchAdapter inplayAdapter;
    private ArrayList<MatchData> listInPlay = new ArrayList<>();
    private boolean stat = false;
    private ArrayList<Integer> pos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        String sport = "Cricket";
        if (getArguments() != null){
            sport = getArguments().getString("sport");
        }

        Rv_Liveinplay = view.findViewById(R.id.Rv_Liveinplay);
        TvNoInplay = view.findViewById(R.id.TvNoInplay);

        Log.d("DATATATA","Live Fragment"+ sport);

        getMatches(sport);

        return view;
    }

    private void getMatches(final String sport){
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
                            if (infoData.sportsData.get(i).name.equalsIgnoreCase(sport)){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("1")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(0);
                                                    listInPlay.add(new MatchData(sport,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listInPlay.add(new MatchData(sport,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        }
                                    }
                                }

                                stat = false;

                            }
                        }

                        Log.d("DATATTA",""+listInPlay.size());

                        if (listInPlay.size() > 0){
                            TvNoInplay.setVisibility(View.GONE);
                            Rv_Liveinplay.setVisibility(View.VISIBLE);
                            Rv_Liveinplay.setLayoutManager(new LinearLayoutManager(getContext()));
                            Rv_Liveinplay.setItemAnimator(new DefaultItemAnimator());
                            //inplayAdapter = new LiveMatchAdapter(getContext(), listInPlay, pos);
                            Rv_Liveinplay.setAdapter(inplayAdapter);
                        }else {
                            TvNoInplay.setVisibility(View.VISIBLE);
                            Rv_Liveinplay.setVisibility(View.GONE);
                        }
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

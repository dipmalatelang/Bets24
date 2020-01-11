package com.app.bet.HomeScreen.Matches.UpcomingMatch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class UpcomingFragment extends Fragment {

    private RecyclerView Rv_Upcominginplay;
    private TextView TvNoInplay;
    private UpcomingMatchAdapter upcomingMatchAdapter;
    private ArrayList<MatchData> listUpcoming;
    private boolean stat = false;
    private ArrayList<Integer> pos;

    public UpcomingFragment(ArrayList<MatchData> listUpcoming, ArrayList<Integer> pos) {
        this.listUpcoming = listUpcoming;
        this.pos = pos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        Rv_Upcominginplay = view.findViewById(R.id.Rv_Upcominginplay);
        TvNoInplay = view.findViewById(R.id.TvNoInplay);

        if (listUpcoming.size() > 0){
            TvNoInplay.setVisibility(View.GONE);
            Rv_Upcominginplay.setVisibility(View.VISIBLE);
            Rv_Upcominginplay.setLayoutManager(new LinearLayoutManager(getContext()));
            Rv_Upcominginplay.setItemAnimator(new DefaultItemAnimator());
            upcomingMatchAdapter = new UpcomingMatchAdapter(getContext(), listUpcoming, pos);
            Rv_Upcominginplay.setAdapter(upcomingMatchAdapter);
        }else {
            TvNoInplay.setVisibility(View.VISIBLE);
            Rv_Upcominginplay.setVisibility(View.GONE);
        }

        //getMatches();

        return view;
    }

    private void getMatches(){
        listUpcoming.clear();
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
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("0")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(0);
                                                    listUpcoming.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listUpcoming.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        }
                                    }
                                }

                                stat = false;

                            } else if (infoData.sportsData.get(i).name.equalsIgnoreCase("Scocer")){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("0")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(listUpcoming.size());
                                                    listUpcoming.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listUpcoming.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        }
                                    }
                                }

                                stat = false;

                            } else if (infoData.sportsData.get(i).name.equalsIgnoreCase("Tennis")){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("0")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!stat){
                                                    stat = true;
                                                    pos.add(listUpcoming.size());
                                                    listUpcoming.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    listUpcoming.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        }
                                    }
                                }
                                stat = false;
                            }
                        }

                        Log.d("DATATTA",""+listUpcoming.size());

                        if (listUpcoming.size() > 0){
                            TvNoInplay.setVisibility(View.GONE);
                            Rv_Upcominginplay.setVisibility(View.VISIBLE);
                            Rv_Upcominginplay.setLayoutManager(new LinearLayoutManager(getContext()));
                            Rv_Upcominginplay.setItemAnimator(new DefaultItemAnimator());
                            upcomingMatchAdapter = new UpcomingMatchAdapter(getContext(), listUpcoming, pos);
                            Rv_Upcominginplay.setAdapter(upcomingMatchAdapter);
                        }else {
                            TvNoInplay.setVisibility(View.VISIBLE);
                            Rv_Upcominginplay.setVisibility(View.GONE);
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

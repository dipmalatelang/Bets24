package com.app.bet.HomeScreen.Home;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bet.HomeScreen.Home.MatchList.MatchListActivity;
import com.app.bet.HomeScreen.Matches.LiveMatch.CricketScoreData;
import com.app.bet.HomeScreen.Matches.LiveMatch.ScoreData;
import com.app.bet.HomeScreen.Matches.LiveMatch.SoccerScoreData;
import com.app.bet.HomeScreen.Matches.LiveMatch.TennisScoreData;
import com.app.bet.HomeScreen.Matches.MatchData;
import com.app.bet.R;
import com.app.bet.Util.APICall;
import com.app.bet.Util.ApiInterface;
import com.app.bet.Util.MatchInfoData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private RecyclerView Rv_Liveinplay,Rv_Featured;
    private ArrayList<MatchData> listInPlay = new ArrayList<>();
    private ArrayList<MatchData> listUpcoming = new ArrayList<>();
    private boolean statinplay = false;
    private ArrayList<Integer> posinplay = new ArrayList<>();
    private ArrayList<String> matchtype = new ArrayList<>();
    private String[] todaydate;
    private ArrayList<ScoreData> scorelist1 = new ArrayList<>();
    private ArrayList<ScoreData> scorelist2 = new ArrayList<>();
    private AllInPlayAdapter inplayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView TvViewAll = view.findViewById(R.id.TvViewAll);
        Rv_Liveinplay = view.findViewById(R.id.Rv_Liveinplay);
        Rv_Featured = view.findViewById(R.id.Rv_Featured);
        LinearLayout LLCricket = view.findViewById(R.id.LLCricket);
        LinearLayout LLSoccer = view.findViewById(R.id.LLSoccer);
        LinearLayout LLTennis = view.findViewById(R.id.LLTennis);

        TvViewAll.setOnClickListener(this);
        LLCricket.setOnClickListener(this);
        LLSoccer.setOnClickListener(this);
        LLTennis.setOnClickListener(this);

        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        todaydate = simpleDateFormat.format(today).split(" ");

        getMatches();

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), MatchListActivity.class);
        switch (v.getId()){
            case R.id.TvViewAll:
                if (listInPlay.size() > 0){
                    intent.putExtra("sport","All");
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "No Live Matches", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.LLCricket:
                intent.putExtra("sport","Cricket");
                startActivity(intent);
                break;
            case R.id.LLSoccer:
                intent.putExtra("sport","Soccer");
                startActivity(intent);
                break;
            case R.id.LLTennis:
                intent.putExtra("sport","Tennis");
                startActivity(intent);
                break;
        }
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
                                                if (!statinplay){
                                                    statinplay = true;
                                                    posinplay.add(0);
                                                    matchtype.add("Cricket");
                                                    listInPlay.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    matchtype.add("Cricket");
                                                    listInPlay.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        }else {
                                            for (int l = 0 ; l < infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.size(); l++){
                                                String[] matchdate = infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate.split(" ");
                                                Log.d("DATATATMatch",""+matchdate[0]+" "+todaydate[0]);
                                                if (todaydate[0].equalsIgnoreCase(matchdate[0])){
                                                    listUpcoming.add(new MatchData("Cricket",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        }
                                    }
                                }

                                statinplay = false;

                            } else if (infoData.sportsData.get(i).name.equalsIgnoreCase("Soccer")){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("1")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!statinplay){
                                                    statinplay = true;
                                                    posinplay.add(listInPlay.size());
                                                    matchtype.add("Soccer");
                                                    listInPlay.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    matchtype.add("Soccer");
                                                    listInPlay.add(new MatchData("Soccer",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }

                                            }
                                        }else {
                                            for (int l = 0 ; l < infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.size(); l++) {
                                                String[] matchdate = infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate.split(" ");
                                                Log.d("DATATATMatch", "" + matchdate[0]+" "+todaydate[0]);
                                                if (todaydate[0].equalsIgnoreCase(matchdate[0])) {
                                                    listUpcoming.add(new MatchData("Soccer", infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        }
                                    }
                                }

                                statinplay = false;

                            } else if (infoData.sportsData.get(i).name.equalsIgnoreCase("Tennis")){
                                for (int j = 0 ; j < infoData.sportsData.get(i).tournaments.size(); j++){
                                    for (int k = 0 ; k < infoData.sportsData.get(i).tournaments.get(j).matches.size() ; k++){
                                        if (infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay.equalsIgnoreCase("1")){
                                            for (int l = 0 ; l < 1; l++){
                                                if (!statinplay){
                                                    statinplay = true;
                                                    posinplay.add(listInPlay.size());
                                                    matchtype.add("Tennis");
                                                    listInPlay.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }else {
                                                    matchtype.add("Tennis");
                                                    listInPlay.add(new MatchData("Tennis",infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        } else {
                                            for (int l = 0 ; l < infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.size(); l++) {
                                                String[] matchdate = infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate.split(" ");
                                                if (todaydate[0].equalsIgnoreCase(matchdate[0])) {
                                                    listUpcoming.add(new MatchData("Tennis", infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Name, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Name, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner1Back, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).runnerData.runner2Back, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).startDate, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).inPlay, infoData.sportsData.get(i).tournaments.get(j).matches.get(k).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).markets.get(l).bfId,infoData.sportsData.get(i).tournaments.get(j).matches.get(k).id));
                                                }
                                            }
                                        }
                                    }
                                }
                                statinplay = false;
                            }
                        }


                        if (listInPlay.size() > 0){
                            Rv_Liveinplay.setVisibility(View.VISIBLE);
                            Rv_Liveinplay.setLayoutManager(new LinearLayoutManager(getContext()));
                            Rv_Liveinplay.setItemAnimator(new DefaultItemAnimator());
                            inplayAdapter = new AllInPlayAdapter(getContext(), listInPlay, posinplay, scorelist1, scorelist2,matchtype);
                            Rv_Liveinplay.setAdapter(inplayAdapter);
                            getScore();
                        }else {
                            Rv_Liveinplay.setVisibility(View.GONE);
                        }

                        if (listUpcoming.size() > 0){
                            Rv_Featured.setVisibility(View.VISIBLE);
                            Rv_Featured.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                            Rv_Featured.setItemAnimator(new DefaultItemAnimator());
                            FeaturedAdapter featuredAdapter = new FeaturedAdapter(getContext(), listUpcoming);
                            Rv_Featured.setAdapter(featuredAdapter);
                        }else {
                            Rv_Featured.setVisibility(View.GONE);
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

    private void getScore() {
        for (int i = 0; i < listInPlay.size(); i++) {

            if (matchtype.get(i).equalsIgnoreCase("Cricket")) {
                getCricketScore(listInPlay.get(i).getMatchId(), listInPlay.get(i).getTeamName1(), listInPlay.get(i).getTeamName2());
            } else if (matchtype.get(i).equalsIgnoreCase("Soccer")) {
                getSoccerScore(listInPlay.get(i).getMatchId(), listInPlay.get(i).getTeamName1(), listInPlay.get(i).getTeamName2());
            } else if (matchtype.get(i).equalsIgnoreCase("Tennis")){
                getTennisScore(listInPlay.get(i).getMatchId(), listInPlay.get(i).getTeamName1(), listInPlay.get(i).getTeamName2());
            }
        }
    }

    private void getCricketScore(final String matchid, final String teamname1, final String teamname2) {
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<CricketScoreData> call = apiInterface.getCricketScore(matchid);
        call.enqueue(new Callback<CricketScoreData>() {
            @Override
            public void onResponse(Call<CricketScoreData> call, Response<CricketScoreData> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        CricketScoreData scoreData = response.body();
                        if (scoreData.data == null || scoreData.data.size() == 0) {
                            scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "cricket"));
                            scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "cricket"));
                        } else {

                            if (scoreData.data.get(0).score.home.inning2 != null || scoreData.data.get(0).score.away.inning2 != null) {
                                if (scoreData.data.get(0).score.home.inning2 == null) {
                                    scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "cricket"));
                                } else {
                                    scorelist1.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.home.name, scoreData.data.get(0).score.home.inning2.runs, scoreData.data.get(0).score.home.inning2.wickets, "cricket"));
                                }

                                if (scoreData.data.get(0).score.away.inning2 == null) {
                                    scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "cricket"));
                                } else {
                                    scorelist2.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.away.name, scoreData.data.get(0).score.away.inning2.runs, scoreData.data.get(0).score.away.inning2.wickets, "cricket"));
                                }
                            } else {
                                if (scoreData.data.get(0).score.home.inning1 == null) {
                                    scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "0 overs"));
                                } else {
                                    scorelist1.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.home.name, scoreData.data.get(0).score.home.inning1.runs, scoreData.data.get(0).score.home.inning1.wickets, "cricket"));
                                }

                                if (scoreData.data.get(0).score.away.inning1 == null) {
                                    scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "cricket"));
                                } else {
                                    scorelist2.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.away.name, scoreData.data.get(0).score.away.inning1.runs, scoreData.data.get(0).score.away.inning1.wickets, "cricket"));
                                }
                            }

                        }

                        inplayAdapter.updateScore(scorelist1, scorelist2);
                    }
                }
            }

            @Override
            public void onFailure(Call<CricketScoreData> call, Throwable t) {
                Log.d("DATATATA", "" + t);
            }
        });
    }

    private void getTennisScore(final String matchid, final String teamname1, final String teamname2) {
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<TennisScoreData> call = apiInterface.getTennisScore(matchid);
        call.enqueue(new Callback<TennisScoreData>() {
            @Override
            public void onResponse(Call<TennisScoreData> call, Response<TennisScoreData> response) {
                if (response.code() == 200) {
                    if (response.body() != null){
                        TennisScoreData scoreData = response.body();
                        if (scoreData.data == null || scoreData.data.size() == 0){
                            scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "tennis"));
                            scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "tennis"));
                        }else {
                            if (scoreData.data.get(0).matchStatus.equalsIgnoreCase("Inprogress")){
                                scorelist1.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.home.name,scoreData.data.get(0).score.home.score,scoreData.data.get(0).currentSet,"tennis"));
                                scorelist2.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.away.name,scoreData.data.get(0).score.away.score,scoreData.data.get(0).currentSet,"tennis"));
                            }else {
                                scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "tennis"));
                                scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "tennis"));
                            }

                        }

                        inplayAdapter.updateScore(scorelist1, scorelist2);
                    }
                }
            }

            @Override
            public void onFailure(Call<TennisScoreData> call, Throwable t) {
                Log.d("DATATATA", "" + t);
            }
        });
    }

    private void getSoccerScore(final String matchid, final String teamname1, final String teamname2) {
        ApiInterface apiInterface = APICall.getScore().create(ApiInterface.class);
        Call<SoccerScoreData> call = apiInterface.getSoccerScore(matchid);
        call.enqueue(new Callback<SoccerScoreData>() {
            @Override
            public void onResponse(Call<SoccerScoreData> call, Response<SoccerScoreData> response) {
                if (response.code() == 200) {
                    if (response.body() != null){
                        SoccerScoreData scoreData = response.body();
                        if (scoreData.data == null || scoreData.data.size() == 0){
                            scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "soccer"));
                            scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "soccer"));
                        }else {
                            scorelist1.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.home.name,scoreData.data.get(0).score.home.score,"0","soccer"));
                            scorelist2.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.away.name,scoreData.data.get(0).score.away.score,"0","soccer"));
                        }

                        inplayAdapter.updateScore(scorelist1, scorelist2);
                    }
                }
            }

            @Override
            public void onFailure(Call<SoccerScoreData> call, Throwable t) {
                Log.d("DATATATA", "" + t);
            }
        });
    }
}

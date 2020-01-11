package com.app.bet.HomeScreen.Matches.LiveMatch;

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

import com.app.bet.HomeScreen.Matches.MatchData;
import com.app.bet.R;
import com.app.bet.Util.APICall;
import com.app.bet.Util.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveFragment extends Fragment {

    private RecyclerView Rv_Liveinplay;
    private TextView TvNoInplay;
    private LiveMatchAdapter inplayAdapter;
    private ArrayList<MatchData> listInPlay;
    private ArrayList<Integer> pos;
    private ArrayList<ScoreData> scorelist1 = new ArrayList<>();
    private ArrayList<ScoreData> scorelist2 = new ArrayList<>();
    private ArrayList<String> matchtype;

    public LiveFragment(ArrayList<MatchData> listInPlay, ArrayList<Integer> pos, ArrayList<String> matchtype) {
        this.listInPlay = listInPlay;
        this.pos = pos;
        this.matchtype = matchtype;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        Rv_Liveinplay = view.findViewById(R.id.Rv_Liveinplay);
        TvNoInplay = view.findViewById(R.id.TvNoInplay);

        if (listInPlay.size() > 0) {
            TvNoInplay.setVisibility(View.GONE);
            Rv_Liveinplay.setVisibility(View.VISIBLE);
            Rv_Liveinplay.setLayoutManager(new LinearLayoutManager(getContext()));
            Rv_Liveinplay.setItemAnimator(new DefaultItemAnimator());
            inplayAdapter = new LiveMatchAdapter(getContext(), listInPlay, pos, scorelist1, scorelist2,matchtype);
            Rv_Liveinplay.setAdapter(inplayAdapter);
            getScore();
        } else {
            TvNoInplay.setVisibility(View.VISIBLE);
            Rv_Liveinplay.setVisibility(View.GONE);
        }

        return view;
    }

    private void getScore() {
        for (int i = 0; i < listInPlay.size(); i++) {

            if (matchtype.get(i).equalsIgnoreCase("Cricket")) {
                Log.d("HAHAHA", "Call API for Cricket position "+i);
                getCricketScore(listInPlay.get(i).getMatchId(), listInPlay.get(i).getTeamName1(), listInPlay.get(i).getTeamName2());
            } else if (matchtype.get(i).equalsIgnoreCase("Soccer")) {
                Log.d("HAHAHA", "Call API for Soccer position "+i);
                getSoccerScore(listInPlay.get(i).getMatchId(), listInPlay.get(i).getTeamName1(), listInPlay.get(i).getTeamName2());
            } else if (matchtype.get(i).equalsIgnoreCase("Tennis")){
                Log.d("HAHAHA", "Call API for Tennis position "+i);
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
                            scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "0 overs"));
                            scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "0 overs"));
                        } else {

                            if (scoreData.data.get(0).score.home.inning2 != null || scoreData.data.get(0).score.away.inning2 != null) {
                                if (scoreData.data.get(0).score.home.inning2 == null) {
                                    scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "0 overs"));
                                } else {
                                    scorelist1.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.home.name, scoreData.data.get(0).score.home.inning2.runs, scoreData.data.get(0).score.home.inning2.wickets, scoreData.data.get(0).score.home.inning2.overs+" overs"));
                                }

                                if (scoreData.data.get(0).score.away.inning2 == null) {
                                    scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "0 overs"));
                                } else {
                                    scorelist2.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.away.name, scoreData.data.get(0).score.away.inning2.runs, scoreData.data.get(0).score.away.inning2.wickets, scoreData.data.get(0).score.away.inning2.overs+" overs"));
                                }
                            } else {
                                if (scoreData.data.get(0).score.home.inning1 == null) {
                                    scorelist1.add(new ScoreData(matchid, teamname1, "0", "0", "0 overs"));
                                } else {
                                    scorelist1.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.home.name, scoreData.data.get(0).score.home.inning1.runs, scoreData.data.get(0).score.home.inning1.wickets, scoreData.data.get(0).score.home.inning1.overs+" overs"));
                                }

                                if (scoreData.data.get(0).score.away.inning1 == null) {
                                    scorelist2.add(new ScoreData(matchid, teamname2, "0", "0", "0 overs"));
                                } else {
                                    scorelist2.add(new ScoreData(scoreData.data.get(0).eventId, scoreData.data.get(0).score.away.name, scoreData.data.get(0).score.away.inning1.runs, scoreData.data.get(0).score.away.inning1.wickets, scoreData.data.get(0).score.away.inning1.overs+" overs"));
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
                            scorelist1.add(new ScoreData(matchid, teamname1, "Set", "0", "Score 0"));
                            scorelist2.add(new ScoreData(matchid, teamname2, "Set", "0", "Score 0"));
                        }else {
                            if (scoreData.data.get(0).matchStatus.equalsIgnoreCase("Inprogress")){
                                scorelist1.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.home.name,"Set",scoreData.data.get(0).currentSet,"Score "+scoreData.data.get(0).score.home.score));
                                scorelist2.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.away.name,"Set",scoreData.data.get(0).currentSet,"Score "+scoreData.data.get(0).score.away.score));
                            }else {
                                scorelist1.add(new ScoreData(matchid, teamname1, "Set", "0", "Score 0"));
                                scorelist2.add(new ScoreData(matchid, teamname2, "Set", "0", "Score 0"));
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
                            scorelist1.add(new ScoreData(matchid, teamname1, "Goals", "0 Goals", ""));
                            scorelist2.add(new ScoreData(matchid, teamname2, "Goals", "0 Goals", ""));
                        }else {
                            scorelist1.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.home.name,"Goals",scoreData.data.get(0).score.home.score+" Goals",""));
                            scorelist2.add(new ScoreData(scoreData.data.get(0).eventId,scoreData.data.get(0).score.away.name,"Goals",scoreData.data.get(0).score.away.score+" Goals",""));
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

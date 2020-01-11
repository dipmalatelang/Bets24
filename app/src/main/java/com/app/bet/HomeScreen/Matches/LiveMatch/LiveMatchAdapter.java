package com.app.bet.HomeScreen.Matches.LiveMatch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bet.HomeScreen.Matches.MatchData;
import com.app.bet.InplayMatchInfo.InplayActivity;
import com.app.bet.R;

import java.util.ArrayList;

public class LiveMatchAdapter extends RecyclerView.Adapter<LiveMatchAdapter.MyViewHolder> {

    Context context;
    ArrayList<MatchData> listInPlay;
    ArrayList<Integer> pos;
    ArrayList<ScoreData> scorelist1;
    ArrayList<ScoreData> scorelist2;
    ArrayList<String> matchtype;

    public LiveMatchAdapter(Context context, ArrayList<MatchData> listInPlay, ArrayList<Integer> pos, ArrayList<ScoreData> scorelist1 , ArrayList<ScoreData> scorelist2,ArrayList<String> matchtype) {
        this.context = context;
        this.listInPlay = listInPlay;
        this.pos = pos;
        this.scorelist1 = scorelist1;
        this.scorelist2 = scorelist2;
        this.matchtype = matchtype;
    }

    @NonNull
    @Override
    public LiveMatchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_live_matches_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveMatchAdapter.MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        for (int i = 0 ; i < pos.size() ; i++){
            if (position == pos.get(i)){
                holder.LLHead.setVisibility(View.VISIBLE);
            }
        }

        holder.TvTitle.setText(listInPlay.get(position).getTeamName1()+" vs "+listInPlay.get(position).getTeamName2());
        holder.TvSportTitle.setText(listInPlay.get(position).getSportName());
        String[] arrteam1 , arrteam2;
        arrteam1 = listInPlay.get(position).getTeamName1().split(" ");
        arrteam2 = listInPlay.get(position).getTeamName2().split(" ");

        String team1 = "", team2 = "";

        if (arrteam1.length == 1){
            for (String s : arrteam1) {
                team1 = s.substring(0, 3);
            }
        }else {
            for (String s : arrteam1) {
                team1 = team1.concat(String.valueOf(s.charAt(0)));
            }
        }

        if (arrteam2.length == 1){
            for (String s : arrteam2) {
                team2 = s.substring(0, 3);
            }
        }else {
            for (String s : arrteam2) {
                team2 = team2.concat(String.valueOf(s.charAt(0)));
            }
        }

        holder.TvTeam1.setText(team1.toUpperCase());
        holder.TvTeam2.setText(team2.toUpperCase());

        if (scorelist1.size() == 0 && scorelist2.size() == 0){
            holder.TvScore1.setText("0");
            holder.TvScore2.setText("0");
            holder.TvWicket1.setText("0");
            holder.TvWicket2.setText("0");
            holder.TvOvers1.setText("0");
            holder.TvOvers2.setText("0");
        } else {
            for (int i = 0 ; i < scorelist1.size() ; i++){
                if (listInPlay.get(position).getMatchId().equalsIgnoreCase(scorelist1.get(i).getMatchId())){
                    if (listInPlay.get(position).getTeamName1().equalsIgnoreCase(scorelist1.get(i).getTeamName1())){
                        holder.TvScore1.setText(scorelist1.get(i).getTeamRuns1());
                        holder.TvOvers1.setText(scorelist1.get(i).getTeamOver1());

                        if (scorelist1.get(i).getTeamWickets1().equalsIgnoreCase("ALL_OUT")){
                            holder.TvWicket1.setText("All Out");
                        }else {
                            holder.TvWicket1.setText(scorelist1.get(i).getTeamWickets1());
                        }

                        if (scorelist1.get(i).getTeamRuns1().equalsIgnoreCase("Goals")){
                            holder.TvScore1.setVisibility(View.GONE);
                            holder.TvSlash1.setVisibility(View.GONE);
                        }
                    }

                    if (listInPlay.get(position).getTeamName2().equalsIgnoreCase(scorelist1.get(i).getTeamName1())){
                        holder.TvScore2.setText(scorelist1.get(i).getTeamRuns1());
                        holder.TvOvers2.setText(scorelist1.get(i).getTeamOver1());

                        if (scorelist1.get(i).getTeamWickets1().equalsIgnoreCase("ALL_OUT")){
                            holder.TvWicket2.setText("All Out");
                        }else {
                            holder.TvWicket2.setText(scorelist1.get(i).getTeamWickets1());
                        }

                        if (scorelist1.get(i).getTeamRuns1().equalsIgnoreCase("Goals")){
                            holder.TvScore2.setVisibility(View.GONE);
                            holder.TvSlash2.setVisibility(View.GONE);
                        }
                    }
                }
            }

            for (int i = 0 ; i < scorelist2.size() ; i++){
                Log.d("DATTATAA",""+scorelist2.get(i).getTeamName1());
                if (listInPlay.get(position).getMatchId().equalsIgnoreCase(scorelist2.get(i).getMatchId())){
                    if (listInPlay.get(position).getTeamName1().equalsIgnoreCase(scorelist2.get(i).getTeamName1())){
                        holder.TvScore1.setText(scorelist2.get(i).getTeamRuns1());
                        holder.TvOvers1.setText(scorelist2.get(i).getTeamOver1());

                        if (scorelist2.get(i).getTeamWickets1().equalsIgnoreCase("ALL_OUT")){
                            holder.TvWicket1.setText("All Out");
                        }else {
                            holder.TvWicket1.setText(scorelist2.get(i).getTeamWickets1());
                        }

                        if (scorelist2.get(i).getTeamRuns1().equalsIgnoreCase("Goals")){
                            holder.TvScore1.setVisibility(View.GONE);
                            holder.TvSlash1.setVisibility(View.GONE);
                        }
                    }

                    if (listInPlay.get(position).getTeamName2().equalsIgnoreCase(scorelist2.get(i).getTeamName1())){
                        holder.TvScore2.setText(scorelist2.get(i).getTeamRuns1());
                        holder.TvOvers2.setText(scorelist2.get(i).getTeamOver1());

                        if (scorelist2.get(i).getTeamWickets1().equalsIgnoreCase("ALL_OUT")){
                            holder.TvWicket2.setText("All Out");
                        }else {
                            holder.TvWicket2.setText(scorelist2.get(i).getTeamWickets1());
                        }

                        if (scorelist2.get(i).getTeamRuns1().equalsIgnoreCase("Goals")){
                            holder.TvScore2.setVisibility(View.GONE);
                            holder.TvSlash2.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }

        holder.CVLiveMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InplayActivity.class);
                intent.putExtra("SportName", matchtype.get(position));
                intent.putExtra("MatchId", listInPlay.get(position).getMatchId());
                intent.putExtra("MarketId", listInPlay.get(position).getMarketId());
                intent.putExtra("FancyId", listInPlay.get(position).getFancyid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listInPlay.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView CVLiveMatch;
        LinearLayout LLHead;
        TextView TvSportTitle , TvTitle , TvTeam1, TvTeam2, TvScore1, TvWicket1 ,TvOvers1 , TvScore2 , TvWicket2 , TvOvers2,TvSlash1,TvSlash2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LLHead = itemView.findViewById(R.id.LLHead);
            TvSportTitle = itemView.findViewById(R.id.TvSportTitle);
            TvTitle = itemView.findViewById(R.id.TvTitle);
            TvTeam1 = itemView.findViewById(R.id.TvTeam1);
            TvTeam2 = itemView.findViewById(R.id.TvTeam2);
            TvWicket1 = itemView.findViewById(R.id.TvWicket1);
            TvWicket2 = itemView.findViewById(R.id.TvWicket2);
            TvScore1 = itemView.findViewById(R.id.TvScore1);
            TvOvers1 = itemView.findViewById(R.id.TvOvers1);
            TvScore2 = itemView.findViewById(R.id.TvScore2);
            TvOvers2 = itemView.findViewById(R.id.TvOvers2);
            TvSlash1 = itemView.findViewById(R.id.TvSlash1);
            TvSlash2 = itemView.findViewById(R.id.TvSlash2);
            CVLiveMatch = itemView.findViewById(R.id.CVLiveMatch);
        }
    }

    public void updateScore(ArrayList<ScoreData> scorelist1 , ArrayList<ScoreData> scorelist2){
        this.scorelist1 = scorelist1;
        this.scorelist2 = scorelist2;
        notifyDataSetChanged();
    }
}

package com.app.bet.HomeScreen.Home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bet.HomeScreen.Matches.LiveMatch.ScoreData;
import com.app.bet.HomeScreen.Matches.MatchData;
import com.app.bet.InplayMatchInfo.InplayActivity;
import com.app.bet.R;

import java.util.ArrayList;

public class AllInPlayAdapter extends RecyclerView.Adapter<AllInPlayAdapter.MyViewHolder> {

    Context context;
    ArrayList<MatchData> listInPlay;
    ArrayList<Integer> pos;
    ArrayList<ScoreData> scorelist1;
    ArrayList<ScoreData> scorelist2;
    ArrayList<String> matchtype;

    public AllInPlayAdapter(Context context, ArrayList<MatchData> listInPlay, ArrayList<Integer> pos, ArrayList<ScoreData> scorelist1 , ArrayList<ScoreData> scorelist2,ArrayList<String> matchtype) {
        this.context = context;
        this.listInPlay = listInPlay;
        this.pos = pos;
        this.scorelist1 = scorelist1;
        this.scorelist2 = scorelist2;
        this.matchtype = matchtype;
    }

    @NonNull
    @Override
    public AllInPlayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inplay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllInPlayAdapter.MyViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        for (int i = 0 ; i < pos.size() ; i++){
            if (position == pos.get(i)){
                holder.LL_Title.setVisibility(View.VISIBLE);
            }
        }

        holder.TvTitle.setText(listInPlay.get(position).getSportName());
        holder.TvTeam1.setText(listInPlay.get(position).getTeamName1());
        holder.TvTeam2.setText(listInPlay.get(position).getTeamName2());
        holder.TvBack1.setText(listInPlay.get(position).getTeamBack1());
        holder.TvBack2.setText(listInPlay.get(position).getTeamBack2());

        String[] time = listInPlay.get(position).getMatchTime().split(" ");

        holder.TvTime.setText("Live "+time[1]);

        if (scorelist1.size() == 0 && scorelist2.size() == 0){
            holder.TvScore1.setText("0");
            holder.TvScore2.setText("0");
        }else {
            for (int i = 0 ; i < scorelist1.size() ; i++){
                if (listInPlay.get(position).getMatchId().equalsIgnoreCase(scorelist1.get(i).getMatchId())){
                    if (listInPlay.get(position).getTeamName1().equalsIgnoreCase(scorelist1.get(i).getTeamName1())){
                        holder.TvScore1.setText(scorelist1.get(i).getTeamRuns1());
                    }

                    if (listInPlay.get(position).getTeamName2().equalsIgnoreCase(scorelist1.get(i).getTeamName1())){
                        holder.TvScore2.setText(scorelist1.get(i).getTeamRuns1());
                    }
                }
            }

            for (int i = 0 ; i < scorelist2.size() ; i++){
                if (listInPlay.get(position).getMatchId().equalsIgnoreCase(scorelist2.get(i).getMatchId())){
                    if (listInPlay.get(position).getTeamName1().equalsIgnoreCase(scorelist2.get(i).getTeamName1())){
                        holder.TvScore1.setText(scorelist2.get(i).getTeamRuns1());
                    }

                    if (listInPlay.get(position).getTeamName2().equalsIgnoreCase(scorelist2.get(i).getTeamName1())){
                        holder.TvScore2.setText(scorelist2.get(i).getTeamRuns1());
                    }
                }
            }
        }

        holder.RLInplay.setOnClickListener(new View.OnClickListener() {
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

        LinearLayout LL_Title;
        TextView TvTitle, TvTeam1, TvTeam2, TvTime, TvBack1, TvBack2,TvScore1,TvScore2;
        RelativeLayout RLInplay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LL_Title = itemView.findViewById(R.id.LL_Title);
            TvTitle = itemView.findViewById(R.id.TvTitle);
            TvTeam1 = itemView.findViewById(R.id.TvTeam1);
            TvTeam2 = itemView.findViewById(R.id.TvTeam2);
            TvTime = itemView.findViewById(R.id.TvTime);
            TvBack1 = itemView.findViewById(R.id.TvBack1);
            TvBack2 = itemView.findViewById(R.id.TvBack2);
            TvScore1 = itemView.findViewById(R.id.TvScore1);
            TvScore2 = itemView.findViewById(R.id.TvScore2);
            RLInplay = itemView.findViewById(R.id.RLInplay);
        }
    }

    public void updateScore(ArrayList<ScoreData> scorelist1 , ArrayList<ScoreData> scorelist2){
        this.scorelist1 = scorelist1;
        this.scorelist2 = scorelist2;
        notifyDataSetChanged();
    }
}

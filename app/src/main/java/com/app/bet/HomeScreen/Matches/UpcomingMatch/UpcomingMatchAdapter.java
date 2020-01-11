package com.app.bet.HomeScreen.Matches.UpcomingMatch;

import android.content.Context;
import android.content.Intent;
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
import com.app.bet.R;
import com.app.bet.UpcomingMatchInfo.UpcomingActivity;

import java.util.ArrayList;

public class UpcomingMatchAdapter extends RecyclerView.Adapter<UpcomingMatchAdapter.MyViewHolder> {

    Context context;
    ArrayList<MatchData> listInPlay;
    ArrayList<Integer> pos;

    public UpcomingMatchAdapter(Context context, ArrayList<MatchData> listInPlay, ArrayList<Integer> pos) {
        this.context = context;
        this.listInPlay = listInPlay;
        this.pos = pos;
    }

    @NonNull
    @Override
    public UpcomingMatchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_upcoming_matches_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMatchAdapter.MyViewHolder holder, final int position) {
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

        holder.TvSlash1.setText(team1.toUpperCase());
        holder.TvSlash2.setText(team2.toUpperCase());
        holder.TvTime.setText(listInPlay.get(position).getMatchTime());

        holder.CVUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpcomingActivity.class);
                intent.putExtra("Team1",listInPlay.get(position).getTeamName1());
                intent.putExtra("Team2",listInPlay.get(position).getTeamName2());
                intent.putExtra("Time","on "+listInPlay.get(position).getMatchTime());
                intent.putExtra("Sport",listInPlay.get(position).getSportName());
                intent.putExtra("MarketId", listInPlay.get(position).getMarketId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listInPlay.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout LLHead;
        TextView TvSportTitle , TvTitle , TvSlash1, TvSlash2,TvTime;
        CardView CVUpcoming;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LLHead = itemView.findViewById(R.id.LLHead);
            TvSportTitle = itemView.findViewById(R.id.TvSportTitle);
            TvTitle = itemView.findViewById(R.id.TvTitle);
            TvSlash1 = itemView.findViewById(R.id.TvSlash1);
            TvSlash2 = itemView.findViewById(R.id.TvSlash2);
            TvTime = itemView.findViewById(R.id.TvTime);
            CVUpcoming = itemView.findViewById(R.id.CVUpcoming);
        }
    }
}

package com.app.bet.HomeScreen.Home;

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
import com.app.bet.InplayMatchInfo.InplayActivity;
import com.app.bet.R;
import com.app.bet.UpcomingMatchInfo.UpcomingActivity;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder> {

    Context context;
    ArrayList<MatchData> listInPlay;

    public FeaturedAdapter(Context context, ArrayList<MatchData> listInPlay) {
        this.context = context;
        this.listInPlay = listInPlay;
    }

    @NonNull
    @Override
    public FeaturedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_featured, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.MyViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        final String[] time = listInPlay.get(position).getMatchTime().split(" ");

        holder.TvTeam1.setText(listInPlay.get(position).getTeamName1());
        holder.TvTeam2.setText(listInPlay.get(position).getTeamName2());
        holder.TvBack1.setText(listInPlay.get(position).getTeamBack1());
        holder.TvBack2.setText(listInPlay.get(position).getTeamBack2());
        holder.TvTime.setText("Today at "+time[1]);
        holder.CVFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpcomingActivity.class);
                intent.putExtra("Team1",listInPlay.get(position).getTeamName1());
                intent.putExtra("Team2",listInPlay.get(position).getTeamName2());
                intent.putExtra("Time","Today by "+time[1]);
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

        TextView TvTeam1, TvTeam2, TvTime, TvBack1, TvBack2;
        CardView CVFeatured;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTeam1 = itemView.findViewById(R.id.TvTeam1);
            TvTeam2 = itemView.findViewById(R.id.TvTeam2);
            TvTime = itemView.findViewById(R.id.TvTime);
            TvBack1 = itemView.findViewById(R.id.TvBack1);
            TvBack2 = itemView.findViewById(R.id.TvBack2);
            CVFeatured = itemView.findViewById(R.id.CVFeatured);
        }
    }
}

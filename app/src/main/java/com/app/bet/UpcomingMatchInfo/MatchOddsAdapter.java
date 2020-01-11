package com.app.bet.UpcomingMatchInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bet.MatchData.MatchOddsData;
import com.app.bet.R;

import java.util.ArrayList;

public class MatchOddsAdapter extends RecyclerView.Adapter<MatchOddsAdapter.MyViewHolder>{
    Context context;
    ArrayList<MatchOddsData.data_info.runnerData_info> matchOddsDatalist;

    public MatchOddsAdapter(Context context, ArrayList<MatchOddsData.data_info.runnerData_info> matchOddsDatalist) {
        this.context = context;
        this.matchOddsDatalist = matchOddsDatalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_match_odds, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MatchOddsData.data_info.runnerData_info data = matchOddsDatalist.get(position);
        holder.Tv_TeamName.setText(data.runnerName);
        holder.Tv_Back.setText(data.back1);
        holder.Tv_back_vol.setText(data.backSize1);
        holder.Tv_Lay.setText(data.lay1);
        holder.Tv_lay_vol.setText(data.laySize1);
    }

    @Override
    public int getItemCount() {
        return matchOddsDatalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Tv_TeamName, Tv_Back, Tv_back_vol, Tv_Lay, Tv_lay_vol;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_TeamName = itemView.findViewById(R.id.Tv_TeamName);
            Tv_Back = itemView.findViewById(R.id.Tv_Back);
            Tv_back_vol = itemView.findViewById(R.id.Tv_back_vol);
            Tv_Lay = itemView.findViewById(R.id.Tv_Lay);
            Tv_lay_vol = itemView.findViewById(R.id.Tv_lay_vol);
        }
    }

    public void refreshOdds(ArrayList<MatchOddsData.data_info.runnerData_info> matchOddsDatalist){
        this.matchOddsDatalist = matchOddsDatalist;
        notifyDataSetChanged();
    }
}

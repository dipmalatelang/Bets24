package com.app.bet.MatchData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bet.R;

import java.util.ArrayList;

public class FancyAdapter extends RecyclerView.Adapter<FancyAdapter.MyViewHolder>{
    Context context;
    ArrayList<FancyData.data_info> fancyDatalist;

    public FancyAdapter(Context context, ArrayList<FancyData.data_info> fancyDatalist) {
        this.context = context;
        this.fancyDatalist = fancyDatalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_match_odds, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FancyData.data_info data = fancyDatalist.get(position);
        holder.Tv_TeamName.setText(data.getName());
        holder.Tv_Back.setText(data.getNoScore());
        holder.Tv_Lay.setText(data.getYesScore());

    }

    @Override
    public int getItemCount() {
        return fancyDatalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Tv_TeamName, Tv_Back, Tv_Lay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_TeamName = itemView.findViewById(R.id.Tv_TeamName);
            Tv_Back = itemView.findViewById(R.id.Tv_Back);
            Tv_Lay = itemView.findViewById(R.id.Tv_Lay);
        }
    }
}

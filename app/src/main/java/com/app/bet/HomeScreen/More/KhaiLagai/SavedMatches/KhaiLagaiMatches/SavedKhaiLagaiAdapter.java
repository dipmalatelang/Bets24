package com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.KhaiLagaiMatches;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.app.bet.HomeScreen.More.KhaiLagai.DatabaseHelper;
import com.app.bet.HomeScreen.More.KhaiLagai.KhaiLagaiCalculator.KhaiLagaiPage;
import com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.SavedMatchesData;
import com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.SavedMatchesInterface;
import com.app.bet.R;

import java.util.ArrayList;

public class SavedKhaiLagaiAdapter extends RecyclerView.Adapter<SavedKhaiLagaiAdapter.Viewholder> {

    private Context context;
    private ArrayList<SavedMatchesData> savedMatchesList;
    private DatabaseHelper databaseHelper;
    private SavedMatchesInterface savedMatchesInterface;

     SavedKhaiLagaiAdapter(Context context, ArrayList<SavedMatchesData> savedMatchesList,SavedMatchesInterface savedMatchesInterface) {
        this.context = context;
        this.savedMatchesList = savedMatchesList;
        this.savedMatchesInterface = savedMatchesInterface;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutInflater = LayoutInflater.from(context).inflate(R.layout.layout_savedmatchesrecycle, parent, false);
        return new SavedKhaiLagaiAdapter.Viewholder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        databaseHelper = new DatabaseHelper(context);
        holder.tv_team1.setText(savedMatchesList.get(position).getTeamone());
        holder.tv_team2.setText(savedMatchesList.get(position).getTeamtwo());
        holder.tv_date.setText(savedMatchesList.get(position).getDate());
        holder.RL_saved_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KhaiLagaiPage.class);
                intent.putExtra("datetime",savedMatchesList.get(position).getDatetime());
                intent.putExtra("pagestart","saved");
                context.startActivity(intent);
            }
        });
        holder.IV_delete.setVisibility(View.VISIBLE);
        holder.IV_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Do you want to delete");
                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.deleteKLDdataDateTime(savedMatchesList.get(position).getDatetime());
                          databaseHelper.deleteKLMData(savedMatchesList.get(position).getId());
                            savedMatchesInterface.refreshData();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedMatchesList.size();
    }

     class Viewholder extends RecyclerView.ViewHolder {

        TextView tv_team1,tv_team2,tv_date;
        RelativeLayout RL_saved_matches;
        ImageView IV_delete;

         Viewholder(@NonNull View itemView) {
            super(itemView);
            IV_delete = itemView.findViewById(R.id.IV_delete);
            RL_saved_matches = itemView.findViewById(R.id.RL_saved_matches);
            tv_team1 = itemView.findViewById(R.id.tv_team1);
            tv_team2 = itemView.findViewById(R.id.tv_team2);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}

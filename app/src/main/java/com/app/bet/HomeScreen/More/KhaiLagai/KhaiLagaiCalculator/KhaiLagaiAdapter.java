package com.app.bet.HomeScreen.More.KhaiLagai.KhaiLagaiCalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.app.bet.HomeScreen.More.KhaiLagai.DatabaseHelper;
import com.app.bet.R;

import java.util.ArrayList;

public class KhaiLagaiAdapter extends RecyclerView.Adapter<KhaiLagaiAdapter.Viewholder> {

    private Context context;
    private ArrayList<KhaiLagaiData> khaiLagaiData;
    private String team1, team2;
    private KhaiLagaiInterface khaiLagaiInterface;
    private DatabaseHelper databaseHelper;

    KhaiLagaiAdapter(Context context, ArrayList<KhaiLagaiData> khaiLagaiData, String team1, String team2, KhaiLagaiInterface khaiLagaiInterface) {
        this.context = context;
        this.khaiLagaiData = khaiLagaiData;
        this.team1 = team1;
        this.team2 = team2;
        this.khaiLagaiInterface = khaiLagaiInterface;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutInflater = LayoutInflater.from(context).inflate(R.layout.layout_khailagairecycle, parent, false);
        return new KhaiLagaiAdapter.Viewholder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        holder.tv_Rate.setText(String.valueOf(khaiLagaiData.get(position).getRate()));
        holder.tv_Amount.setText(String.valueOf(khaiLagaiData.get(position).getAmount()));
        holder.tv_TeamName.setText(khaiLagaiData.get(position).getTeamname());
        holder.tv_KhaiLagai.setText(khaiLagaiData.get(position).getKhaiLagai());
        databaseHelper = new DatabaseHelper(context);
        float rate = khaiLagaiData.get(position).getRate();
        float amount = khaiLagaiData.get(position).getAmount();
        float LagaiLoss = amount * -1;
        float winningamount = rate * amount - amount;
        float FinalRate = rate - 1;
        float FinalWinning = FinalRate * amount * -1;

        if (khaiLagaiData.get(position).getKhaiLagai().equalsIgnoreCase("Lagai")) {

            if (khaiLagaiData.get(position).getTeamname().equalsIgnoreCase(team1)) {
                holder.tv_team1win.setText(String.valueOf(Math.round(winningamount)));
                holder.tv_team2win.setText(String.valueOf(Math.round(LagaiLoss)));
                holder.tv_Draw.setText(String.valueOf(Math.round(LagaiLoss)));
                khaiLagaiInterface.winnings(winningamount,LagaiLoss,LagaiLoss);
                Log.i("tttttttt","1 : "+winningamount);
                Log.i("tttttttt","2 : "+LagaiLoss);
            }
            else if (khaiLagaiData.get(position).getTeamname().equalsIgnoreCase(team2)) {
                holder.tv_team2win.setText(String.valueOf(Math.round(winningamount)));
                holder.tv_team1win.setText(String.valueOf(Math.round(LagaiLoss)));
                holder.tv_Draw.setText(String.valueOf(Math.round(LagaiLoss)));
                khaiLagaiInterface.winnings(LagaiLoss,winningamount,LagaiLoss);
                Log.i("tttttttt","3 : "+LagaiLoss);
                Log.i("tttttttt","4 : "+winningamount);
            }
        } else if (khaiLagaiData.get(position).getKhaiLagai().equalsIgnoreCase("Khai")) {

            if (khaiLagaiData.get(position).getTeamname().equalsIgnoreCase(team1)) {
                holder.tv_team1win.setText(String.valueOf(Math.round(FinalWinning)));
                holder.tv_team2win.setText(String.valueOf(Math.round(amount)));
                holder.tv_Draw.setText(String.valueOf(Math.round(amount)));
                khaiLagaiInterface.winnings(FinalWinning,amount,amount);
                Log.i("tttttttt","5 : "+FinalWinning);
                Log.i("tttttttt","6 : "+amount);
            }
            else if (khaiLagaiData.get(position).getTeamname().equalsIgnoreCase(team2)) {

                holder.tv_team2win.setText(String.valueOf(Math.round(FinalWinning)));
                holder.tv_team1win.setText(String.valueOf(Math.round(amount)));
                holder.tv_Draw.setText(String.valueOf(Math.round(amount)));
                khaiLagaiInterface.winnings(amount,FinalWinning,amount);
                Log.i("tttttttt","7 : "+amount);
                Log.i("tttttttt","8 : "+FinalWinning);
            }
        }

        holder.IV_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Do you want to Delete");
                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.deleteKLDData(khaiLagaiData.get(position).getId());
                        khaiLagaiInterface.refreshdata();

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
        return khaiLagaiData.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        TextView tv_Rate, tv_Amount, tv_KhaiLagai, tv_team1win, tv_team2win, tv_TeamName, tv_Draw;
        ImageView IV_delete;

        private Viewholder(@NonNull View itemView) {
            super(itemView);

            IV_delete = itemView.findViewById(R.id.IV_delete);
            tv_Rate = itemView.findViewById(R.id.tv_Rate);
            tv_Amount = itemView.findViewById(R.id.tv_Amount);
            tv_KhaiLagai = itemView.findViewById(R.id.tv_KhaiLagai);
            tv_team1win = itemView.findViewById(R.id.tv_team1win);
            tv_team2win = itemView.findViewById(R.id.tv_team2win);
            tv_TeamName = itemView.findViewById(R.id.tv_TeamName);
            tv_Draw = itemView.findViewById(R.id.tv_Draw);
        }
    }
}

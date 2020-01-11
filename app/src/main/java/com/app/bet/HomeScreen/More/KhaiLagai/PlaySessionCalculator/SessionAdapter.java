package com.app.bet.HomeScreen.More.KhaiLagai.PlaySessionCalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SessionData> sessionList;
    private SessionInterface sessionInterface;
    private DatabaseHelper databaseHelper;

    SessionAdapter(Context context, ArrayList<SessionData> sessionList, SessionInterface sessionInterface) {
        this.context = context;
        this.sessionList = sessionList;
        this.sessionInterface = sessionInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutInflater = LayoutInflater.from(context).inflate(R.layout.layout_sessionrecycle,parent,false);
        return new ViewHolder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tv_overDisplay.setText(sessionList.get(position).getOver());
        holder.tv_amountdisplay.setText(String.valueOf(sessionList.get(position).getAmount()));
        holder.tv_scoreDisplay.setText(String.valueOf(sessionList.get(position).getScore()));
        holder.tv_fancyDisplay.setText(String.valueOf(sessionList.get(position).getbidValue()));
        holder.tv_updownDisplay.setText(sessionList.get(position).getSelectedspinner());
        int profit = sessionList.get(position).getAmount();
        int loss = sessionList.get(position).getAmount() * -1;
        databaseHelper = new DatabaseHelper(context);

        if (sessionList.get(position).getSelectedspinner().equals("UP")) {
            if (sessionList.get(position).getScore() > sessionList.get(position).getbidValue()) {
                holder.tv_winloss.setText("WIN");
                holder.tv_winningAmount.setText(String.valueOf(profit));
                holder.tv_winningAmount.setBackgroundColor(Color.parseColor("#1FB313"));
                sessionInterface.profitloss(profit);
            } else {
                holder.tv_winloss.setText("LOSS");
                holder.tv_winningAmount.setText(String.valueOf(loss));
                holder.tv_winningAmount.setBackgroundColor(Color.parseColor("#FC2727"));
                sessionInterface.profitloss(loss);
            }
        } else if (sessionList.get(position).getSelectedspinner().equals("Down")) {
            if (sessionList.get(position).getbidValue() > sessionList.get(position).getScore()) {
                holder.tv_winloss.setText("WIN");
                holder.tv_winningAmount.setText(String.valueOf(profit));
                holder.tv_winningAmount.setBackgroundColor(Color.parseColor("#1FB313"));
                sessionInterface.profitloss(profit);
            } else {
                holder.tv_winloss.setText("LOSS");
                holder.tv_winningAmount.setText(String.valueOf(loss));
                holder.tv_winningAmount.setBackgroundColor(Color.parseColor("#FC2727"));
                sessionInterface.profitloss(loss);
            }
        }
        holder.IV_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Do you want to Delete");
                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deletePSDData(sessionList.get(position).getId());
                        sessionInterface.refreshdata();

                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_overDisplay, tv_fancyDisplay, tv_updownDisplay, tv_scoreDisplay, tv_amountdisplay, tv_winloss, tv_winningAmount;
        ImageView IV_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_overDisplay = itemView.findViewById(R.id.tv_overDisplay);
            tv_fancyDisplay = itemView.findViewById(R.id.tv_fancyDisplay);
            tv_updownDisplay = itemView.findViewById(R.id.tv_updownDisplay);
            tv_scoreDisplay = itemView.findViewById(R.id.tv_scoreDisplay);
            tv_amountdisplay = itemView.findViewById(R.id.tv_amountdisplay);
            tv_winloss = itemView.findViewById(R.id.tv_winloss);
            tv_winningAmount = itemView.findViewById(R.id.tv_winningAmount);
            IV_delete = itemView.findViewById(R.id.IV_delete);
        }
    }
}

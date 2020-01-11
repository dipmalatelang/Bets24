package com.app.bet.HomeScreen.More.KhaiLagai.PlaySessionCalculator;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.bet.HomeScreen.More.KhaiLagai.DatabaseHelper;
import com.app.bet.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class SessionPage extends AppCompatActivity implements SessionInterface {

    FloatingActionButton btn_add;
    Dialog dialog;
    EditText et_over,et_matchscore,et_yourbid,et_amount;
    Spinner spinner_updown;
    String team1, team2,currentDateandTime,Selectedspinner = "UP",pagedetail;
    int ProfitLoss = 0;
    DatabaseHelper databaseHelper;
    Button btn_cancel,btn_AddDialog;
    TextView TV_profit,TV_loss,TV_team1 , TV_team2;
    Intent intent;
    Cursor res;
    RecyclerView recyclerview_Session;
    SessionAdapter sessionAdapter;
    ArrayList<SessionData> SessionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_page);

        TV_profit = findViewById(R.id.TV_profit);
        TV_loss = findViewById(R.id.TV_loss);
        TV_team1 = findViewById(R.id.TV_team1);
        TV_team2 = findViewById(R.id.TV_team2);
        btn_add = findViewById(R.id.btn_add);
        recyclerview_Session = findViewById(R.id.recyclerview_Session);
        databaseHelper = new DatabaseHelper(SessionPage.this);

        intent = getIntent();
        pagedetail = intent.getStringExtra("pagestart");
        if(pagedetail != null && pagedetail.equals("fresh")){
            team1 = intent.getStringExtra("firstteam");
            team2 = intent.getStringExtra("secondteam");
            currentDateandTime = intent.getStringExtra("currentDateandTime");
            TV_team1.setText(team1);
            TV_team2.setText(team2);
        }
        else if(pagedetail != null && pagedetail.equals("saved")){
            currentDateandTime = intent.getStringExtra("datetime");
            res = databaseHelper.getPSMData(currentDateandTime);
            if(res.getCount() != 0){
                while (res.moveToNext()) {
                    team1 = res.getString(2);
                    team2 = res.getString(3);
                    TV_team1.setText(team1);
                    TV_team2.setText(team2);
                    TV_profit.setText(res.getString(5));
                    TV_loss.setText(res.getString(6));
                }
            }
             res = databaseHelper.getPSD(currentDateandTime);
            if(res.getCount() != 0){
                SessionList.clear();
                while (res.moveToNext()) {
                    String id = String.valueOf(res.getInt(0));
                    String over = res.getString(1);
                    String updown = res.getString(2);
                    int bidvalue = Integer.parseInt(res.getString(3));
                    int matchscore = Integer.parseInt(res.getString(4));
                    int amount = Integer.parseInt(res.getString(5));
                    SessionList.add(new SessionData(over,matchscore,bidvalue,amount,updown,id));
                }
            }
            recyclerview_Session.setLayoutManager(new LinearLayoutManager(SessionPage.this));
            sessionAdapter = new SessionAdapter(SessionPage.this,SessionList,SessionPage.this);
            recyclerview_Session.setAdapter(sessionAdapter);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDialog();
            }
        });
    }

    public void makeDialog(){

        dialog = new Dialog(SessionPage.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_session);
        dialog.show();

        et_over = dialog.findViewById(R.id.et_over);
        et_matchscore = dialog.findViewById(R.id.et_matchscore);
        et_yourbid = dialog.findViewById(R.id.et_yourbid);
        et_amount = dialog.findViewById(R.id.et_amount);
        btn_AddDialog = dialog.findViewById(R.id.btn_AddDialog);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        spinner_updown = dialog.findViewById(R.id.spinner_updown);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_AddDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_over.getText()) && TextUtils.isEmpty(et_matchscore.getText()) && TextUtils.isEmpty(et_yourbid.getText()) && TextUtils.isEmpty(et_amount.getText())){
                    Toast.makeText(SessionPage.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(et_over.getText())){
                    Toast.makeText(SessionPage.this, "Please Enter Over", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(et_matchscore.getText())){
                    Toast.makeText(SessionPage.this, "Please Enter Match Score", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(et_yourbid.getText())){
                    Toast.makeText(SessionPage.this, "Please Enter Your Bid", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(et_amount.getText())){
                    Toast.makeText(SessionPage.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    int bidValue = Integer.parseInt(et_yourbid.getText().toString());
                    int score = Integer.parseInt(et_matchscore.getText().toString());
                    int amountt = Integer.parseInt(et_amount.getText().toString());
                    String overr = et_over.getText().toString();
                    Selectedspinner = spinner_updown.getSelectedItem().toString();
                    databaseHelper.playSessionDataInsert(overr,Selectedspinner,
                            String.valueOf(bidValue),String.valueOf(score),
                            String.valueOf(amountt),currentDateandTime);

                    res = databaseHelper.getPSD(currentDateandTime);
                    if(res.getCount() != 0){
                        SessionList.clear();
                        while (res.moveToNext()) {
                            String id = String.valueOf(res.getInt(0));
                            String over = res.getString(1);
                            String updown = res.getString(2);
                            int bidvalue = Integer.parseInt(res.getString(3));
                            int matchscore = Integer.parseInt(res.getString(4));
                            int amount = Integer.parseInt(res.getString(5));
                            SessionList.add(new SessionData(over,matchscore,bidvalue,amount,updown,id));
                        }
                    }
                    if (SessionList.size() == 1){
                        recyclerview_Session.setLayoutManager(new LinearLayoutManager(SessionPage.this));
                        sessionAdapter = new SessionAdapter(SessionPage.this,SessionList,SessionPage.this);
                        recyclerview_Session.setAdapter(sessionAdapter);
                        dialog.dismiss();
                    }
                    else {
                        sessionAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
            }
        });

    }

    @Override
    public void profitloss(int profitloss) {
        ProfitLoss = ProfitLoss + profitloss;
        if(ProfitLoss > 0){
            TV_profit.setText(String.valueOf(ProfitLoss));
            TV_loss.setText("0");
            databaseHelper.updateplaySessionMatchesInsert(currentDateandTime,String.valueOf(ProfitLoss),"0");
        }
        else if(ProfitLoss < 0){
            TV_loss.setText(String.valueOf(ProfitLoss));
            TV_profit.setText("0");
            databaseHelper.updateplaySessionMatchesInsert(currentDateandTime,"0",String.valueOf(ProfitLoss));
        }
        else {
            TV_profit.setText("0");
            TV_loss.setText("0");
            databaseHelper.updateplaySessionMatchesInsert(currentDateandTime,"0","0");
        }
    }

    @Override
    public boolean refreshdata() {

        res = databaseHelper.getPSMData(currentDateandTime);
        if(res.getCount() != 0){
            while (res.moveToNext()) {
                team1 = res.getString(2);
                team2 = res.getString(3);
                TV_team1.setText(team1);
                TV_team2.setText(team2);
                TV_profit.setText(res.getString(5));
                TV_loss.setText(res.getString(6));
            }
        }

        res = databaseHelper.getPSD(currentDateandTime);
        if(res.getCount() == 0){
            if (sessionAdapter != null){
                SessionList.clear();
                sessionAdapter.notifyDataSetChanged();
            }
        }
        else {
            SessionList.clear();
            while (res.moveToNext()) {
                String id = String.valueOf(res.getInt(0));
                String over = res.getString(1);
                String updown = res.getString(2);
                int bidvalue = Integer.parseInt(res.getString(3));
                int matchscore = Integer.parseInt(res.getString(4));
                int amount = Integer.parseInt(res.getString(5));
                SessionList.add(new SessionData(over,matchscore,bidvalue,amount,updown,id));
                recyclerview_Session.setLayoutManager(new LinearLayoutManager(SessionPage.this));
                sessionAdapter = new SessionAdapter(SessionPage.this,SessionList,SessionPage.this);
                recyclerview_Session.setAdapter(sessionAdapter);
            }
        }
        return true;
    }
}

package com.app.bet.HomeScreen.More.KhaiLagai.KhaiLagaiCalculator;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
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

public class KhaiLagaiPage extends AppCompatActivity implements KhaiLagaiInterface {

    EditText et_rate,et_amount;
    Button btn_cancel,btn_AddDialog;
    FloatingActionButton btn_add;
    RecyclerView recyclerview_KhaiLagai;
    KhaiLagaiAdapter khaiLagaiAdapter;
    Dialog dialog;
    Spinner spinner_Teamname,spinner_KhaiLagai;
    ArrayList<String> teamname = new ArrayList<>();
    Intent intent;
    Cursor res;
    DatabaseHelper databaseHelper;
    float winningOne = 0 , winningTwo = 0,Draw = 0;
    String team1, team2 ,Teamname,KhaiLagai = "Khai",currentDateandTime,pagedetail;
    TextView TV_team1Name,TV_team2Name,TV_team1Winning,TV_team2Winning,TV_drawamount,TV_teamonename,TV_teamtwoname;
    ArrayList<KhaiLagaiData> KhaiLagaiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_lagai_page);

        btn_add = findViewById(R.id.btn_add);
        TV_team1Name = findViewById(R.id.TV_team1Name);
        TV_team2Name = findViewById(R.id.TV_team2Name);
        TV_teamonename = findViewById(R.id.TV_teamoneName);
        TV_teamtwoname = findViewById(R.id.TV_teamtwoName);
        TV_team1Winning = findViewById(R.id.TV_team1Winning);
        TV_team2Winning = findViewById(R.id.TV_team2Winning);
        TV_drawamount= findViewById(R.id.TV_drawamount);
        recyclerview_KhaiLagai = findViewById(R.id.recyclerview_KhaiLagai);
        databaseHelper = new DatabaseHelper(KhaiLagaiPage.this);

        intent = getIntent();
        pagedetail = intent.getStringExtra("pagestart");
        if(pagedetail!=null && pagedetail.equals("fresh")){
            team1 = intent.getStringExtra("firstteam");
            team2 = intent.getStringExtra("secondteam");
            currentDateandTime = intent.getStringExtra("currentDateandTime");

            teamname.add(team1);
            teamname.add(team2);

            TV_team1Name.setText(team1);
            TV_team2Name.setText(team2);
            TV_teamonename.setText(team1);
            TV_teamtwoname.setText(team2);
        }
        else if(pagedetail!=null && pagedetail.equals("saved")){
             currentDateandTime = intent.getStringExtra("datetime");

            res = databaseHelper.getKLMData(currentDateandTime);
            if(res.getCount() != 0){
                while (res.moveToNext()) {
                    team1 = res.getString(2);
                    team2 = res.getString(3);
                    teamname.add(team1);
                    teamname.add(team2);
                    TV_team1Name.setText(res.getString(2));
                    TV_team2Name.setText(res.getString(3));
                    TV_teamonename.setText(res.getString(2));
                    TV_teamtwoname.setText(res.getString(3));
                    TV_team1Winning.setText(res.getString(5));
                    TV_drawamount.setText(res.getString(6));
                    TV_team2Winning.setText(res.getString(7));
                }
            }

            res = databaseHelper.getKLD(currentDateandTime);
            if(res.getCount() != 0){
                KhaiLagaiList.clear();
                while (res.moveToNext()) {
                    String id = String.valueOf(res.getInt(0));
                    float rate = Float.parseFloat(res.getString(1));
                    int amount = Integer.parseInt(res.getString(2));
                    String khai_lagai = res.getString(3);
                    String betteam = res.getString(4);
                    KhaiLagaiList.add(new KhaiLagaiData(rate,amount,betteam,khai_lagai,id));
                }
            }
            recyclerview_KhaiLagai.setLayoutManager(new LinearLayoutManager(KhaiLagaiPage.this));
            khaiLagaiAdapter = new KhaiLagaiAdapter(KhaiLagaiPage.this,KhaiLagaiList,team1,team2,KhaiLagaiPage.this);
            recyclerview_KhaiLagai.setAdapter(khaiLagaiAdapter);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makedialog();
            }
        });
    }


    private void makedialog() {

        dialog = new Dialog(KhaiLagaiPage.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_khai_lagai);
        dialog.show();

        et_rate = dialog.findViewById(R.id.et_rate);
        et_amount = dialog.findViewById(R.id.et_amount);
        spinner_KhaiLagai = dialog.findViewById(R.id.spinner_KhaiLagai);
        spinner_Teamname = dialog.findViewById(R.id.spinner_Teamname);
        btn_AddDialog = dialog.findViewById(R.id.btn_AddDialog);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,teamname);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Teamname.setAdapter(aa);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        btn_AddDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(et_rate.getText()) && TextUtils.isEmpty(et_amount.getText())){
                    Toast.makeText(KhaiLagaiPage.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(et_rate.getText())){
                    Toast.makeText(KhaiLagaiPage.this, "Please Enter Rate", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(et_amount.getText())){
                    Toast.makeText(KhaiLagaiPage.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    float ratee = Float.parseFloat(et_rate.getText().toString());
                    int amountt = Integer.parseInt(et_amount.getText().toString());
                    Teamname = spinner_Teamname.getSelectedItem().toString();
                    KhaiLagai = spinner_KhaiLagai.getSelectedItem().toString();

                    databaseHelper.khailagaiDataInsert(String.valueOf(ratee),String.valueOf(amountt), KhaiLagai,Teamname,currentDateandTime);

                    res = databaseHelper.getKLD(currentDateandTime);
                    if(res.getCount() != 0){
                        KhaiLagaiList.clear();
                        while (res.moveToNext()) {
                            String id = String.valueOf(res.getInt(0));
                            float rate = Float.parseFloat(res.getString(1));
                            int amount = Integer.parseInt(res.getString(2));
                            String khai_lagai = res.getString(3);
                            String betteam = res.getString(4);
                            KhaiLagaiList.add(new KhaiLagaiData(rate,amount,betteam,khai_lagai,id));
                        }
                    }

                    if (KhaiLagaiList.size() == 1){
                        recyclerview_KhaiLagai.setLayoutManager(new LinearLayoutManager(KhaiLagaiPage.this));
                        khaiLagaiAdapter = new KhaiLagaiAdapter(KhaiLagaiPage.this,KhaiLagaiList,team1,team2,KhaiLagaiPage.this);
                        recyclerview_KhaiLagai.setAdapter(khaiLagaiAdapter);
                        dialog.dismiss();
                    }else {
                        khaiLagaiAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
            }
        });

    }

    @Override
    public void winnings(float amount, float amt, float draw) {
        winningOne = winningOne + amount;
        TV_team1Winning.setText(String.valueOf(Math.round(winningOne)));

        winningTwo = winningTwo + amt;
        TV_team2Winning.setText(String.valueOf(Math.round(winningTwo)));

        Draw = Draw + draw;
        TV_drawamount.setText(String.valueOf(Math.round(Draw)));

        databaseHelper.updatekhailagaiMatchesInsert(currentDateandTime,String.valueOf(Math.round(winningOne)),String.valueOf(Math.round(Draw)),String.valueOf(Math.round(winningTwo)));

    }

    @Override
    public boolean refreshdata() {
        res = databaseHelper.getKLMData(currentDateandTime);
        if(res.getCount() != 0){
            while (res.moveToNext()) {
                team1 = res.getString(2);
                team2 = res.getString(3);
                teamname.add(team1);
                teamname.add(team2);
               /* TV_team1Name.setText(res.getString(2));
                TV_team2Name.setText(res.getString(3));
                TV_teamonename.setText(res.getString(2));
                TV_teamtwoname.setText(res.getString(3));
                TV_team1Winning.setText(res.getString(5));
                TV_drawamount.setText(res.getString(6));
                TV_team2Winning.setText(res.getString(7));*/
            }
        }

        res = databaseHelper.getKLD(currentDateandTime);
        if(res.getCount() == 0){
            if (khaiLagaiAdapter != null){
                KhaiLagaiList.clear();
                khaiLagaiAdapter.notifyDataSetChanged();
            }
        }
        else {
            KhaiLagaiList.clear();
            while (res.moveToNext()) {
                String id = String.valueOf(res.getInt(0));
                float rate = Float.parseFloat(res.getString(1));
                int amount = Integer.parseInt(res.getString(2));
                String khai_lagai = res.getString(3);
                String betteam = res.getString(4);
                KhaiLagaiList.add(new KhaiLagaiData(rate,amount,betteam,khai_lagai,id));
                recyclerview_KhaiLagai.setLayoutManager(new LinearLayoutManager(KhaiLagaiPage.this));
                khaiLagaiAdapter = new KhaiLagaiAdapter(KhaiLagaiPage.this,KhaiLagaiList,team1,team2,KhaiLagaiPage.this);
                recyclerview_KhaiLagai.setAdapter(khaiLagaiAdapter);
            }
        }
        return true;
    }

}

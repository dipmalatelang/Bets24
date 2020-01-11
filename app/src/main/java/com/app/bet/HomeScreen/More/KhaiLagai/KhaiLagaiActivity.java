package com.app.bet.HomeScreen.More.KhaiLagai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.HomeScreen.More.KhaiLagai.KhaiLagaiCalculator.KhaiLagaiPage;
import com.app.bet.HomeScreen.More.KhaiLagai.PlaySessionCalculator.SessionPage;
import com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.SaveMatches;
import com.app.bet.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KhaiLagaiActivity extends AppCompatActivity {

    Button BTN_playkhailagai,BTN_playsession,BTN_savematch;
    EditText ET_enterfirstteam,ET_entersecondteam;
    Intent intent;
    String firstteam,secondteam,date,currentDateandTime;
    DatabaseHelper databaseHelper;
    Toolbar Tb_App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_lagai);
        Tb_App = findViewById(R.id.Tb_App);
        setSupportActionBar(Tb_App);
        Tb_App.setTitle("Khai Lagai Calculator");

        BTN_playkhailagai = findViewById(R.id.BTN_playkhailagai);
        BTN_playsession = findViewById(R.id.BTN_playsession);
        BTN_savematch = findViewById(R.id.BTN_savematch);
        ET_enterfirstteam = findViewById(R.id.ET_enterfirstteam);
        ET_entersecondteam = findViewById(R.id.ET_entersecondteam);
        databaseHelper = new DatabaseHelper(KhaiLagaiActivity.this);
        BTN_savematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(KhaiLagaiActivity.this, SaveMatches.class);
                startActivity(intent);
            }
        });

        BTN_playsession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentDateandTime = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

                if (TextUtils.isEmpty(ET_enterfirstteam.getText()) && TextUtils.isEmpty(ET_entersecondteam.getText())){
                    Toast.makeText(KhaiLagaiActivity.this, "Enter both team name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ET_enterfirstteam.getText())){
                    Toast.makeText(KhaiLagaiActivity.this, "Enter first team name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ET_entersecondteam.getText())){
                    Toast.makeText(KhaiLagaiActivity.this, "Enter Second team name", Toast.LENGTH_SHORT).show();
                }
                else {
                    firstteam =  ET_enterfirstteam.getText().toString();
                    secondteam =  ET_entersecondteam.getText().toString();

                    if (firstteam.equalsIgnoreCase(secondteam)){
                        Toast.makeText(KhaiLagaiActivity.this, "Enter Different team name", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseHelper.playSessionMatchesInsert("1",firstteam,secondteam,date,"0","0",currentDateandTime);
                        intent = new Intent(KhaiLagaiActivity.this, SessionPage.class);
                        intent.putExtra("firstteam", firstteam);
                        intent.putExtra("secondteam", secondteam);
                        intent.putExtra("currentDateandTime",currentDateandTime);
                        intent.putExtra("pagestart","fresh");
                        startActivity(intent);
                    }
                }
            }
        });

        BTN_playkhailagai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentDateandTime  = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

                if (TextUtils.isEmpty(ET_enterfirstteam.getText()) && TextUtils.isEmpty(ET_entersecondteam.getText())){
                    Toast.makeText(KhaiLagaiActivity.this, "Enter both team name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ET_enterfirstteam.getText())){
                    Toast.makeText(KhaiLagaiActivity.this, "Enter first team name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ET_entersecondteam.getText())){
                    Toast.makeText(KhaiLagaiActivity.this, "Enter Second team name", Toast.LENGTH_SHORT).show();
                }
                else {
                    firstteam =  ET_enterfirstteam.getText().toString();
                    secondteam =  ET_entersecondteam.getText().toString();

                    if (firstteam.equalsIgnoreCase(secondteam)){
                        Toast.makeText(KhaiLagaiActivity.this, "Enter Different team name", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseHelper.khailagaiMatchesInsert("1",firstteam,secondteam,date,"0","0","0",currentDateandTime);
                        intent = new Intent(KhaiLagaiActivity.this, KhaiLagaiPage.class);
                        intent.putExtra("firstteam", firstteam);
                        intent.putExtra("secondteam", secondteam);
                        intent.putExtra("currentDateandTime",currentDateandTime);
                        intent.putExtra("pagestart","fresh");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                startActivity(new Intent(this, HomeScreenActivity.class));
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

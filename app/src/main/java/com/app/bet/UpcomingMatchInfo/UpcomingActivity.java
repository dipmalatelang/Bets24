package com.app.bet.UpcomingMatchInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.MatchData.MatchOddsData;
import com.app.bet.R;
import com.app.bet.Util.APICall;
import com.app.bet.Util.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingActivity extends AppCompatActivity {

    TextView TvMatchName,TvMatchTime,TvSportName;
    RecyclerView Rv_MatchOdd;
    Toolbar Tb_App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        Tb_App = findViewById(R.id.Tb_App);
        setSupportActionBar(Tb_App);
        Tb_App.setTitle("Upcoming Match");

        TvMatchName = findViewById(R.id.TvMatchName);
        TvMatchTime = findViewById(R.id.TvMatchTime);
        TvSportName = findViewById(R.id.TvSportName);
        Rv_MatchOdd = findViewById(R.id.Rv_MatchOdd);

        String marketId = "";
        String Team1 = "",Team2 = "",Time = "",Sport = "";

        if (getIntent() != null){
            marketId = getIntent().getStringExtra("MarketId");
            Team1 = getIntent().getStringExtra("Team1");
            Team2 = getIntent().getStringExtra("Team2");
            Time = getIntent().getStringExtra("Time");
            Sport = getIntent().getStringExtra("Sport");
        }

        TvMatchName.setText(Team1+" Vs "+Team2);
        TvMatchTime.setText("Match Will be Live "+Time);
        TvSportName.setText("Sport Name : "+Sport);

        getMatchOdds(marketId);

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

    public void getMatchOdds(String bfid){
        ApiInterface apiInterface = APICall.getAPII().create(ApiInterface.class);
        Call<MatchOddsData> call = apiInterface.getMatchOddsData(bfid);
        call.enqueue(new Callback<MatchOddsData>() {
            @Override
            public void onResponse(Call<MatchOddsData> call, Response<MatchOddsData> response) {
                if (response.code() == 200){
                    if (response.body() != null){
                        MatchOddsData matchOddsData = response.body();
                        ArrayList<MatchOddsData.data_info.runnerData_info> datalist = matchOddsData.getData().getRunnerData();
                        if (datalist.size() != 0){
                            Rv_MatchOdd.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            MatchOddsAdapter matchOddsAdapter = new MatchOddsAdapter(getApplicationContext(), datalist);
                            Rv_MatchOdd.setAdapter(matchOddsAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchOddsData> call, Throwable t) {
                Log.d("DATATATATAA", ""+t);
            }
        });
    }
}

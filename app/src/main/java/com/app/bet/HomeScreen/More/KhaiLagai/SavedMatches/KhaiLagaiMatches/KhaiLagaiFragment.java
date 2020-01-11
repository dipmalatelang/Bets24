package com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.KhaiLagaiMatches;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bet.HomeScreen.More.KhaiLagai.DatabaseHelper;
import com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.SavedMatchesData;
import com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.SavedMatchesInterface;
import com.app.bet.R;

import java.util.ArrayList;

public class KhaiLagaiFragment extends Fragment implements SavedMatchesInterface {

    private RecyclerView recyclerview_saved_matches;
    private SavedKhaiLagaiAdapter myAdapter;
    private DatabaseHelper databaseHelper;
    private Cursor res;
    private ArrayList<SavedMatchesData> SavedMatchesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khai_lagai, container, false);
        recyclerview_saved_matches = view.findViewById(R.id.recyclerview_saved_matches);
        databaseHelper = new DatabaseHelper(getContext());
        getSavedKhaiLagaiData();
        return view;
    }

    private void getSavedKhaiLagaiData() {
         res = databaseHelper.getKLM();
        if(res.getCount() == 0){
            Toast.makeText(getContext(), "No Saved Matches", Toast.LENGTH_SHORT).show();
        }
        else {
            SavedMatchesList.clear();
            while (res.moveToNext()){
                String id = String.valueOf(res.getInt(0));
                String teamonename = res.getString(2);
                String teamtwoname = res.getString(3);
                String date = res.getString(4);
                String datetime = res.getString(8);
                SavedMatchesList.add(new SavedMatchesData(teamonename,teamtwoname,date,datetime,id));
            }

            recyclerview_saved_matches.setLayoutManager(new LinearLayoutManager(getContext()));
            myAdapter = new SavedKhaiLagaiAdapter(getContext(),SavedMatchesList,KhaiLagaiFragment.this);
            recyclerview_saved_matches.setAdapter(myAdapter);
        }
    }

    @Override
    public void refreshData() {
        res = databaseHelper.getKLM();
        if(res.getCount() == 0){
           if(myAdapter != null){
               SavedMatchesList.clear();
               myAdapter.notifyDataSetChanged();
           }
        }
        else {
            SavedMatchesList.clear();
            while (res.moveToNext()){
                String id = String.valueOf(res.getInt(0));
                String teamonename = res.getString(2);
                String teamtwoname = res.getString(3);
                String date = res.getString(4);
                String datetime = res.getString(8);
                SavedMatchesList.add(new SavedMatchesData(teamonename,teamtwoname,date,datetime,id));
            }

            recyclerview_saved_matches.setLayoutManager(new LinearLayoutManager(getContext()));
            myAdapter = new SavedKhaiLagaiAdapter(getContext(),SavedMatchesList,KhaiLagaiFragment.this);
            recyclerview_saved_matches.setAdapter(myAdapter);
        }
    }
}

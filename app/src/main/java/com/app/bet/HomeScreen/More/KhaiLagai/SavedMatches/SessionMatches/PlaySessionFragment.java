package com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches.SessionMatches;

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

public class PlaySessionFragment extends Fragment implements SavedMatchesInterface {

    private RecyclerView recyclerview_saved_matches_session;
    private SavedSessionAdapter savedSessionAdapter;
    private DatabaseHelper databaseHelper;
    private Cursor res;
    private ArrayList<SavedMatchesData> SavedMatchesList = new ArrayList<>();

    public PlaySessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_session, container, false);
        recyclerview_saved_matches_session = view.findViewById(R.id.recyclerview_saved_matches_session);
        databaseHelper = new DatabaseHelper(getContext());
        getSessionMatches();
        return view;
    }

    private void getSessionMatches(){
         res = databaseHelper.getPSM();
        if(res.getCount() == 0){
            Toast.makeText(getContext(), "No Saved Sessions", Toast.LENGTH_SHORT).show();
        }
        else {
            SavedMatchesList.clear();
            while (res.moveToNext()){
                String id = String.valueOf(res.getInt(0));
                String teamonename = res.getString(2);
                String teamtwoname = res.getString(3);
                String date = res.getString(4);
                String datetime = res.getString(7);
                SavedMatchesList.add(new SavedMatchesData(teamonename,teamtwoname,date,datetime,id));
            }

            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview_saved_matches_session.setLayoutManager(llm);
            savedSessionAdapter = new SavedSessionAdapter(getContext(),SavedMatchesList,PlaySessionFragment.this);
            recyclerview_saved_matches_session.setAdapter(savedSessionAdapter);

        }

    }

    @Override
    public void refreshData() {
        res = databaseHelper.getPSM();
        if(res.getCount() == 0){
            if(savedSessionAdapter != null){
                SavedMatchesList.clear();
                savedSessionAdapter.notifyDataSetChanged();
            }
        }
        else {
            SavedMatchesList.clear();
            while (res.moveToNext()){
                String id = String.valueOf(res.getInt(0));
                String teamonename = res.getString(2);
                String teamtwoname = res.getString(3);
                String date = res.getString(4);
                String datetime = res.getString(7);
                SavedMatchesList.add(new SavedMatchesData(teamonename,teamtwoname,date,datetime,id));
            }

            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview_saved_matches_session.setLayoutManager(llm);
            savedSessionAdapter = new SavedSessionAdapter(getContext(),SavedMatchesList,PlaySessionFragment.this);
            recyclerview_saved_matches_session.setAdapter(savedSessionAdapter);

        }
    }
}

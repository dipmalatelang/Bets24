package com.app.bet.MatchData;

import java.util.ArrayList;

public class FancyData {

    ArrayList<data_info> data;

    public ArrayList<data_info> getData() {
        return data;
    }

    public class data_info{
        String name;
        String noScore;
        String yesScore;

        public String getYesScore() {
            return yesScore;
        }

        public String getName() {
            return name;
        }

        public String getNoScore() {
            return noScore;
        }
    }
}

package com.app.bet.HomeScreen.Matches.LiveMatch;

import java.util.ArrayList;

public class TennisScoreData {

    public ArrayList<TennisData> data;

    public class TennisData{
        public String eventTypeId;
        public String eventId;

        public MatchScore score;

        public class MatchScore{

            public Away away;
            public Home home;

            public class Home{
                public String name;
                public String score;
                public String halfTimeScore;
                public String fullTimeScore;
                public String penaltiesScore;
                public String games;
                public String sets;
                public String aces;
                public String doubleFaults;
                public String isServing;
                public String highlight;
                public String serviceBreaks;
                public ArrayList<String> gameSequence;
            }

            public class Away{
                public String name;
                public String score;
                public String halfTimeScore;
                public String fullTimeScore;
                public String penaltiesScore;
                public String games;
                public String sets;
                public String aces;
                public String doubleFaults;
                public String isServing;
                public String highlight;
                public String serviceBreaks;
                public ArrayList<String> gameSequence;
            }
        }

        public String currentSet;
        public String currentPoint;

        public TimeElapsed fullTimeElapsed;

        class TimeElapsed {
            public String hour;
            public String min;
            public String sec;
        }

        public String matchStatus;
    }
}

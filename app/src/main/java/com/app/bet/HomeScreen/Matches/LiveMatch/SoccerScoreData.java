package com.app.bet.HomeScreen.Matches.LiveMatch;

import java.util.ArrayList;

public class SoccerScoreData {

    public ArrayList<SoccerData> data;

    public class SoccerData{
        public String eventId;
        public String eventTypeId;

        public ScoreData score;

        public class ScoreData{
            public String numberOfYellowCards;
            public String numberOfRedCards;
            public String numberOfCards;
            public String numberOfCorners;
            public String numberOfCornersFirstHalf;
            public String bookingPoints;
            public HomeTeam home;
            public AwayTeam away;

            public class HomeTeam{
                public String name;
                public String score;
            }

            public class AwayTeam{
                public String name;
                public String score;
            }
        }

        public String timeElapsed;
        public String elapsedRegularTime;
        public String elapsedAddedTime;
        public String status;
        public String inPlayMatchStatus;
    }
}

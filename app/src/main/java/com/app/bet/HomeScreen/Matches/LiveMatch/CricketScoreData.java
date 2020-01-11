package com.app.bet.HomeScreen.Matches.LiveMatch;

import java.util.ArrayList;

public class CricketScoreData {

    public ArrayList<MatchInfo> data;

    public class MatchInfo{
        public String eventTypeId;
        public String eventId;
        public Score score;

        public class Score{
            public HomeTeam home;

            public class HomeTeam{
                public String name;
                public String halfTimeScore;
                public String fullTimeScore;
                public String highlight;
                public HomeInning inning1;
                public HomeInning inning2;

                public class HomeInning{
                    public String runs;
                    public String wickets;
                    public String overs;
                }
            }

            public AwayTeam away;

            public class AwayTeam{
                public String name;
                public String halfTimeScore;
                public String fullTimeScore;
                public String highlight;
                public AwayInning inning1;
                public AwayInning inning2;

                public class AwayInning{
                    public String runs;
                    public String wickets;
                    public String overs;
                }
            }
        }

        public String currentSet;
        public String hasSets;
        public BallState stateOfBall;

        public class BallState{
            public String overNumber;
            public String overBallNumber;
            public String bowlerName;
            public String batsmanName;
            public String batsmanRuns;
            public String appealId;
            public String appealTypeName;
            public String wide;
            public String bye;
            public String legBye;
            public String noBall;
            public String outcomeId;
            public String dismissalTypeName;
            public String referralOutcome;
        }

        public String currentDay;
        public String matchType;
        public Time fullTimeElapsed;

        class Time{
            public String hour;
            public String min;
            public String sec;
        }

        public String matchStatus;
    }
}

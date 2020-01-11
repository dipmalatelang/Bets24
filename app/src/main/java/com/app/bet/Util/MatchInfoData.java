package com.app.bet.Util;

import java.util.ArrayList;

public class MatchInfoData {

    public String curTime;
    public ArrayList<SportsData> sportsData;

    public class SportsData{
        public String bfId;
        public String id;
        public String img;
        public String name;

        public ArrayList<Tournaments> tournaments;

        public class Tournaments{

            public String bfId;
            public String id;
            public ArrayList<Matches> matches;

            public class Matches{
                public String _avgmatchedBets;
                public String _fancyBets;
                public String _matchedBets;
                public String _unMatchedBets;
                public String bfId;
                public String bookRates;
                public String commentary;
                public String data;
                public String dataMode;
                public String displayApplication;
                public String id;
                public String inPlay;

                public ArrayList<MarketInfo> markets;

                public class MarketInfo{

                    public String bfId;
                    public String id;
                    public String isBettingAllow;
                    public String isMulti;
                    public String name;
                    public RunnerInfo runnerData;
                    public ArrayList<RunnerData> runnerData1;
                    public String status;

                    public class RunnerInfo{
                        public String runner1Back;
                        public String runner1BackSize;
                        public String runner1Lay;
                        public String runner1LaySize;
                        public String runner1Name;
                        public String runner2Back;
                        public String runner2BackSize;
                        public String runner2Lay;
                        public String runner2LaySize;
                        public String runner2Name;
                        public String runner3Back;
                        public String runner3BackSize;
                        public String runner3Lay;
                        public String runner3LaySize;
                        public String runner3Name;
                    }

                    public class RunnerData{
                        public String Key;
                        public RunnerInfo Value;

                        public class RunnerInfo{
                            public String back1;
                            public String back2;
                            public String back3;
                            public String backSize1;
                            public String backSize2;
                            public String backSize3;
                            public String bfId;
                            public String lay1;
                            public String lay2;
                            public String lay3;
                            public String laySize1;
                            public String laySize2;
                            public String laySize3;
                            public String marketId;
                            public String mktStatus;
                            public String runnerName;
                            public String selectionId;
                            public String status;
                        }
                    }

                }

                public String method;
                public String name;
                public String oddsType;
                public String settings;
                public String startDate;
                public String status;
                public String tvConfig;
            }

            public String name;
        }
    }
}

package com.app.bet.MatchData;

import java.util.ArrayList;

public class MatchOddsData {
    public data_info data;

    public data_info getData() {
        return data;
    }

    public class data_info{

        public ArrayList<runnerData_info> runnerData;

        public ArrayList<runnerData_info> getRunnerData() {
            return runnerData;
        }

        public class runnerData_info {
            public String back1;
            public String lay1;
            public String backSize1;
            public String laySize1;

            public String getBackSize1() {
                return backSize1;
            }

            public String getLaySize1() {
                return laySize1;
            }

            public String runnerName;

            public String getBack1() {
                return back1;
            }

            public String getLay1() {
                return lay1;
            }

            public String getRunnerName() {
                return runnerName;
            }
        }
    }
}

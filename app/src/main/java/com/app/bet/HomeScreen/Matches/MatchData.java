package com.app.bet.HomeScreen.Matches;

public class MatchData {

    private String SportName,TeamName1,TeamName2,TeamBack1,TeamBack2,MatchTime,MatchLive,MatchId,marketId,Fancyid;

    public MatchData(String sportName, String teamName1, String teamName2, String teamBack1, String teamBack2, String matchTime, String matchLive, String matchid, String MarketId,String fancyid) {
        SportName = sportName;
        TeamName1 = teamName1;
        TeamName2 = teamName2;
        TeamBack1 = teamBack1;
        TeamBack2 = teamBack2;
        MatchTime = matchTime;
        MatchLive = matchLive;
        MatchId = matchid;
        marketId = MarketId;
        Fancyid = fancyid;
    }

    public String getSportName() {
        return SportName;
    }

    public String getTeamName1() {
        return TeamName1;
    }

    public String getTeamName2() {
        return TeamName2;
    }

    public String getTeamBack1() {
        return TeamBack1;
    }

    public String getTeamBack2() {
        return TeamBack2;
    }

    public String getMatchTime() {
        return MatchTime;
    }

    public String getMatchLive() {
        return MatchLive;
    }

    public String getMatchId() {
        return MatchId;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getFancyid() {
        return Fancyid;
    }
}

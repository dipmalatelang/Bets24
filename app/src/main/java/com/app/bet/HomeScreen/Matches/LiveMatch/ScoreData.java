package com.app.bet.HomeScreen.Matches.LiveMatch;

public class ScoreData {

    private String MatchId, TeamName, TeamRuns, TeamWickets, TeamOver;

    public ScoreData(String matchId, String teamName, String teamRuns, String teamWickets, String teamOver) {
        MatchId = matchId;
        TeamName = teamName;
        TeamRuns = teamRuns;
        TeamWickets = teamWickets;
        TeamOver = teamOver;
    }

    public String getMatchId() {
        return MatchId;
    }

    public String getTeamName1() {
        return TeamName;
    }

    public String getTeamRuns1() {
        return TeamRuns;
    }

    public String getTeamWickets1() {
        return TeamWickets;
    }

    public String getTeamOver1() {
        return TeamOver;
    }

}

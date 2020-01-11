package com.app.bet.Util;

import com.app.bet.HomeScreen.Matches.LiveMatch.CricketScoreData;
import com.app.bet.HomeScreen.Matches.LiveMatch.SoccerScoreData;
import com.app.bet.HomeScreen.Matches.LiveMatch.TennisScoreData;
import com.app.bet.HomeScreen.News.CricketNews.RssFeedCricket;
import com.app.bet.HomeScreen.News.SoccerNews.RssFeedSoccer;
import com.app.bet.HomeScreen.News.TennisNews.RssFeedTennis;
import com.app.bet.MatchData.FancyData;
import com.app.bet.MatchData.MatchOddsData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    /*TODO  --------------------------- Get Inplay Fancy Data --------------------------- */

    @GET("Read/BetClient.svc/Data/FancyData2")
    Call<FancyData> getFancyData(@Query("mtid") String MtId);

    /*TODO  --------------------------- Get MatchOdds Data --------------------------- */

    @GET("BetBuzzzClient/BetBuzzClient.svc/Data/MktData3")
    Call<MatchOddsData> getMatchOddsData(@Query("bfid") String BfId);

    /*TODO  --------------------------- Get Soccer Match Score --------------------------- */

    @GET("tv/SoccerScore?token=678540d1-9359-4232-a9dd-144c6d8e2c0f")
    Call<SoccerScoreData> getSoccerScore(@Query("mtbfid") String matchid);

    /*TODO  --------------------------- Get Tennis Match Score --------------------------- */

    @GET("tv/TennisScore?token=678540d1-9359-4232-a9dd-144c6d8e2c0f")
    Call<TennisScoreData> getTennisScore(@Query("mtbfid") String matchid);

    /*TODO  --------------------------- Get Cricket Match Score --------------------------- */

    @GET("tv/CricketScore?token=678540d1-9359-4232-a9dd-144c6d8e2c0f")
    Call<CricketScoreData> getCricketScore(@Query("mtbfid") String matchid);

    /*TODO  --------------------------- Get Match List --------------------------- */

    @GET("Read/BetClient.svc/Data/GetSportData")
    Call<MatchInfoData> getMatchList();

    /*TODO  --------------------------- Get Cricket News --------------------------- */

    @GET("rss/content/story/feeds/0.xml")
    Call<RssFeedCricket> getCricketFeeds();

    /*TODO  --------------------------- Get Soccer News --------------------------- */

    @GET("espn/rss/football/news")
    Call<RssFeedSoccer> getSoccerFeeds();

    /*TODO  --------------------------- Get Tennis News --------------------------- */

    @GET("espn/rss/tennis/news")
    Call<RssFeedTennis> getTennisFeeds();

}

package com.app.bet.HomeScreen.News.SoccerNews;

import com.app.bet.HomeScreen.News.CricketNews.ItemCricket;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "rss", strict = false)
public class RssFeedSoccer {

    @Path("channel")
    @ElementList(name = "item", entry = "item", inline = true, required = false)
    ArrayList<ItemSoccer> itemSoccer;

    public ArrayList<ItemSoccer> getItemSoccer() {
        return itemSoccer;
    }
}

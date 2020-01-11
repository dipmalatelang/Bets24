package com.app.bet.HomeScreen.News.TennisNews;

import com.app.bet.HomeScreen.News.SoccerNews.ItemSoccer;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "rss", strict = false)
public class RssFeedTennis {

    @Path("channel")
    @ElementList(name = "item", entry = "item", inline = true, required = false)
    ArrayList<ItemTennis> itemTennis;

    public ArrayList<ItemTennis> getItemTennis() {
        return itemTennis;
    }
}

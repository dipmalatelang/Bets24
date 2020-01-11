package com.app.bet.HomeScreen.News.CricketNews;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "rss", strict = false)
public class RssFeedCricket {

    @Path("channel")
    @ElementList(name = "item", entry = "item", inline = true, required = false)
    ArrayList<ItemCricket> itemCrickets;

    public ArrayList<ItemCricket> getItemCrickets() {
        return itemCrickets;
    }
}

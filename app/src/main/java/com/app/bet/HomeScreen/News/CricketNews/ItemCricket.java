package com.app.bet.HomeScreen.News.CricketNews;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "item",strict = false)
public class ItemCricket {

    @Element(name = "title",required = false)
    public String title;

    @Element(name = "link",required = false)
    public String link;

    @Element(name = "description",required = false)
    public String description;

    @Element(name = "coverImages",required = false)
    public String image;

    @Element(name = "pubDate", required = false)
    public String pubDate;
}

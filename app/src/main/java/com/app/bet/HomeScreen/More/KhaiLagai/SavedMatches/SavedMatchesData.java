package com.app.bet.HomeScreen.More.KhaiLagai.SavedMatches;

public class SavedMatchesData {

    private String Teamone,Teamtwo,date,datetime,id;

    public SavedMatchesData(String teamone, String teamtwo, String date, String datetime,String id) {
        this.Teamone = teamone;
        this.Teamtwo = teamtwo;
        this.date = date;
        this.datetime = datetime;
        this.id = id;
    }

    public String getTeamone(){
        return Teamone;
    }

    public String getTeamtwo(){
        return Teamtwo;
    }

    public String getDate(){
        return date;
    }

    public String getDatetime(){ return datetime; }

    public String getId(){ return id; }
}

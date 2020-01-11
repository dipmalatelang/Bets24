package com.app.bet.HomeScreen.More.KhaiLagai.PlaySessionCalculator;

class SessionData {

    private String over,Selectedspinner,id;
    private int score,bidValue,amount;

    SessionData(String over , int score , int bidValue , int amount , String SelectedSpinner,String id){
        this.over = over;
        this.score = score;
        this.bidValue = bidValue;
        this.amount = amount;
        this.Selectedspinner = SelectedSpinner;
        this.id = id;
    }

    String getOver(){
        return over;
    }
    int getAmount(){
        return amount;
    }
    String getSelectedspinner(){
        return Selectedspinner;
    }
    String getId(){ return id; }
    Integer getScore(){
        return score;
    }
    Integer getbidValue(){
        return bidValue;
    }
}

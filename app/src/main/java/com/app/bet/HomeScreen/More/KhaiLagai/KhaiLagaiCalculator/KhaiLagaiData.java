package com.app.bet.HomeScreen.More.KhaiLagai.KhaiLagaiCalculator;

 class KhaiLagaiData {

    private Float rate;
    private int amount;
    private String Teamname,KhaiLagai,id;

    KhaiLagaiData(Float rate, int amount, String teamname, String khaiLagai,String id) {
        this.rate = rate;
        this.amount = amount;
        this.Teamname = teamname;
        this.KhaiLagai = khaiLagai;
        this.id = id;
    }

    Float getRate(){
        return rate;
    }

    int getAmount(){
        return amount;
    }

    String getTeamname(){
        return Teamname;
    }

    String getKhaiLagai(){
        return KhaiLagai;
    }

    String getId(){ return id;}
}

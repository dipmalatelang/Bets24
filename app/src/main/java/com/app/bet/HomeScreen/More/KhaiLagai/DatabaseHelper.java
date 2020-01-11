package com.app.bet.HomeScreen.More.KhaiLagai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Calculator.db";
    private static final String TABLE_NAME_KLM = "KhaiLagai_matches";
    private static final String TABLE_NAME_PSM = "PlaySession_matches";
    private static final String TABLE_NAME_KLD = "KhaiLagai_data";
    private static final String TABLE_NAME_PSD = "PlaySession_data";
    private ContentValues contentValues;
    private SQLiteDatabase db;
    private Cursor res;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_KLM +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,USER TEXT,TEAMONENAME TEXT,TEAMTWONAME TEXT,DATE TEXT,TEAM_ONE_WINNING TEXT,DRAW_WINNING TEXT,TEAM_TWO_WINNING TEXT,DATETIME TEXT)");
        db.execSQL("create table " + TABLE_NAME_PSM + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,USER TEXT,TEAMONENAME TEXT,TEAMTWONAME TEXT,DATE TEXT,PROFIT TEXT,LOSS TEXT,DATETIME TEXT)");
        db.execSQL("create table " + TABLE_NAME_PSD + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,OVER TEXT,UP_DOWN TEXT,BIDVALUE TEXT,MATCHSCORE TEXT,AMOUNT TEXT,DATETIME TEXT)");
        db.execSQL("create table " + TABLE_NAME_KLD + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,RATE TEXT,AMOUNT TEXT,KHAI_LAGAI TEXT,BETTEAM TEXT,DATETIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KLM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PSM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PSD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KLD);
        onCreate(db);
    }

    public void khailagaiMatchesInsert(String user,String teamonename,String teamtwoname,String date , String team1win , String drawwin , String team2win,String datetime ) {
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("USER", user);
        contentValues.put("TEAMONENAME", teamonename);
        contentValues.put("TEAMTWONAME", teamtwoname);
        contentValues.put("DATE", date);
        contentValues.put("TEAM_ONE_WINNING", team1win);
        contentValues.put("DRAW_WINNING", drawwin);
        contentValues.put("TEAM_TWO_WINNING", team2win);
        contentValues.put("DATETIME", datetime);
        db.insert(TABLE_NAME_KLM, null, contentValues);
    }

    public void updatekhailagaiMatchesInsert(String datetime ,  String team1win , String drawwin , String team2win ){
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("DATETIME",datetime);
        contentValues.put("TEAM_ONE_WINNING", team1win);
        contentValues.put("DRAW_WINNING", drawwin);
        contentValues.put("TEAM_TWO_WINNING", team2win);
        db.update(TABLE_NAME_KLM , contentValues , "DATETIME = ?" , new String[]{datetime});
    }

    public void khailagaiDataInsert(String rate,String amount,String khai_lagai,String betteam,String datetime){
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("RATE",rate);
        contentValues.put("AMOUNT",amount);
        contentValues.put("KHAI_LAGAI",khai_lagai);
        contentValues.put("BETTEAM",betteam);
        contentValues.put("DATETIME",datetime);
        db.insert(TABLE_NAME_KLD, null, contentValues);
    }

    public void playSessionMatchesInsert(String user,String teamonename,String teamtwoname,String date , String profit , String loss,String datetime ) {
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("USER",user);
        contentValues.put("TEAMONENAME", teamonename);
        contentValues.put("TEAMTWONAME", teamtwoname);
        contentValues.put("DATE", date);
        contentValues.put("PROFIT", profit);
        contentValues.put("LOSS", loss);
        contentValues.put("DATETIME", datetime);
        db.insert(TABLE_NAME_PSM, null, contentValues);
    }

    public void updateplaySessionMatchesInsert(String datetime , String profit,String loss ){
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("DATETIME",datetime);
        contentValues.put("PROFIT", profit);
        contentValues.put("LOSS", loss);
        db.update(TABLE_NAME_PSM , contentValues , "DATETIME = ?" , new String[]{datetime});
    }

    public void playSessionDataInsert(String over, String updown ,String bidvalue , String matchscore,String amount,String datetime){
        db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("OVER",over);
        contentValues.put("UP_DOWN",updown);
        contentValues.put("BIDVALUE",bidvalue);
        contentValues.put("MATCHSCORE",matchscore);
        contentValues.put("AMOUNT",amount);
        contentValues.put("DATETIME",datetime);
        db.insert(TABLE_NAME_PSD,null,contentValues);
    }

    public Cursor getKLM(){
        db = this.getWritableDatabase();
        res = db.rawQuery("select * from "+TABLE_NAME_KLM,null);
        return res;
    }

    public Cursor getPSM(){
        db = this.getWritableDatabase();
        res = db.rawQuery("select * from "+TABLE_NAME_PSM,null);
        return res;
    }

    public Cursor getKLMData(String datetime){
        db = this.getWritableDatabase();
        res = db.rawQuery("select * from "+TABLE_NAME_KLM+" WHERE DATETIME = ?" ,new String[] {datetime});
        return res;
    }

    public Cursor getPSMData(String datetimee){
        db = this.getWritableDatabase();
        res = db.rawQuery("select * from "+TABLE_NAME_PSM+" WHERE DATETIME = ?" ,new String[] {datetimee});
        return res;
    }

    public Cursor getPSD(String datetime){
        db = this.getWritableDatabase();
        res = db.rawQuery("select * from "+TABLE_NAME_PSD+" WHERE DATETIME = ?" ,new String[] {datetime});
        return res;
    }

    public Cursor getKLD(String datetime){
        db = this.getWritableDatabase();
        res = db.rawQuery("select * from "+TABLE_NAME_KLD+" WHERE DATETIME = ?" ,new String[] {datetime});
        return res;
    }

    public void deletePSDdataDateTime(String datetime) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME_PSD, "DATETIME=?", new String[]{datetime});
    }

    public void deleteKLDdataDateTime(String datetime){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME_KLD,"DATETIME=?", new String[]{datetime});
    }

    public void deletePSMData(String id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME_PSM,"ID=?", new String[]{id});
    }

    public void deleteKLMData(String id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME_KLM,"ID=?", new String[]{id});
    }

    public void deletePSDData(String id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME_PSD,"ID=?", new String[]{id});
    }

    public void deleteKLDData(String id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME_KLD, "ID=?", new String[]{id});
    }
}

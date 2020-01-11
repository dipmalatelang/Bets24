package com.app.bet.Util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class APICall {

    private static String baseUrl = "http://galaxyexch7.com/";
    private static String baseUrl1 = "https://www.shivexch.com/";
    private static String baseUrl2 = "http://173.249.21.26/";
    private static Retrofit cricRetro = null;
    private static Retrofit soccRetro = null;
    private static Retrofit tennRetro = null;
    private static Retrofit inplayRetro = null;
    private static Retrofit scoreRetro = null;
    private static Retrofit retrofit3 = null;

    public static Retrofit getAPI(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();
        if (inplayRetro == null){
            inplayRetro = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }
        return inplayRetro;
    }

    public static Retrofit getAPII(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        if (retrofit3 == null){
            retrofit3 = new Retrofit.Builder().baseUrl(baseUrl2).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        } return retrofit3;
    }

    public static Retrofit getScore(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();
        if (scoreRetro == null){
            scoreRetro = new Retrofit.Builder().baseUrl(baseUrl1).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }
        return scoreRetro;
    }

    public static Retrofit getRssCricket(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();
        if (cricRetro == null){
            cricRetro = new Retrofit.Builder().baseUrl("http://www.espncricinfo.com/").addConverterFactory(SimpleXmlConverterFactory.create()).client(client).build();
        }
        return cricRetro;
    }

    public static Retrofit getRssSoccer(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();
        if (soccRetro == null){
            soccRetro = new Retrofit.Builder().baseUrl("https://www.espn.co.uk/").addConverterFactory(SimpleXmlConverterFactory.create()).client(client).build();
        }
        return soccRetro;
    }

    public static Retrofit getRssTennis(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();
        if (tennRetro == null){
            tennRetro = new Retrofit.Builder().baseUrl("https://www.espn.com/").addConverterFactory(SimpleXmlConverterFactory.create()).client(client).build();
        }
        return tennRetro;
    }
}

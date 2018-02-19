package com.example.priyanka.mediator2.Network;

/**
 * Created by priyanka on 1/23/18.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiClient {

    //public static final String BASE_URL = "http://192.168.43.60:8000/";
    public static final String BASE_URL = "http://prajakta.pythonanywhere.com/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getStringClient() {
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit1;
    }
}

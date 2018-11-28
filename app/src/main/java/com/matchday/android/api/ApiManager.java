package com.matchday.android.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.matchday.android.Const;
import com.matchday.android.model.MenuItemTemplate;
import com.matchday.android.model.Template;
import com.matchday.android.ui.adapter.RecyclerMenuAdapter;
import com.matchday.android.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marina on 30.08.16.
 */
public class ApiManager {
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptor
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().
                    baseUrl(Const.BASE_URL).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    client(httpClient.build()).
                    build();
        }
        return sRetrofit;
    }
}
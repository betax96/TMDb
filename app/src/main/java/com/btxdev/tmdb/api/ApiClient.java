package com.btxdev.tmdb.api;

import com.btxdev.tmdb.App;
import com.btxdev.tmdb.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getClient() {

        Interceptor authInterceptor = new Interceptor(){

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + App.getInstance().getString(R.string.TMDb_api_access_token))
                        .build();
                return chain.proceed(newRequest);
            }
        };

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        TLSSocketFactory tlsSocketFactory= new TLSSocketFactory();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(httpLoggingInterceptor)
                .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();


        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

}

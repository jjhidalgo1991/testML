package com.jangapp.myappmla.repository.network.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jangapp.myappmla.Constants.MLA_BASE_URL;
import static com.jangapp.myappmla.Constants.PROD_ARRAY_LIST_CLASS_TYPE;


public class TheProductDBAPIClient {


    public static MLAAPIInterface getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // create OkHttpClient and register an interceptor
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PROD_ARRAY_LIST_CLASS_TYPE, new ProductsJsonDeserializer())
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(MLA_BASE_URL);

        return builder.build().create(MLAAPIInterface.class);
    }
}

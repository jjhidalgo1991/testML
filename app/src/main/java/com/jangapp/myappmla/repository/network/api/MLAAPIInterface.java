package com.jangapp.myappmla.repository.network.api;


import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.jangapp.myappmla.Constants.LIMIT_REQUEST_PARAM;
import static com.jangapp.myappmla.Constants.OFFSET_REQUEST_PARAM;
import static com.jangapp.myappmla.Constants.QUERY_REQUEST_PARAM;


public interface MLAAPIInterface {
    @GET("/sites/MLA/search")
    Call<ArrayList<ProdOverView>> findProd(@Query(QUERY_REQUEST_PARAM) String query,
                                           @Query(LIMIT_REQUEST_PARAM) int limit,
                                           @Query(OFFSET_REQUEST_PARAM) int offset);
//    @GET("/items/{id_item}")
//    Call<ProdEsp> getProdById(@Path("id_item") String iditem);
//
//    @GET("/items/{id_item}/description")
//    Call<ProdDes> getDescProdById(@Path("id_item") String iditem);
}

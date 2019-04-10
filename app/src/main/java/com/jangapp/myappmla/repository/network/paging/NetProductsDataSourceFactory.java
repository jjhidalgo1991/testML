package com.jangapp.myappmla.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import rx.subjects.ReplaySubject;


public class NetProductsDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetProductsDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetProductsPageKeyedDataSource> networkStatus;
    private NetProductsPageKeyedDataSource productsPageKeyedDataSource;

    public NetProductsDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        productsPageKeyedDataSource = new NetProductsPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(productsPageKeyedDataSource);
        return productsPageKeyedDataSource;
    }

    public MutableLiveData<NetProductsPageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<ProdOverView> getProducts(String query) {
        return productsPageKeyedDataSource.getProducts(query);
    }

}

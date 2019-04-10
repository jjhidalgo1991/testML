package com.jangapp.myappmla.repository.storge.paging;

import android.arch.paging.DataSource;

import com.jangapp.myappmla.repository.storge.ProductDao;


public class DBProductsDataSourceFactory extends DataSource.Factory {

    private static final String TAG = DBProductsDataSourceFactory.class.getSimpleName();
    private DBProductsPageKeyedDataSource productsPageKeyedDataSource;

    public DBProductsDataSourceFactory(ProductDao dao) {
        productsPageKeyedDataSource = new DBProductsPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return productsPageKeyedDataSource;
    }

}

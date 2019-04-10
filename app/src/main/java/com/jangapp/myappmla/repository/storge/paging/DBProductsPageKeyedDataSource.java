package com.jangapp.myappmla.repository.storge.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jangapp.myappmla.repository.storge.ProductDao;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import java.util.List;

/**
 * Created by Elad on 6/25/2018.
 */

public class DBProductsPageKeyedDataSource extends PageKeyedDataSource<String, ProdOverView> {

    public static final String TAG = DBProductsPageKeyedDataSource.class.getSimpleName();
    private final ProductDao productDao;

    public DBProductsPageKeyedDataSource(ProductDao dao) {
        productDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, ProdOverView> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<ProdOverView> products = productDao.getProduct();
        if (products.size() != 0) {
            callback.onResult(products, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, ProdOverView> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, ProdOverView> callback) {
    }
}

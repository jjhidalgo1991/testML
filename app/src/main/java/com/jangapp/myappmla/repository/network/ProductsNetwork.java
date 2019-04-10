package com.jangapp.myappmla.repository.network;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.jangapp.myappmla.repository.network.paging.NetProductsDataSourceFactory;
import com.jangapp.myappmla.repository.network.paging.NetProductsPageKeyedDataSource;
import com.jangapp.myappmla.repository.storge.model.NetworkState;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.jangapp.myappmla.Constants.LOADING_PAGE_SIZE;
import static com.jangapp.myappmla.Constants.NUMBERS_OF_THREADS;


public class ProductsNetwork {

    final private static String TAG = ProductsNetwork.class.getSimpleName();
    final private LiveData<PagedList<ProdOverView>> productsPaged;
    final private LiveData<NetworkState> networkState;

    public ProductsNetwork(NetProductsDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<ProdOverView> boundaryCallback) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<NetProductsPageKeyedDataSource, LiveData<NetworkState>>)
                        NetProductsPageKeyedDataSource::getNetworkState);
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        productsPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<ProdOverView>> getPagedProducts(String query) {
        return productsPaged;
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}

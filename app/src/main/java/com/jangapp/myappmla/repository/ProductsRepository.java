package com.jangapp.myappmla.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;

import com.jangapp.myappmla.repository.network.ProductsNetwork;
import com.jangapp.myappmla.repository.network.paging.NetProductsDataSourceFactory;
import com.jangapp.myappmla.repository.storge.ProductDatabase;
import com.jangapp.myappmla.repository.storge.model.NetworkState;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import rx.schedulers.Schedulers;

public class ProductsRepository {
    private static final String TAG = ProductsRepository.class.getSimpleName();
    private static ProductsRepository instance;
    private ProductsNetwork network;
    private ProductDatabase database;
    private MediatorLiveData liveDataMerger;
    private Context context;

    private PagedList.BoundaryCallback<ProdOverView> boundaryCallback = new PagedList.BoundaryCallback<ProdOverView>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getProducts(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(database.getProducts());
            });
        }
    };

    private ProductsRepository(Context context) {
        this.context = context;
    }

    public static ProductsRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductsRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<ProdOverView>> getProducts(String query) {
        NetProductsDataSourceFactory dataSourceFactory = new NetProductsDataSourceFactory();

        network = new ProductsNetwork(dataSourceFactory, boundaryCallback);
        database = ProductDatabase.getInstance(context.getApplicationContext());
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedProducts(query), value -> {
            liveDataMerger.setValue(value);
            Log.d(TAG, value.toString());
        });

        dataSourceFactory.getProducts(query).
                observeOn(Schedulers.io()).
                subscribe(product -> {
                    database.productDao().insertProduct(product);
                });
        return liveDataMerger;
    }

    public LiveData<ProdOverView> getProduct(String id) {
        MutableLiveData<ProdOverView> prod;
        database = ProductDatabase.getInstance(context.getApplicationContext());
        prod = new MutableLiveData<>();
        prod.setValue(database.productDao().getProd(id));
        return prod;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }
}

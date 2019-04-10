package com.jangapp.myappmla.repository.storge;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jangapp.myappmla.repository.storge.model.ProdOverView;
import com.jangapp.myappmla.repository.storge.paging.DBProductsDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.jangapp.myappmla.Constants.DATA_BASE_NAME;
import static com.jangapp.myappmla.Constants.NUMBERS_OF_THREADS;


/**
 * The Room database that contains the Users table
 */
@Database(entities = {ProdOverView.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    private static ProductDatabase instance;


    public abstract ProductDao productDao();

    private static final Object sLock = new Object();
    private LiveData<PagedList<ProdOverView>> productsPaged;

    public static ProductDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        ProductDatabase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        DBProductsDataSourceFactory dataSourceFactory = new DBProductsDataSourceFactory(productDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        productsPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<ProdOverView>> getProducts() {
        return productsPaged;
    }
}

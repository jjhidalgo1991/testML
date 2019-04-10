package com.jangapp.myappmla.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jangapp.myappmla.repository.network.api.MLAAPIInterface;
import com.jangapp.myappmla.repository.network.api.TheProductDBAPIClient;
import com.jangapp.myappmla.repository.storge.model.NetworkState;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;


public class NetProductsPageKeyedDataSource extends PageKeyedDataSource<String, ProdOverView> {

    private static final String TAG = NetProductsPageKeyedDataSource.class.getSimpleName();
    private final MLAAPIInterface productService;
    private final MutableLiveData networkState;
    private final ReplaySubject<ProdOverView> productObservable;
    private String query = "";

    NetProductsPageKeyedDataSource() {
        productService = TheProductDBAPIClient.getClient();
        networkState = new MutableLiveData();
        productObservable = ReplaySubject.create();

    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<ProdOverView> getProducts(String query) {
        this.query=query;
        return productObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, ProdOverView> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        Call<ArrayList<ProdOverView>> callBack = productService.findProd(query, 10, 0);
        callBack.enqueue(new Callback<ArrayList<ProdOverView>>() {
            @Override
            public void onResponse(Call<ArrayList<ProdOverView>> call, Response<ArrayList<ProdOverView>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        response.body().forEach(productObservable::onNext);
                    } else {
                        for (ProdOverView m : response.body()) {
                            productObservable.onNext(m);
                        }
                    }
                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProdOverView>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }


    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, ProdOverView> callback) {
        Log.i(TAG, "Loading page " + params.key);
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(params.key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Call<ArrayList<ProdOverView>> callBack = productService.findProd(query, 10, page.get() * 10);
        callBack.enqueue(new Callback<ArrayList<ProdOverView>>() {
            @Override
            public void onResponse(Call<ArrayList<ProdOverView>> call, Response<ArrayList<ProdOverView>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(page.get() + 1));
                    networkState.postValue(NetworkState.LOADED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        response.body().forEach(productObservable::onNext);
                    } else {
                        for (ProdOverView m : response.body()) {
                            productObservable.onNext(m);
                        }
                    }
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProdOverView>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(page.get()));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, ProdOverView> callback) {

    }
}

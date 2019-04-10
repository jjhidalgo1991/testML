package com.jangapp.myappmla.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.jangapp.myappmla.repository.ProductsRepository;
import com.jangapp.myappmla.repository.storge.model.NetworkState;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;


public class ProductsListViewModel extends AndroidViewModel {
    private ProductsRepository repository;

    public ProductsListViewModel(@NonNull Application application) {
        super(application);
        repository = ProductsRepository.getInstance(application);
    }
    public LiveData<PagedList<ProdOverView>> getProducts(String query) {
        return repository.getProducts(query);
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}

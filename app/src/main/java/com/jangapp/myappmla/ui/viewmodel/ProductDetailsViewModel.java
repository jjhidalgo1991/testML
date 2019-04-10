package com.jangapp.myappmla.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.jangapp.myappmla.repository.storge.model.ProdOverView;


public class ProductDetailsViewModel extends ViewModel {
    final private MutableLiveData product;

    public ProductDetailsViewModel() {
        product = new MutableLiveData<ProdOverView>();
    }

    public MutableLiveData<ProdOverView> getProduct() {
        return product;
    }
}

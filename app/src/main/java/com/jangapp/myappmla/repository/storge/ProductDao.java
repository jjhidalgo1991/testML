package com.jangapp.myappmla.repository.storge;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import java.util.List;


@Dao
public interface ProductDao {

    @Query("SELECT * FROM product_over")
    List<ProdOverView> getProduct();

    @Query("SELECT * FROM product_over WHERE id = :id")
    ProdOverView getProd(String id);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(ProdOverView prodOverView);

    @Query("DELETE FROM product_over")
    abstract void deleteAllProducts();

}

package com.jangapp.myappmla.repository.storge.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

/**
 * Created by jorge on 10/04/19.
 */
@Entity(tableName = "product_over")
public class ProdOverView extends BaseObservable {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @NonNull
    private String id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "price")
    private double price;
    @ColumnInfo(name = "currency_id")
    private String currency_id;
    @ColumnInfo(name = "condition")
    private String condition;
    @ColumnInfo(name = "thumbnail")
    private String thumbnail;
    @ColumnInfo(name = "accepts_mercadopago")
    private boolean accepts_mercadopago;
    @ColumnInfo(name = "available_quantity")
    private int available_quantity;
    @ColumnInfo(name = "permalink")
    private String permalink;


    // use for ordering the items in view
    public static DiffUtil.ItemCallback<ProdOverView> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProdOverView>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProdOverView oldItem, @NonNull ProdOverView newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProdOverView oldItem, @NonNull ProdOverView newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Bindable
    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    @Bindable
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Bindable
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Bindable
    public boolean isAccepts_mercadopago() {
        return accepts_mercadopago;
    }

    public void setAccepts_mercadopago(boolean accepts_mercadopago) {
        this.accepts_mercadopago = accepts_mercadopago;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }
}

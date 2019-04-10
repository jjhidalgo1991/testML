package com.jangapp.myappmla.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jangapp.myappmla.R;
import com.jangapp.myappmla.repository.storge.model.NetworkState;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;
import com.jangapp.myappmla.ui.listeners.ItemClickListener;
import com.jangapp.myappmla.ui.view.viewholder.ProductViewHolder;
import com.jangapp.myappmla.ui.view.viewholder.NetworkStateItemViewHolder;

/**
 * Created by Elad on 6/25/2018.
 */

public class ProductsPageListAdapter extends PagedListAdapter<ProdOverView, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private ItemClickListener itemClickListener;

    public ProductsPageListAdapter(ItemClickListener itemClickListener) {
        super(ProdOverView.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.product_item) {
            View view = layoutInflater.inflate(R.layout.product_item, parent, false);
            ProductViewHolder viewHolder = new ProductViewHolder(view, itemClickListener);;
            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            View view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.product_item:
                ((ProductViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.product_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}

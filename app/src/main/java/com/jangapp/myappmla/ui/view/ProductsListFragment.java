package com.jangapp.myappmla.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jangapp.myappmla.R;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;
import com.jangapp.myappmla.ui.adapter.ProductsPageListAdapter;
import com.jangapp.myappmla.ui.listeners.ItemClickListener;
import com.jangapp.myappmla.ui.viewmodel.ProductDetailsViewModel;
import com.jangapp.myappmla.ui.viewmodel.ProductsListViewModel;


public class ProductsListFragment extends Fragment implements ItemClickListener {

    protected ProductsListViewModel viewModel;
    private ProductDetailsViewModel detailsViewModel;

    protected RecyclerView recyclerView;
    protected SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView = view.findViewById(R.id.productsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(getActivity()).get(ProductsListViewModel.class);
        searchView = view.findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                observersRegisters(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                observersRegisters(s);
                return true;
            }
        });


        // observersRegisters();
        return view;
    }

    private void observersRegisters(String query) {

        final ProductsPageListAdapter pageListAdapter = new ProductsPageListAdapter(this);
        viewModel.getProducts(query).observe(this, pageListAdapter::submitList);
        viewModel.getNetworkState().observe(this, networkState -> {
            pageListAdapter.setNetworkState(networkState);
        });
        recyclerView.setAdapter(pageListAdapter);
        detailsViewModel = ViewModelProviders.of(getActivity()).get(ProductDetailsViewModel.class);
    }

    @Override
    public void OnItemClick(ProdOverView prodOverView) {
        detailsViewModel.getProduct().postValue(prodOverView);
        if (!detailsViewModel.getProduct().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            ProductsDetailsFragment detailsFragment = new ProductsDetailsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }
}

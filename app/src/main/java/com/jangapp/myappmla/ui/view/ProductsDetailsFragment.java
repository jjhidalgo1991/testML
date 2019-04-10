package com.jangapp.myappmla.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jangapp.myappmla.R;
import com.jangapp.myappmla.ui.viewmodel.ProductDetailsViewModel;
import com.squareup.picasso.Picasso;


public class ProductsDetailsFragment extends Fragment {

    private ProductDetailsViewModel viewModel;

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        if (url != null) {
            Picasso.get().load(url).into(view);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdetails, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(ProductDetailsViewModel.class);

        final ImageView image = view.findViewById(R.id.cover);
        final TextView price = view.findViewById(R.id.price);
        final TextView title = view.findViewById(R.id.title);
        final TextView link = view.findViewById(R.id.link);
        final TextView quanty = view.findViewById(R.id.quanty);
        final TextView condition = view.findViewById(R.id.condition);
        final TextView mercado = view.findViewById(R.id.mercado_pago);

        viewModel.getProduct().observe(this, prodOverView -> {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (prodOverView.getTitle() != null) {
                        title.setText(prodOverView.getTitle());
                    }
                    if (prodOverView.getCurrency_id() != null) {
                        price.setText(prodOverView.getCurrency_id() + " " + String.format("%1$,.2f", prodOverView.getPrice()));
                    }
                    if (prodOverView.getCondition() != null) {
                        condition.setText(prodOverView.getCondition().toUpperCase());
                    }


                        quanty.setText(prodOverView.getAvailable_quantity()+" disponibles");


                    mercado.setText(prodOverView.isAccepts_mercadopago() ? "MERCADO PAGO" : "");
                    if (prodOverView.getThumbnail() != null) {
                        String poster = prodOverView.getThumbnail();
                        if (!poster.equals(""))
                            Picasso.get().load(poster).into(image);
                    }
                }
            });

        });
        return view;
    }
}

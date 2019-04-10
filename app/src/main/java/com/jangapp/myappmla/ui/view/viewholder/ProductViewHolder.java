package com.jangapp.myappmla.ui.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jangapp.myappmla.R;
import com.jangapp.myappmla.repository.storge.model.ProdOverView;
import com.jangapp.myappmla.ui.listeners.ItemClickListener;
import com.squareup.picasso.Picasso;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private ProdOverView prodOverView;
    private TextView titleTextView;
    private TextView priceTextView;
    private TextView conditionTextView;
    private TextView mercadopagoTextView;
    private ImageView thumbnailImageView;
    private ItemClickListener itemClickListener;

    public ProductViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        this.titleTextView = view.findViewById(R.id.title);
        this.priceTextView = view.findViewById(R.id.price);
        this.conditionTextView = view.findViewById(R.id.condition);
        this.mercadopagoTextView = view.findViewById(R.id.mercado_pago);
        this.thumbnailImageView = view.findViewById(R.id.thumbnail);
        this.itemClickListener = itemClickListener;
        view.setOnClickListener(this);

    }

    public void bindTo(ProdOverView prodOverView) {
        this.prodOverView = prodOverView;
        if (prodOverView.getTitle() != null) {
            titleTextView.setText(prodOverView.getTitle());
        }
        if (prodOverView.getCurrency_id() != null) {
            priceTextView.setText(prodOverView.getCurrency_id() + " " + String.format("%1$,.2f", prodOverView.getPrice()));
        }
        if (prodOverView.getCondition() != null) {
            conditionTextView.setText(prodOverView.getCondition().toUpperCase());
        }
        mercadopagoTextView.setText(prodOverView.isAccepts_mercadopago() ? "MERCADO PAGO" : "");
        if (prodOverView.getThumbnail() != null) {
            String poster = prodOverView.getThumbnail();
            if (!poster.equals(""))
                Picasso.get().load(poster).into(thumbnailImageView);
        }
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(prodOverView); // call the onClick in the OnItemClickListener
        }
    }

}

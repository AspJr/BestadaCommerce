package com.testing.bestcommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testing.bestcommerce.R;
import com.testing.bestcommerce.model.ClsProductDetail;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private List<ClsProductDetail> clsProductDetails;
    private Context context;


    public AdapterProduct(List<ClsProductDetail> clsProductDetails, Context context) {
        this.clsProductDetails = clsProductDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(clsProductDetails.get(position).getNama_produk());
        holder.description.setText(clsProductDetails.get(position).getDeskripsi_produk());
    }

    @Override
    public int getItemCount() {
        return clsProductDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
        }
    }
}

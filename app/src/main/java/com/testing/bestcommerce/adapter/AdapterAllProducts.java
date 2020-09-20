package com.testing.bestcommerce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testing.bestcommerce.DetailProductActivity;
import com.testing.bestcommerce.MainActivity;
import com.testing.bestcommerce.R;
import com.testing.bestcommerce.model.ClsProductDetail;

import java.util.List;

public class AdapterAllProducts extends RecyclerView.Adapter<AdapterAllProducts.ViewHolder> {

    private List<ClsProductDetail> clsProductDetails;
    private Context context;

    public AdapterAllProducts(List<ClsProductDetail> clsProductDetails, Context context) {
        this.clsProductDetails = clsProductDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAllProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_all_products, parent, false);
        return new AdapterAllProducts.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllProducts.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txt_id.setText(Integer.toString(clsProductDetails.get(position).getId()));
        holder.txt_nama_produk.setText(clsProductDetails.get(position).getNama_produk());
        holder.txt_harga.setText("Rp "+clsProductDetails.get(position).getHarga());
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("id_produk", Integer.toString(clsProductDetails.get(position).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clsProductDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product;
        TextView txt_id, txt_nama_produk, txt_harga;
        CardView card_product;
        LinearLayout lyt_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_product = itemView.findViewById(R.id.iv_product);
            txt_id = itemView.findViewById(R.id.txt_id);
            txt_nama_produk = itemView.findViewById(R.id.txt_nama_produk);
            txt_harga = itemView.findViewById(R.id.txt_harga);
            card_product = itemView.findViewById(R.id.card_product);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }
}

package com.testing.bestcommerce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.testing.bestcommerce.R;
import com.testing.bestcommerce.model.ClsDeliveryDetailForSeller;
import com.testing.bestcommerce.model.ClsInboxCheckout;
import com.testing.bestcommerce.orm.tbl_new_cart;

import java.util.List;

public class AdapterInboxDetail extends RecyclerView.Adapter<AdapterInboxDetail.ViewHolder>  {

    private List<ClsInboxCheckout> clsInboxCheckouts;
    private Context context;

    public AdapterInboxDetail(List<ClsInboxCheckout> clsInboxCheckouts, Context context) {
        this.clsInboxCheckouts = clsInboxCheckouts;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInboxDetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart, parent, false);
        return new AdapterInboxDetail.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInboxDetail.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txt_harga.setText(String.valueOf(clsInboxCheckouts.get(position).getHarga()));
        holder.txt_qty.setText(String.valueOf(clsInboxCheckouts.get(position).getQty()));
        holder.txt_nama_produk.setText(String.valueOf(clsInboxCheckouts.get(position).getNama_produk()));
        holder.txt_nama_produk.setText(String.valueOf(clsInboxCheckouts.get(position).getNama_produk()));
    }

    @Override
    public int getItemCount() {
        return clsInboxCheckouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        ImageButton ib_add, ib_min;
        TextView txt_nama_produk, txt_nama_toko, txt_harga, txt_qty, txt_id_produk, txt_id_penjual, txt_id_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            txt_nama_produk = itemView.findViewById(R.id.txt_nama_produk);
            txt_nama_toko = itemView.findViewById(R.id.txt_nama_toko);
            txt_harga = itemView.findViewById(R.id.txt_harga);
            txt_qty = itemView.findViewById(R.id.txt_qty);
            ib_add = itemView.findViewById(R.id.ib_add);
            ib_min = itemView.findViewById(R.id.ib_min);
            txt_id_produk = itemView.findViewById(R.id.txt_id_produk);
            txt_id_penjual = itemView.findViewById(R.id.txt_id_penjual);
            txt_id_user = itemView.findViewById(R.id.txt_id_user);
        }
    }
}

package com.testing.bestcommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.testing.bestcommerce.KeranjangActivity;
import com.testing.bestcommerce.R;
import com.testing.bestcommerce.orm.tbl_new_cart;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {
    public List<tbl_new_cart> p_tbl_new_carts;
    Context pContext;

    public AdapterCart(List<tbl_new_cart> p_tbl_new_carts, Context pContext) {
        this.p_tbl_new_carts = p_tbl_new_carts;
        this.pContext = pContext;
    }

    @NonNull
    @Override
    public AdapterCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCart.ViewHolder holder, final int position) {
        final tbl_new_cart iTbl = p_tbl_new_carts.get(position);

        holder.txt_id_produk.setText(String.valueOf(iTbl.id_produk));
        holder.txt_id_penjual.setText(String.valueOf(iTbl.id_penjual));
        holder.txt_id_user.setText(String.valueOf(iTbl.id_user));
        holder.txt_nama_produk.setText(iTbl.nama_produk);
        holder.txt_harga.setText(iTbl.harga);
        holder.txt_qty.setText(String.valueOf(iTbl.qty));
        holder.ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = holder.txt_qty.getText().toString();
                int new_qty = Integer.parseInt(qty) + 1;
                holder.txt_qty.setText(String.valueOf(new_qty));
                int total = new_qty * Integer.parseInt(iTbl.harga);
                KeranjangActivity.pInstance.setTotal(total);
            }
        });
        holder.ib_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = holder.txt_qty.getText().toString();
                if(Integer.parseInt(qty) > 1){
                    int new_qty = Integer.parseInt(qty) - 1;
                    holder.txt_qty.setText(String.valueOf(new_qty));
                    int total = new_qty * Integer.parseInt(iTbl.harga);
                    KeranjangActivity.pInstance.setTotal(total);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return p_tbl_new_carts.size();
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

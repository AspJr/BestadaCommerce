package com.testing.bestcommerce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testing.bestcommerce.DetailProductActivity;
import com.testing.bestcommerce.InboxDetailActivity;
import com.testing.bestcommerce.R;
import com.testing.bestcommerce.model.ClsDeliveryDetailForSeller;
import com.testing.bestcommerce.model.ClsProductDetail;

import java.util.List;

public class AdapterInbox extends RecyclerView.Adapter<AdapterInbox.ViewHolder> {

    private List<ClsDeliveryDetailForSeller> clsDeliveryDetailForSellers;
    private Context context;

    public AdapterInbox(List<ClsDeliveryDetailForSeller> clsDeliveryDetailForSellers, Context context) {
        this.clsDeliveryDetailForSellers = clsDeliveryDetailForSellers;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInbox.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_inbox, parent, false);
        return new AdapterInbox.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInbox.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            holder.txt_kurir.setText(clsDeliveryDetailForSellers.get(position).getKurir());
            holder.txt_id_pengiriman.setText(String.valueOf(clsDeliveryDetailForSellers.get(position).getId()));
            holder.txt_id_order.setText("Order Number : "+clsDeliveryDetailForSellers.get(position).getOrder_number());
            if(clsDeliveryDetailForSellers.get(position).getStatus_pengiriman() == 0){
                holder.txt_status.setText("Menunggu Konfirmasi");
            } else if(clsDeliveryDetailForSellers.get(position).getStatus_pengiriman() == 1){
                holder.txt_status.setText("Proses Pengiriman");
            } else {
                holder.txt_status.setText("Pesanan Selesai");
            }
            holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InboxDetailActivity.class);
                    intent.putExtra("id_pengiriman", Integer.toString(clsDeliveryDetailForSellers.get(position).getId()));
                    intent.putExtra("param_kurir", clsDeliveryDetailForSellers.get(position).getKurir());
                    intent.putExtra("param_alamat", clsDeliveryDetailForSellers.get(position).getAlamat_pengiriman());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return clsDeliveryDetailForSellers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id_order,txt_status,txt_id_pengiriman,txt_kurir;
        LinearLayout lyt_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            txt_id_order = itemView.findViewById(R.id.txt_id_order);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_id_pengiriman = itemView.findViewById(R.id.txt_id_pengiriman);
            txt_kurir = itemView.findViewById(R.id.txt_kurir);
        }
    }
}

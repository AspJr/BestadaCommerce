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

import com.testing.bestcommerce.DetailHistoryActivity;
import com.testing.bestcommerce.InboxDetailActivity;
import com.testing.bestcommerce.R;
import com.testing.bestcommerce.model.ClsDeliveryDetailForSeller;
import com.testing.bestcommerce.model.ClsDetailHistory;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {

    private List<ClsDetailHistory> clsDetailHistories;
    private Context context;

    public AdapterHistory(List<ClsDetailHistory> clsDetailHistories, Context context) {
        this.clsDetailHistories = clsDetailHistories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_history, parent, false);
        return new AdapterHistory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            holder.txt_kurir.setText(clsDetailHistories.get(position).getKurir());
            holder.txt_id_pengiriman.setText(String.valueOf(clsDetailHistories.get(position).getId()));
            holder.txt_id_order.setText("Order Number : "+clsDetailHistories.get(position).getOrder_number());
            if(clsDetailHistories.get(position).getStatus_pengiriman() == 0){
                holder.txt_status.setText("Menunggu Konfirmasi");
            } else if(clsDetailHistories.get(position).getStatus_pengiriman() == 1){
                holder.txt_status.setText("Proses Pengiriman");
            } else {
                holder.txt_status.setText("Pesanan Selesai");
            }
            holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailHistoryActivity.class);
                    intent.putExtra("id_pengiriman", Integer.toString(clsDetailHistories.get(position).getId()));
                    intent.putExtra("param_kurir", clsDetailHistories.get(position).getKurir());
                    intent.putExtra("param_alamat", clsDetailHistories.get(position).getAlamat_pengiriman());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return clsDetailHistories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id_pengiriman,txt_kurir,txt_id_order,txt_status;
        LinearLayout lyt_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_id_pengiriman = itemView.findViewById(R.id.txt_id_pengiriman);
            txt_kurir = itemView.findViewById(R.id.txt_kurir);
            txt_id_order = itemView.findViewById(R.id.txt_id_order);
            txt_status = itemView.findViewById(R.id.txt_status);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }
}

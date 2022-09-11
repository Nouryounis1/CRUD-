package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemProductViewHolder extends RecyclerView.ViewHolder {

    public TextView namaBarang;
    public TextView merkBarang;
    public TextView hargaBarang;
    public View view;

    public ItemProductViewHolder(View view){
        super(view);

        namaBarang = (TextView)view.findViewById(R.id.nama_barang);
        merkBarang = (TextView)view.findViewById(R.id.merk_barang);
        hargaBarang = (TextView)view.findViewById(R.id.harga_barang);
        this.view = view;
    }
}

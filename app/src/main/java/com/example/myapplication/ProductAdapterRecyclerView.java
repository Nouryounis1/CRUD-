package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapterRecyclerView  extends RecyclerView.Adapter<ItemProductViewHolder> {

    private Context context;
    private ArrayList<ProductModel> daftarBarang;
    private FirebaseDataListener listener;

    public ProductAdapterRecyclerView(Context context, ArrayList<ProductModel> daftarBarang){
        this.context = context;
        this.daftarBarang = daftarBarang;
        this.listener = (FirebaseDataListener)context;
    }

    @Override
    public ItemProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // TODO: Implement this method
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        ItemProductViewHolder holder = new ItemProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemProductViewHolder holder,   int position)
    {
        // TODO: Implement this method
        holder.namaBarang.setText("Nama   : "+daftarBarang.get(position).getNama());
        holder.merkBarang.setText("Brand     : "+daftarBarang.get(position).getMerk());
        holder.hargaBarang.setText("Price   : "+daftarBarang.get(position).getHarga());

        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onDataClick(daftarBarang.get(holder.getBindingAdapterPosition()), holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        // TODO: Implement this method
        return daftarBarang.size();
    }


     public interface FirebaseDataListener {
        void onDataClick(ProductModel barang, int position);
    }

}

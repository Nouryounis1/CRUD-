package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CRUDactiviy extends AppCompatActivity   implements  ProductAdapterRecyclerView.FirebaseDataListener   {

    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;
    private EditText mEditNama;
    private EditText mEditMerk;
    private EditText mEditHarga;
    private RecyclerView mRecyclerView;
    private ProductAdapterRecyclerView mAdapter;
    private ArrayList<ProductModel> daftarBarang;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudactiviy);
     //   setupToolbar(R.id.toolbar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("products");
        mDatabaseReference.child("producs_data").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                daftarBarang = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()){
                    ProductModel barang = mDataSnapshot.getValue(ProductModel.class);
                    barang.setKey(mDataSnapshot.getKey());
                    daftarBarang.add(barang);
                }
                //set adapter RecyclerView
                mAdapter = new ProductAdapterRecyclerView(CRUDactiviy.this, daftarBarang);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                // TODO: Implement this method
                Toast.makeText(CRUDactiviy.this, databaseError.getDetails()+" "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


         mFloatingActionButton = (FloatingActionButton)findViewById(R.id.tambah_barang);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemDialog();
            }
        });




    }

        private void itemDialog() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Item");
            View view = getLayoutInflater().inflate(R.layout.layout_tambah_barang, null);

            mEditNama = (EditText)view.findViewById(R.id.nama_barang);
            mEditMerk = (EditText)view.findViewById(R.id.merk_barang);
            mEditHarga = (EditText)view.findViewById(R.id.harga_barang);

            builder.setView(view);

             builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id){

                    String namaBarang = mEditNama.getText().toString();
                    String merkBarang = mEditMerk.getText().toString();
                    String hargaBarang = mEditHarga.getText().toString();

                    if(!namaBarang.isEmpty() && !merkBarang.isEmpty() && !hargaBarang.isEmpty()){
                        submitProduct(new ProductModel(namaBarang, merkBarang, hargaBarang));
                    }
                    else {
                        Toast.makeText(CRUDactiviy.this, "Data must be filled in!", Toast.LENGTH_LONG).show();
                    }
                }
            });

             builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id){
                    dialog.dismiss();
                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }

    private void updateDialog(final ProductModel product){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data  ");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_barang, null);

        mEditNama = (EditText)view.findViewById(R.id.nama_barang);
        mEditMerk = (EditText)view.findViewById(R.id.merk_barang);
        mEditHarga = (EditText)view.findViewById(R.id.harga_barang);

        mEditNama.setText(product.getNama());
        mEditMerk.setText(product.getMerk());
        mEditHarga.setText(product.getHarga());
        builder.setView(view);

         if (product != null){
            builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id){
                    product.setNama(mEditNama.getText().toString());
                    product.setMerk(mEditMerk.getText().toString());
                    product.setHarga(mEditHarga.getText().toString());
                    updateProductData(product);
                }
            });
        }
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }



    private void submitProduct(ProductModel product){
        mDatabaseReference.child("producs_data").push().setValue(product).addOnSuccessListener(this, new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(CRUDactiviy.this, "Item data saved successfully !", Toast.LENGTH_LONG).show();
            }
        });
    }



    private void updateProductData(ProductModel product){
        mDatabaseReference.child("producs_data").child(product.getKey()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(CRUDactiviy.this, "Data successfully updated !", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void deleteProductData(ProductModel product){
        if(mDatabaseReference != null){
            mDatabaseReference.child("producs_data").child(product.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void mVoid){
                    Toast.makeText(CRUDactiviy.this,"Data deleted successfully !", Toast.LENGTH_LONG).show();
                }
            });
        }


    }


    @Override
    public void onDataClick(final ProductModel product, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                updateDialog(product);
            }
        });
        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                deleteProductData(product);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void setupToolbar(int id){
        mToolbar = (Toolbar)findViewById(id);
        setSupportActionBar(mToolbar);
    }


}
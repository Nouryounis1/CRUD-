package com.example.myapplication;

public class ProductModel {

    private String key;
    private String name;
    private String brand;
    private String price;


    public ProductModel() {

    }
    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setNama(String name){
        this.name = name;
    }

    public String getNama(){
        return name;
    }

    public void setMerk(String brand){
        this.brand = brand;
    }

    public String getMerk(){
        return brand;
    }

    public void setHarga(String price){
        this.price = price;
    }

    public String getHarga(){
        return price;
    }

    public ProductModel(String namaBarang, String merkBarang, String hargaBarang){
        name = namaBarang;
        brand = merkBarang;
        price = hargaBarang;
    }
}

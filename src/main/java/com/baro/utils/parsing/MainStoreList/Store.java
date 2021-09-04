package com.baro.utils.parsing.MainStoreList;

public class Store {
    public int store_id;
    public String store_name;

    public Store(int store_id, String store_name) {
        this.store_id = store_id;
        this.store_name = store_name;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    @Override
    public String toString() {
        return "Store{" +
                "store_id=" + store_id +
                ", store_name='" + store_name + '\'' +
                '}';
    }
}

package com.baro.utils.parsing.MainStoreList;

public class NewStore {
    public int new_store_id;
    public String store_name;

    @Override
    public String toString() {
        return "NewStore{" +
                "new_store_id=" + new_store_id +
                ", store_name='" + store_name + '\'' +
                '}';
    }

    public int getNew_store_id() {
        return new_store_id;
    }

    public void setNew_store_id(int new_store_id) {
        this.new_store_id = new_store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public NewStore(int new_store_id, String store_name) {
        this.new_store_id = new_store_id;
        this.store_name = store_name;
    }
}

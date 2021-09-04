package com.baro.utils.parsing.MainStoreList;

public class Ultra {
    public int ultra_store_id;
    public String store_name;

    public Ultra(int ultra_store_id, String store_name) {
        this.ultra_store_id = ultra_store_id;
        this.store_name = store_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getUltra_store_id() {
        return ultra_store_id;
    }

    public void setUltra_store_id(int ultra_store_id) {
        this.ultra_store_id = ultra_store_id;
    }
}

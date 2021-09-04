package com.baro.utils.parsing.MainStoreList;

import java.util.ArrayList;

public class StoreList {
    public boolean result;
    public ArrayList<Store> store;

    public StoreList(boolean result, ArrayList<Store> store) {
        this.result = result;
        this.store = store;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<Store> getStore() {
        return store;
    }

    public void setStore(ArrayList<Store> store) {
        this.store = store;
    }
}

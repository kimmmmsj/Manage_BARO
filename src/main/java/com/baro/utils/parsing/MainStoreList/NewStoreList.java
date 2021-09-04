package com.baro.utils.parsing.MainStoreList;

import java.util.ArrayList;

public class NewStoreList {
    public boolean result;
    public String message;
    public ArrayList<NewStore> newStore;

    public NewStoreList(boolean result, String message, ArrayList<NewStore> newStore) {
        this.result = result;
        this.message = message;
        this.newStore = newStore;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<NewStore> getNewStore() {
        return newStore;
    }

    public void setNewStore(ArrayList<NewStore> newStore) {
        this.newStore = newStore;
    }

    @Override
    public String toString() {
        return "NewStoreList{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", newStore=" + newStore +
                '}';
    }
}

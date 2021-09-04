package com.baro.utils.parsing.MainStoreList;

import java.util.ArrayList;

public class UltraList {
    public boolean result;
    public String message;
    public ArrayList<Ultra> ultra;

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

    public ArrayList<Ultra> getUltra() {
        return ultra;
    }

    public void setUltra(ArrayList<Ultra> ultra) {
        this.ultra = ultra;
    }

    @Override
    public String toString() {
        return "UltraList{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", ultra=" + ultra +
                '}';
    }

    public UltraList(boolean result, String message, ArrayList<Ultra> ultra) {
        this.result = result;
        this.message = message;
        this.ultra = ultra;
    }
}

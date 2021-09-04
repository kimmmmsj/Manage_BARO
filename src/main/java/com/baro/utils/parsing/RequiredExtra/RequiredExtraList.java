package com.baro.utils.parsing.RequiredExtra;

import java.util.ArrayList;

public class RequiredExtraList {
    public boolean result;
    public ArrayList<RequiredExtra> requiredExtras;

    @Override
    public String toString() {
        return "RequiredExtraList{" +
                "result=" + result +
                ", requiredExtras=" + requiredExtras +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<RequiredExtra> getRequiredExtras() {
        return requiredExtras;
    }

    public void setRequiredExtras(ArrayList<RequiredExtra> requiredExtras) {
        this.requiredExtras = requiredExtras;
    }

    public RequiredExtraList(boolean result, ArrayList<RequiredExtra> requiredExtras) {
        this.result = result;
        this.requiredExtras = requiredExtras;
    }
}

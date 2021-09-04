package com.baro.utils.parsing.MenuExtra;

import java.util.ArrayList;

public class MenuExtraList {
    public boolean result;
    public ArrayList<MenuExtra> extras;

    public MenuExtraList(boolean result, ArrayList<MenuExtra> extras) {
        this.result = result;
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "MenuExtraList{" +
                "result=" + result +
                ", extras=" + extras +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<MenuExtra> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<MenuExtra> extras) {
        this.extras = extras;
    }
}

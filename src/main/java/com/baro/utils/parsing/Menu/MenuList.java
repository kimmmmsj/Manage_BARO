package com.baro.utils.parsing.Menu;

import java.util.ArrayList;

public class MenuList {
    public boolean result;
    public String message;
    public ArrayList<Menu> menu;

    public MenuList(boolean result, String message, ArrayList<Menu> menu) {
        this.result = result;
        this.message = message;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "MenuList{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", menu=" + menu +
                '}';
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

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Menu> menu) {
        this.menu = menu;
    }
}

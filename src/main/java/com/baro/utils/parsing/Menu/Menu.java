package com.baro.utils.parsing.Menu;

public class Menu {
    public int menu_defaultprice;
    public int menu_id;
    public String menu_name;

    public Menu(int menu_defaultprice, int menu_id, String menu_name) {
        this.menu_defaultprice = menu_defaultprice;
        this.menu_id = menu_id;
        this.menu_name = menu_name;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu_defaultprice=" + menu_defaultprice +
                ", menu_id=" + menu_id +
                ", menu_name='" + menu_name + '\'' +
                '}';
    }

    public int getMenu_defaultprice() {
        return menu_defaultprice;
    }

    public void setMenu_defaultprice(int menu_defaultprice) {
        this.menu_defaultprice = menu_defaultprice;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
}

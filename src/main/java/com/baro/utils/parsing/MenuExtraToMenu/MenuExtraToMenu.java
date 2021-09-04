package com.baro.utils.parsing.MenuExtraToMenu;

public class MenuExtraToMenu {
    public int extra_price;
    public String extra_name;
    public int id;
    public int extra_maxcount;

    @Override
    public String toString() {
        return "MenuExtraToMenu{" +
                "extra_price=" + extra_price +
                ", extra_name='" + extra_name + '\'' +
                ", id=" + id +
                ", extra_maxcount=" + extra_maxcount +
                '}';
    }

    public int getExtra_price() {
        return extra_price;
    }

    public void setExtra_price(int extra_price) {
        this.extra_price = extra_price;
    }

    public String getExtra_name() {
        return extra_name;
    }

    public void setExtra_name(String extra_name) {
        this.extra_name = extra_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExtra_maxcount() {
        return extra_maxcount;
    }

    public void setExtra_maxcount(int extra_maxcount) {
        this.extra_maxcount = extra_maxcount;
    }

    public MenuExtraToMenu(int extra_price, String extra_name, int id, int extra_maxcount) {
        this.extra_price = extra_price;
        this.extra_name = extra_name;
        this.id = id;
        this.extra_maxcount = extra_maxcount;
    }
}

package com.baro.utils.parsing.MenuExtra;

public class MenuExtra {
    public int extra_id;
    public int extra_price;
    public String extra_name;

    @Override
    public String toString() {
        return "MenuExtra{" +
                "extra_id=" + extra_id +
                ", extra_price=" + extra_price +
                ", extra_name='" + extra_name + '\'' +
                '}';
    }

    public int getExtra_id() {
        return extra_id;
    }

    public void setExtra_id(int extra_id) {
        this.extra_id = extra_id;
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

    public MenuExtra(int extra_id, int extra_price, String extra_name) {
        this.extra_id = extra_id;
        this.extra_price = extra_price;
        this.extra_name = extra_name;
    }
}

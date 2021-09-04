package com.baro.utils.parsing.RequiredExtra;

public class RequiredExtra {
    public int store_id;
    public String extra_group;
    public int extra_id;
    public int extra_price;
    public String extra_name;

    @Override
    public String toString() {
        return "RequiredExtra{" +
                "store_id=" + store_id +
                ", extra_group='" + extra_group + '\'' +
                ", extra_id=" + extra_id +
                ", extra_price=" + extra_price +
                ", extra_name='" + extra_name + '\'' +
                '}';
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getExtra_group() {
        return extra_group;
    }

    public void setExtra_group(String extra_group) {
        this.extra_group = extra_group;
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

    public RequiredExtra(int store_id, String extra_group, int extra_id, int extra_price, String extra_name) {
        this.store_id = store_id;
        this.extra_group = extra_group;
        this.extra_id = extra_id;
        this.extra_price = extra_price;
        this.extra_name = extra_name;
    }
}

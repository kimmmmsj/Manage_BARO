package com.baro.Models;

public class DeleteOrderModel {
    public String order_date;
    public String receipt_id;
    public String order_state;
    public String store_name;
    public int store_id;
    public String representative_name;

    public String getRepresentative_name() {
        return representative_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public int getStore_id() {
        return store_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public String getOrder_state() {
        return order_state;
    }
}

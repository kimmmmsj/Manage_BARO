package com.baro.utils.parsing.Coupon;

public class Coupon {
    public int coupon_id;
    public String coupon_title;

    public Coupon(int coupon_id, String coupon_title) {
        this.coupon_id = coupon_id;
        this.coupon_title = coupon_title;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "coupon_id=" + coupon_id +
                ", coupon_title='" + coupon_title + '\'' +
                '}';
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }
}

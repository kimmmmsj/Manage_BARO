package com.baro.utils.parsing.Coupon;

import java.util.ArrayList;

public class CouponList {
    public boolean result;
    public ArrayList<Coupon> coupon;

    public CouponList(boolean result, ArrayList<Coupon> coupon) {
        this.result = result;
        this.coupon = coupon;
    }

    @Override
    public String toString() {
        return "CouponList{" +
                "result=" + result +
                ", coupon=" + coupon +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<Coupon> getCoupon() {
        return coupon;
    }

    public void setCoupon(ArrayList<Coupon> coupon) {
        this.coupon = coupon;
    }
}

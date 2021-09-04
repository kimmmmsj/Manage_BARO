package com.baro.Models;

import java.util.ArrayList;

public class PayBackResult {
    public String message;
    public ArrayList<PayBack> payBackMoneyList;
    public Boolean result;

    public String getMessage() {
        return message;
    }

    public ArrayList<PayBack> getPayBackMoneyList() {
        return payBackMoneyList;
    }

    public Boolean getResult() {
        return result;
    }
}

package com.baro.Models;

import java.util.ArrayList;

public class DeleteOrderResult {
    public boolean result;
    public ArrayList<DeleteOrderModel> requiredExtras;
    public String message;

    public boolean isResult() {
        return result;
    }

    public ArrayList<DeleteOrderModel> getRequiredExtras() {
        return requiredExtras;
    }

    public String getMessage() {
        return message;
    }


}

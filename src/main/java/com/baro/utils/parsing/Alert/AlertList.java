package com.baro.utils.parsing.Alert;

import java.util.ArrayList;

public class AlertList {
    public boolean result;
    public ArrayList<Alert> alert;

    public AlertList(boolean result, ArrayList<Alert> alert) {
        this.result = result;
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "AlertList{" +
                "result=" + result +
                ", alert=" + alert +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<Alert> getAlert() {
        return alert;
    }

    public void setAlert(ArrayList<Alert> alert) {
        this.alert = alert;
    }
}

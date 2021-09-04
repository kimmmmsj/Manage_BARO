package com.baro.utils.parsing.Alert;

public class Alert {
    public String alert_title;
    public int alert_id;

    public Alert(String alert_title, int alert_id) {
        this.alert_title = alert_title;
        this.alert_id = alert_id;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alert_title='" + alert_title + '\'' +
                ", alert_id=" + alert_id +
                '}';
    }

    public String getAlert_title() {
        return alert_title;
    }

    public void setAlert_title(String alert_title) {
        this.alert_title = alert_title;
    }

    public int getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(int alert_id) {
        this.alert_id = alert_id;
    }
}

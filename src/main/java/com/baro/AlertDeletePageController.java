package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.Alert.Alert;
import com.baro.utils.parsing.Alert.AlertList;
import com.baro.utils.parsing.MainStoreList.Ultra;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertDeletePageController implements Initializable {
    @FXML
    private JFXListView<AnchorPane> alert_list_view;
    @FXML
    private Button alert_delete_button;

    private AlertList alertList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeAlertList();
    }
    public void makeAlertList() {
        String result = ConnectToServer.Get("AlertPrint.do");
        if(GetBool.getBool(result)){
            setAlertList(result);
        }
    }

    private void setAlertList(String result) {
        alertList = new Gson().fromJson(result, AlertList.class);
        if(!alertList.result || alertList.alert == null) {
            return;
        }
        if(alert_list_view.getItems().size() != 0 ){
            alert_list_view.getItems().clear();
        }

        AnchorPane header = new AnchorPane();
        Text alertIdText = new Text("alert_id");
        Text alertNameText = new Text("alert_name");

        header.getChildren().addAll(alertIdText, alertNameText);
        header.getChildren().get(1).setLayoutX(100);

        alert_list_view.getItems().add(header);
        for (int i = 0; i < alertList.alert.size(); i++) {
            Alert alert = alertList.alert.get(i);
            AnchorPane content = new AnchorPane();
            Label alertId = new Label(alert.alert_id+"");
            Label alertName = new Label(alert.alert_title);
            CheckBox thisAlert = new CheckBox();
            final int index = i;

            thisAlert.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        alert_delete_button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(event.getEventType() == ActionEvent.ACTION) {
                                    String result = ConnectToServer.Get("AlertDelete.do?alert_id="+alert.alert_id);
                                    if(GetBool.getBool(result)) {
                                        alertList.alert.remove(index);
                                        alert_list_view.getItems().remove(index + 1);
                                        System.out.println("삭제완료");
                                    }
                                    else {
                                        System.out.println("삭제실패");
                                    }
                                }
                            }
                        });
                    }else {

                    }
                }
            });
            content.getChildren().addAll(alertId, alertName, thisAlert);
            content.getChildren().get(1).setLayoutX(100);
            content.getChildren().get(2).setLayoutX(200);
            alert_list_view.getItems().add(content);
        }
    }
}

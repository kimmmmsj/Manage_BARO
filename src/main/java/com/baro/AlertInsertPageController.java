package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertInsertPageController implements Initializable {
    public TextField alert_title_insert;
    public TextField alert_content_insert;
    public Button insert_alert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonEvent();
    }
    public void setButtonEvent() {
        insert_alert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alertInsert(alert_title_insert.getText(), alert_content_insert.getText());
            }
        });
    }
    public void alertInsert(String alertTitle, String alertContent) {
        if(alertTitle.equals("")||alertContent.equals("")) {
            System.out.println("내용을 입력해주세요");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("alert_title", alertTitle);
        jsonObject.put("alert_content", alertContent);
        String result = ConnectToServer.Post("AlertInsert.do", jsonObject);
        if(GetBool.getBool(result)) {
            alert_title_insert.clear();
            alert_content_insert.clear();
            System.out.println("추가 성공");
        }
    }
}

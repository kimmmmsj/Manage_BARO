package com.baro;

import com.baro.Models.DeleteOrderModel;
import com.baro.Models.DeleteOrderResult;
import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ResourceBundle;

public class MarketingController implements Initializable {
    @FXML
    public TextField titleInput;
    @FXML
    public TextField contentInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        SendMarketing
    }

    public void sendFcm(ActionEvent event) {
        if (titleInput.getText() == "" || contentInput.getText() == ""){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title",titleInput.getText());
        jsonObject.put("content",contentInput.getText());
        ConnectToServer.Post("SendMarketing.do",jsonObject);

    }
}

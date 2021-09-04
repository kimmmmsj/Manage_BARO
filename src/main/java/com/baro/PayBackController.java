package com.baro;

import com.baro.Models.DeleteOrderModel;
import com.baro.Models.PayBack;
import com.baro.Models.PayBackResult;
import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PayBackController implements Initializable {
    @FXML private TableView payBackTable;
    @FXML private TableColumn<PayBack,String> store_id_column;
    @FXML private TableColumn<PayBack,String> payBackSum_column;
    @FXML private TableColumn<PayBack,String> store_name_column;
    @FXML private JFXDatePicker date_picker;
    @FXML private JFXButton look_up_button;
    private PayBackResult payBacks = new PayBackResult();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        store_id_column.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        payBackSum_column.setCellValueFactory(new PropertyValueFactory<>("payBackSum"));
        store_name_column.setCellValueFactory(new PropertyValueFactory<>("store_name"));
    }

    public void getData(ActionEvent event) {
        if(date_picker.getValue() == null ) {
            return;
        }
        String result = ConnectToServer.Get("/PayBackMoney.do?date="+date_picker.getValue());
        System.out.println(result);
        if(GetBool.getBool(result)) {
            payBacks = new Gson().fromJson(result, PayBackResult.class);
            ObservableList<PayBack> collections = FXCollections.observableArrayList(payBacks.payBackMoneyList);
            payBackTable.setItems(collections);
        }else {
        }
    }
}

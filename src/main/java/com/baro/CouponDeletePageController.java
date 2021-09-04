package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.Alert.Alert;
import com.baro.utils.parsing.Coupon.Coupon;
import com.baro.utils.parsing.Coupon.CouponList;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CouponDeletePageController implements Initializable {
    public JFXListView coupon_list_view;
    public Button coupon_delete_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCouponList();
    }
    public void getCouponList() {
        String result = ConnectToServer.Get("CouponPrint.do");
        if(GetBool.getBool(result)) {
            setCouponList(result);
        }
    }

    private void setCouponList(String result) {
        CouponList couponList = new Gson().fromJson(result, CouponList.class);
        if(!couponList.result || couponList.coupon == null) {
            return;
        }
        if(coupon_list_view.getItems().size() != 0) {
            coupon_list_view.getItems().clear();
        }
        AnchorPane header = new AnchorPane();
        Text couponIdText = new Text("coupon_id");
        Text couponNameText = new Text("coupon_name");
        header.getChildren().addAll(couponIdText, couponNameText);
        header.getChildren().get(1).setLayoutX(100);
        coupon_list_view.getItems().add(header);
        for (int i = 0; i < couponList.coupon.size(); i++) {
            final int index = i;
            Coupon coupon = couponList.coupon.get(i);
            AnchorPane content = new AnchorPane();
            Label couponId = new Label(coupon.coupon_id+"");
            Label couponName = new Label(coupon.coupon_title);
            CheckBox thisCoupon = new CheckBox();

            thisCoupon.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        System.out.println("isTrue");
                        coupon_delete_button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(event.getEventType() == ActionEvent.ACTION) {
                                    String result = ConnectToServer.Get("CouponDelete.do?coupon_id=" + coupon.coupon_id);
                                    if (GetBool.getBool(result)) {
                                        couponList.coupon.remove(index);
                                        coupon_list_view.getItems().remove(index + 1);
                                    }else {
                                        System.out.println("삭제 실패");
                                    }
                                }
                            }
                        });
                    }else {

                    }
                }
            });
            content.getChildren().addAll(couponId, couponName, thisCoupon);
            content.getChildren().get(1).setLayoutX(100);
            content.getChildren().get(2).setLayoutX(200);
            coupon_list_view.getItems().add(content);
        }
    }
}

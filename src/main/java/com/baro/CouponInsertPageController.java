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

public class CouponInsertPageController implements Initializable {
    public TextField coupon_title_insert;
    public TextField coupon_content_insert;
    public TextField coupon_condition_insert;
    public TextField coupon_discount_insert;
    public TextField coupon_type_insert;
    public TextField coupon_enddate_insert;
    public TextField coupon_number_insert;
    public Button insert_coupon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insert_coupon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertCoupon();
            }
        });
    }
    public void insertCoupon() {
        String couponTitle = coupon_title_insert.getText();
        String couponContent = coupon_content_insert.getText();
        String couponCondition = coupon_condition_insert.getText();
        String couponDiscount = coupon_discount_insert.getText();
        String couponType = coupon_type_insert.getText();
        String couponEndDate = coupon_enddate_insert.getText();
        String couponNumber = coupon_number_insert.getText();
        
        if(couponTitle.equals("") || couponContent.equals("") || couponCondition.equals("") || couponDiscount.equals("") ||
        couponType.equals("") || couponEndDate.equals("") || couponNumber.equals("")) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("coupon_title", couponTitle)
                .put("coupon_content", couponContent)
                .put("coupon_condition", couponCondition)
                .put("coupon_discount", couponDiscount)
                .put("coupon_type", couponType)
                .put("coupon_enddate", couponEndDate)
                .put("coupon_number", couponNumber);
        if(GetBool.getBool(ConnectToServer.Post("CouponInsert.do", jsonObject))) {
            coupon_title_insert.clear();
            coupon_content_insert.clear();
            coupon_condition_insert.clear();
            coupon_discount_insert.clear();
            coupon_type_insert.clear();
            coupon_enddate_insert.clear();
            coupon_number_insert.clear();
            System.out.println("추가성공");
        }
    }
}

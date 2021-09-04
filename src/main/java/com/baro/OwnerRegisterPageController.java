package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class OwnerRegisterPageController implements Initializable {
    @FXML
    private TextField idInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void save(ActionEvent event) {
        if (idInput.getText().equals("") || phoneInput.getText().equals("") || emailInput.getText().equals("") || passwordInput.getText().equals("")){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phoneInput.getText())
                .put("email", emailInput.getText())
                .put("id",idInput.getText() )
                .put("pass", passwordInput.getText());
        String result = ConnectToServer.Post("FxOwnerRegister.do",jsonObject);
        if (GetBool.getBool(result)){
            idInput.clear();
            phoneInput.clear();
            emailInput.clear();
            passwordInput.clear();
            System.out.println("등록성공");
        }else{
            System.out.println(GetBool.getMessage(result));
        }
    }
}

package com.baro;

import com.baro.Models.Type;
import com.baro.Models.TypePrintResult;
import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class StoreInsertPageController implements Initializable {
    @FXML
    private ChoiceBox<String> type_code_choice;
    @FXML
    private TextField ownerPhoneInput;
    @FXML
    private TextField searchResult;
    @FXML
    private TextField storeNameInput;
    @FXML
    private TextField storeLatitudeInput;
    @FXML
    private TextField storeLongitudeInput;
    @FXML
    private TextField storeOpenTimeInput;
    @FXML
    private TextField storeCloseTimeInput;
    @FXML
    private TextField storePhoneInput;
    @FXML
    private TextField storeDayOFFInput;
    @FXML
    private TextField storeLocationInput;
    @FXML
    private TextField storeInfoInput;
//    @FXML
//    private TextField storeImageInput;
    @FXML
    private TextField representativeNameInput;
    @FXML
    private TextField businessNumberInput;

    private TypePrintResult raw;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String result = ConnectToServer.Get("TypePrint.do");
        System.out.println(result);
        updateLayout(result);
        ownerPhoneInput.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ownerPhoneInput.setText("");
                searchResult.setText("");
            }
        });
    }
    public void updateLayout(String sentence){
        if (GetBool.getBool(sentence)){
            raw = new Gson().fromJson(sentence,TypePrintResult.class);
            int sizeOfRaw = raw.getType().size();
            for (int i = 0;i<sizeOfRaw;i++) {
                type_code_choice.getItems().add(raw.getType().get(sizeOfRaw - i - 1).getType_code());
            }
        }
    }

    public void pressAdd(ActionEvent event) {
        if (searchResult.getText().equals("")  || storeNameInput.getText().equals("") || storeLatitudeInput.getText().equals("") || storeLongitudeInput.getText().equals("")
                || storeOpenTimeInput.getText().equals("") || storeCloseTimeInput.getText().equals("") || storePhoneInput.getText().equals("") || storeDayOFFInput.getText().equals("")
                || storeDayOFFInput.getText().equals("") || storeLocationInput.getText().equals("") || storeInfoInput.getText().equals("") ||
                 representativeNameInput.getText().equals("") || businessNumberInput.getText().equals("") ) {
            System.out.println("공백없애세요");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type_code",type_code_choice.getSelectionModel().getSelectedItem());
        jsonObject.put("store_name",storeNameInput.getText());
        jsonObject.put("store_latitude",storeLatitudeInput.getText());
        jsonObject.put("store_longitude",storeLongitudeInput.getText());
        jsonObject.put("store_opentime",storeOpenTimeInput.getText());
        jsonObject.put("store_closetime",storeCloseTimeInput.getText());
        jsonObject.put("store_phone",storePhoneInput.getText());
        jsonObject.put("store_dayoff",storeDayOFFInput.getText());
        jsonObject.put("store_location",storeLocationInput.getText());
        jsonObject.put("store_info",storeInfoInput.getText());
//        jsonObject.put("store_image",storeImageInput.getText());
        jsonObject.put("owner_id",searchResult.getText());
        jsonObject.put("representative_name",representativeNameInput.getText());
        jsonObject.put("business_number",businessNumberInput.getText());
        String result = ConnectToServer.Post("StoreInsert.do",jsonObject);
        if (GetBool.getBool(result)) {
            System.out.println("가게 등록 성공");
            ownerPhoneInput.setText("");
            type_code_choice.getSelectionModel().clearSelection();
            storeNameInput.setText("");
            storeLatitudeInput.setText("");
            storeLongitudeInput.setText("");
            storeOpenTimeInput.setText("");
            storeCloseTimeInput.setText("");
            storePhoneInput.setText("");
            storeDayOFFInput.setText("");
            storeLocationInput.setText("");
            storeInfoInput.setText("");
//            storeImageInput.setText("");
            searchResult.setText("");
            representativeNameInput.setText("");
            businessNumberInput.setText("");
        }else{
            System.out.println(GetBool.getMessage(result));
        }
    }

    public void pressSearch(ActionEvent event) {
        String result = ConnectToServer.Get("FindOwnerByPhone.do?owner_phone=" + ownerPhoneInput.getText());
        if (GetBool.getBool(result)){
            searchResult.setText(new JSONObject(result).getString("owner_id"));
        }
    }
}

package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.MainStoreList.Store;
import com.baro.utils.parsing.MainStoreList.StoreList;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuExtraInsertPageController implements Initializable {


    public HBox required_hbox;
    @FXML
    private TextField store_search_text_field;
    @FXML
    private Button search_button;
    @FXML
    private VBox store_list_vbox;
    @FXML
    private JFXListView<AnchorPane> search_store_result_list;
    @FXML
    private Button extra_menu_insert_button;
    @FXML
    private VBox menu_extra_insert_vbox;
    @FXML
    private TextField extra_price_insert;
    @FXML
    private TextField extra_name_insert;
    public JFXToggleButton set_required;
    public TextField required_group_name_insert;
    private boolean isLoading = false;

    int selectedStoreCheckedBoxIndex = 0;

    public void initialize(URL location, ResourceBundle resources) {
        store_list_vbox.setVisible(false);
        menu_extra_insert_vbox.setVisible(false);
        required_hbox.setVisible(false);
        set_required.setSelected(false);
        setSearchStoreEvent();
    }
    private void setSearchStoreEvent() {
        store_search_text_field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER && !isLoading) {
                    isLoading = true;
                    String storeNameTrim = store_search_text_field.getText().trim();
                    makeStoreList(storeNameTrim);
                }
            }
        });

        search_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(event.getEventType() == ActionEvent.ACTION) {
                    isLoading = true;
                    String storeNameTrim = store_search_text_field.getText().trim();
                    makeStoreList(storeNameTrim);
                }
            }
        });
    }
    public void makeStoreList(String storeNameTrim) {
        String result = ConnectToServer.GetWithParam("StorePrintByStoreName.do?store_name=", storeNameTrim);
        if(GetBool.getBool(result)){
            setStoreList(result);
        }
    }

    private void setStoreList(String result) {
        StoreList storeList = new Gson().fromJson(result, StoreList.class);
        if(!storeList.result || storeList.store == null) {
            return;
        }
        store_list_vbox.setVisible(true);
        if(search_store_result_list.getItems().size() != 0) {
            search_store_result_list.getItems().clear();
        }
        AnchorPane header = new AnchorPane();
        Text storeIdText = new Text("Store_Id");
        Text storeNameText = new Text("Store_name");
        header.getChildren().addAll(storeIdText, storeNameText);
        header.getChildren().get(1).setLayoutX(100);
        search_store_result_list.getItems().add(header);

        for (int i = 0 ; i < storeList.store.size(); i++) {
            Store store = storeList.store.get(i);
            final int index = i;
            AnchorPane content = new AnchorPane();
            Label storeId = new javafx.scene.control.Label(store.store_id+"");
            Label storeName = new Label(store.store_name);
            CheckBox thisStore = new CheckBox();

            thisStore.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(selectedStoreCheckedBoxIndex != index) {
                        ((CheckBox)search_store_result_list.getItems().get(selectedStoreCheckedBoxIndex+1).getChildren().get(2)).setSelected(false);
                    }
                    if(newValue) {
                        selectedStoreCheckedBoxIndex = index;
                        menu_extra_insert_vbox.setVisible(true);


                        set_required.selectedProperty().addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                if(newValue) {
                                    required_hbox.setVisible(true);
                                }
                                else{
                                    required_hbox.setVisible(false);
                                }
                            }
                        });
                        extra_menu_insert_button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(event.getEventType() == ActionEvent.ACTION) {
                                    System.out.println("click");
                                    String menuExtraName = extra_name_insert.getText();
                                    String menuExtraPrice = extra_price_insert.getText();
                                    System.out.println(menuExtraName+" : "+menuExtraPrice);
                                    if(menuExtraPrice.equals("") || menuExtraName.equals("")) {
                                        return;
                                    }
                                    System.out.println("pass");
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("extra_price", menuExtraPrice);
                                    jsonObject.put("extra_name", menuExtraName);
                                    jsonObject.put("store_id", store.store_id);
                                    System.out.println("property : " +set_required.selectedProperty().getValue());
                                    if(set_required.selectedProperty().getValue()) {
                                        jsonObject.put("is_required", true);
                                        jsonObject.put("required_group_name",required_group_name_insert.getText());
                                    }

                                    if(GetBool.getBool(ConnectToServer.Post("ExtraInsert.do", jsonObject))) {
                                        System.out.println("추가성공");
                                        extra_name_insert.clear();
                                        extra_price_insert.clear();
                                        menu_extra_insert_vbox.setVisible(false);
                                    }else {
                                        System.out.println("추가실패");

                                    }
                                }
                                else {

                                }
                            }
                        });
                    }else {

                    }
                }
            });
            content.getChildren().addAll(storeId, storeName, thisStore);
            content.getChildren().get(1).setLayoutX(100);
            content.getChildren().get(2).setLayoutX(200);
            search_store_result_list.getItems().add(content);
            isLoading = false;
        }
    }
}

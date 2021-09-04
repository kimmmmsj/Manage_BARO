package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.Category.Category;
import com.baro.utils.parsing.Category.CategoryList;
import com.baro.utils.parsing.MainStoreList.Store;
import com.baro.utils.parsing.MainStoreList.StoreList;
import com.baro.utils.parsing.Menu.Menu;
import com.baro.utils.parsing.Menu.MenuList;
import com.baro.utils.parsing.MenuExtra.MenuExtra;
import com.baro.utils.parsing.MenuExtra.MenuExtraList;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuExtraDeletePageController implements Initializable {
    @FXML
    private TextField store_search_text_field;
    @FXML
    private Button search_button;
    @FXML
    private VBox store_list_vbox;
    @FXML
    private JFXListView<AnchorPane> search_store_result_list;
    @FXML
    private Button search_store_category_button;
    @FXML
    private VBox menu_extra_vbox;
    @FXML
    private JFXListView<AnchorPane> menu_extra_list;
    @FXML
    private Button extra_delete_button;
    private boolean isLoading = false;

    int selectedStoreCheckedBoxIndex = 0;

    public void initialize(URL location, ResourceBundle resources) {
        store_list_vbox.setVisible(false);
        menu_extra_vbox.setVisible(false);
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
                        search_store_category_button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(event.getEventType() == ActionEvent.ACTION) {
                                    String result = ConnectToServer.Get("ExtraPrint.do?store_id="+store.store_id);
                                    if(GetBool.getBool(result)) {
                                        menu_extra_vbox.setVisible(true);
                                        makeMenuExtraList(result);
                                    }
                                }
                                else {
                                    if(menu_extra_vbox.isVisible())
                                    menu_extra_vbox.setVisible(false);
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

    private void makeMenuExtraList(String result) {
        MenuExtraList menuExtraList = new Gson().fromJson(result, MenuExtraList.class);
        if(!menuExtraList.result || menuExtraList.extras == null) {
            return;
        }
        if(menu_extra_list.getItems().size() != 0) {
            menu_extra_list.getItems().clear();
        }
        AnchorPane header = new AnchorPane();
        Label extraIdLabel = new Label("extra_id");
        Label extraPriceLabel = new Label("extra_price");
        Label extraNameLabel = new Label("extra_name");
        header.getChildren().addAll(extraIdLabel, extraPriceLabel, extraNameLabel);
        header.getChildren().get(1).setLayoutX(100);
        header.getChildren().get(2).setLayoutX(200);
        menu_extra_list.getItems().add(header);
        for (int i = 0; i < menuExtraList.extras.size(); i++) {
            final int index = i;
            MenuExtra menuExtra = menuExtraList.extras.get(i);
            AnchorPane content = new AnchorPane();
            Text extraIdText = new Text(menuExtra.extra_id+"");
            Text extraPriceText = new Text(menuExtra.extra_price+"");
            Text extraNameText = new Text(menuExtra.extra_name);
            CheckBox thisMenuExtra = new CheckBox();
            thisMenuExtra.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        extra_delete_button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(GetBool.getBool(ConnectToServer.Get("ExtraDelete.do?extra_id="+menuExtra.extra_id))) {
                                    System.out.println("삭제완료");
                                    menuExtraList.extras.remove(index);
                                    menu_extra_list.getItems().remove(index + 1);
                                    if(menuExtraList.extras.size() == 0) {
                                        menu_extra_vbox.setVisible(false);
                                    }
                                }else {
                                    System.out.println("삭제실패");
                                }
                            }
                        });
                    }else {

                    }
                }
            });
            content.getChildren().addAll(extraIdText, extraPriceText, extraNameText, thisMenuExtra);
            content.getChildren().get(1).setLayoutX(100);
            content.getChildren().get(2).setLayoutX(200);
            content.getChildren().get(3).setLayoutX(300);
            menu_extra_list.getItems().add(content);
        }

    }
}


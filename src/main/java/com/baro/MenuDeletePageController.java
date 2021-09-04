package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.Category.Category;
import com.baro.utils.parsing.Category.CategoryList;
import com.baro.utils.parsing.MainStoreList.Store;
import com.baro.utils.parsing.MainStoreList.StoreList;
import com.baro.utils.parsing.Menu.Menu;
import com.baro.utils.parsing.Menu.MenuList;
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
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuDeletePageController implements Initializable {
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
    private JFXListView<AnchorPane> category_name_list;
    @FXML
    private VBox menu_delete_vbox;
    @FXML
    private JFXListView<AnchorPane> menu_list;
    @FXML
    private Button delete_menu_button;
    private boolean isLoading = false;

    int selectedStoreCheckedBoxIndex = 0;
    int selectedCategoryCheckBoxIndex = 0;

    public void initialize(URL location, ResourceBundle resources) {
        store_list_vbox.setVisible(false);
        category_name_list.setVisible(false);
        menu_delete_vbox.setVisible(false);

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
                                    getCategoryList(store.store_id);
                                }
                                else {
                                    if(category_name_list.isVisible()) {
                                        category_name_list.setVisible(false);
                                    }
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

    private void getCategoryList(int store_id) {
        String result = ConnectToServer.Get("CategoryPrintByStoreId.do?store_id="+store_id);
        if(GetBool.getBool(result)) {
            setCategoryList(result);
        }
    }

    private void setCategoryList(String result) {
        CategoryList categoryList = new Gson().fromJson(result, CategoryList.class);
        if(!categoryList.result || categoryList.category == null) {
            return;
        }
        category_name_list.setVisible(true);
        if(category_name_list.getItems().size() != 0) {
            category_name_list.getItems().clear();
        }
        for(int i = 0 ; i < categoryList.category.size() ; i ++) {
            Category category = categoryList.category.get(i);
            final int index = i;
            AnchorPane categoryContent = new AnchorPane();
            Label categoryName = new Label(category.category_name);
            CheckBox thisCategory = new CheckBox();

            thisCategory.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(selectedCategoryCheckBoxIndex != index) {
                        ((CheckBox)category_name_list.getItems().get(selectedCategoryCheckBoxIndex+1).getChildren().get(1)).setSelected(false);
                    }
                    if(newValue) {
                        selectedCategoryCheckBoxIndex = index;
                        String result = ConnectToServer.Get("MenuPrintByCategoryId.do?category_id="+category.category_id);
                        if(GetBool.getBool(result)) {
                            makeMenuList(result);
                        }
                    }
                    else {
                        if(menu_delete_vbox.isVisible()) {
                            menu_delete_vbox.setVisible(false);
                        }
                    }
                }
            });
            categoryContent.getChildren().addAll(categoryName, thisCategory);
            categoryContent.getChildren().get(1).setLayoutX(100);
            category_name_list.getItems().add(categoryContent);
        }
    }

    private void makeMenuList(String result) {
        MenuList menuList = new Gson().fromJson(result, MenuList.class);
        if(!menuList.result || menuList.menu == null) {
            return;
        }
        menu_delete_vbox.setVisible(true);
        if(menu_list.getItems().size() != 0) {
            menu_list.getItems().clear();
        }
        AnchorPane header = new AnchorPane();
        Label menuIdLabel = new Label("menu_id");
        Label menuNameLabel = new Label("menu_name");
        Label menuPriceLabel = new Label("menu_defaultprice");
        header.getChildren().addAll(menuIdLabel, menuNameLabel, menuPriceLabel);
        header.getChildren().get(1).setLayoutX(100);
        header.getChildren().get(2).setLayoutX(200);

        menu_list.getItems().add(header);
        for (int i = 0; i < menuList.menu.size() ; i++) {
            final int index = i;
            Menu menu = menuList.menu.get(i);
            AnchorPane content = new AnchorPane();
            Text menuIdText = new Text(menu.menu_id+"");
            Text menuNameText = new Text(menu.menu_name);
            Text menuPriceText = new Text(menu.menu_defaultprice+"");
            CheckBox thisMenu = new CheckBox();
            content.getChildren().addAll(menuIdText, menuNameText, menuPriceText, thisMenu);
            content.getChildren().get(1).setLayoutX(100);
            content.getChildren().get(2).setLayoutX(200);
            content.getChildren().get(3).setLayoutX(300);
            thisMenu.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        delete_menu_button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(GetBool.getBool(ConnectToServer.Get("MenuDelete.do?menu_id="+menu.menu_id))) {
                                    menuList.menu.remove(index);
                                    menu_list.getItems().remove(index+1);
                                    System.out.println("삭제성공");
                                }else {
                                    System.out.println("삭제실패");
                                }
                            }
                        });
                    }
                    else {

                    }
                }
            });
            menu_list.getItems().add(content);
        }
    }
}

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
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuExtraApplyToMenuPageController implements Initializable {
    public TextField extra_maxcount_insert;
    public Button inser_to_menu_extra_to_menu;
    @FXML
    private VBox menu_list_vbox;
    @FXML
    private JFXListView menu_list;

    @FXML
    private VBox menu_extra_list_vbox;
    @FXML
    private JFXListView menu_extra_list;
    @FXML
    private VBox menu_extra_insert_to_menu_vbox;
    @FXML
    private TextField store_search_text_field;
    @FXML
    private Button search_button;
    @FXML
    private VBox store_list_vbox;
    @FXML
    private JFXListView search_store_result_list;
    @FXML
    private Button search_store_category_button;
    @FXML
    private JFXListView category_name_list;

    private boolean isLoading = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("reload");
        store_list_vbox.setVisible(false);
        category_name_list.setVisible(false);
        menu_list_vbox.setVisible(false);
        menu_extra_list_vbox.setVisible(false);
        menu_extra_insert_to_menu_vbox.setVisible(false);
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

        for (Store store : storeList.store) {
            AnchorPane content = new AnchorPane();
            Label storeId = new javafx.scene.control.Label(store.store_id+"");
            Label storeName = new Label(store.store_name);
            CheckBox thisStore = new CheckBox();

            thisStore.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
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
            setCategoryList(result, store_id);
        }
    }

    private void setCategoryList(String result, int store_id) {
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
                    if(newValue) {
                        String result = ConnectToServer.Get("MenuPrintByCategoryId.do?category_id="+category.category_id);
                        if(GetBool.getBool(result)) {
                            makeMenuList(result, store_id);
                        }
                    }
                    else {
                        if(menu_list_vbox.isVisible()) {
                            menu_list_vbox.setVisible(false);
                        }
                    }
                }
            });
            categoryContent.getChildren().addAll(categoryName, thisCategory);
            categoryContent.getChildren().get(1).setLayoutX(100);
            category_name_list.getItems().add(categoryContent);
        }
    }

    private void makeMenuList(String result, int storeId) {
        MenuList menuList = new Gson().fromJson(result, MenuList.class);
        if(!menuList.result || menuList.menu == null) {
            return;
        }
        menu_list_vbox.setVisible(true);
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
                        String result = ConnectToServer.Get("ExtraPrint.do?store_id="+storeId);
                        if(GetBool.getBool(result)) {
                            makeMenuExtraList(result, menu.menu_id);
                        }
                    }
                    else {

                    }
                }
            });
            menu_list.getItems().add(content);
        }
    }
    private void makeMenuExtraList(String result, int menuId) {
        MenuExtraList menuExtraList = new Gson().fromJson(result, MenuExtraList.class);
        if (!menuExtraList.result || menuExtraList.extras == null) {
            return;
        }
        menu_extra_list_vbox.setVisible(true);
        if (menu_extra_list.getItems().size() != 0) {
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
            Text extraIdText = new Text(menuExtra.extra_id + "");
            Text extraPriceText = new Text(menuExtra.extra_price + "");
            Text extraNameText = new Text(menuExtra.extra_name);
            CheckBox thisMenuExtra = new CheckBox();
            thisMenuExtra.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        menu_extra_insert_to_menu_vbox.setVisible(true);
                        inser_to_menu_extra_to_menu.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                String menuMaxCount = extra_maxcount_insert.getText();
                                if (menuMaxCount.equals("")) {
                                    return;
                                }
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("extra_id", menuExtra.extra_id);
                                jsonObject.put("menu_id", menuId);
                                jsonObject.put("extra_maxcount", menuMaxCount);
                                if (GetBool.getBool(ConnectToServer.Post("InsertExtraByMenu.do", jsonObject))) {
                                    menu_extra_insert_to_menu_vbox.setVisible(false);
                                    menu_extra_list_vbox.setVisible(false);
                                    System.out.println("추가완료");
                                } else {
                                    menu_extra_insert_to_menu_vbox.setVisible(false);
                                    menu_extra_list_vbox.setVisible(false);
                                    System.out.println("추가실패");
                                }
                                thisMenuExtra.setSelected(false);
                            }
                        });
                    } else {

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


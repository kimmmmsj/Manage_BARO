package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.Category.Category;
import com.baro.utils.parsing.Category.CategoryList;
import com.baro.utils.parsing.MainStoreList.Store;
import com.baro.utils.parsing.MainStoreList.StoreList;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ResourceBundle;

public class CategoryDeletePageController implements Initializable {
    public VBox store_list_vbox;
    @FXML
    private TextField store_search_text_field;
    @FXML
    private Button search_button;
    @FXML
    private JFXListView<AnchorPane> search_store_result_list;
    @FXML
    private VBox insert_category_name_vbox;
    @FXML
    private JFXListView<AnchorPane> category_name_list;
    @FXML
    private Button delete_category_name_button;

    private StoreList storeList;
    private boolean isLoading = false;
    public void initialize(URL location, ResourceBundle resources) {
        insert_category_name_vbox.setVisible(false);
        store_list_vbox.setVisible(false);
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
                if(event.getEventType() == ActionEvent.ACTION && !isLoading) {
                    isLoading = true;
                    String storeNameTrim = store_search_text_field.getText().trim();
                    makeStoreList(storeNameTrim);
                }
            }
        });
    }
    public void makeStoreList(String storeNameTrim) {
        try {
            String paramEncode = URLEncoder.encode(storeNameTrim, "UTF-8");
            URL url = new URL("http://3.35.180.57:8080/StorePrintByStoreName.do?store_name="+paramEncode);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/json;utf-8");
            http.setRequestProperty("Accept", "application/json");
            http.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer bf = new StringBuffer();

            while ((line = br.readLine()) != null) {
                bf.append(line);
            }
            br.close();

            if(GetBool.getBool(bf.toString())) {
                storeList = new Gson().fromJson(bf.toString(), StoreList.class);
                getStoreList();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isLoading = false;
    }
    public void getStoreList() {
        if(storeList == null || storeList.store == null) {
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
                        CategoryList categoryList = new Gson().fromJson(ConnectToServer.Get("CategoryPrintByStoreId.do?store_id=" + store.store_id), CategoryList.class);
                        if(categoryList.category.size() == 0) {
                            return;
                        }
                        insert_category_name_vbox.setVisible(true);
                        if(category_name_list.getItems().size() != 0) {
                            category_name_list.getItems().clear();
                        }

                        AnchorPane header = new AnchorPane();
                        Text categoryNameText = new Text("Category_name");
                        header.getChildren().add(categoryNameText);
                        category_name_list.getItems().add(header);


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
                                        delete_category_name_button.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                if(GetBool.getBool(ConnectToServer.Get("CategoryDelete.do?category_id="+category.category_id))) {
                                                    categoryList.category.remove(index);
                                                    category_name_list.getItems().remove(index +1);
                                                    System.out.println("삭제성공");
                                                }
                                            }
                                        });
                                    }
                                    else {

                                    }
                                }
                            });
                            categoryContent.getChildren().addAll(categoryName, thisCategory);
                            categoryContent.getChildren().get(1).setLayoutX(100);
                            category_name_list.getItems().add(categoryContent);
                        }

                    }else {

                    }
                }
            });
            content.getChildren().addAll(storeId, storeName, thisStore);
            content.getChildren().get(1).setLayoutX(100);
            content.getChildren().get(2).setLayoutX(200);
            search_store_result_list.getItems().add(content);

        }
        isLoading = false;
    }

}

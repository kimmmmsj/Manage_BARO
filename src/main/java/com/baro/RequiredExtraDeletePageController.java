package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.SearchStoreList;
import com.baro.utils.parsing.RequiredExtra.RequiredExtra;
import com.baro.utils.parsing.RequiredExtra.RequiredExtraList;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequiredExtraDeletePageController implements Initializable, SearchStoreList.ClickStore {
    public HBox store_list_hbox;

    public TableView<RequiredExtra> required_table_view;
    public TableColumn<RequiredExtra, String> extra_group;
    public TableColumn<RequiredExtra, Integer> extra_id;
    public TableColumn<RequiredExtra, String> extra_price;
    public TableColumn<RequiredExtra, String> extra_name;
    public HBox required_list_box;
    public TableColumn<RequiredExtra, String> store_id;
    public Button delete_button;

    @Override
    public void clickStore(int store_id) {
        makeRequiredExtraList(store_id);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        required_list_box.setVisible(false);
        getStoreList();
    }
    public void getStoreList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/commonListLayout/searchStoreList.fxml"));
            Parent contentView = loader.load();
            if(store_list_hbox.getChildren().size() != 0) {
                store_list_hbox.getChildren().remove(0);
            }
            SearchStoreList searchStoreList = loader.<SearchStoreList>getController();
            System.out.println(searchStoreList.toString());
            searchStoreList.setInterface(this);
            store_list_hbox.getChildren().add(contentView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void makeRequiredExtraList(int storeId) {
        String result = ConnectToServer.Get("RequiredExtrasPrint.do?store_id="+storeId);
        System.out.println(result);
        if(GetBool.getBool(result)) {
            setRequiredExtraList(result);
        }else {
            System.out.println("clear");
            required_list_box.setVisible(false);
        }
    }

    private void setRequiredExtraList(String result) {
        RequiredExtraList requiredExtraList = new Gson().fromJson(result, RequiredExtraList.class);
        System.out.println(requiredExtraList.toString());
        if(!requiredExtraList.result || requiredExtraList.requiredExtras == null) {
            return;
        }
        required_list_box.setVisible(true);
        if(required_table_view.getItems().size() != 0 ) {
            required_table_view.getItems().clear();
        }
        store_id.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        extra_group.setCellValueFactory(new PropertyValueFactory<>("extra_group"));
        extra_id.setCellValueFactory(new PropertyValueFactory<>("extra_id"));
        extra_price.setCellValueFactory(new PropertyValueFactory<>("extra_price"));
        extra_name.setCellValueFactory(new PropertyValueFactory<>("extra_name"));

        ObservableList<RequiredExtra> collections = FXCollections.observableArrayList(requiredExtraList.requiredExtras);
        required_table_view.setItems(collections);
    }

    public void onClick(MouseEvent mouseEvent) {
        int extraId = required_table_view.getSelectionModel().getSelectedItem().extra_id;
        if(extraId == 0) {
            return;
        }
        delete_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(GetBool.getBool(ConnectToServer.Get("RequiredExtrasDelete.do?extra_id="+extraId))) {
                    System.out.println("삭제 성공");
                    required_table_view.getItems().remove(required_table_view.getSelectionModel().getSelectedItem());
                }
            }
        });
    }
}

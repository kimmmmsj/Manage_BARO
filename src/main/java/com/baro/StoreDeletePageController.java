package com.baro;

import com.baro.Models.Store;
import com.baro.Models.StorePrintResult;
import com.baro.Models.Type;
import com.baro.Models.TypePrintResult;
import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class StoreDeletePageController implements Initializable {
    @FXML
    private TableView<Store> table;
    @FXML
    private TableColumn<Store,String> id_column;
    @FXML
    private TableColumn<Store,String> type_code_column;
    @FXML
    private TableColumn<Store,String> name_column;
    @FXML
    private TextField searchInput;
    @FXML
    private Button searchBtn;
    private StorePrintResult raw;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_column.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        type_code_column.setCellValueFactory(new PropertyValueFactory<>("type_code"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("store_name"));
//        String result = ConnectToServer.Get("StorePrintByStoreName.do?store_name=");
//        System.out.println(result);
        searchBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                searchInput.setText("");
            }
        });
//        updateLayout(result);
    }
    public void updateLayout(String sentence){
        if (GetBool.getBool(sentence)){
            raw = new Gson().fromJson(sentence, StorePrintResult.class);
            ObservableList<Store> collections = FXCollections.observableArrayList(raw.getStore());
            table.setItems(collections);
        }
    }
    public void delete(ActionEvent event) {
        String result = ConnectToServer.Get("StoreDelete.do?store_id="+table.getSelectionModel().getSelectedItem().getStore_id());
        if (GetBool.getBool(result)){
            table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        }
    }

    public void search(ActionEvent event) {
        try {
            String paramEncode = URLEncoder.encode(searchInput.getText(), "UTF-8");
            String result = ConnectToServer.Get("StorePrintByStoreName.do?store_name="+paramEncode);
            System.out.println(result);
            updateLayout(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}

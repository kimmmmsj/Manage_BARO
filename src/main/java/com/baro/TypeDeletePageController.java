package com.baro;

import com.baro.Models.Type;
import com.baro.Models.TypePrintResult;
import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bouncycastle.math.raw.Mod;
import org.omg.CORBA.portable.ValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TypeDeletePageController implements Initializable {
    @FXML
    private TableView<Type> table;
    @FXML
    private TableColumn<Type,String> code_column;
    @FXML
    private TableColumn<Type,String> name_column;
    private TypePrintResult raw;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        code_column.setCellValueFactory(new PropertyValueFactory<>("type_code"));
        code_column.setStyle("-fx-alignment : CENTER");
        name_column.setCellValueFactory(new PropertyValueFactory<>("type_name"));
        name_column.setStyle("-fx-alignment : CENTER");
        String result = ConnectToServer.Get("TypePrint.do");
        System.out.println(result);
        updateLayout(result);

    }
    public void updateLayout(String sentence){
        if (GetBool.getBool(sentence)){
            raw = new Gson().fromJson(sentence,TypePrintResult.class);
            ObservableList<Type> collections = FXCollections.observableArrayList(raw.getType());
            table.setItems(collections);
        }
    }
    public void delete(ActionEvent event) {
        String result = ConnectToServer.Get("TypeDelete.do?type_code="+table.getSelectionModel().getSelectedItem().getType_code());
        if (GetBool.getBool(result)){
            table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        }

    }
}

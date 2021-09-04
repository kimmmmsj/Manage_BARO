package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.MainStoreList.NewStore;
import com.baro.utils.parsing.MainStoreList.NewStoreList;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ResourceBundle;

public class NewStoreDeletePageController implements Initializable {
    public JFXListView new_store_list;
    public Button new_store_delete;
    private NewStoreList newStoreList;

    public void initialize(URL location, ResourceBundle resources) {
        getNewList();
    }
    public void getNewList() {
        try{
            URL url = new URL("http://3.35.180.57:8080/NewStorePrint.do");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type","application/json;utf-8");
            http.setRequestProperty("Accept","application/json");
            http.setDoOutput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer bf = new StringBuffer();

            while((line = br.readLine()) != null) {
                bf.append(line);
            }
            br.close();
            System.out.println("response" + bf.toString());

            newStoreList = new Gson().fromJson(bf.toString(), NewStoreList.class);

            if (newStoreList.result) {
                setNewList();
            }else{
                System.out.println("실패");
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setNewList() {
        if(!newStoreList.result || newStoreList.newStore == null ){
            return;
        }
        if(new_store_list.getItems().size() != 0) {
            new_store_list.getItems().clear();
        }
        AnchorPane header = new AnchorPane();
        Text storeIdText = new Text("Store_Id");
        Text storeNameText = new Text("Store_name");
        header.getChildren().addAll(storeIdText, storeNameText);
        header.getChildren().get(1).setLayoutX(100);
        new_store_list.getItems().add(header);

        for (int i = 0 ; i < newStoreList.newStore.size() ; i ++) {
            NewStore newStore = newStoreList.newStore.get(i);
            AnchorPane content = new AnchorPane();
            Label storeId = new Label(newStore.new_store_id+"");
            Label storeName = new Label(newStore.store_name);
            CheckBox thisStore = new CheckBox();
            final int index = i;
            thisStore.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        System.out.println("isTrue");
                        new_store_delete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(event.getEventType() == ActionEvent.ACTION) {
                                    if(GetBool.getBool(ConnectToServer.Get("NewStoreDelete.do?store_id="))) {
                                        newStoreList.newStore.remove(index);
                                        new_store_list.getItems().remove(index+1);
                                        System.out.println("삭제 성공");
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
            new_store_list.getItems().add(content);
        }
    }
}

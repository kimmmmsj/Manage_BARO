package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.parsing.MainStoreList.Ultra;
import com.baro.utils.parsing.MainStoreList.UltraList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class UltraStoreDeletePageController implements Initializable {
    public UltraList ultraList;
    @FXML
    private JFXListView<AnchorPane> ultra_store_list;
    @FXML
    private Button ultra_store_delete;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getUltraList();
    }
    private void setDeleteButton(int storeId) {
        if(storeId != 0) {
            deleteUltraStore(storeId);
        }else {

        }
    }
    public void deleteUltraStore(int store_id) {
        try{
            URL url = new URL("http://3.35.180.57:8080/UltraDelete.do?store_id="+store_id);
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
            if(GetBool.getBool(bf.toString())) {
                getUltraList();
            }
            else{
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
    public void getUltraList() {
        try{
            URL url = new URL("http://3.35.180.57:8080/UltraPrint.do");
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

            ultraList = new Gson().fromJson(bf.toString(), UltraList.class);

            if (ultraList.result) {
                setUltraList();
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
    public void setUltraList() {
        if(!ultraList.result || ultraList.ultra.size() == 0 ){
            return;
        }
        if(ultra_store_list.getItems().size() != 0) {
            ultra_store_list.getItems().clear();
        }
        AnchorPane header = new AnchorPane();
        Text storeIdText = new Text("Store_Id");
        Text storeNameText = new Text("Store_name");
        header.getChildren().addAll(storeIdText, storeNameText);
        header.getChildren().get(1).setLayoutX(100);
        ultra_store_list.getItems().add(header);

        for (int i = 0; i < ultraList.ultra.size(); i++) {
            Ultra ultra = ultraList.ultra.get(i);
            AnchorPane content = new AnchorPane();
            Label storeId = new Label(ultra.ultra_store_id+"");
            Label storeName = new Label(ultra.store_name);
            CheckBox thisStore = new CheckBox();

            final int index = i;
            thisStore.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        System.out.println("isTrue");
                        ultra_store_delete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(event.getEventType() == ActionEvent.ACTION) {
                                    if(GetBool.getBool(ConnectToServer.Get("UltraDelete.do?store_id="+ultra.ultra_store_id))) {
                                        ultraList.ultra.remove(index);
                                        ultra_store_list.getItems().remove(index+1);
                                        System.out.println("삭제완료");
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
            ultra_store_list.getItems().add(content);
        }
    }
}

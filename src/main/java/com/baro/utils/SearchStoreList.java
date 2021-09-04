package com.baro.utils;

import com.baro.utils.parsing.MainStoreList.Store;
import com.baro.utils.parsing.MainStoreList.StoreList;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchStoreList implements Initializable {
    /**
     *  스토어 클릭 인터페이스
     */
    public interface ClickStore{
        void clickStore(int store_id);
    }

    public TextField store_search_text_field;
    public Button search_button;
    public VBox store_list_vbox;
    public TableView<Store> search_store_result_list;
    public TableColumn<Store,String> store_id;
    public TableColumn<Store,String> store_name;


    /**
     *  스토어 검색으로 리스트 새로고침 중에는 검색 기능 막기
     */
    private boolean isLoading = false;



    /**
     *  스토어 클릭 인터페이스 객체
     */
    public ClickStore clickStore;



    /*******************************************************************************************************
     *
     * 스토어 테이블 클릭 이벤트 받아오기
     *
     *******************************************************************************************************/
    /**
     *  외부의 클래스는 아래 클래스에 implements 하여 구현부가 완성된 인터페이스를 넣어 주어야함
     */
    public void setInterface(ClickStore clickStore) {
        this.clickStore = clickStore;
    }



    /*******************************************************************************************************
     *
     * 스토어 테이블 뷰 마우스 클릭 이벤트
     *
     *******************************************************************************************************/
    public void onClick(MouseEvent mouseEvent) {
        clickStore.clickStore(search_store_result_list.getSelectionModel().getSelectedItem().store_id);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSearchStoreEvent();
    }


    /*******************************************************************************************************
     *
     * 스토어 검색 이벤트 | ENTER, Button Click
     *
     *******************************************************************************************************/
    /**
     *  검색 중 = true;
     */
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

    /*******************************************************************************************************
     *
     * 스토어 리스트 HTTP 통신
     *
     *******************************************************************************************************/
    public void makeStoreList(String storeNameTrim) {
        String result = ConnectToServer.GetWithParam("StorePrintByStoreName.do?store_name=", storeNameTrim);
        if(GetBool.getBool(result)){
            setStoreList(result);
        }
    }


    /*******************************************************************************************************
     *
     * 스토어 리스트 설정
     *
     *******************************************************************************************************/
    /**
     *  검색 중 = false;
     */
    private void setStoreList(String result) {
        StoreList storeList = new Gson().fromJson(result, StoreList.class);
        if(!storeList.result || storeList.store == null) {
            return;
        }
        store_list_vbox.setVisible(true);
        if(search_store_result_list.getItems().size() != 0) {
            search_store_result_list.getItems().clear();
        }
        store_id.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        store_name.setCellValueFactory(new PropertyValueFactory<>("store_name"));

        ObservableList<Store> collections = FXCollections.observableArrayList(storeList.store);
        search_store_result_list.setItems(collections);
        isLoading = false;
    }
}

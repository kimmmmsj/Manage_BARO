package com.baro.utils;

import com.baro.utils.parsing.Category.Category;
import com.baro.utils.parsing.Category.CategoryList;
import com.baro.utils.parsing.MainStoreList.Store;
import com.baro.utils.parsing.MainStoreList.StoreList;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchCategoryList implements Initializable {
    /**
    *  카테고리 클릭 인터페이스
    */
    public interface ClickCategory{
        void clickCategory(int category_id);
    }

    public VBox category_list_vbox;
    public TableView<Category> category_list;
    public TableColumn<Category, String> category_id;
    public TableColumn<Category, String> category_name;

    /**
     *  카테고리 클릭 인터페이스 객체
     */
    public ClickCategory clickCategory;

    public int store_id = 0;


    /*******************************************************************************************************
     *
     * 카테고리 테이블 클릭 이벤트 받아오기
     *
     *******************************************************************************************************/

    /**
     *  외부의 클래스는 아래 클래스에 implements 하여 구현부가 완성된 인터페이스를 넣어 주어야함
     */
    public void setInterface(ClickCategory clickCategory) {
        this.clickCategory = clickCategory;
    }

    /*******************************************************************************************************
     * 
     * 카테고리 테이블 뷰 마우스 클릭 이벤트
     * 
     *******************************************************************************************************/
    
    public void onClick(MouseEvent mouseEvent) {
        clickCategory.clickCategory(category_list.getSelectionModel().getSelectedItem().category_id);
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }



    /*******************************************************************************************************
     *
     * 스토어 테이블 뷰에서 받아온 스토어 아이디 확인 후 HTTP 통신
     *
     *******************************************************************************************************/
    public void makeStoreList(int storeId) {
        if(store_id == 0 ) {
            return;
        }
        makeStoreList(store_id);
        String result = ConnectToServer.Get("CategoryPrintByStoreId.do?store_id="+storeId);
        if(GetBool.getBool(result)){
            setStoreList(result);
        }
    }

    /*******************************************************************************************************
     *
     * 카테고리 리스트 받기
     *
     *******************************************************************************************************/
    private void setStoreList(String result) {
        category_id.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        category_name.setCellValueFactory(new PropertyValueFactory<>("store_name"));
        CategoryList categoryList = new Gson().fromJson(result, CategoryList.class);
        if(!categoryList.result || categoryList.category == null) {
            return;
        }
        category_list_vbox.setVisible(true);
        if(category_list.getItems().size() != 0) {
            category_list.getItems().clear();
        }
        ObservableList<Category> collections = FXCollections.observableArrayList(categoryList.category);
        category_list.setItems(collections);
    }

}

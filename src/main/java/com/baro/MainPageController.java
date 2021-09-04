package com.baro;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private JFXTabPane tabContainer;
    @FXML
    private Tab typeAddTab;
    @FXML
    private AnchorPane typeAddPane;
    @FXML
    private Tab typeDeleteTab;
    @FXML
    private AnchorPane typeDeletePane;
    @FXML
    private Tab ultraStoreInsertTab;
    @FXML
    private AnchorPane ultraStoreInsertPane;
    @FXML
    private Tab ultraStoreDeleteTab;
    @FXML
    private AnchorPane ultraStoreDeletePane;
    @FXML
    private Tab newStoreInsertTab;
    @FXML
    private AnchorPane newStoreInsertPane;
    @FXML
    private Tab newStoreDeleteTab;
    @FXML
    private AnchorPane newStoreDeletePane;
    @FXML
    private Tab menuInsertTab;
    @FXML
    private AnchorPane menuInsertPane;
    @FXML
    private Tab menuDeleteTab;
    @FXML
    private AnchorPane menuDeletePane;
    @FXML
    private Tab menuExtraInsertTab;
    @FXML
    private AnchorPane menuExtraInsertPane;
    @FXML
    private Tab menuExtraDeleteTab;
    @FXML
    private AnchorPane menuExtraDeletePane;
    @FXML
    private Tab menuRequiredExtraDeleteTab;
    @FXML
    private AnchorPane menuRequiredExtraDeletePane;
    @FXML
    private Tab menuExtraApplyToMenuTab;
    @FXML
    private AnchorPane menuExtraApplyToMenuPane;
    @FXML
    private Tab menuExtraExemptToMenuTab;
    @FXML
    private AnchorPane menuExtraExemptToMenuPane;
    @FXML
    private Tab categoryInsertTab;
    @FXML
    private AnchorPane categoryInsertPane;
    @FXML
    private Tab categoryDeleteTab;
    @FXML
    private AnchorPane categoryDeletePane;
    @FXML
    private Tab ownerRegisterTab;
    @FXML
    private AnchorPane ownerRegisterPane;
    @FXML
    private Tab storeInsertTab;
    @FXML
    private AnchorPane storeInsertPane;
    @FXML
    private Tab storeDeleteTab;
    @FXML
    private AnchorPane storeDeletePane;
    @FXML
    private Tab alertInsertTab;
    @FXML
    private AnchorPane alertInsertPane;
    @FXML
    private Tab alertDeleteTab;
    @FXML
    private AnchorPane alertDeletePane;
    @FXML
    private Tab couponInsertTab;
    @FXML
    private AnchorPane couponInsertPane;
    @FXML
    private Tab couponDeleteTab;
    @FXML
    private AnchorPane couponDeletePane;
    @FXML
    private Tab orderCancelTab;
    @FXML
    private AnchorPane orderCancelPane;
    @FXML
    private Tab marketingTab;
    @FXML
    private AnchorPane marketingPane;
    @FXML
    private Tab payBackTab;
    @FXML
    private AnchorPane payBackPane;
    private double tabWidth = 100.0;
    private double tabHeight = 250.0;
    public static int lastSelectedTabIndex = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureSideView();
    }
    private void configureSideView() {
        setTabs(typeAddTab, "Type 추가");
        setTabs(typeDeleteTab, "Type 삭제");
        setTabs(ultraStoreInsertTab, "Ultra 추가");
        setTabs(ultraStoreDeleteTab, "Ultra 삭제");
        setTabs(newStoreInsertTab, "NewStore 추가");
        setTabs(newStoreDeleteTab, "NewStore 삭제");
        setTabs(menuInsertTab, "Menu 추가");
        setTabs(menuDeleteTab, "Menu 삭제");
        setTabs(menuExtraInsertTab, "MenuExtra 추가");
        setTabs(menuExtraDeleteTab, "MenuExtra 삭제");
//        setTabs(menuRequiredExtraDeleteTab, "RequiredExtra 삭제");
        setTabs(menuExtraApplyToMenuTab, "Extra 적용");
        setTabs(menuExtraExemptToMenuTab, "Extra 해제");
        setTabs(categoryInsertTab, "Category 추가");
        setTabs(categoryDeleteTab, "Category 삭제");
        setTabs(ownerRegisterTab, "Owner 추가");
        setTabs(storeInsertTab, "Store 추가");
        setTabs(storeDeleteTab, "Store 삭제");
        setTabs(alertInsertTab, "Alert 추가");
        setTabs(alertDeleteTab, "Alert 삭제");
        setTabs(couponInsertTab, "Coupon 추가");
        setTabs(couponDeleteTab, "Coupon 삭제");
        setTabs(orderCancelTab, "주문 삭제");
        setTabs(marketingTab, "마케팅");
        setTabs(payBackTab, "환급");


        tabContainer.setTabMinWidth(tabWidth);
        tabContainer.setTabMaxWidth(tabWidth);
        tabContainer.setTabMinHeight(tabHeight);
        tabContainer.setTabMaxHeight(tabHeight);
        tabContainer.setRotateGraphic(true);
        tabContainer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                switch (observable.getValue().getId()){
                    case "typeAddTab":
                        configureTab(typeAddTab, typeAddPane, getClass().getResource("/typeAddPage.fxml"));
                        break;
                    case "typeDeleteTab":
                        configureTab(typeDeleteTab, typeDeletePane, getClass().getResource("/typeDeletePage.fxml"));
                        break;
                    case "ultraStoreInsertTab":
                        configureTab(ultraStoreInsertTab, ultraStoreInsertPane, getClass().getResource("/ultraStoreInsertPage.fxml"));
                        break;
                    case "ultraStoreDeleteTab":
                        configureTab(ultraStoreDeleteTab, ultraStoreDeletePane, getClass().getResource("/ultraStoreDeletePage.fxml"));
                        break;
                    case "categoryInsertTab":
                        configureTab(categoryInsertTab, categoryInsertPane, getClass().getResource("/categoryInsertPage.fxml"));
                        break;
                    case "categoryDeleteTab":
                        configureTab(categoryDeleteTab, categoryDeletePane, getClass().getResource("/categoryDeletePage.fxml"));
                        break;
                    case "ownerRegisterTab":
                        configureTab(ownerRegisterTab, ownerRegisterPane, getClass().getResource("/ownerRegisterPage.fxml"));
                        break;
                    case "storeInsertTab":
                        configureTab(storeInsertTab, storeInsertPane, getClass().getResource("/storeInsertPage.fxml"));
                        break;
                    case "storeDeleteTab":
                        configureTab(storeDeleteTab, storeDeletePane, getClass().getResource("/storeDeletePage.fxml"));
                        break;
                    case "newStoreInsertTab":
                        configureTab(newStoreInsertTab, newStoreInsertPane, getClass().getResource("/newStoreInsertPage.fxml"));
                        break;
                    case "newStoreDeleteTab":
                        configureTab(newStoreDeleteTab, newStoreDeletePane, getClass().getResource("/newStoreDeletePage.fxml"));
                        break;
                    case "menuInsertTab":
                        configureTab(menuInsertTab, menuInsertPane, getClass().getResource("/menuInsertPage.fxml"));
                        break;
                    case "menuDeleteTab":
                        configureTab(menuDeleteTab, menuDeletePane, getClass().getResource("/menuDeletePage.fxml"));
                        break;
                    case "menuExtraInsertTab":
                        configureTab(menuExtraInsertTab, menuExtraInsertPane, getClass().getResource("/menuExtraInsertPage.fxml"));
                        break;
                    case "menuExtraDeleteTab":
                        configureTab(menuExtraDeleteTab, menuExtraDeletePane, getClass().getResource("/menuExtraDeletePage.fxml"));
                        break;
//                    case "menuRequiredExtraDeleteTab":
//                        configureTab(menuRequiredExtraDeleteTab, menuRequiredExtraDeletePane, getClass().getResource("/menuRequiredExtraDeletePage.fxml"));
//                        break;
                    case "menuExtraApplyToMenuTab":
                        configureTab(menuExtraApplyToMenuTab, menuExtraApplyToMenuPane, getClass().getResource("/menuExtraApplyToMenuPage.fxml"));
                        break;
                    case "menuExtraExemptToMenuTab":
                        configureTab(menuExtraExemptToMenuTab, menuExtraExemptToMenuPane, getClass().getResource("/menuExtraExemptToMenuPage.fxml"));
                        break;
                    case "alertInsertTab" :
                        configureTab(alertInsertTab, alertInsertPane, getClass().getResource("/alertInsertPage.fxml"));
                        break;
                    case "alertDeleteTab":
                        configureTab(alertDeleteTab, alertDeletePane, getClass().getResource("/alertDeletePage.fxml"));
                        break;
                    case "couponDeleteTab":
                        configureTab(couponDeleteTab, couponDeletePane, getClass().getResource("/couponDeletePage.fxml"));
                        break;
                    case "couponInsertTab":
                        configureTab(couponInsertTab, couponInsertPane, getClass().getResource("/couponInsertPage.fxml"));
                        break;
                    case "orderCancelTab":
                        configureTab(orderCancelTab, orderCancelPane, getClass().getResource("/orderCancelPage.fxml"));
                        break;
                    case "marketingTab":
                        configureTab(marketingTab, marketingPane, getClass().getResource("/marketingPage.fxml"));
                        break;
                    case "payBackTab":
                        configureTab(payBackTab, payBackPane, getClass().getResource("/payBackPage.fxml"));
                        break;
                    default:
                        break;
                }
                oldValue.setStyle("-fx-background-color: #8D45E7");
            }
        });
        tabContainer.getSelectionModel().select(typeDeleteTab);
        tabContainer.getSelectionModel().selectFirst();

    }
    private void setTabs(Tab tab, String title) {
        double imageWidth = 40.0;
//        ImageView imageView = new ImageView(new Image());
//        imageView.setFitHeight(imageWidth);
//        imageView.setFitWidth(imageWidth);
//        tabPane.setCenter(imageView);
        Label label = new Label(title);
        label.setMaxWidth(tabHeight/2);
        label.setMinWidth(tabHeight/2);
        label.setPadding(new Insets(0, 0, 0, 0));
        label.setStyle("-fx-text-fill: white; -fx-font-size: 12pt; -fx-font-weight: normal; -fx-font-family: 'Noto Sans Korean Regular'");
        label.setAlignment(Pos.CENTER);

        BorderPane tabPane = new BorderPane();
        tabPane.setRotate(90.0);
        tabPane.setMaxWidth(tabHeight/2);
        tabPane.setMinWidth(tabHeight/2);
        tabPane.setCenter(label);
        tab.setGraphic(tabPane);
    }
    private void configureTab(Tab tab, AnchorPane containerPane, URL resourceURL) {
        System.out.println("reload?");
        tab.setStyle("-fx-background-color: #8333e6");
        try {
            Parent contentView = FXMLLoader.load(resourceURL);
            if(containerPane.getChildren().size() != 0) {
                containerPane.getChildren().remove(0);
            }
            containerPane.getChildren().add(contentView);
            AnchorPane.setTopAnchor(contentView, 0.0);
            AnchorPane.setBottomAnchor(contentView, 0.0);
            AnchorPane.setRightAnchor(contentView, 0.0);
            AnchorPane.setLeftAnchor(contentView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

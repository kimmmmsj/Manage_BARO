package com.baro;

import com.baro.Models.DeleteOrderModel;
import com.baro.Models.DeleteOrderResult;
import com.baro.Models.Store;
import com.baro.utils.GetBool;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class OrderCancelPageController implements Initializable {
    @FXML
    private TextField memberPhoneInput;
    @FXML
    private TableColumn<DeleteOrderModel,String> order_date_column;
    @FXML
    private TableColumn<DeleteOrderModel,String> receipt_id_column;
    @FXML
    private TableColumn<DeleteOrderModel,String> order_state_column;
    @FXML
    private TableColumn<DeleteOrderModel,String> store_name_column;
    @FXML
    private TableColumn<DeleteOrderModel,String> store_id_column;
    @FXML
    private TableColumn<DeleteOrderModel,String> representative_name_column;
    @FXML
    private TableView<DeleteOrderModel> order_list_table;
    @FXML
    private Button deleteBtn;

    private DeleteOrderResult orderResult;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        order_date_column.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        receipt_id_column.setCellValueFactory(new PropertyValueFactory<>("receipt_id"));
        order_state_column.setCellValueFactory(new PropertyValueFactory<>("order_state"));
        store_name_column.setCellValueFactory(new PropertyValueFactory<>("store_name"));
        store_id_column.setCellValueFactory(new PropertyValueFactory<>("store_id"));
        representative_name_column.setCellValueFactory(new PropertyValueFactory<>("representative_name"));
    }

    public void searchOrderList(ActionEvent event) {
        try {
            URL url = new URL("http://3.35.180.57:8080/"+"FindOrderListByPhoneForManage.do?phone="+memberPhoneInput.getText());
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
            System.out.println(bf.toString());
            if (GetBool.getBool(bf.toString())){
                orderResult = new Gson().fromJson(bf.toString(),DeleteOrderResult.class);
                ObservableList<DeleteOrderModel> collections = FXCollections.observableArrayList(orderResult.getRequiredExtras());
                order_list_table.setItems(collections);
                memberPhoneInput.setText("");
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tabDelete(ActionEvent event) {
        JSONObject jsonObject = new JSONObject();
        DeleteOrderModel selected = order_list_table.getSelectionModel().getSelectedItem();
        jsonObject.put("receipt_id",selected.getReceipt_id());
        jsonObject.put("cancel_reason","죄송합니다 가게의 사정으로 인해 주문이 취소되었습니다");
        jsonObject.put("store_name",selected.getStore_name());
        jsonObject.put("nick",selected.getRepresentative_name());
        System.out.println(selected.getReceipt_id());
        try {
            URL url = new URL("http://3.35.180.57:8080/BillingCancel.do");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json;utf-8");
            http.setRequestProperty("Accept", "application/json");
            http.setDoOutput(true);

            OutputStream os = http.getOutputStream();

            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer bf = new StringBuffer();

            while ((line = br.readLine()) != null) {
                bf.append(line);
            }
            System.out.println(bf.toString());
            br.close();
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

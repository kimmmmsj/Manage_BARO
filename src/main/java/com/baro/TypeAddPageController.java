package com.baro;

import com.baro.utils.ConnectToServer;
import com.baro.utils.GetBool;
import com.baro.utils.ImageSends.FileTransferAddress;
import com.baro.utils.ImageSends.FileTransferClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.json.JSONObject;
import sample.Main;
import sun.rmi.runtime.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class TypeAddPageController implements Initializable {
    @FXML
    private TextField inputTypeCode;
    @FXML
    private TextField inputTypeName;
//    @FXML
//    private TextField inputTypeImage;
    @FXML
    private Button saveBtn;
    @FXML
    private ImageView image;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void save(ActionEvent event) {
        if (inputTypeCode.getText() != ""  && inputTypeName.getText() != "") {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type_code",inputTypeCode.getText());
            jsonObject.put("type_name",inputTypeName.getText());
//            jsonObject.put("type_image",inputTypeImage.getText());
            String result = ConnectToServer.Post("TypeInsert.do",jsonObject);
            System.out.println(result);
            Boolean bool = GetBool.getBool(result);
        }
    }

    public void getImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg","*.gif"));
        File selectedFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
        try {
            // 파일 읽어오기
            FileInputStream fis = new FileInputStream(selectedFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            // 이미지 생성하기
            Image img = new Image(bis);
            // 이미지 띄우기
            image.setImage(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL("http://3.35.180.57:8080/PictureTest.do");
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
            try{
                FileTransferAddress address = new FileTransferAddress("3.35.180.57",9999);
                FileTransferClient client = new FileTransferClient(address,selectedFile);
                client.sendFile(selectedFile);
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println(bf.toString());
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

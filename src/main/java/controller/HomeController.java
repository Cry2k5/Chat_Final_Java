package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.User;
import model.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;



public class HomeController implements Initializable {
    @FXML
    private TextArea chatArea;
    @FXML
    private VBox friendView;

    @FXML
    private VBox chatView;

    @FXML
    private Button sendBtn;

    @FXML
    private ImageView yourAvatar;

    @FXML
    private Label yourName;

    @FXML
    private AnchorPane chatHomeView;

    @FXML
    private AnchorPane welcomeView;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Image image;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public HomeController() throws SQLException {
    }

    public void setName(){
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/view/home.fxml"));
        yourName.setText(data.name);
    }
    public void showFriend() throws Exception {
        FriendController friendController = new FriendController();
        friendView.getChildren().add(friendController.setFriendField());

    }
    ObservableList<User> users = new FriendController().getUser();
    public void showChatViewClient() throws Exception {

    }

    public void showChatView() throws Exception {

        friendView.setOnMouseClicked(event->{
            welcomeView.setVisible(false);
            chatHomeView.setVisible(true);
        });
    }

    public void send() throws Exception {
        // Tạo một label để đại diện cho ô tin nhắn mới
        Label newMessage = new Label(chatArea.getText());
        showChatView();
        // Thiết lập căn lề và giao diện của label
        newMessage.setPadding(new Insets(10));
        chatView.setSpacing(20);
        chatView.setPadding(new Insets(5));
        newMessage.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(20), null)));
        newMessage.setTextFill(Color.WHITE); // Thiết lập màu chữ là màu trắng
        newMessage.setFont(Font.font(15)); // Thiết lập font là cỡ chữ 14
        chatView.setAlignment(Pos.TOP_RIGHT);


        // Đặt id để có thể tìm kiếm trong VBox
        newMessage.setId("message");
        VBox messengerScene = (VBox) chatHomeView.lookup("#chatView");
        // Thêm label vào VBox
        messengerScene.getChildren().add(newMessage);
        clearChatArea();
       // sendMessage(data.name, chatArea.getText());
    }
    public void clearChatArea(){
        chatArea.setText("");
    }
    public void sendMessage(String recipient, String message) {
        try {
            // Tạo HashMap chứa thông tin tin nhắn
            HashMap<String, String> messageMap = new HashMap<>();
            messageMap.put("recipient", recipient);
            messageMap.put("message", message);

            // Gửi HashMap tới server
            outputStream.writeObject(messageMap);
            outputStream.flush();
            System.out.println("Đã gửi tin nhắn tới server: " + messageMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
        try {
            showFriend();
            showChatView();
            clearChatArea();
            showChatViewClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

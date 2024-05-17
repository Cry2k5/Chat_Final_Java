package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.JDBCUtil;
import model.User;
import model.data;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class FriendController{



    public ImageView friendAvatar;


    public AnchorPane friendField;


    public Label friendName;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Image image;
    private String name;
    private String avatar;

    public FriendController() throws SQLException {
    }

    public void setUser(User user){
        name = user.getName();
        avatar = user.getAvatar();
        String path = "File:"+ user.getAvatar();
        friendName.setText(user.getName());
        image = new Image(path, 45,45, false, true);
        friendAvatar.setImage(image);
    }

    public ObservableList<User> getUser() throws SQLException {

        ObservableList<User> datalist = FXCollections.observableArrayList();

        connect = JDBCUtil.getConnection();
        prepare = connect.prepareStatement("SELECT name, avatar FROM user WHERE name != "+"'"+ data.name+"'");
        result = prepare.executeQuery();

        while (result.next()) {
            User user = new User(result.getString("name"),result.getString("avatar") );
            datalist.add(user);
        }
        return datalist;
    }

    private ObservableList<User> list = getUser();

    public VBox setFriendField() throws Exception {
        data.listUser= list;
        VBox container = new VBox(); // Container để chứa các AnchorPane

        for(User user : list){
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/view/friend.fxml"));
            AnchorPane friendPane = load.load();

            // Tìm các thành phần trong friendPane bằng @FXML
            ImageView friendAvatar = (ImageView) friendPane.lookup("#friendAvatar");
            Label friendName = (Label) friendPane.lookup("#friendName");

            friendName.setText(user.getName());
            String path = user.getAvatar();
            friendAvatar.setImage(urlToImage(path));

//            friendPane.setOnMouseClicked(event ->{
//                System.out.print(friendName.getText());
//               data.chooseUser = friendName.getText();
//
//            });
            container.getChildren().add(friendPane); // Thêm friendPane vào container
        }
        return container;
    }
    public Image urlToImage(String imagePath) {
        try {
            File file = new File(imagePath);
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            return image;
        } catch (Exception e) {
            // Xử lý nếu không thể tạo được Image từ URL
            e.printStackTrace();
            return null;
        }
    }
}

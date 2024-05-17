package view;
	
import com.mysql.cj.xdevapi.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import controller.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			Parent root = loader.load();

			// Lấy tham chiếu đến controller của login.fxml
			LoginController loginController = loader.getController();

			// Đọc thông tin đăng nhập từ tệp văn bản và tự động điền vào trường email và password
			String[] credentials = CredentialsManager.loadCredentials();
			System.out.println(Arrays.toString(credentials));
			if (credentials != null && credentials.length >=2) {
				String email = credentials[0];
				String password = credentials[1];

				loginController.lo_email.setText(email);
				loginController.lo_password.setText(password);
			}

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}
}

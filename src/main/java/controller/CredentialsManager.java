package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CredentialsManager {
    private static final String CREDENTIALS_FILE = "D:\\ChatATC_remake\\src\\main\\resources\\yourlogin\\login.txt";

    public static void saveCredentials(String email, String password) {
        try {
            // Lấy thời gian hiện tại
            LocalDateTime currentTime = LocalDateTime.now();

            // Định dạng thời gian
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            // Ghi dữ liệu đăng nhập vào tệp
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(CREDENTIALS_FILE), StandardOpenOption.APPEND)) {
                writer.write(email + "," + password + ", Đã đăng nhập vào: " + formattedTime);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] loadCredentials() {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(CREDENTIALS_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Phần còn lại của lớp CredentialsManager...
}
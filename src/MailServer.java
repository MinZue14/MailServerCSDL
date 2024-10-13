import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MailServer {
    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false; // Có thể xảy ra lỗi như đã tồn tại người dùng
        }
    }
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Nếu có kết quả, đăng nhập thành công
        } catch (SQLException e) {
            return false;
        }
    }

    public void sendEmail(String sender, String recipient, String subject, String body) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO emails (sender_username, receiver_username, subject, content) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sender);
            pstmt.setString(2, recipient);
            pstmt.setString(3, subject);
            pstmt.setString(4, body);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet receiveEmails(String recipient) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM emails WHERE receiver_username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipient);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Email> getReceivedEmails(String username) {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM emails WHERE receiver_username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String sender = rs.getString("sender_username");
                String subject = rs.getString("subject");
                String content = rs.getString("content");
                Timestamp sentTime = rs.getTimestamp("sent_time");

                emails.add(new Email(id, sender, subject, content, sentTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }
    public static void main(String[] args) {
        new MailServer();
    }
}

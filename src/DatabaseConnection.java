import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mail_system";
    private static final String USER = "your_username"; // Thay bằng username MySQL của bạn
    private static final String PASSWORD = "your_password"; // Thay bằng password MySQL của bạn

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

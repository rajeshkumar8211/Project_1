package SmallPrograms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cgpa";
    private static final String USERNAME = "Your_Username";
    private static final String PASSWORD = "Your_Password";
    private DBConnection() {
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
    public static void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Database Connection Closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
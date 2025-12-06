package ProductManagement.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final String url = "jdbc:postgresql://localhost:5432/product_management_db";
    private final String user = "product_manager_user";
    private final String password = "123456";

    public Connection getDBConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url,user,password);
    }

    public static void main() {
        DBConnection dbConnection = new DBConnection();
        try {
        Connection connection = dbConnection.getDBConnection();
            System.out.println("Connection successful");
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

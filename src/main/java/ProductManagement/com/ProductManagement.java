package ProductManagement.com;

import java.sql.Connection;
import java.sql.DriverManager;

public class ProductManagement {
    public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/product_management_db";
    String username = "product_manager_user";
    String password = "123456";
        try {
            Connection connect = DriverManager.getConnection(url,username,password);
                System.out.println("Connection successful");
                connect.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

package ProductManagement.com;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataRetriever {
    private DBConnection dbConnection;


    public DataRetriever() {
        this.dbConnection = new DBConnection();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name, product_id FROM Product_category";

        try (Connection connection = dbConnection.getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("product_id")
                );
                categories.add(category);
            }

        } catch (SQLException e) {
            System.err.println("Error");
            e.printStackTrace();
        }

        return categories;
    }


    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String query = "SELECT Product.id, Product.name, Product.price, Product.creation_datetime " +
                "FROM Product " +
                "ORDER BY Product.id " +
                "LIMIT ? OFFSET ?";

        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, offset);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("creation_datetime").toInstant()
                );

                int productId = product.getId();
                String categoryQuery = "SELECT id, name, product_id FROM Product_category WHERE product_id = ?";

                PreparedStatement catPstmt = connection.prepareStatement(categoryQuery);
                catPstmt.setInt(1, productId);
                ResultSet catRs = catPstmt.executeQuery();

                while (catRs.next()) {
                    Category category = new Category(
                            catRs.getInt("id"),
                            catRs.getString("name"),
                            catRs.getInt("product_id")
                    );
                    product.setCategory(category);
                }

                catRs.close();
                catPstmt.close();

                products.add(product);
            }

            resultSet.close();
            preparedStatement.close();


        } catch (SQLException e) {
            System.err.println("Errors");
            e.printStackTrace();
        }

        return products;
    }
}



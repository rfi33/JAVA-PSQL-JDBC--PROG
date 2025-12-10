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
            dbConnection.getDBConnection().close();
        } catch (SQLException e) {
            System.err.println("Error");
            e.printStackTrace();
        }

        return categories;
    }


    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();
            int offset = (page-1) * size;
            String query = "SELECT Product.id, Product.name, Product.price, Product.creation_datetime FROM Product "+
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

                    PreparedStatement catPreparedStatement = connection.prepareStatement(categoryQuery);
                    catPreparedStatement.setInt(1, productId);
                    ResultSet catResultSet = catPreparedStatement.executeQuery();

                    while (catResultSet.next()) {
                        Category category = new Category(
                                catResultSet.getInt("id"),
                                catResultSet.getString("name"),
                                catResultSet.getInt("product_id")
                        );
                        product.setCategory(category);
                    }
                    products.add(product);
                }

                dbConnection.getDBConnection().close();
            } catch (SQLException e) {
                System.err.println("Errors");
                e.printStackTrace();
            }
        return products;
    }


    public List<Product> getProductsByCriteria(String productName,String categoryName,Instant creationMin,Instant creationMax){
        List<Product> products = new ArrayList<>();
        String query = "SELECT DISTINCT Product.id, Product.name, Product.creation_datetime " +
                "FROM Product " +
                "LEFT JOIN Product_category ON Product_category.product_id = Product.id";

        boolean hasCondition = false;

        if (productName != null) {
            if (hasCondition) {
                query += " AND Product.name ILIKE ? ";
            } else {
                query += " WHERE Product.name ILIKE ? ";
                hasCondition = true;
            }
        }

        if (categoryName != null){
            if (hasCondition) {
                query += " AND Product_category.name ILIKE ? ";
            } else {
                query += " WHERE Product_category.name ILIKE ? ";
                hasCondition = true;
            }
        }

        if (creationMin != null) {
            if (hasCondition) {
                query += " AND Product.creation_datetime >= ? ";
            } else {
                query += " WHERE Product.creation_datetime >= ? ";
                hasCondition = true;
            }
        }

        if (creationMax != null) {
            if (hasCondition) {
                query += " AND Product.creation_datetime <= ? ";
            } else {
                query += " WHERE Product.creation_datetime <= ? ";
                hasCondition = true;
            }
        }



        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int index = 1;

            if (productName != null) {
                preparedStatement.setString(index++, "%" + productName + "%");
            }
            if (categoryName != null) {
                preparedStatement.setString(index++, "%" + categoryName + "%");
            }
            if (creationMin != null) {
                preparedStatement.setTimestamp(index++, Timestamp.from(creationMin));
            }
            if (creationMax != null) {
                preparedStatement.setTimestamp(index++, Timestamp.from(creationMax));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    Product product = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getTimestamp("creation_datetime").toInstant()
                    );

                    String catQuery = "SELECT id, name, product_id FROM Product_category WHERE product_id = ?";
                    try (PreparedStatement catPreparedStatement = connection.prepareStatement(catQuery)) {

                        catPreparedStatement.setInt(1, product.getId());
                        try (ResultSet catResultSet = catPreparedStatement.executeQuery()) {

                            while (catResultSet.next()) {
                                Category category = new Category(
                                        catResultSet.getInt("id"),
                                        catResultSet.getString("name"),
                                        catResultSet.getInt("product_id")
                                );
                                product.setCategory(category);
                            }
                        }
                    }

                    products.add(product);
                }
            }
            dbConnection.getDBConnection().close();
        } catch (SQLException e) {
            System.err.println("Erreur dans getProductsByCriteria :");
            e.printStackTrace();
        }

        return products;
    }


    public List<Product> getProductsByCriteria2(String productName,String categoryName,Instant creationMin,Instant creationMax,int page , int size){
        List<Product> products = new ArrayList<>();
        int offset = (page-1 )*size;
        String query = "SELECT DISTINCT Product.id, Product.name, Product.creation_datetime " +
                "FROM Product " +
                "LEFT JOIN Product_category ON Product_category.product_id = Product.id";

        boolean hasCondition = false;

        if (productName != null) {
            if (hasCondition) {
                query += " AND Product.name ILIKE ? ";
            } else {
                query += " WHERE Product.name ILIKE ? ";
                hasCondition = true;
            }
        }

        if (categoryName != null){
            if (hasCondition) {
                query += " AND Product_category.name ILIKE ? ";
            } else {
                query += " WHERE Product_category.name ILIKE ? ";
                hasCondition = true;
            }
        }

        if (creationMin != null) {
            if (hasCondition) {
                query += " AND Product.creation_datetime >= ? ";
            } else {
                query += " WHERE Product.creation_datetime >= ? ";
                hasCondition = true;
            }
        }

        if (creationMax != null) {
            if (hasCondition) {
                query += " AND Product.creation_datetime <= ? ";
            } else {
                query += " WHERE Product.creation_datetime <= ? ";
                hasCondition = true;
            }
        }

        query += " ORDER BY Product.id LIMIT ? OFFSET ?";


        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int index = 1;

            if (productName != null) {
                preparedStatement.setString(index++, "%" + productName + "%");
            }
            if (categoryName != null) {
                preparedStatement.setString(index++, "%" + categoryName + "%");
            }
            if (creationMin != null) {
                preparedStatement.setTimestamp(index++, Timestamp.from(creationMin));
            }
            if (creationMax != null) {
                preparedStatement.setTimestamp(index++, Timestamp.from(creationMax));
            }

            preparedStatement.setInt(index++, size);
            preparedStatement.setInt(index, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    Product product = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getTimestamp("creation_datetime").toInstant()
                    );

                    String catQuery = "SELECT id, name, product_id FROM Product_category WHERE product_id = ?";
                    try (PreparedStatement catPreparedStatement = connection.prepareStatement(catQuery)) {

                        catPreparedStatement.setInt(1, product.getId());
                        try (ResultSet catResultSet = catPreparedStatement.executeQuery()) {

                            while (catResultSet.next()) {
                                Category category = new Category(
                                        catResultSet.getInt("id"),
                                        catResultSet.getString("name"),
                                        catResultSet.getInt("product_id")
                                );
                                product.setCategory(category);
                            }
                        }
                    }

                    products.add(product);
                }
            }
            dbConnection.getDBConnection().close();
        } catch (SQLException e) {
            System.err.println("Erreur dans getProductsByCriteria :");
            e.printStackTrace();
        }

        return products;

    }
}



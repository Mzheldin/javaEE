package persist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final Connection connection;

    public OrderRepository(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists(connection);
    }

    public void insert(Order order) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "insert into orders(products) values (?);")) {
            stmt.setString(1, order.getProducts());
            stmt.execute();
        }
    }

    public void save(Order order) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "update orders set products = ? where id = ?;")) {
            stmt.setString(1, order.getProducts());
            stmt.setInt(2, order.getId());
            stmt.execute();
        }
    }

    public Order findByProducts(String products) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select id, number from orders where products = ?")) {
            stmt.setString(1, products);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Order(rs.getInt(1), rs.getString(2));
            }
        }
        return new Order(-1, "");
    }

    public Order findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select id, products from orders where id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Order(rs.getInt(1), rs.getString(2));
            }
        }
        return null;
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> res = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, products from orders");
            while (rs.next()) {
                res.add(new Order(rs.getInt(1), rs.getString(2)));
            }
        }
        return res;
    }

    public void deleteOrder(Order order) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "delete from orders where id = ?")){
            stmt.setInt(1, order.getId());
            stmt.execute();
        }
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("create table if not exists orders (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    products varchar(100),\n" +
                    "    unique index uq_products(products)\n" +
                    ");");
        }
    }
}

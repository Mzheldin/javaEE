package persist;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ProductRepository implements Serializable {

    @Inject
    private ServletContext servletContext;
    private Connection connection;

    @PostConstruct
    private void init(){
        this.connection = (Connection) servletContext.getAttribute("db_connection");
    }

    public ProductRepository() {
    }

    public ProductRepository(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists(connection);
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "insert into products(name, description) values (?, ?);")) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.execute();
        }
    }

    public void save(Product product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "update products set name = ?, description = ? where id = ?;")) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getId());
            stmt.execute();
        }
    }

    public Product findByName(String name) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select id, name, description from products where name = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }
        return new Product(-1, "", "");
    }

    public Product findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select id, name, description from products where id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, name, description from products");
            while (rs.next()) {
                res.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        }
        return res;
    }

    public void deleteProduct(Product product) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "delete from products where id = ?")){
            stmt.setInt(1, product.getId());
            stmt.execute();
        }
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("create table if not exists products (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    name varchar(25),\n" +
                    "    description varchar(100),\n" +
                    "    unique index uq_name(name)\n" +
                    ");");
        }
    }
}

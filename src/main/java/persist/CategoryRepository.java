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
public class CategoryRepository implements Serializable{

    @Inject
    private ServletContext servletContext;
    private Connection connection;

    @PostConstruct
    private void init(){
        this.connection = (Connection) servletContext.getAttribute("db_connection");
    }

    public CategoryRepository() {
    }

    public CategoryRepository(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists(connection);
    }

    public void insert(Category category) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "insert into categories(name) values (?);")) {
            stmt.setString(1, category.getName());
            stmt.execute();
        }
    }

    public void save(Category category) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "update categories set name = ? where id = ?;")) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.execute();
        }
    }

    public Category findByName(String name) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select id, name from categories where name = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt(1), rs.getString(2));
            }
        }
        return new Category(-1, "");
    }

    public Category findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "select id, name from categories where id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt(1), rs.getString(2));
            }
        }
        return null;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> res = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, name from categories");
            while (rs.next()) {
                res.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        }
        return res;
    }

    public void deleteCategory(Category category) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "delete from categories where id = ?")){
            stmt.setInt(1, category.getId());
            stmt.execute();
        }
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("create table if not exists categories (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    name varchar(25),\n" +
                    "    unique index uq_catname(name)\n" +
                    ");");
        }
    }
}

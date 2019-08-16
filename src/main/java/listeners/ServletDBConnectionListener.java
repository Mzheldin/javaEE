package listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persist.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ServletDBConnectionListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(ServletDBConnectionListener.class);

    private Connection connection = null;
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String dbName = "javaee_test_db?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&amp&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private final String userName = "root";
    private final String password = "1488";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            connection = DriverManager.getConnection(url + dbName, userName, password);
            if (connection != null)
                sce.getServletContext().setAttribute("db_connection", connection);
            logger.info("Connection ", connection == null);
            initProductRepository(sce);
            initCategoryRepository(sce);
            initOrderRepository(sce);
        } catch (Exception e){
            e.printStackTrace();
            closeConnection();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        closeConnection();
    }

    private void initProductRepository(ServletContextEvent sce){
        try {
            ProductRepository productRepository = new ProductRepository(connection);
            sce.getServletContext().setAttribute("productRepository", productRepository);
            if (productRepository.getAllProducts().size() == 0)
                for (int i = 1; i <= 9; i++)
                    productRepository.insert(new Product(-1, "product#" + i, "product#" + i + " description"));
        } catch (SQLException e){
            e.printStackTrace();
            closeConnection();
        }
    }

    private void initCategoryRepository(ServletContextEvent sce){
        try {
            CategoryRepository categoryRepository = new CategoryRepository(connection);
            sce.getServletContext().setAttribute("categoryRepository", categoryRepository);
            if (categoryRepository.getAllCategories().size() == 0)
                for (int i = 1; i <= 3; i++)
                    categoryRepository.insert(new Category(-1, "category#" + i));
        } catch (SQLException e){
            e.printStackTrace();
            closeConnection();
        }
    }

    private void initOrderRepository(ServletContextEvent sce){
        try {
            OrderRepository orderRepository = new OrderRepository(connection);
            sce.getServletContext().setAttribute("orderRepository", orderRepository);
        } catch (SQLException e){
            e.printStackTrace();
            closeConnection();
        }
    }

    private void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

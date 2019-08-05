package listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persist.Product;
import persist.ProductRepository;

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
            ProductRepository productRepository = new ProductRepository(connection);
            sce.getServletContext().setAttribute("productRepository", productRepository);
            if (productRepository.getAllProducts().size() == 0)
                for (int i = 1; i <= 9; i++)
                    productRepository.insert(new Product(-1, "product#" + i, "product#" + i + " description"));
        } catch (Exception e){
            e.printStackTrace();
            closeConnection();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        closeConnection();
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

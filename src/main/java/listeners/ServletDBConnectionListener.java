package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ServletDBConnectionListener implements ServletContextListener {

    private Connection connection = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "...";
            String userName = "...";
            String password = "...";
            connection = DriverManager.getConnection(url + dbName, userName, password);
            if (connection != null)
                sce.getServletContext().setAttribute("db_connection", connection);
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

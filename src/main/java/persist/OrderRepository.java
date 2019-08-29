package persist;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderRepository implements Serializable{

    @PersistenceContext(unitName = "ds")
    protected EntityManager entityManager;

    public OrderRepository() {
    }

    @Transactional
    public Order merge(Order order){
        return entityManager.merge(order);
    }

    @Transactional
    public Order findByProducts(String products) {
        return entityManager.find(Order.class, products);
    }

    @Transactional
    public Order findById(int id) {
        return entityManager.find(Order.class, id);
    }

    @Transactional
    public boolean existById(int id){
        return entityManager.find(Order.class, id) != null;
    }

    @Transactional
    public List<Order> getAllOrders() {
        return entityManager.createQuery("from Order ", Order.class).getResultList();
    }

    @Transactional
    public void deleteOrder(Order order) {
        try {
            Order attached = findById(order.getId());
            if (attached != null)
                entityManager.remove(attached);
        } catch (Exception e){
            e.printStackTrace();
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

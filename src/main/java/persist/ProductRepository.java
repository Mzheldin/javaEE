package persist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Named
@ApplicationScoped
public class ProductRepository {

    @PersistenceContext(unitName = "ds")
    protected EntityManager entityManager;

    public ProductRepository() {
    }

    @Transactional
    public Product merge(Product product){
        return entityManager.merge(product);
    }

    public Product findByName(String name) {
        return entityManager.find(Product.class, name);
    }

    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    public boolean existById(int id){
        return entityManager.find(Product.class, id) != null;
    }

    public List<Product> getAllProducts() {
        return entityManager.createQuery("from Product ").getResultList();
    }

    @Transactional
    public void deleteProduct(Product product) {
        try {
            Product attached = findById(product.getId());
            if (attached != null)
                entityManager.remove(attached);
        } catch (Exception e){
            e.printStackTrace();
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

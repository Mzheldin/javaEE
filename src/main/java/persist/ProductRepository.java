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
public class ProductRepository implements Serializable{

    @PersistenceContext(unitName = "ds")
    protected EntityManager entityManager;

    public ProductRepository() {
    }

    @Transactional
    public Product merge(Product product){
        return entityManager.merge(product);
    }

    @Transactional
    public Product findByName(String name) {
        return entityManager.find(Product.class, name);
    }

    @Transactional
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Transactional
    public List<Product> getProductsByCategory(Category category){
        return entityManager.createQuery("select distinct p from Product p left join fetch p.category where p.category = :category ", Product.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Transactional
    public boolean existById(int id){
        return entityManager.find(Product.class, id) != null;
    }

    @Transactional
    public boolean existByName(String name){
        return entityManager.find(Product.class, name) != null;
    }

    @Transactional
    public List<Product> getAllProducts() {
        return entityManager.createQuery("select distinct p from Product p left join fetch p.category", Product.class).getResultList();
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

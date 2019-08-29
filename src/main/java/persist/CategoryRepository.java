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
public class CategoryRepository implements Serializable{

    @PersistenceContext(unitName = "ds")
    protected EntityManager entityManager;

    public CategoryRepository() {
    }

    @Transactional
    public Category merge(Category category){
        return entityManager.merge(category);
    }

    @Transactional
    public Category findByName(String name) {
        return entityManager.find(Category.class, name);
    }

    @Transactional
    public Category findById(int id) {
        return entityManager.find(Category.class, id);
    }

    @Transactional
    public boolean existById(int id){
        return entityManager.find(Category.class, id) != null;
    }

    @Transactional
    public boolean existByName(String name){
        return entityManager.find(Category.class, name) != null;
    }

    @Transactional
    public List<Category> getAllCategories() {
        return entityManager.createQuery("select distinct c from Category c left join fetch c.products", Category.class).getResultList();
    }

    @Transactional
    public void deleteCategory(Category category) {
        try {
            Category attached = findById(category.getId());
            if (attached != null)
                entityManager.remove(attached);
        } catch (Exception e){
            e.printStackTrace();
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

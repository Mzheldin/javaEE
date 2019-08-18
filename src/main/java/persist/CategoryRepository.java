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
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    protected EntityManager entityManager;

    public CategoryRepository() {
    }

    @Transactional
    public Category merge(Category category){
        return entityManager.merge(category);
    }

    public Category findByName(String name) {
        return entityManager.find(Category.class, name);
    }

    public Category findById(int id) {
        return entityManager.find(Category.class, id);
    }
    public boolean existById(int id){
        return entityManager.find(Category.class, id) != null;
    }

    public List<Category> getAllCategories() {
        return entityManager.createQuery("from Category ").getResultList();
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

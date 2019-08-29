package services;

import persist.Category;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryService {
    List<Category> getAllCategories();
    String createCategory();
    String editCategory(Category category);
    void deleteCategory(Category category);
    String saveCategory();
    Category findByName(String name);
    Category findById(int id);
    boolean existById(int id);
    boolean existByName(String name);
}

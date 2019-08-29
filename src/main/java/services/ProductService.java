package services;

import persist.Category;
import persist.Product;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductService {
    List<Product> getAllProducts();
    String createProduct();
    String editProduct(Product product);
    void deleteProduct(Product product);
    String saveProduct();
    List<Product> getProductsByCategory(Category category);
    Product findByName(String name);
    Product findById(int id);
    boolean existById(int id);
    boolean existByName(String name);
}

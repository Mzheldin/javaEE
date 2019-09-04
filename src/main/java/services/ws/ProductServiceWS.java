package services.ws;

import persist.Category;
import persist.Product;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Local
@WebService
public interface ProductServiceWS  {

    @WebMethod
    List<Product> getAllProducts();

    @WebMethod
    List<Product> getAllProductsByCategory(Category category);

    @WebMethod
    Product getProductByName(String name);

    @WebMethod
    Product getProductById(Integer id);

    @WebMethod
    Product addProduct(Product product);

    @WebMethod
    void deleteProduct(Product product);
}

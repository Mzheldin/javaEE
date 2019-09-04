package beans;

import interceptors.LogInterceptor;
import persist.Category;
import persist.CategoryRepository;
import persist.Product;
import persist.ProductRepository;
import services.ws.ProductServiceWS;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import java.io.Serializable;
import java.util.List;

@WebService(endpointInterface = "services.ws.ProductServiceWS")
public class ProductBeanWS implements Serializable, ProductServiceWS {
    @EJB
    private ProductRepository productRepository;
    @EJB
    private CategoryRepository categoryRepository;

    @Override
    @Interceptors({LogInterceptor.class})
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public List<Product> getAllProductsByCategory(Category category) {
        return productRepository.getProductsByCategory(category);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public Product getProductById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public Product addProduct(Product product) {
        return productRepository.merge(product);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public void deleteProduct(Product product) {
        if (productRepository.existById(product.getId()))
            productRepository.deleteProduct(product);
    }
}

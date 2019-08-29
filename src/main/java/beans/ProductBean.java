package beans;

import interceptors.LogInterceptor;
import persist.Category;
import persist.Product;
import persist.ProductRepository;
import services.ProductService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProductBean implements Serializable, ProductService {

    @EJB
    private ProductRepository productRepository;
    private Product product;

    public ProductBean() {
    }

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        productRepository = (ProductRepository) context.getAttribute("productRepository");
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public List<Product> getProductsByCategory(Category category){
        return productRepository.getProductsByCategory(category);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public Product findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public boolean existById(int id) {
        return productRepository.existById(id);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public boolean existByName(String name) {
        return productRepository.existByName(name);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String createProduct() {
        this.product = new Product();
        return "/product.xhtml?faces-redirect=true";
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String editProduct(Product product) {
        this.product = product;
        return "/product.xhtml?faces-redirect=true";
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public void deleteProduct(Product product) {
        productRepository.deleteProduct(product);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String saveProduct() {
        productRepository.merge(this.product);
        return "/products.xhtml?faces-redirect=true";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

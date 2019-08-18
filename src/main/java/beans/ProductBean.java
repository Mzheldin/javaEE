package beans;

import persist.Product;
import persist.ProductRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class ProductBean implements Serializable {

    @Inject
    private ProductRepository productRepository;
    private Product product;

    public ProductBean() {
    }

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        productRepository = (ProductRepository) context.getAttribute("productRepository");
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public String createProduct() {
        this.product = new Product();
        return "/product.xhtml?faces-redirect=true";
    }

    public String editProduct(Product product) {
        this.product = product;
        return "/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        productRepository.deleteProduct(product);
    }

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

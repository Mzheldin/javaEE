package beans;

import persist.Product;
import persist.ProductRepository;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@ManagedBean
public class ProductBean {

    private ProductRepository productRepository;
    private Product product;

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        productRepository = (ProductRepository) context.getAttribute("productRepository");
    }

    public List<Product> getAllProducts() throws SQLException {
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

    public String deleteProduct(Product product) throws SQLException{
        productRepository.deleteProduct(product);
        return "/products.xhtml?faces-redirect=true";
    }

    public String saveProduct() throws SQLException {
        productRepository.save(this.product);
        return "/products.xhtml?faces-redirect=true";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

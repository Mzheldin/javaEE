package beans;

import interceptors.LogInterceptor;
import persist.Category;
import persist.CategoryRepository;
import services.CategoryService;

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
public class CategoryBean implements Serializable, CategoryService {

    @EJB
    private CategoryRepository categoryRepository;
    private Category category;

    public CategoryBean() {
    }

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        categoryRepository = (CategoryRepository) context.getAttribute("categoryRepository");
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String createCategory() {
        this.category = new Category();
        return "/category.xhtml?faces-redirect=true";
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String editCategory(Category category) {
        this.category = category;
        return "/category.xhtml?faces-redirect=true";
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public void deleteCategory(Category category) {
        categoryRepository.deleteCategory(category);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public String saveCategory() {
        categoryRepository.merge(this.category);
        return "/categories.xhtml?faces-redirect=true";
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public Category findById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public boolean existById(int id) {
        return categoryRepository.existById(id);
    }

    @Override
    @Interceptors({LogInterceptor.class})
    public boolean existByName(String name) {
        return categoryRepository.existByName(name);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

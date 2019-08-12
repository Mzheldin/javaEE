package beans;

import persist.Category;
import persist.CategoryRepository;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@ManagedBean
public class CategoryBean {

    private CategoryRepository categoryRepository;
    private Category category;

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        categoryRepository = (CategoryRepository) context.getAttribute("categoryRepository");
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryRepository.getAllCategories();
    }

    public String createCategory() {
        this.category = new Category();
        return "/category.xhtml?faces-redirect=true";
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category.xhtml?faces-redirect=true";
    }

    public String deleteCategory(Category category) throws SQLException{
        categoryRepository.deleteCategory(category);
        return "/categories.xhtml?faces-redirect=true";
    }

    public String saveCategory() throws SQLException {
        categoryRepository.save(this.category);
        return "/categories.xhtml?faces-redirect=true";
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

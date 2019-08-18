package beans;

import persist.Category;
import persist.CategoryRepository;

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
public class CategoryBean implements Serializable {

    @Inject
    private CategoryRepository categoryRepository;
    private Category category;

    public CategoryBean() {
    }

    @PostConstruct
    public void init(){
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        categoryRepository = (CategoryRepository) context.getAttribute("categoryRepository");
    }

    public List<Category> getAllCategories() {
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

    public void deleteCategory(Category category) {
        categoryRepository.deleteCategory(category);
    }

    public String saveCategory() {
        categoryRepository.merge(this.category);
        return "/categories.xhtml?faces-redirect=true";
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package beans;

import persist.Category;
import persist.CategoryRepository;
import services.ws.CategoryServiceWS;

import javax.ejb.EJB;
import javax.jws.WebService;
import java.io.Serializable;

@WebService(endpointInterface = "services.ws.CategoryServiceWS")
public class CategoryBeanWS implements Serializable, CategoryServiceWS {

    @EJB
    CategoryRepository categoryRepository;

    @Override
    public Category addCatogory(Category category) {
        return categoryRepository.merge(category);
    }
}

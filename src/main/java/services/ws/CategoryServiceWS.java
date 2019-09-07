package services.ws;

import persist.Category;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebService;

@Local
@WebService
public interface CategoryServiceWS {

    @WebMethod
    Category addCatogory(Category category);
}

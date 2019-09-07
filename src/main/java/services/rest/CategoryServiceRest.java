package services.rest;

import persist.Category;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Local
@Path("/categories")
public interface CategoryServiceRest {
    @POST
    @Path("/category")
    @Consumes(MediaType.APPLICATION_JSON)
    Response addCategory(Category category);
}

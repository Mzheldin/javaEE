package services.rest;

import persist.Product;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Local
@Path("/products")
public interface ProductServiceRest {

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    List<Product> getAllProducts();

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Product findById(@PathParam("id") int id);

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Product findByName(@PathParam("name") String name);

    @GET
    @Path("/category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Product> getProductsByCategory(@PathParam("id") int id);

    @DELETE
    @Path("/id/{id}")
    Response deleteProduct(@PathParam("id") int id);

    @POST
    @Path("/product")
    @Consumes(MediaType.APPLICATION_JSON)
    Response addProduct(Product product);
}

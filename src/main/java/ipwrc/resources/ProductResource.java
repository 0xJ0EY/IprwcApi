package ipwrc.resources;


import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Product;
import ipwrc.services.ProductService;
import ipwrc.views.View;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("/products/")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource extends View.Public {

    public static class ProductPublicView extends View.Public {}
    public static class ProductPrivateView extends View.Private {}

    private ProductService service;

    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GET
    @UnitOfWork
    @JsonView(ProductPublicView.class)
    public List<Product> get() {
        return this.service.getAll();
    }

    @GET
    @UnitOfWork
    @JsonView(ProductPrivateView.class)
    @Path("/{title}/")
    public Product getByTitle(@PathParam("title") String title) {
        return this.service.findByTitle(title);
    }

    @GET
    @UnitOfWork
    @JsonView(ProductPrivateView.class)
    @Path("/id/{id}/")
    public Product getById(@PathParam("id") int id) {
        return this.service.findById(id);
    }

    @POST
    @UnitOfWork
    @JsonView(View.Public.class)
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(Product product) {
        System.out.println("product = " + product.subcategory);

        this.service.create(product);
        return Response.ok().build();
    }

}

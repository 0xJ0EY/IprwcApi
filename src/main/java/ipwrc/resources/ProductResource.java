package ipwrc.resources;


import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Product;
import ipwrc.models.ProductImage;
import ipwrc.services.ProductService;
import ipwrc.views.View;

import javax.annotation.security.RolesAllowed;
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
    public static class ProductEditView extends ProductPrivateView {}

    private ProductService service;

    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GET
    @UnitOfWork
    @JsonView(ProductPrivateView.class)
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
    @Path("/{title}/image/{index}")
    @Produces({"image/png", "image/jpeg", "image/gif"})
    public Response getImage(@PathParam("title") String title, @PathParam("index") int index) {
        ProductImage image = this.service.getProductImage(this.service.findByTitle(title), --index);

        return Response
                .ok(image.getContent())
                .header("Content-type", image.getMediaType())
                .build();
    }

    @GET
    @UnitOfWork
    @JsonView(ProductPrivateView.class)
    @Path("/id/{id}/")
    public Product getById(@PathParam("id") int id) {
        return this.service.findById(id);
    }

    @GET
    @UnitOfWork
    @JsonView(ProductEditView.class)
    @Path("/edit/{id}")
    public Product getEditById(@PathParam("id") int id) {
        return this.service.findById(id);
    }

    @POST
    @UnitOfWork
    @RolesAllowed("admin")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Product create(Product product) {
        this.service.create(product);
        return product;
    }

    @PUT
    @UnitOfWork
    @RolesAllowed("admin")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Product update(Product product) {
        this.service.update(product);
        return product;
    }

    @DELETE
    @UnitOfWork
    @RolesAllowed("admin")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response delete(int id) {
        this.service.delete(this.service.findById(id));
        return Response.ok().build();
    }

}

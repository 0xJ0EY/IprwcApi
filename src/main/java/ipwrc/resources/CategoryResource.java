package ipwrc.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Category;
import ipwrc.services.CategoryService;
import ipwrc.views.View;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/category/")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {
    public static class CategoryPublicView extends View.Public {}

    private CategoryService service;

    @Inject
    public CategoryResource(CategoryService service) {
        this.service = service;
    }

    @GET
    @Path("/")
    @JsonView(CategoryPublicView.class)
    @UnitOfWork
    public List<Category> all() {
        return service.getAll();
    }

    @GET
    @Path("/{category}")
    @JsonView(CategoryPublicView.class)
    @UnitOfWork
    public Category category(@PathParam("category") String title) {
        return service.findByTitle(title);
    }

}

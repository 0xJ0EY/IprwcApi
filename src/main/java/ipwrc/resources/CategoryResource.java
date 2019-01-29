package ipwrc.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Category;
import ipwrc.services.CategoryService;
import ipwrc.views.View;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
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

    @POST
    @UnitOfWork
    @RolesAllowed("admin")
    public void save(@Valid Category category) {
        this.service.create(category);
    }

    @PUT
    @UnitOfWork
    @RolesAllowed("admin")
    public void update(@Valid Category category) {
        this.service.update(category);
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed("admin")
    public void delete(@PathParam("id") int id) {
        Category category = this.service.findById(id);
        this.service.delete(category);
    }

}

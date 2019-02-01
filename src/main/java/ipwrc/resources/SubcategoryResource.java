package ipwrc.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Category;
import ipwrc.models.Subcategory;
import ipwrc.services.SubcategoryService;
import ipwrc.views.View;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/subcategory/")
@Produces(MediaType.APPLICATION_JSON)
public class SubcategoryResource {

    public static class SubcategoryPublicView extends View.Public {}
    public static class SubcategoryPrivateView extends View.Private {}

    private SubcategoryService service;

    public SubcategoryResource(SubcategoryService service) {
        this.service = service;
    }

    @GET()
    @UnitOfWork
    @JsonView(SubcategoryPublicView.class)
    public List<Subcategory> all() {
        return this.service.getAll();
    }

    @GET
    @Path("/{subcategory}")
    @UnitOfWork
    @JsonView(SubcategoryPrivateView.class)
    public Subcategory subcategory(@PathParam("subcategory") String title) {
        return this.service.findByTitle(title);
    }

    @POST
    @UnitOfWork
    @RolesAllowed("admin")
    public Subcategory save(@Valid Subcategory subcategory) {
        this.service.create(subcategory);
        return subcategory;
    }

    @PUT
    @UnitOfWork
    @RolesAllowed("admin")
    public Subcategory update(@Valid Subcategory subcategory) {
        this.service.update(subcategory);
        return subcategory;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed("admin")
    public void delete(@PathParam("id") int id) {
        Subcategory subcategory = this.service.findById(id);
        this.service.delete(subcategory);
    }


}

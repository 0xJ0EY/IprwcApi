package ipwrc.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Subcategory;
import ipwrc.services.SubcategoryService;
import ipwrc.views.View;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    @Path("/")
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

}

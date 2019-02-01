package ipwrc.resources;

import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Brand;
import ipwrc.services.BrandService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import java.util.List;

@Path("/brands/")
public class BrandResource  {

    private BrandService service;

    public BrandResource(BrandService service) {
        this.service = service;
    }

    @GET
    @UnitOfWork
    public List<Brand> getAll() {
        return this.service.getAll();
    }

    @GET
    @UnitOfWork
    @Path("/{title}")
    public Brand get(@PathParam("title") String title) {
        return this.service.findByTitle(title);
    }

    @GET
    @UnitOfWork
    @Path("/id/{id}")
    public Brand get(@PathParam("id") int id) {
        return this.service.findById(id);
    }

    @POST
    @UnitOfWork
    @RolesAllowed("admin")
    public Brand create(Brand brand) {
        this.service.create(brand);
        return brand;
    }

    @PUT
    @UnitOfWork
    @RolesAllowed("admin")
    public Brand update(Brand brand) {
        this.service.update(brand);
        return brand;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed("admin")
    public void delete(@PathParam("id") int id) {
        this.service.delete(this.service.findById(id));
    }

}

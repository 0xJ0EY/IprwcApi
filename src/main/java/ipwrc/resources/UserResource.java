package ipwrc.resources;


import ipwrc.models.User;
import ipwrc.persistence.UserDAO;
import ipwrc.services.UserService;
import ipwrc.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService service;

    @Inject
    public UserResource(UserService service) {
         this.service = service;
    }

    @GET
    @RolesAllowed("user")
    @Path("/")
    @JsonView(View.Public.class)
    @UnitOfWork
    public List<User> all() {
        return this.service.getAll();
    }

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    @JsonView(View.Public.class)
    @UnitOfWork
    public User show(@PathParam("id") int id) {
        return this.service.findById(id);
    }

    @GET
    @Path("/me")
    @JsonView(View.Private.class)
    public User auth(@Auth User authenticator) {
        return authenticator;
    }
}

package ipwrc.resources;

import ipwrc.models.User;
import ipwrc.services.UserService;
import ipwrc.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private UserService service;

    @Inject
    public AuthResource(UserService service) {
        this.service = service;
    }

    @POST
    @UnitOfWork
    @Path("/login")
    @JsonView(View.Private.class)
    public User login(@FormDataParam("username") String username, @FormDataParam("password") String password) {
        User user = this.service.findUserByUsername(username);

        if (BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            throw new NotFoundException();
        }
    }

}

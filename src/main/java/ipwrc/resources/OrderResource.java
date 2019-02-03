package ipwrc.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Order;
import ipwrc.models.User;
import ipwrc.services.OrderService;
import ipwrc.views.View;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/orders/")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService service;

    public OrderResource(OrderService service) {
        this.service = service;
    }

    @GET
    @UnitOfWork
    @Path("/all")
    @RolesAllowed("admin")
    @JsonView(View.Public.class)
    public List<Order> getAll() {
        return this.service.getAll();
    }

    @GET
    @UnitOfWork
    @JsonView(View.Public.class)
    public List<Order> getPersonal(@Auth User user) {
        return this.service.getPersonal(user);
    }

    @GET
    @UnitOfWork
    @JsonView(View.Private.class)
    @Path("/{id}/")
    public Order getById(@Auth User user, @PathParam("id") int id) {
        return this.service.getPersonalById(user, id);
    }

    @POST
    @UnitOfWork
    @JsonView(View.Private.class)
    @Consumes({ MediaType.APPLICATION_JSON })
    public Order create(@Auth User user, Order order) {
        this.service.create(user, order);
        return order;
    }

}

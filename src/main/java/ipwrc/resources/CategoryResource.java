package ipwrc.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.hibernate.UnitOfWork;
import ipwrc.models.Category;
import ipwrc.models.User;
import ipwrc.persistence.CategoryDAO;
import ipwrc.persistence.UserDAO;
import ipwrc.views.View;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/category/")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private CategoryDAO dao;

    @Inject
    public CategoryResource(CategoryDAO dao) {
        this.dao = dao ;
    }

    @GET
    @Path("/")
    @JsonView(View.Public.class)
    @UnitOfWork
    public List<Category> all() {
        return dao.getAll();
    }

}

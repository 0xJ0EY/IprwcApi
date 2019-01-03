package ipwrc.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import ipwrc.models.Category;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDAO extends AbstractDAO<Category> {

    public CategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Category> getAll() {
        return list((Query<Category>) namedQuery("Category.getAll"));
    }

}

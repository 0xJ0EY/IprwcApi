package ipwrc.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import ipwrc.models.Category;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CategoryDAO extends AbstractDAO<Category> {

    public CategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Category> getAll() {
        return list((Query<Category>) namedQuery("Category.getAll"));
    }

    @SuppressWarnings("unchecked")
    public Optional<Category> findByTitle(String name) {
        List<Category> categories = list((Query<Category>) namedQuery("Category.findByTitle")
            .setParameter("title", name.toLowerCase())
            .setMaxResults(1)
        );

        return Optional.ofNullable(categories.size() > 0 ? categories.get(0) : null);
    }

}

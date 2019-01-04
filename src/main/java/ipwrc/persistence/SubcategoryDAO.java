package ipwrc.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import ipwrc.models.Subcategory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SubcategoryDAO extends AbstractDAO<Subcategory> {

    public SubcategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Subcategory> getAll() {
        return list((Query<Subcategory>) namedQuery("Subcategory.getAll"));
    }

    @SuppressWarnings("unchecked")
    public Optional<Subcategory> findByTitle(String name) {
        List<Subcategory> categories = list((Query<Subcategory>) namedQuery("Subcategory.findByTitle")
                .setParameter("title", name.toLowerCase())
                .setMaxResults(1)
        );

        return Optional.ofNullable(categories.size() > 0 ? categories.get(0) : null);
    }

}

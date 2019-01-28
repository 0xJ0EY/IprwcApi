package ipwrc.persistence;

import ipwrc.models.Subcategory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SubcategoryDAO extends DAO<Subcategory> {

    public SubcategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Subcategory> getAll() {
        return list((Query<Subcategory>) namedQuery("Subcategory.getAll"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Subcategory getByTitle(String name) {
        List<Subcategory> categories = list((Query<Subcategory>) namedQuery("Subcategory.findByTitle")
            .setParameter("title", name.toLowerCase())
            .setMaxResults(1)
        );

        return categories.size() > 0 ? categories.get(0) : null;
    }

}

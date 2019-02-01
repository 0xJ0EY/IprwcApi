package ipwrc.persistence;

import ipwrc.models.Brand;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class BrandDAO extends DAO<Brand> {

    public BrandDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Brand> getAll() {
        return list((Query<Brand>) namedQuery("Brand.getAll"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brand getByTitle(String name) {
        List<Brand> brands = list((Query<Brand>) namedQuery("Brand.findByTitle")
                .setParameter("title", name.toLowerCase())
                .setMaxResults(1)
        );

        return brands.size() > 0 ? brands.get(0) : null;
    }
}

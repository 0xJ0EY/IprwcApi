package ipwrc.persistence;

import ipwrc.models.Product;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO extends DAO<Product> {

    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAll() {
        return list((Query<Product>) namedQuery("Product.getAll"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Product getById(int id) {
        List<Product> products = list((Query<Product>) namedQuery("Product.findById")
                .setParameter("id", id)
                .setMaxResults(1)
        );

        return products.size() > 0 ? products.get(0) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Product getByTitle(String title) {
        List<Product> products = list((Query<Product>) namedQuery("Product.findByTitle")
            .setParameter("title", title.toLowerCase())
            .setMaxResults(1)
        );

        return products.size() > 0 ? products.get(0) : null;
    }

}

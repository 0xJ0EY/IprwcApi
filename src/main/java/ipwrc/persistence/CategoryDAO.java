package ipwrc.persistence;

import ipwrc.models.Category;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CategoryDAO extends DAO<Category> {

    public CategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Category> getAll() {
        return list((Query<Category>) namedQuery("Category.getAll"));
    }

    @Override
    public Category getById(int id) {
        return this.get(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Category getByTitle(String title) {
        List<Category> categories = list((Query<Category>) namedQuery("Category.findByTitle")
            .setParameter("title", title.toLowerCase())
            .setMaxResults(1)
        );

        return categories.size() > 0 ? categories.get(0) : null;
    }

    @Override
    public void create(Category obj) {
        this.currentSession().save(obj);
    }

    @Override
    public void update(Category obj) {
        this.currentSession().update(obj);
    }

    @Override
    public void delete(Category obj) {
        this.currentSession().delete(obj);
    }
}

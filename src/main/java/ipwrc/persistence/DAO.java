package ipwrc.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public abstract class DAO<T> extends AbstractDAO<T> implements DAOInterface<T> {

    public DAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public T getById(int id) {
        return get(id);
    }

    @Override
    public void create(T obj) {
        this.currentSession().save(obj);
    }

    @Override
    public void update(T obj) {
        this.currentSession().merge(obj);
    }

    @Override
    public void delete(T obj) {
        this.currentSession().delete(obj);
    }
}

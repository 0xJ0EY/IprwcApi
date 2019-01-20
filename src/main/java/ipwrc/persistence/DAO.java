package ipwrc.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public abstract class DAO<T> extends AbstractDAO<T> implements DAOInterface<T> {

    public DAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}

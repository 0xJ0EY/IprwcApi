package ipwrc.persistence;

import ipwrc.models.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return list((Query<User>) namedQuery("User.findAll"));
    }

    public Optional<User> findById(int id) {
        return Optional.ofNullable(get(id));
    }

    @SuppressWarnings("unchecked")
    public Optional<User> findUserByUsername(String username) {

        List<User> users = list((Query<User>) namedQuery("User.findByCredentials")
            .setParameter("username", username)
            .setMaxResults(1)
        );

        return Optional.ofNullable(users.size() > 0 ? users.get(0) : null);
    }

}

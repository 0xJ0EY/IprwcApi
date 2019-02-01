package ipwrc.persistence;

import ipwrc.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAO extends DAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return list((Query<User>) namedQuery("User.findAll"));
    }

    @SuppressWarnings("unchecked")
    public User getByUsername(String username) {
        List<User> users = list((Query<User>) namedQuery("User.findByCredentials")
            .setParameter("username", username)
            .setMaxResults(1)
        );

        return users.get(0);
    }

}

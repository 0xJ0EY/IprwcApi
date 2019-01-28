package ipwrc.services;

import ipwrc.models.User;
import ipwrc.persistence.DAO;
import ipwrc.persistence.UserDAO;

import javax.inject.Inject;
import java.util.List;

public class UserService extends BaseService<User> {

    @Inject
    public UserService(DAO<User> dao) {
        super(dao);
    }

    public List<User> getAll() {
        return this.dao.getAll();
    }

    public User findById(int id) {
        User user = this.dao.getById(id);

        this.requireResult(user);

        return user;
    }

    public User findUserByUsername(String username) {
        User user = ((UserDAO)this.dao).getByUsername(username);

        this.requireResult(user);

        return user;
    }

}

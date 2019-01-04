package ipwrc.services;

import ipwrc.models.User;
import ipwrc.persistence.UserDAO;

import javax.inject.Inject;
import java.util.List;

public class UserService extends BaseService<User> {

    private UserDAO dao;

    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public List<User> getAll() {
        return this.dao.getAll();
    }

    public User findById(int id) {
        User user = this.dao.findById(id).get();

        this.requireResult(user);

        return user;
    }

    public User findUserByUsername(String username) {
        User user = this.dao.findUserByUsername(username).get();

        this.requireResult(user);

        return user;
    }

}

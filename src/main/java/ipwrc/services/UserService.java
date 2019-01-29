package ipwrc.services;

import ipwrc.models.User;
import ipwrc.persistence.DAO;
import ipwrc.persistence.UserDAO;

import javax.inject.Inject;

public class UserService extends BaseService<User> {

    @Inject
    public UserService(DAO<User> dao) {
        super(dao);
    }

    public User findUserByUsername(String username) {
        User user = ((UserDAO)this.dao).getByUsername(username);

        this.requireResult(user);

        return user;
    }

}

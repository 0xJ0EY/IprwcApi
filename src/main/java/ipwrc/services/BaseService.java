package ipwrc.services;

import ipwrc.models.User;
import ipwrc.persistence.DAO;

import javax.ws.rs.NotFoundException;

public abstract class BaseService<T> {

    public T requireResult(T model) {
        if (model == null)
            throw new NotFoundException();

        return model;
    }

    public void assertSelf(User user1, User user2) {
        if (!user1.equals(user2))
            throw new NotFoundException();

    }

}

package ipwrc.services;

import ipwrc.models.User;
import ipwrc.persistence.DAO;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

public abstract class BaseService<T> {

    protected DAO<T> dao;

    @Inject
    public BaseService(DAO<T> dao) {
        this.dao = dao;
    }

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

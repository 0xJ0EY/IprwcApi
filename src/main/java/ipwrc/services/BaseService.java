package ipwrc.services;

import ipwrc.models.User;
import ipwrc.persistence.DAO;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;

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

    public List<T> getAll() {
        return this.dao.getAll();
    }

    public T findById(int id) {
        T obj = this.dao.getById(id);

        // Check if it has a result, else throw an exception
        this.requireResult(obj);

        return obj;
    }

    public T findByTitle(String title) {
        T obj = this.dao.getByTitle(title);

        // Check if it has a result, else throw an exception
        this.requireResult(obj);

        return obj;
    }

    public void create(T obj) {
        this.dao.create(obj);
    }

    public void update(T obj) {
        this.dao.update(obj);
    }

    public void delete(T obj) {
        this.dao.delete(obj);
    }

}

package ipwrc.persistence;

import ipwrc.exceptions.NotImplementedException;

import java.util.List;

public interface DAOInterface<T> {

    default List<T> getAll() {
        throw new NotImplementedException();
    }

    default T getById(int id) {
        throw new NotImplementedException();
    }

    default T getByTitle(String title) {
        throw new NotImplementedException();
    }

    default void create(T obj) {
        throw new NotImplementedException();
    }

    default void update(T obj) {
        throw new NotImplementedException();
    }

    default void delete(T obj) {
        throw new NotImplementedException();
    }

}

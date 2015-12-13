package project.server.work_with_client.database;

import project.server.work_with_client.database.exceptions.DataAccessException;

import java.util.List;

public interface Dao<T extends IdentifiedObject> {

    List<T> get(List<Object> values, String where) throws DataAccessException;

    T getOne(List<Object> values, String where) throws DataAccessException;

    T getById(Long id) throws DataAccessException;

    T insert(T object) throws DataAccessException;

    void update(T object) throws DataAccessException;

    void delete(T object) throws DataAccessException;

    List<T> getAll() throws DataAccessException;
}

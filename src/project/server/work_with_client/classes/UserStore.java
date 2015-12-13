package project.server.work_with_client.classes;

import project.server.work_with_client.database.exceptions.DataAccessException;

/**
 * Created by rikachka on 02.12.15.
 */
public interface UserStore {
    User createUser(String login) throws DataAccessException;

    User getUser(Long id) throws DataAccessException;

    User getUser(String login) throws DataAccessException;

    User getUser(String login, String password) throws DataAccessException;

    void updateUser(User user) throws DataAccessException;
}

package project.server.work_with_client.classes;

import project.server.work_with_client.database.DaoUser;
import project.server.work_with_client.database.exceptions.DataAccessException;

/**
 * Created by rikachka on 07.11.15.
 */

public class Users implements UserStore {
    final private String BASIC_PASSWORD = "0";
    private DaoUser daoUser;

    public Users(DaoUser daoUser) {
        this.daoUser = daoUser;
    }

    public User createUser(String login) throws DataAccessException {
        User createdUser = new User(login, BASIC_PASSWORD);
        daoUser.insert(createdUser);
        return createdUser;
    }

    public User getUser(Long id) throws DataAccessException {
        return daoUser.getById(id);
    }

    public User getUser(String login) throws DataAccessException {
        return daoUser.getByLogin(login);
    }

    public User getUser(String login, String password) throws DataAccessException {
        return daoUser.getByLoginAndPassword(login, password);
    }

    public void updateUser(User user) throws DataAccessException {
        daoUser.update(user);
    }
}

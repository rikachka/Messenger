package project.server.work_with_client.database;

import project.server.work_with_client.classes.User;
import project.server.work_with_client.database.exceptions.DataAccessException;
import project.server.work_with_client.database.exceptions.IllegalDataStateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DaoUser extends DaoAbstract<User> {
    public DaoUser(TableProvider tableProvider) {
        super(tableProvider, TableType.USER);
    }

    @Override
    protected List<Object> prepareValuesForInsert(User object) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(object.getLogin());
        values.add(object.getPassword());
        values.add(object.getNickname());
        return values;
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) {
        List<User> chatMessageList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int index = 1;
                Long id = resultSet.getLong(index++);
                String login = resultSet.getString(index++).trim();
                String password = resultSet.getString(index++).trim();
                String nickname = resultSet.getString(index++).trim();
                chatMessageList.add(new User(id, login, password, nickname));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new IllegalDataStateException(e);
        }
        return chatMessageList;
    }

    @Override
    protected List<Object> prepareValuesForUpdate(User object) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(object.getLogin());
        values.add(object.getPassword());
        values.add(object.getNickname());
        values.add(object.getId());
        return values;
    }

    public User getByLogin(String login) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(login);
        String where = " WHERE login = ?";
        return getOne(values, where);
    }

    public User getByLoginAndPassword(String login, String password) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(login);
        values.add(password);
        String where = " WHERE login = ? AND password = ?";
        return getOne(values, where);
    }

}

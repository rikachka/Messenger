package project.server.work_with_client.database;

import project.server.work_with_client.classes.Chat;
import project.server.work_with_client.database.exceptions.DataAccessException;
import project.server.work_with_client.database.exceptions.IllegalDataStateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DaoChat extends DaoAbstract<Chat> {
    public DaoChat(TableProvider tableProvider) {
        super(tableProvider, TableType.CHAT);
    }

    @Override
    protected List<Object> prepareValuesForInsert(Chat object) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(object.getAdminId());
        return values;
    }

    @Override
    protected List<Chat> parseResultSet(ResultSet resultSet) {
        List<Chat> chatMessageList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int index = 1;
                Long id = resultSet.getLong(index++);
                Long adminId = resultSet.getLong(index++);
                chatMessageList.add(new Chat(id, adminId));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new IllegalDataStateException(e);
        }
        return chatMessageList;
    }

    @Override
    protected List<Object> prepareValuesForUpdate(Chat object) throws DataAccessException {
        throw new DataAccessException("Impossible to update chats");
    }

}

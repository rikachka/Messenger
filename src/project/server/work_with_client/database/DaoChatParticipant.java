package project.server.work_with_client.database;

import project.server.work_with_client.classes.ChatParticipant;
import project.server.work_with_client.database.exceptions.DataAccessException;
import project.server.work_with_client.database.exceptions.IllegalDataStateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DaoChatParticipant extends DaoAbstract<ChatParticipant> {
    public DaoChatParticipant(TableProvider tableProvider) {
        super(tableProvider, TableType.CHATPARTICIPANT);
    }

    @Override
    protected List<Object> prepareValuesForInsert(ChatParticipant object) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(object.getChatId());
        values.add(object.getUserId());
        return values;
    }

    @Override
    protected List<ChatParticipant> parseResultSet(ResultSet resultSet) {
        List<ChatParticipant> chatMessageList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int index = 1;
                Long id = resultSet.getLong(index++);
                Long chatId = resultSet.getLong(index++);
                Long userId = resultSet.getLong(index++);
                chatMessageList.add(new ChatParticipant(id, chatId, userId));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new IllegalDataStateException(e);
        }
        return chatMessageList;
    }

    @Override
    protected List<Object> prepareValuesForUpdate(ChatParticipant object) throws DataAccessException {
        throw new DataAccessException("Impossible to update chat participants");
    }

    public List<ChatParticipant> getByUserId(Long userId) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(userId);
        String where = " WHERE user_id = ?";
        return get(values, where);
    }

    public List<ChatParticipant> getByChatId(Long chatId) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(chatId);
        String where = " WHERE chat_id = ?";
        return get(values, where);
    }
}

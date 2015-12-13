package project.server.work_with_client.database;

import project.server.work_with_client.classes.ChatMessage;
import project.server.work_with_client.database.exceptions.DataAccessException;
import project.server.work_with_client.database.exceptions.IllegalDataStateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DaoChatMessage extends DaoAbstract<ChatMessage> {
    public DaoChatMessage(TableProvider tableProvider) {
        super(tableProvider, TableType.CHATMESSAGE);
    }

    @Override
    protected List<Object> prepareValuesForInsert(ChatMessage object) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(object.getChatId());
        values.add(object.getSenderId());
        values.add(object.getText());
        values.add(object.getTimestamp());
        return values;
    }

    @Override
    protected List<ChatMessage> parseResultSet(ResultSet resultSet) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int index = 1;
                Long id = resultSet.getLong(index++);
                Long chatId = resultSet.getLong(index++);
                Long senderId = resultSet.getLong(index++);
                String text = resultSet.getString(index++);
                Timestamp timestamp = resultSet.getTimestamp(index++);
                chatMessageList.add(new ChatMessage(id, chatId, senderId, text, timestamp));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new IllegalDataStateException(e);
        }
        return chatMessageList;
    }

    @Override
    protected List<Object> prepareValuesForUpdate(ChatMessage object) throws DataAccessException {
        throw new DataAccessException("Impossible to update messages");
    }

    public List<ChatMessage> getByChatId(Long chatId) throws DataAccessException {
        List<Object> values = new LinkedList<>();
        values.add(chatId);
        String where = " WHERE chat_id = ?";
        return get(values, where);
    }
}

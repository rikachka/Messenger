package project.server.work_with_client.database;

import java.util.*;

public class Tables {
    public static final String USERTABLE = "users";
    public static final String CHATTABLE = "chats";
    public static final String MESSAGETABLE = "chat_messages";
    public static final String PARTICIPANTTABLE = "chat_participants";

    public static Map<TableType, String> prepareTableNames() {
        Map<TableType, String> tableNames = new HashMap<>();
        tableNames.put(TableType.CHAT, CHATTABLE);
        tableNames.put(TableType.USER, USERTABLE);
        tableNames.put(TableType.CHATMESSAGE, MESSAGETABLE);
        tableNames.put(TableType.CHATPARTICIPANT, PARTICIPANTTABLE);
        return tableNames;
    }


    public static Map<TableType, List<String>> prepareTableColumnsNames() {
        Map<TableType, List<String>> tableColumnsNames = new HashMap<>();
        List<String> columnNames = new LinkedList<>();
        columnNames.add("id");
        columnNames.add("login");
        columnNames.add("password");
        columnNames.add("nickname");
        tableColumnsNames.put(TableType.USER, columnNames);
        columnNames = new LinkedList<>();
        columnNames.add("id");
        columnNames.add("admin_id");
        tableColumnsNames.put(TableType.CHAT, columnNames);
        columnNames = new LinkedList<>();
        columnNames.add("id");
        columnNames.add("chat_id");
        columnNames.add("sender_id");
        columnNames.add("message_text");
        columnNames.add("message_timestamp");
        tableColumnsNames.put(TableType.CHATMESSAGE, columnNames);
        columnNames = new LinkedList<>();
        columnNames.add("id");
        columnNames.add("chat_id");
        columnNames.add("user_id");
        tableColumnsNames.put(TableType.CHATPARTICIPANT, columnNames);
        return tableColumnsNames;
    }

}

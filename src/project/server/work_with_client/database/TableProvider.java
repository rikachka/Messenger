package project.server.work_with_client.database;

import java.util.List;
import java.util.Map;


public class TableProvider {
//    private Map<TableType, DaoAbstract> daoMap;
    private Map<TableType, List<String>> tableColumns;
    private Map<TableType, String> tableNames;
    private QueryExecutor queryExecutor;

    public TableProvider() {
        queryExecutor = new QueryExecutor();
        this.tableNames = Tables.prepareTableNames();
        this.tableColumns = Tables.prepareTableColumnsNames();
//        setup();
    }

//    private void setup() {
//        daoMap = new HashMap<>();
//        daoMap.put(TableType.USER, new DaoUser(queryExecutor));
//        daoMap.put(TableType.CHAT, new DaoChat(queryExecutor));
//        daoMap.put(TableType.CHATMESSAGE, new DaoChatMessage(queryExecutor));
//        daoMap.put(TableType.CHATPARTICIPANT, new DaoChatParticipant(queryExecutor));
//    }

    public List<String> getTableColumns(TableType tableType) {
        if (tableColumns.containsKey(tableType)) {
            return tableColumns.get(tableType);
        }
        return null;
    }

    public String getTableName(TableType tableType) {
        if (tableType != null && tableNames.containsKey(tableType)) {
            return tableNames.get(tableType);
        }
        return null;
    }

    public QueryExecutor getQueryExecutor() {
        return queryExecutor;
    }
}

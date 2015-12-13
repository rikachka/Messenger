package project.server.work_with_client.database;

import project.server.work_with_client.database.exceptions.DataAccessException;
import project.server.work_with_client.database.exceptions.IllegalDataStateException;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public abstract class DaoAbstract<T extends IdentifiedObject> implements Dao<T> {
    protected String ID_CLAUSE;
    protected String tableName;
    protected List<String> columnNames;
    protected QueryExecutor queryExecutor;
    protected TableType tableType;

    public DaoAbstract(TableProvider tableProvider, TableType tableType) {
        this.tableType = tableType;
        this.queryExecutor = tableProvider.getQueryExecutor();
        this.tableName = tableProvider.getTableName(tableType);
        this.columnNames = tableProvider.getTableColumns(tableType);
        ID_CLAUSE = " WHERE " + columnNames.get(0) + " = ?";
    }



    public List<T> get(List<Object> values, String where) throws DataAccessException {
        String sql = getSelectQuery() + where;
        List<T> list = queryExecutor.executeQuery(sql, values, this::parseResultSet);
        return list;
    }

    public T getOne(List<Object> values, String where) throws DataAccessException {
        List<T> list = get(values, where);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new IllegalDataStateException("Received more than one record by id.");
        }
        return list.get(0);
    }

    public T getById(Long id) throws DataAccessException {
        if (id == null) {
            throw new DataAccessException("Can't get record without id");
        }
        List<Object> values = new LinkedList<>();
        values.add(id);
        return getOne(values, ID_CLAUSE);
    }

    public T insert(T item) throws DataAccessException {
        if (item.getId() != null) {
            throw new DataAccessException("Object already has id.");
        }
        String sql = getInsertQuery();
        List<Object> values = prepareValuesForInsert(item);
        Long objectId = queryExecutor.executeUpdateReturningId(sql, values);
        if (objectId == null) {
            throw new IllegalDataStateException("Exception on new inserted record.");
        }
        item.setId(objectId);
        return item;
    }

    public void update(T object) throws DataAccessException {
        if (object.getId() == null) {
            throw new DataAccessException("Can't update record without id");
        }
        String sql = getUpdateQuery();
        List<Object> values = prepareValuesForUpdate(object);
        int updated = queryExecutor.executeUpdate(sql, values);
        if (updated != 1) {
            throw new IllegalDataStateException("On update modified more than 1 record: " + updated);
        }
    }

    public void delete(T object) throws DataAccessException {
        if (object.getId() == null) {
            throw new DataAccessException("Can't delete record without id");
        }
        String sql = getDeleteQuery();
        List<Object> values = new LinkedList<>();
        values.add(object.getId());
        int updated = queryExecutor.executeUpdate(sql, values);
        if (updated != 1) {
            throw new IllegalDataStateException("On delete modified more than 1 record: " + updated);
        }

    }

    public List<T> getAll() throws DataAccessException {
        String sql = getSelectQuery();
        return queryExecutor.executeQuery(sql, this::parseResultSet);
    }



    private String getSelectQuery() {
        return "SELECT * FROM " + tableName;
    }


    private String getInsertQuery() {
        String columns = "";
        String values = "";
        for (int index = 1; index < columnNames.size(); index++) {
            columns += columnNames.get(index);
            values += "?";
            if (index != columnNames.size() - 1) {
                columns += ", ";
                values += ", ";
            }
        }
        String insertQuery = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        return insertQuery;
    }

    private String getUpdateQuery() {
        String columns = "";
        for (int index = 1; index < columnNames.size(); index++) {
            columns += columnNames.get(index) + " = ?";
            if (index != columnNames.size() - 1) {
                columns += ", ";
            }
        }
        String updateQuery = "UPDATE " + tableName + " SET " + columns + ID_CLAUSE;
        return updateQuery;
    }

    private String getDeleteQuery() {
        return "DELETE FROM " + tableName + ID_CLAUSE;
    }



    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     */
    protected abstract List<Object> prepareValuesForInsert(T object) throws DataAccessException;

    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     */
    protected abstract List<Object> prepareValuesForUpdate(T object) throws DataAccessException;

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet.
     */
    protected abstract List<T> parseResultSet(ResultSet rs);
}

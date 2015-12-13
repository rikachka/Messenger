package project.server.work_with_client.database;

import project.server.work_with_client.database.exceptions.DataAccessException;
import project.server.work_with_client.database.exceptions.IllegalDataStateException;
import project.server.work_with_client.database.exceptions.ObjectAccessException;

import java.sql.*;
import java.util.List;

/**
 * Обертка для запроса в базу
 */
public class QueryExecutor {
    private ConnectionPool connectionPool;

    public QueryExecutor() {
        connectionPool = ConnectionPool.getPoolInstance();
    }

    // Простой запрос
    public <T> T executeQuery(String query, ResultHandler<T> handler) throws DataAccessException {
        try {
            Connection connection = connectionPool.getInstance();
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
            ResultSet resultSet = stmt.getResultSet();
            T value = handler.handle(resultSet);
            resultSet.close();
            stmt.close();
            connectionPool.releaseInstance(connection);
            return value;
        } catch (SQLException | ObjectAccessException e) {
            throw new DataAccessException(e);
        }
    }

    // Подготовленный запрос
    public <T> T executeQuery(String query, List<Object> args,
                              ResultHandler<T> handler) throws DataAccessException {
        try {
            Connection connection = connectionPool.getInstance();
            PreparedStatement stmt = connection.prepareStatement(query);
            int index = 1;
            for (Object arg : args) {
                stmt.setObject(index, arg);
                index++;
            }
            ResultSet resultSet = stmt.executeQuery();
            T value = handler.handle(resultSet);
            resultSet.close();
            stmt.close();
            connectionPool.releaseInstance(connection);
            return value;
        } catch (SQLException | ObjectAccessException e) {
            throw new DataAccessException(e);
        }
    }

    // Update запросы
    public int[] executeUpdateBatch(String updateQuery, List<List<Object>> args)
            throws DataAccessException {
        try {
            Connection connection = connectionPool.getInstance();
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            for (List<Object> recordArgs : args) {
                int index = 1;
                for (Object arg : recordArgs) {
                    stmt.setObject(index, arg);
                    index++;
                }
                stmt.addBatch();
            }
            int[] results = stmt.executeBatch();
            stmt.close();
            connectionPool.releaseInstance(connection);
            return results;
        } catch (SQLException | ObjectAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public int executeUpdate(String updateQuery) throws DataAccessException {
        try {
            Connection connection = connectionPool.getInstance();
            Statement stmt = connection.createStatement();
            int updated = stmt.executeUpdate(updateQuery);
            stmt.close();
            connectionPool.releaseInstance(connection);
            return updated;
        } catch (SQLException | ObjectAccessException e) {
            throw new DataAccessException(e);
        }
    }

    // Подготовленный запрос
    public int executeUpdate(String updateQuery, List<Object> args) throws DataAccessException {
        try {
            Connection connection = connectionPool.getInstance();
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            int index = 1;
            for (Object arg : args) {
                stmt.setObject(index, arg);
                index++;
            }
            int updated = stmt.executeUpdate();
            stmt.close();
            connectionPool.releaseInstance(connection);
            return updated;
        } catch (SQLException | ObjectAccessException e) {
            throw new DataAccessException(e);
        }
    }

    // Подготовленный запрос с возвращением id вставленной записи
    public Long executeUpdateReturningId(String updateQuery, List<Object> args) throws DataAccessException {
        try {
            Connection connection = connectionPool.getInstance();
            PreparedStatement stmt = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (Object arg : args) {
                stmt.setObject(index, arg);
                index++;
            }

            int updated = stmt.executeUpdate();
            if (updated != 1) {
                throw new IllegalDataStateException("On update modify more than 1 record: " + updated);
            }
            ResultSet rs = stmt.getGeneratedKeys();
            Long insertedId = null;
            while (rs.next()) {
                insertedId = rs.getLong(1);
            }
            rs.close();
            stmt.close();
            connectionPool.releaseInstance(connection);
            return insertedId;
        } catch (SQLException | ObjectAccessException e) {
            throw new DataAccessException(e);
        }
    }

}

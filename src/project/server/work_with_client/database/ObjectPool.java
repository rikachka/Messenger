package project.server.work_with_client.database;

import project.server.work_with_client.database.exceptions.ObjectAccessException;

public interface ObjectPool<Context> extends AutoCloseable {

    Context getInstance() throws ObjectAccessException;

    void releaseInstance(Context instance) throws ObjectAccessException;
}

/**
 * 
 */
package org.taskonaut.db;

import com.amazon.carbonado.ConfigurationException;
import com.amazon.carbonado.Repository;
import com.amazon.carbonado.RepositoryException;
import com.amazon.carbonado.Storage;
import com.amazon.carbonado.SupportException;
import com.amazon.carbonado.repo.sleepycat.BDBRepositoryBuilder;
import org.taskonaut.tasks.OneTask;
import org.taskonaut.tasks.TimeLogItem;
import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ProlubnikovDA
 */
public class TaskDB {
    private BDBRepositoryBuilder builder = null;
    Repository repo = null;
    private static TaskDB me = null;

    public static TaskDB getInstance() {
        if(me==null) {
            me = new TaskDB();
        }
        return me;
    }

    public Repository getRepository() {
        return repo;
    }

    public void openStorage() throws ConfigurationException, RepositoryException {
        builder = new BDBRepositoryBuilder();
        builder.setName("taskonaut");
        File envHome = new File("./db", "taskonaut");
        builder.setEnvironmentHomeFile(envHome);
        builder.setTransactionWriteNoSync(true);
        repo = builder.build();
    }

    public void closeStorage() {
        repo.close();
    }

    public void storeAllTimeLog(Collection<TimeLogItem> list) throws SupportException, RepositoryException {
        Storage<TimeLogStore> storage = repo.storageFor(TimeLogStore.class);
        TimeLogStore t;
        for(TimeLogItem k : list) {
            t = Adapter.adaptee(k, storage.prepare());
            if(t.tryUpdate())
                t.update(); // Если запись уже есть - обновим
            else
                t.insert(); // Иначе вставляем
        }
    }

    public void storeAllTasks(Collection<OneTask> list) throws SupportException, RepositoryException {
        Storage<TaskStore> storage = repo.storageFor(TaskStore.class);
        TaskStore t;
        for(OneTask k : list) {
            t = Adapter.adaptee(k, storage.prepare());
            if(t.tryUpdate())
                t.update(); // Если запись уже есть - обновим
            else
                t.insert(); // Иначе вставляем
        }
    }
}

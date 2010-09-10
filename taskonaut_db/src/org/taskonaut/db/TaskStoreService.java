/**
 * 
 */
package org.taskonaut.db;

import java.io.File;
import java.util.Collection;

import org.taskonaut.api.tasks.ITaskStoreService;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TimeLogItem;

import com.amazon.carbonado.ConfigurationException;
import com.amazon.carbonado.Repository;
import com.amazon.carbonado.RepositoryException;
import com.amazon.carbonado.Storage;
import com.amazon.carbonado.SupportException;
import com.amazon.carbonado.repo.sleepycat.BDBRepositoryBuilder;

/**
 * @author spec
 *
 */
public class TaskStoreService implements ITaskStoreService {
	private BDBRepositoryBuilder builder = null;
    private Repository repo = null;
    
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

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readAllTasks()
	 */
	@Override
	public Collection<TaskItem> readAllTasks() {
		try {
			Storage<TaskStore> storage = repo.storageFor(TaskStore.class);
			TaskStore t;
			t = storage.query().loadOne();
			System.out.println(t.getName());
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#saveAllTasks(java.util.Collection)
	 */
	@Override
	public void saveAllTasks(Collection<TaskItem> с) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readAllTimeLogItems()
	 */
	@Override
	public Collection<TimeLogItem> readAllTimeLogItems() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readTimeLogItems(long)
	 */
	@Override
	public Collection<TimeLogItem> readTimeLogItems(long task_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#saveTask(org.taskonaut.api.tasks.TaskItem)
	 */
	@Override
	public void saveTask(TaskItem t) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#saveTimeLog(org.taskonaut.api.tasks.TimeLogItem)
	 */
	@Override
	public void saveTimeLog(TimeLogItem t) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#createNewTask(java.lang.String)
	 */
	@Override
	public TaskItem createNewTask(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#addTime(long, long, long)
	 */
	@Override
	public TimeLogItem addTime(long start, long duration, long task_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readTask(long)
	 */
	@Override
	public TaskItem readTask(long task_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#deleteTask(long)
	 */
	@Override
	public void deleteTask(long task_id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#deleteTimeLog(long)
	 */
	@Override
	public void deleteTimeLog(long time_log_id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#deleteAllTimeLog(long)
	 */
	@Override
	public void deleteAllTimeLog(long task_id) {
		// TODO Auto-generated method stub

	}

}

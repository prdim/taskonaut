/**
 * 
 */
package org.taskonaut.db;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Collection;

import org.taskonaut.api.tasks.ITaskStoreService;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TimeLogItem;

import com.amazon.carbonado.ConfigurationException;
import com.amazon.carbonado.Cursor;
import com.amazon.carbonado.PersistException;
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

	/**
	 * Создает новый TaskStore на основе объекта TaskItem
	 * @param i
	 * @return
	 */
	private TaskStore adaptee(TaskItem i) {
		TaskStore t = (TaskStore) createNewTask(i.getName());
		return adaptee(i,t);
	}
	
	/**
	 * Копирует TaskItems -> TaskStore
	 * @param i
	 * @param t
	 * @return
	 */
	private TaskStore adaptee(TaskItem i, TaskStore t) {
		t.setID(i.getID());
		t.setName(i.getName());
		t.setComment(i.getComment());
		t.setExecute(i.getExecute());
		t.setOwner(i.getOwner());
		t.setPriority(i.getPriority());
		t.setRelation_id(i.getRelation_id());
		t.setState(i.getState());
		t.setType(i.getType());
		return t;
	}
	
	private TimeLogStore adaptee(TimeLogItem i, TimeLogStore t) {
		t.setComment(i.getComment());
		t.setEnd(i.getEnd());
		t.setID(i.getID());
		t.setTaskId(i.getTaskId());
		return t;
	}
	
	private TimeLogStore adaptee(TimeLogItem i) {
		TimeLogStore t = null;
		try {
			Storage<TimeLogStore> store = repo.storageFor(TimeLogStore.class);
			t = adaptee(i, store.prepare());
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readAllTasks()
	 */
	@Override
	public List<TaskItem> readAllTasks() {
		List<TaskItem> ic = new ArrayList<TaskItem>();
		try {
			Storage<TaskStore> storage = repo.storageFor(TaskStore.class);
			Cursor<TaskStore> c = storage.query().fetch();
			while(c.hasNext()) {
				ic.add(c.next());
			}
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ic;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#saveAllTasks(java.util.Collection)
	 */
	@Override
	public void saveAllTasks(List<TaskItem> c) {
		try {
			Storage<TaskStore> storage = repo.storageFor(TaskStore.class);
			TaskStore t = storage.prepare();
			for(TaskItem i : c) {
				t = adaptee(i,t);
				if(!t.tryUpdate()) t.insert();
			}
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readAllTimeLogItems()
	 */
	@Override
	public List<TimeLogItem> readAllTimeLogItems() {
		List<TimeLogItem> lt = new ArrayList<TimeLogItem>();
		try {
			Storage<TimeLogStore> store = repo.storageFor(TimeLogStore.class);
			Cursor<TimeLogStore> c = store.query().fetch();
			while(c.hasNext()) {
				lt.add(adaptee(c.next()));
			}
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lt;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readTimeLogItems(long)
	 */
	@Override
	public List<TimeLogItem> readTimeLogItems(long task_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAllTimeLogItems(List<TimeLogItem> c) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#saveTask(org.taskonaut.api.tasks.TaskItem)
	 */
	@Override
	public void saveTask(TaskItem t) {
		TaskStore s = adaptee(t);
		try {
			if (!s.tryUpdate())
				s.insert();
		} catch (PersistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		TaskStore t = null;
		try {
			t = repo.storageFor(TaskStore.class).prepare();
			t.setName(name);
			t.setID(System.currentTimeMillis());
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
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

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
import org.taskonaut.api.tasks.TaskItem.Status;
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
		List<TimeLogItem> lt = new ArrayList<TimeLogItem>();
		try {
			Storage<TimeLogStore> store = repo.storageFor(TimeLogStore.class);
			Cursor<TimeLogStore> c = store.query("taskId=?").with(task_id).fetch();
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

	@Override
	public void saveAllTimeLogItems(List<TimeLogItem> c) {
		try {
			Storage<TimeLogStore> store = repo.storageFor(TimeLogStore.class);
			TimeLogStore t = store.prepare();
			for(TimeLogItem i : c) {
				t = adaptee(i, t);
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
		TimeLogStore s = adaptee(t);
		try {
//			System.out.println("save " + s.getTaskId() + " - " + s.getPeriod());
			if(!s.tryUpdate()) s.insert();
		} catch (PersistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			// Определяем значения по умолчанию для новой задачи
			t.setComment("");
			t.setExecute(System.currentTimeMillis());
			t.setOwner("");
			t.setPriority(TaskItem.Priority.средний.name());
			t.setRelation_id(0);
			t.setState(TaskItem.Status.запланирована.name());
			t.setType(TaskItem.Type.задача.name());
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
	public TimeLogItem addTime(long start, long end, long task_id) {
		TimeLogStore t = null;
		try {
			t = repo.storageFor(TimeLogStore.class).prepare();
			t.setID(start);
			t.setEnd(end);
			t.setTaskId(task_id);
			t.setComment("");
			saveTimeLog(t);
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
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readTask(long)
	 */
	@Override
	public TaskItem readTask(long task_id) {
		TaskStore t = null;
		try {
			Storage<TaskStore> store = repo.storageFor(TaskStore.class);
			t = store.query("ID=?").with(task_id).tryLoadOne();
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
	 * @see org.taskonaut.api.tasks.ITaskStoreService#deleteTask(long)
	 */
	@Override
	public void deleteTask(long task_id) {
		try {
			TaskStore t = repo.storageFor(TaskStore.class).prepare();
			t.setID(task_id);
			t.tryDelete();
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#deleteTimeLog(long)
	 */
	@Override
	public void deleteTimeLog(long time_log_id) {
		try {
			TimeLogStore t = repo.storageFor(TimeLogStore.class).prepare();
			t.setID(time_log_id);
			t.tryDelete();
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#deleteAllTimeLog(long)
	 */
	@Override
	public void deleteAllTimeLog(long task_id) {
		try {
			Storage<TimeLogStore> store = repo.storageFor(TimeLogStore.class);
			store.query("taskId=?").with(task_id).deleteAll();
		} catch (SupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<TaskItem> getTasksForStatus(Status s) {
		List<TaskItem> lt = new ArrayList<TaskItem>();
		try {
			Storage<TaskStore> store = repo.storageFor(TaskStore.class);
			Cursor<TaskStore> c = store.query("state=?").with(s.name()).fetch();
			while(c.hasNext()) {
				lt.add(c.next());
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

	
}

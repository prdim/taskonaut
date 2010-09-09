/**
 * 
 */
package org.taskonaut.db;

import java.util.Collection;

import org.taskonaut.api.tasks.ITaskStoreService;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TimeLogItem;

/**
 * @author spec
 *
 */
public class TaskStoreService implements ITaskStoreService {

	/* (non-Javadoc)
	 * @see org.taskonaut.api.tasks.ITaskStoreService#readAllTasks()
	 */
	@Override
	public Collection<TaskItem> readAllTasks() {
		System.out.println("Test!!!");
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

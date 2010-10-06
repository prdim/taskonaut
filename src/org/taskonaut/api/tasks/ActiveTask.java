/**
 * 
 */
package org.taskonaut.api.tasks;

import org.taskonaut.util.FileUtils;

/**
 * Активная задача
 * 
 * @author spec
 *
 */
public class ActiveTask {
	private static ActiveTask me = null;
	private static String FILE_NAME = "active_task.xml"; 
	private long taskId = 0;
	private long startTime = 0;
	private long lastCheck = 0;
	
	public synchronized void start(long task_id) {
		taskId = task_id;
		startTime = System.currentTimeMillis();
		lastCheck = startTime;
	}
	
	public synchronized void stop() {
		long t = System.currentTimeMillis();
		if (taskId != 0) TaskStoreServiceConnector.getStore().addTime(startTime, t, taskId);
		taskId=0;
	}
	
	public synchronized void check() {
		if(taskId != 0) lastCheck = System.currentTimeMillis();
	}
	
	public synchronized void restore() {
		if (taskId != 0) TaskStoreServiceConnector.getStore().addTime(startTime, lastCheck, taskId);
		taskId=0;
	}
	
	public boolean isActive() {
		return taskId!=0;
	}
	
	public long getActiveTaskId() {
		return taskId;
	}
	
	public long getTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(long lastCheck) {
		this.lastCheck = lastCheck;
	}

	public static ActiveTask getInstance() {
		if(me==null) {
			try {
				me = (ActiveTask) FileUtils.XmlToBean(FILE_NAME);
				if(me.isActive()) {
					me.restore();
				}
			} catch (Exception e) {
				me = new ActiveTask();
			}
		}
		return me;
	}
	
	public synchronized void save() {
		try {
			FileUtils.beanToXML(me, FILE_NAME);
		} catch (Exception e) {
			System.err.println("Error saving task check " + e.getMessage());
		}
		System.out.println("Saved task check successfully");
	}
}

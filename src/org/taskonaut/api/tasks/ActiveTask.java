/*
 * Copyright 2010 Prolubnikov Dmitry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.taskonaut.api.tasks;

import org.taskonaut.api.GlobalConfig;
import org.taskonaut.util.FileUtils;

/**
 * Активная задача
 * 
 * @author Prolubnikov Dmitry
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
		if (taskId != 0 && (t-startTime) >= (GlobalConfig.getInstance().getMinTaskActive()*1000)) TaskStoreServiceConnector.getStore().addTime(startTime, t, taskId);
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

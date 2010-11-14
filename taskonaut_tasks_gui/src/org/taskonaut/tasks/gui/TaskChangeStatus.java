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

package org.taskonaut.tasks.gui;

import java.util.Dictionary;
import java.util.Hashtable;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.osgi.service.event.Event;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.tasks.gui.internal.Activator;

/**
 * Таск смены статуса задачи
 * @author Prolubnikov Dmitry
 *
 */
public class TaskChangeStatus extends Task<Void, Void> {
	TaskItem ts;
	String status;
	
	public TaskChangeStatus(Application app, TaskItem t, String newStatus) {
		super(app);
		ts = t;
		status = newStatus;
	}

	@Override
	protected Void doInBackground() throws Exception {
		try {
			// Контроль на существование такого статуса
			TaskItem.Status.valueOf(status);
		} catch (Exception e) {
			e.printStackTrace();
			setMessage("Ошибка при смене статуса задачи");
			return null;
		}
		ts.setState(status);
		TaskStoreServiceConnector.getStore().saveTask(ts);
		Dictionary<String, Object> p = new Hashtable<String, Object>();
		p.put("task_id", ts.getID());
		Activator.getEventAdmin().postEvent(
				new Event("org/taskonaut/tasks/gui/events/edit_task",	p));
		setMessage("Статус задачи изменен на " + status);
		return null;
	}
}
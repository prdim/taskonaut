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

import javax.swing.JOptionPane;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.osgi.service.event.Event;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.tasks.gui.internal.Activator;

/**
 * Таск удаления задачи
 * @author Prolubnikov Dmitry
 *
 */
public class TaskDelete extends Task<Void, Void> {
	private TaskItem ts = null;

	public TaskDelete(Application application) {
		super(application);
	}
	
	public TaskDelete(Application app, TaskItem t) {
		super(app);
		ts = t;
	}

	@Override
	protected Void doInBackground() throws Exception {
		if(ts==null) return null;
		if(JOptionPane.showConfirmDialog(null, "Удалить задачу \"" + ts.getName() + "\"?",
                "Предупреждение", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION) {
			setMessage("Операция отменена пользователем");
            return null;
		}
		// Нужно разорвать все ссылки на текущую задачу
		for(TaskItem t : TaskStoreServiceConnector.getStore().findChildren(ts.getID())) {
			t.setRelation_id(0);
			TaskStoreServiceConnector.getStore().saveTask(t);
		}
		TaskStoreServiceConnector.getStore().deleteAllTimeLog(ts.getID());
		TaskStoreServiceConnector.getStore().deleteTask(ts.getID());
		Activator.getEventAdmin().postEvent(
				new Event("org/taskonaut/tasks/gui/events/delete_task", 
						getProperties(ts.getID())));
		setMessage("Задача удалена");
		return null;
	}
	
	private Dictionary<String, Object> getProperties(Long id) {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put("task_id", id);
		return result;
	}
}
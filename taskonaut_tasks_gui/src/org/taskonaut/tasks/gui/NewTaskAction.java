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

import javax.swing.JFrame;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.osgi.service.event.Event;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.MainApplication;
import org.taskonaut.app.MainApplication.MessageTask;
import org.taskonaut.tasks.gui.internal.Activator;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class NewTaskAction implements IMenuAction {
	
	@Action
	public Task<Void,Void> newTaskAction() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				DefaultDialog d = new DefaultDialog(new JFrame(), true);
				EditTaskPanel p = new EditTaskPanel(TaskStoreServiceConnector.getStore().createNewTask(""), true);
				d.setPanel(p);
				d.setTitle("Создание новой задачи");
				d.setVisible(true);
				if(p.ok) {
					setMessage("Новая задача создана");
//					TaskList.getInstance().putTask(p.getTask());
					TaskStoreServiceConnector.getStore().saveTask(p.getTask());
					Activator.getEventAdmin().postEvent(
							new Event("org/taskonaut/tasks/gui/events/new_task", 
									getProperties(p.getTask().getID())));
					// TODO запись в БД
				} else {
					setMessage("Операция отменена пользователем");
				}
				return null;
			}
			
		};
		return t;
////		EditTaskDialog d = new EditTaskDialog(new JFrame(), true);
//		DefaultDialog d = new DefaultDialog(new JFrame(), true);
////		d.setTaskPanel(new EditTaskPanel(new OneTask()));
//		EditTaskPanel p = new EditTaskPanel(new OneTask());
//		d.setPanel(p);
//		d.setTitle("Создание новой задачи");
//		d.setVisible(true);
//		if(p.ok) {
//			return new MainApplication.MessageTask(MainApplication.getInstance(), "Новая задача создана");
//		}
//		return null;
	}
	
	private Dictionary<String, Object> getProperties(Long id) {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put("task_id", id);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return "newTaskAction";
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuAction#getMenu()
	 */
	@Override
	public String getMenuPath() {
		return "Задачи";
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuAction#getPriority()
	 */
	@Override
	public int getPriority() {
		return 10;
	}

}

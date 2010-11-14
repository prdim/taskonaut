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
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.app.MainApplication;

/**
	 * Task редактирования задачи
	 * @author Prolubnikov Dmitry
	 *
	 */
	public class TaskEdit extends Task<Void, Void> {
		private TaskItem ts;

		public TaskEdit(Application application) {
			super(application);
		}
		
		public TaskEdit(Application app, TaskItem t) {
			super(app);
			ts = t;
		}

		@Override
		protected Void doInBackground() throws Exception {
//			DefaultDialog d = new DefaultDialog(new JFrame(), true);
			EditTaskPanel p = new EditTaskPanel(ts, false);
			InternalFrameDialog d = new InternalFrameDialog(p);
			d.setTitle("Редактирование задачи " + ts.getName());
			((MainApplication)MainApplication.getInstance()).addInternalFrame(d);
//			d.setPanel(p);
//			d.setTitle("Редактирование задачи");
//			d.setVisible(true);
//			if(p.ok) {
//				setMessage("Задача изменена");
////				TaskList.getInstance().putTask(p.getTask());
//				TaskStoreServiceConnector.getStore().saveTask(p.getTask());
//				Activator.getEventAdmin().postEvent(
//						new Event("org/taskonaut/tasks/gui/events/edit_task", 
//								getProperties(p.getTask().getID())));
//				// TODO запись в БД
//			} else {
//				setMessage("Операция отменена пользователем");
//			}
			return null;
		}
		
		private Dictionary<String, Object> getProperties(Long id) {
			Dictionary<String, Object> result = new Hashtable<String, Object>();
			result.put("task_id", id);
			return result;
		}
	}
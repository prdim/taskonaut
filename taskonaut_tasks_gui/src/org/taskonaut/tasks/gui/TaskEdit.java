package org.taskonaut.tasks.gui;

import java.util.Dictionary;
import java.util.Hashtable;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.app.MainApplication;

/**
	 * Task редактирования задачи
	 * @author spec
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
/**
 * 
 */
package org.taskonaut.tasks.gui;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JFrame;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.osgi.service.event.Event;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.MainApplication;
import org.taskonaut.app.MainApplication.MessageTask;
import org.taskonaut.tasks.OneTask;
import org.taskonaut.tasks.TaskList;
import org.taskonaut.tasks.gui.internal.Activator;

/**
 * @author ProlubnikovDA
 *
 */
public class NewTaskAction implements IMenuAction {
	
	@Action
	public Task<Void,Void> newTaskAction() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				DefaultDialog d = new DefaultDialog(new JFrame(), true);
				EditTaskPanel p = new EditTaskPanel(new OneTask());
				d.setPanel(p);
				d.setTitle("Создание новой задачи");
				d.setVisible(true);
				if(p.ok) {
					setMessage("Новая задача создана");
					TaskList.getInstance().putTask(p.getTask());
					Activator.getEventAdmin().postEvent(
							new Event("org/taskonaut/tasks/gui/events/new_task", 
									getProperties(p.getTask().getId())));
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
	public String getMenu() {
		return "taskMenu";
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuAction#getPriority()
	 */
	@Override
	public int getPriority() {
		return 100;
	}

}

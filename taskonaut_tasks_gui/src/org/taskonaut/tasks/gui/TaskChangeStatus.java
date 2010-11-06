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
 * @author spec
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
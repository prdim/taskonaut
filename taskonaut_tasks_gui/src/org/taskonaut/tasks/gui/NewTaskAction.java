/**
 * 
 */
package org.taskonaut.tasks.gui;

import javax.swing.JFrame;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.MainApplication;
import org.taskonaut.app.MainApplication.MessageTask;
import org.taskonaut.tasks.OneTask;

/**
 * @author ProlubnikovDA
 *
 */
public class NewTaskAction implements IMenuAction {
	
	@Action
	public Task newTaskAction() {
//		EditTaskDialog d = new EditTaskDialog(new JFrame(), true);
		DefaultDialog d = new DefaultDialog(new JFrame(), true);
//		d.setTaskPanel(new EditTaskPanel(new OneTask()));
		EditTaskPanel p = new EditTaskPanel(new OneTask());
		d.setPanel(p);
		d.setTitle("Создание новой задачи");
		d.setVisible(true);
		if(p.ok) {
			return new MainApplication.MessageTask(MainApplication.getInstance(), "Новая задача создана");
		}
		return null;
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

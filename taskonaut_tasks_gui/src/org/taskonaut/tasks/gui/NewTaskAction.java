/**
 * 
 */
package org.taskonaut.tasks.gui;

import javax.swing.JFrame;

import org.jdesktop.application.Action;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.tasks.OneTask;

/**
 * @author ProlubnikovDA
 *
 */
public class NewTaskAction implements IMenuAction {
	
	@Action
	public void newTaskAction() {
		EditTaskDialog d = new EditTaskDialog(new JFrame(), true);
		d.setTaskPanel(new EditTaskPanel(new OneTask()));
		d.setVisible(true);
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

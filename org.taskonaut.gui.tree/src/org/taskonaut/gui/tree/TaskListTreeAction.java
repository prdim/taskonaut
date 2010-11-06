/**
 * 
 */
package org.taskonaut.gui.tree;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.MainApplication;
import org.taskonaut.tasks.gui.DefaultInternalFrame;

/**
 * @author ProlubnikovDA
 *
 */
public class TaskListTreeAction implements IMenuAction {
	
	@Action public Task<Void,Void> taskListTreeAction() {
		return new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				TaskListTreePanel p = new TaskListTreePanel();
				DefaultInternalFrame f = new DefaultInternalFrame(p);
				f.setTitle("Дерево задач");
				((MainApplication)MainApplication.getInstance()).addInternalFrame(f);
//				setMessage("!!!");
				return null;
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return "taskListTreeAction";
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
		return 30;
	}

}

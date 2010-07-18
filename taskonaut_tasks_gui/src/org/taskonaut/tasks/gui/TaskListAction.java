/**
 * 
 */
package org.taskonaut.tasks.gui;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.MainApplication;

/**
 * @author ProlubnikovDA
 *
 */
public class TaskListAction implements IMenuAction {
	
	@Action public Task<Void,Void> taskListAction() {
		return new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				TaskListPanel p = new TaskListPanel();
				DefaultInternalFrame f = new DefaultInternalFrame(p);
				f.setTitle("Список задач");
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
		return "taskListAction";
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
		return 110;
	}

}

/**
 * 
 */
package org.taskonaut.convert.actions;

import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.*;
import org.taskonaut.db.TaskDB;
import org.taskonaut.tasks.TaskList;
import org.taskonaut.tasks.TimeLogger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 * @author spec
 *
 */
public class ImportFromXml implements IMenuAction {

	@Action
	public Task<Void, Void> importXMLActon() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				TaskDB db = new TaskDB();
				db.openStorage();
				db.storeAllTasks(TaskList.getInstance().getTaskList().values());
				db.storeAllTimeLog(TimeLogger.getInstance().getList());
				db.closeStorage();
				System.out.println("...");
				return null;
			}
			
		};
		return t;
	}
	
	@Override
	public String getActionName() {
		return "importXMLActon";
	}

	@Override
	public String getMenu() {
		return "fileMenu";
	}

	@Override
	public int getPriority() {
		return 10;
	}

}

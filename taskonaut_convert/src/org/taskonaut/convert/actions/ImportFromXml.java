/**
 * 
 */
package org.taskonaut.convert.actions;

import java.util.Collection;
import java.util.List;

import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.*;
import org.taskonaut.tasks.OneTask;
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
				// TODO Переделать...
//				TaskDB db = new TaskDB();
//				db.openStorage();
//				db.storeAllTasks(TaskList.getInstance().getTaskList().values());
//				db.storeAllTimeLog(TimeLogger.getInstance().getList());
//				db.closeStorage();
				Collection<OneTask> t1 = TaskList.getInstance().getTaskList().values();
				TaskItem k = TaskStoreServiceConnector.getStore().createNewTask("");
				for(OneTask i : t1) {
					k.setComment(i.getComment());
					k.setExecute(i.getExecute().getTime());
					k.setID(i.getId());
					k.setName(i.getName());
					k.setOwner(i.getOwner());
					k.setPriority(i.getPriority().name());
					k.setRelation_id(i.getRelation_id());
					k.setState(i.getState().name());
					k.setType(i.getType().name());
					TaskStoreServiceConnector.getStore().saveTask(k);
				}
				List<org.taskonaut.tasks.TimeLogItem> t2 = TimeLogger.getInstance().getList();
				for(org.taskonaut.tasks.TimeLogItem i : t2) {
					org.taskonaut.api.tasks.TimeLogItem m = TaskStoreServiceConnector.getStore().addTime(i.getId(), i.getEnd(), i.getTaskId());
					m.setComment(i.getComment());
					TaskStoreServiceConnector.getStore().saveTimeLog(m);
				}
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

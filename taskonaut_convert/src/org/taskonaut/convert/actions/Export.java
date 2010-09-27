package org.taskonaut.convert.actions;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.MainApplication;
import org.taskonaut.convert.data.TaskForExport;
import com.google.gson.*;

/**
 * Экспорт данных в файл
 * @author spec
 *
 */
public class Export implements IMenuAction {
	
	@Action
	public Task<Void, Void> exportActon() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				List<TaskItem> ts = TaskStoreServiceConnector.getStore().readAllTasks();
				List<TaskForExport> tse = new ArrayList<TaskForExport>();
				for(TaskItem i : ts) {
					tse.add(new TaskForExport(i));
				}
				Gson gson = new Gson();
				System.out.println(gson.toJson(tse));
				return null;
			}
			
		};
		return t;
	}

	@Override
	public String getActionName() {
		return "exportActon";
	}

	@Override
	public String getMenu() {
		return "fileMenu";
	}

	@Override
	public int getPriority() {
		return 20;
	}

}

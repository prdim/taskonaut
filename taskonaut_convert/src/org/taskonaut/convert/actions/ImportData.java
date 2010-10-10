/**
 * 
 */
package org.taskonaut.convert.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.*;
import org.taskonaut.convert.data.TaskForExport;
import org.taskonaut.convert.data.TimeLogForExport;
import org.taskonaut.tasks.OneTask;
import org.taskonaut.tasks.TaskList;
import org.taskonaut.tasks.TimeLogger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author spec
 *
 */
public class ImportData implements IMenuAction {

	@Action
	public Task<Void, Void> importActon() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Переделать...
////				TaskDB db = new TaskDB();
////				db.openStorage();
////				db.storeAllTasks(TaskList.getInstance().getTaskList().values());
////				db.storeAllTimeLog(TimeLogger.getInstance().getList());
////				db.closeStorage();
//				Collection<OneTask> t1 = TaskList.getInstance().getTaskList().values();
//				TaskItem k = TaskStoreServiceConnector.getStore().createNewTask("");
//				for(OneTask i : t1) {
//					k.setComment(i.getComment());
//					k.setExecute(i.getExecute().getTime());
//					k.setID(i.getId());
//					k.setName(i.getName());
//					k.setOwner(i.getOwner());
//					k.setPriority(i.getPriority().name());
//					k.setRelation_id(i.getRelation_id());
//					k.setState(i.getState().name());
//					k.setType(i.getType().name());
//					TaskStoreServiceConnector.getStore().saveTask(k);
//				}
//				List<org.taskonaut.tasks.TimeLogItem> t2 = TimeLogger.getInstance().getList();
//				for(org.taskonaut.tasks.TimeLogItem i : t2) {
//					org.taskonaut.api.tasks.TimeLogItem m = TaskStoreServiceConnector.getStore().addTime(i.getId(), i.getEnd(), i.getTaskId());
//					m.setComment(i.getComment());
//					TaskStoreServiceConnector.getStore().saveTimeLog(m);
//				}
//				System.out.println("...");
				try {
					JFileChooser fc = new JFileChooser("./");
					fc.setDialogTitle("Выбор текстового файла для импорта");
					int r = fc.showOpenDialog(new JFrame());
					if (JFileChooser.CANCEL_OPTION == r || JFileChooser.ERROR_OPTION == r) return null;
					File f = fc.getSelectedFile();
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF8"));
					StringBuilder sb = new StringBuilder();
					while(br.ready()) {
						sb.append(br.readLine());
					}
					br.close();
					Type list = new TypeToken<List<TaskForExport>>() {}.getType();
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					List<TaskForExport> t = gson.fromJson(sb.toString(), list);
					for(TaskForExport i : t) {
						TaskStoreServiceConnector.getStore().saveTask(i);
						for(TimeLogForExport j : i.getTimes()) {
							TaskStoreServiceConnector.getStore().saveTimeLog(j);
						}
					}
					
					setMessage("Импорт успешно завершен, обработано задач: " + t.size());
				} catch (Exception e) {
					setMessage("Ошибка: " + e.getMessage());
					e.printStackTrace();
				}
				return null;
			}
			
		};
		return t;
	}
	
	@Override
	public String getActionName() {
		return "importActon";
	}

	@Override
	public String getMenuPath() {
		return "Файл|Конвертация";
	}

	@Override
	public int getPriority() {
		return 30;
	}

}

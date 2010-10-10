package org.taskonaut.convert.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.api.tasks.TimeLogItem;
import org.taskonaut.app.MainApplication;
import org.taskonaut.convert.data.TaskForExport;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Экспорт данных в файл
 * @author spec
 *
 */
public class ExportData implements IMenuAction {
	
	@Action
	public Task<Void, Void> exportActon() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					JFileChooser fc = new JFileChooser("./");
					fc.setDialogTitle("Имя текстового файла для экспорта");
					int r = fc.showSaveDialog(new JFrame());
					if (JFileChooser.CANCEL_OPTION == r || JFileChooser.ERROR_OPTION == r) return null;
					File f = fc.getSelectedFile();
					List<TaskItem> ts = TaskStoreServiceConnector.getStore().readAllTasks();
					List<TaskForExport> tse = new ArrayList<TaskForExport>();
					for(TaskItem i : ts) {
						TaskForExport te = new TaskForExport(i);
//					List<TimeLogItem> tt = TaskStoreServiceConnector.getStore().readTimeLogItems(i.getID());
						te.addTimes(TaskStoreServiceConnector.getStore().readTimeLogItems(i.getID()));
						tse.add(te);
					}
					Type list = new TypeToken<List<TaskForExport>>() {}.getType();
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					FileOutputStream fo = new FileOutputStream(f);
					BufferedWriter w = new BufferedWriter(new OutputStreamWriter(fo, "UTF8"));
					w.write(gson.toJson(tse, list));
					w.close();
					setMessage("Экспорт успешно завершен, обработано задач: " + tse.size());
				} catch (Exception e) {
					setMessage("Ошибка: " + e.getMessage());
					e.printStackTrace();
				}
//				System.out.println(gson.toJson(tse));
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
	public String getMenuPath() {
		return "Файл|Конвертация";
	}

	@Override
	public int getPriority() {
		return 20;
	}

}

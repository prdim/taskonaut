/*
 * Copyright 2010 Prolubnikov Dmitry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.taskonaut.convert.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.*;
import org.taskonaut.convert.data.TaskForExport;
import org.taskonaut.convert.data.TimeLogForExport;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class ImportData implements IMenuAction {

	@Action
	public Task<Void, Void> importActon() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
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
		return 20;
	}

}

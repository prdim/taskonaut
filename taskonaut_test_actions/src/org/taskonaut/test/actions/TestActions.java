/**
 * 
 */
package org.taskonaut.test.actions;

import java.io.FileNotFoundException;
import java.util.List;

import org.jdesktop.application.Action;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskFactory;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.*;
import org.taskonaut.util.FileUtils;

/**
 * @author spec
 *
 */
public class TestActions implements IMenuAction {
	
	@Action
	public void testAction1() throws FileNotFoundException {
//		List<TaskItem> i = TaskStoreServiceConnector.getStore().readAllTasks();
//		for(TaskItem t : i) {
//			System.out.println(t.getName());
//		}
//		System.out.println(TaskStoreServiceConnector.getStore().readAllTimeLogItems().size());
		GuiConfig.getInstance().setHideMinimized(true);
		GuiConfig.getInstance().save();
	}

	@Override
	public String getActionName() {
		return "testAction1";
	}

	@Override
	public String getMenuPath() {
		return "Задачи|Тест|Вложенное меню|Еще немного...|Глубже";
	}

	@Override
	public int getPriority() {
		return 100;
	}
	
	
}

/**
 * 
 */
package org.taskonaut.test.actions;

import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.Action;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.test.actions.internal.Activator;

/**
 * @author spec
 *
 */
public class TestActions implements IMenuAction {
	
	@Action
	public void testAction1() throws FileNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		List<TaskItem> i = TaskStoreServiceConnector.getStore().readAllTasks();
//		for(TaskItem t : i) {
//			System.out.println(t.getName());
//		}
//		System.out.println(TaskStoreServiceConnector.getStore().readAllTimeLogItems().size());

//		GuiConfig.getInstance().setHideMinimized(true);
//		GuiConfig.getInstance().save();
		
		System.out.println(Activator.getProps().getTitle());
//		PropertyUtils.setDebug(1);
		PropertyDescriptor[] pr = PropertyUtils.getPropertyDescriptors(Activator.getProps());
		for(PropertyDescriptor p : pr) {
			System.out.println(p.getDisplayName() + " : " + PropertyUtils.getProperty(Activator.getProps(), p.getName()));
		}
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

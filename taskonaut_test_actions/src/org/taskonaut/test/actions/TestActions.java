/**
 * 
 */
package org.taskonaut.test.actions;

import java.io.FileNotFoundException;

import org.jdesktop.application.Action;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskFactory;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.app.*;
import org.taskonaut.util.FileUtils;

/**
 * @author spec
 *
 */
public class TestActions implements IMenuAction {
	
	@Action
	public void testAction1() throws FileNotFoundException {
		System.out.println("Test action!");
		TaskItem t = TaskFactory.createNewTask("test task!!!");
		t.setPriority(TaskItem.Priority.критично.name());
		FileUtils.beanToXML(t, "./test.xml");
	}

	@Override
	public String getActionName() {
		return "testAction1";
	}

	@Override
	public String getMenu() {
		return "taskMenu";
	}

	@Override
	public int getPriority() {
		return 110;
	}
	
	
}
